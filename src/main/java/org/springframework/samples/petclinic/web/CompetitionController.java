
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.CredentialException;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Calendary;
import org.springframework.samples.petclinic.model.Competition;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.FootballPlayerMatchStatistic;
import org.springframework.samples.petclinic.model.Jornada;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.MatchRecord;
import org.springframework.samples.petclinic.model.Enum.CompetitionType;
import org.springframework.samples.petclinic.model.Enum.MatchRecordStatus;
import org.springframework.samples.petclinic.model.Enum.MatchStatus;
import org.springframework.samples.petclinic.service.CalendaryService;
import org.springframework.samples.petclinic.service.CompetitionService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.FootballPlayerMatchStatisticService;
import org.springframework.samples.petclinic.service.FootballPlayerService;
import org.springframework.samples.petclinic.service.JornadaService;
import org.springframework.samples.petclinic.service.MatchRecordService;
import org.springframework.samples.petclinic.service.MatchService;
import org.springframework.samples.petclinic.service.RefereeService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.samples.petclinic.service.exceptions.IllegalDateException;
import org.springframework.samples.petclinic.service.exceptions.MatchRecordResultException;
import org.springframework.samples.petclinic.service.exceptions.NotEnoughMoneyException;
import org.springframework.samples.petclinic.service.exceptions.StatusException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CompetitionController {

	private static final String							VIEWS_COMPETITION_CREATE_OR_UPDATE_FORM	= "competitions/createOrUpdateCompetitionForm";

	@Autowired
	private final CompetitionService					competitionService;

	@Autowired
	private final FootballClubService					footballClubService;

	@Autowired
	private final FootballPlayerService					footballPlayerService;

	@Autowired
	private final RefereeService						refereeService;

	@Autowired
	private final MatchService							matchService;

	@Autowired
	private final FootballPlayerMatchStatisticService	footballPlayerMatchStatisticService;

	@Autowired
	private final MatchRecordService					matchRecordService;

	@Autowired
	private final JornadaService						jornadaService;

	@Autowired
	private final CalendaryService						calendaryService;


	@Autowired
	public CompetitionController(final CompetitionService competitionService, final CalendaryService calendaryService, final JornadaService jornadaService, final FootballPlayerService footballPlayerService, final MatchRecordService matchRecordService,
		final FootballPlayerMatchStatisticService footballPlayerMatchStatisticService, final MatchService matchService, final FootballClubService footballClubService, final RefereeService refereeService) {
		this.competitionService = competitionService;
		this.footballClubService = footballClubService;
		this.footballPlayerService = footballPlayerService;
		this.refereeService = refereeService;
		this.matchService = matchService;
		this.footballPlayerMatchStatisticService = footballPlayerMatchStatisticService;
		this.matchRecordService = matchRecordService;
		this.jornadaService = jornadaService;
		this.calendaryService = calendaryService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@ModelAttribute("types")
	public List<CompetitionType> populateTypes() {
		List<CompetitionType> types = new ArrayList<>();
		types.add(CompetitionType.LEAGUE);
		types.add(CompetitionType.PLAYOFFS);
		return types;
	}

	@ModelAttribute("status")
	public List<Boolean> populateStatus() {
		List<Boolean> status = new ArrayList<>();
		status.add(true);
		status.add(false);
		return status;
	}

	@GetMapping("/competitions/{competitionId}") //VISTA DETALLADA DE COMPETICIÓN
	public ModelAndView showCompetition(@PathVariable("competitionId") final int competitionId) throws CredentialException {

		Competition competition = this.competitionService.findCompetitionById(competitionId);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		ModelAndView mav = new ModelAndView("competitions/competitionDetails");
		mav.addObject("competition", competition);
		mav.addObject("size", competition.getClubs().size());

		//Si no está publicada y no eres el creador no puedes verla
		if (competition.getStatus() == false && !competition.getCreator().equals(currentPrincipalName)) { //SEGURIDAD
			throw new CredentialException();
		}

		return mav;
	}

	@GetMapping("/competitions/{competitionId}/calendary") //VISTA DETALLADA DE CALENDARIO
	public ModelAndView showCalendary(@PathVariable("competitionId") final int competitionId) {

		ModelAndView mav = new ModelAndView("competitions/calendaryDetails");
		mav.addObject(this.calendaryService.findCalendaryByCompetitionId(competitionId));
		mav.addObject("jornadas", this.jornadaService.findAllJornadasFromCompetitionId(competitionId));

		return mav;
	}

	@GetMapping("/competitions/{competitionId}/calendary/jornada/{jornadaId}/match/{matchId}") //VISTA DETALLADA DE PARTIDO
	public ModelAndView showMatch(@PathVariable("matchId") final int matchId) throws CredentialException {

		Match match = this.matchService.findMatchById(matchId);

		ModelAndView mav = new ModelAndView("competitions/matchDetails");

		mav.addObject(match);

		return mav;
	}

	@GetMapping("/competitions/{competitionId}/calendary/jornada/{jornadaId}") //VISTA DETALLADA DE JORNADA
	public ModelAndView showJornada(@PathVariable("jornadaId") final int jornadaId) {

		ModelAndView mav = new ModelAndView("competitions/jornadasDetails");
		mav.addObject(this.jornadaService.findJornadaById(jornadaId));
		mav.addObject("partidos", this.competitionService.findAllMatchByJornadaId(jornadaId));
		return mav;
	}

	@GetMapping(value = "/competitions/list") //LISTA DE COMPETICIONES PUBLICADAS
	public String showCompetitionList(final Map<String, Object> model) {

		List<Competition> competitions = new ArrayList<>();
		competitions.addAll(this.competitionService.findAllPublishedCompetitions());

		model.put("competitions", competitions);

		return "competitions/competitionList";
	}

	@GetMapping(value = "/competition/mylist") //LISTA DE MIS COMPETICIONES
	public String showMyCompetitionList(final Map<String, Object> model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		List<Competition> competitions = new ArrayList<>();
		competitions.addAll(this.competitionService.findMyCompetitions(currentPrincipalName));

		model.put("competitions", competitions);

		return "competitions/competitionList";
	}

	@GetMapping(value = "/competition/new") //CREAR COMPETICIÓN - GET
	public String initCreationForm(final Map<String, Object> model) {

		Competition competition = new Competition();
		model.put("competition", competition);
		model.put("isNew", true);
		return CompetitionController.VIEWS_COMPETITION_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/competition/new") //CREAR COMPETICIÓN - POST
	public String processCreationForm(@Valid final Competition competition, final BindingResult result, final Model model) throws DataAccessException, StatusException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		if (result.hasErrors()) {
			model.addAttribute("competition", competition);
			model.addAttribute("isNew", true);
			return CompetitionController.VIEWS_COMPETITION_CREATE_OR_UPDATE_FORM;
		} else {

			try {
				competition.setStatus(false);
				competition.setCreator(currentPrincipalName);
				this.competitionService.saveCompetition(competition);
			} catch (DuplicatedNameException e) {
				result.rejectValue("name", "duplicate", "already exists");
				return CompetitionController.VIEWS_COMPETITION_CREATE_OR_UPDATE_FORM;
			} catch (NotEnoughMoneyException e) {
				result.rejectValue("reward", "code.validator.competition.money", "Cantidad de recompensa mínima: 5.000.000 €");
				return CompetitionController.VIEWS_COMPETITION_CREATE_OR_UPDATE_FORM;
			}
		}
		return "redirect:/competitions/" + competition.getId();
	}

	@GetMapping(value = "/competition/{competitionId}/edit") //EDITAR COMPETITION - GET
	public String initUpdateCompetitionForm(@PathVariable("competitionId") final int compId, final Map<String, Object> model) throws CredentialException {

		Competition comp = this.competitionService.findCompetitionById(compId);

		model.put("competition", comp);
		model.put("isEditing", true);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		if (!currentPrincipalName.equals(comp.getCreator()) || comp.getStatus() == true) { //SEGURIDAD
			throw new CredentialException("Forbidden Access");
		}

		return CompetitionController.VIEWS_COMPETITION_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/competition/{competitionId}/edit") //EDITAR COMPETICION - POST
	public String processUpdateFootballClubForm(@Valid final Competition competition, final BindingResult result, @PathVariable("competitionId") final Integer compId, final Model model) throws DataAccessException, CredentialException, StatusException {

		model.addAttribute("isEditing", true);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Competition compToUpdate = this.competitionService.findCompetitionById(compId);

		if (!currentPrincipalName.equals(compToUpdate.getCreator())) { //SEGURIDAD
			throw new CredentialException("Forbidden Access");
		}

		if (result.hasErrors()) {
			return CompetitionController.VIEWS_COMPETITION_CREATE_OR_UPDATE_FORM;
		} else {

			BeanUtils.copyProperties(competition, compToUpdate, "id", "creator", "clubs", "status");

			try {

				this.competitionService.saveCompetition(compToUpdate);
			} catch (NotEnoughMoneyException e) {
				result.rejectValue("reward", "code.validator.competition.money", "Cantidad de recompensa mínima: 5.000.000 €");
				return CompetitionController.VIEWS_COMPETITION_CREATE_OR_UPDATE_FORM;
			} catch (DuplicatedNameException e) {
				result.rejectValue("name", "duplicate", "already exists");
				return CompetitionController.VIEWS_COMPETITION_CREATE_OR_UPDATE_FORM;
			}

			return "redirect:/competitions/" + compId;

		}

	}

	@GetMapping("/competition/{competitionId}/addClubs") //AÑADIR EQUIPOS A COMPETICIÓN
	public ModelAndView showClubs(@PathVariable("competitionId") final int competitionId) throws CredentialException {

		Collection<String> allclubsName = this.competitionService.findClubsById(competitionId);

		Competition thisComp = this.competitionService.findCompetitionById(competitionId);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		//Si no está publicada y no eres el creador no puedes verlo
		if (thisComp.getStatus() == true || !thisComp.getCreator().equals(currentPrincipalName)) { //SEGURIDAD
			throw new CredentialException();
		}

		ModelAndView mav = new ModelAndView("competitions/listClubs");
		mav.addObject("clubsName", allclubsName);
		mav.addObject("isAdd", true);
		mav.addObject("size", allclubsName.size());
		mav.addObject(thisComp);

		return mav;
	}
	@PostMapping("/competition/{competitionId}/addClubs") //AÑADIR EQUIPOS A COMPETICIÓN
	public ModelAndView addClub(@PathVariable("competitionId") final int competitionId, @ModelAttribute("clubs") final String club) throws DataAccessException, DuplicatedNameException, CredentialException {

		Competition thisComp = this.competitionService.findCompetitionById(competitionId);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		if (!thisComp.getCreator().equals(currentPrincipalName)) { //SEGURIDAD
			throw new CredentialException();
		}

		List<String> newClubs = thisComp.getClubs();
		newClubs.add(club);
		thisComp.setClubs(newClubs);

		try {
			this.competitionService.saveCompetition(thisComp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ModelAndView mav = new ModelAndView("redirect:/competitions/" + competitionId);

		return mav;
	}

	@GetMapping("/competitions/{competitionId}/clubs") //VER EQUIPOS DE LA COMPETICIÓN
	public ModelAndView showClubsMycomp(@PathVariable("competitionId") final int competitionId) throws CredentialException {

		Competition thisComp = this.competitionService.findCompetitionById(competitionId);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		//Si no está publicada y no eres el creador no puedes verlo
		if (thisComp.getStatus() == false && !thisComp.getCreator().equals(currentPrincipalName)) { //SEGURIDAD
			throw new CredentialException();
		}

		ModelAndView mav = new ModelAndView("competitions/listClubs");
		mav.addObject("clubsName", thisComp.getClubs());
		mav.addObject("size", thisComp.getClubs().size());
		mav.addObject("isAdd", false);
		mav.addObject(thisComp);

		return mav;
	}

	@PostMapping("/competitions/{competitionId}/clubs") //BORRAR EQUIPOS A COMPETICIÓN
	public ModelAndView deleteClub(@PathVariable("competitionId") final int competitionId, @ModelAttribute("clubs") final String club) throws DataAccessException, DuplicatedNameException, NotEnoughMoneyException, StatusException, CredentialException {

		Competition thisComp = this.competitionService.findCompetitionById(competitionId);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		if (!thisComp.getCreator().equals(currentPrincipalName)) { //SEGURIDAD
			throw new CredentialException();
		}

		List<String> newClubs = thisComp.getClubs();
		newClubs.remove(club);
		thisComp.setClubs(newClubs);

		this.competitionService.saveCompetition(thisComp);

		ModelAndView mav = new ModelAndView("redirect:/competitions/" + competitionId);

		return mav;
	}

	@RequestMapping(value = "/competition/{competitionId}/publish") //PUBLICAR COMPETITION
	public String initPublishCompetitionForm(@PathVariable("competitionId") final int compId, final Model model)
		throws CredentialException, DataAccessException, DuplicatedNameException, NotEnoughMoneyException, StatusException, IllegalDateException, MatchRecordResultException {

		Competition comp = this.competitionService.findCompetitionById(compId);

		model.addAttribute("competition", comp);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		if (!currentPrincipalName.equals(comp.getCreator()) || comp.getStatus() == true) { //SEGURIDAD
			throw new CredentialException("Forbidden Access");
		}

		if (comp.getType().equals(CompetitionType.LEAGUE)) {

			try {
				comp.setStatus(true);
				this.competitionService.saveCompetition(comp);
			} catch (StatusException e) {
				model.addAttribute("statusError", true);
				model.addAttribute("competition", comp);
				model.addAttribute("size", comp.getClubs().size());
				return "/competitions/competitionDetails";
			}

			List<String> equipos = comp.getClubs();
			List<String> equipos2 = comp.getClubs();
			Collections.reverse(equipos2);

			Date fechaPartido = new Date(System.currentTimeMillis() - 1);

			//Creamos el calendario

			Calendary calendary = new Calendary();
			calendary.setCompetition(comp);
			this.calendaryService.saveCalendary(calendary);

			//Creamos las jornadas

			Integer N = equipos.size();

			for (int i = 0; i < N * 2 - 2; i++) {

				int numero = i + 1;
				Jornada j = new Jornada();
				j.setCalendary(calendary);
				j.setName("Jornada " + numero);
				this.jornadaService.saveJornada(j);
				;
			}

			Collection<Jornada> jornadas = this.jornadaService.findAllJornadasFromCompetitionId(compId);

			for (Jornada a : jornadas) {

				int contador = 1;
				int i = 0;
				int j = equipos.size() - 1;

				FootballClub club = new FootballClub();
				FootballClub club2 = new FootballClub();

				while (contador <= equipos.size() / 2) {

					club = this.footballClubService.findFootballClubByName(equipos.get(i));
					club2 = this.footballClubService.findFootballClubByName(equipos.get(j));

					Match newMatch = new Match();

					Calendar cal = Calendar.getInstance();
					cal.setTime(fechaPartido);
					cal.add(Calendar.HOUR, 2);
					fechaPartido = cal.getTime();

					newMatch.setCreator(currentPrincipalName);
					newMatch.setFootballClub1(club);
					newMatch.setFootballClub2(club2);
					newMatch.setMatchDate(fechaPartido);
					newMatch.setMatchStatus(MatchStatus.TO_BE_PLAYED);
					newMatch.setStadium(club.getStadium());
					newMatch.setTitle("Partido de Liga");
					newMatch.setReferee(this.refereeService.findRefereeById(1));
					newMatch.setJornada(a);

					this.matchService.saveMatch(newMatch);

					MatchRecord newRecord = new MatchRecord();

					newRecord.setMatch(newMatch);
					newRecord.setSeason_start("2020");
					newRecord.setSeason_end("2021");
					newRecord.setTitle("Acta del partido: " + club.getName() + " - " + club2.getName() + " de la " + a.getName());
					newRecord.setStatus(MatchRecordStatus.NOT_PUBLISHED);

					this.matchRecordService.saveMatchRecord(newRecord);

					// Añadimos los jugadores al acta

					List<FootballPlayer> fps = new ArrayList<>();

					fps.addAll(this.footballPlayerService.findAllClubFootballPlayers(newMatch.getFootballClub1().getId()));
					fps.addAll(this.footballPlayerService.findAllClubFootballPlayers(newMatch.getFootballClub2().getId()));

					for (FootballPlayer fp : fps) {
						FootballPlayerMatchStatistic fpms = new FootballPlayerMatchStatistic();

						fpms.setAssists(0);
						fpms.setGoals(0);
						fpms.setReceived_goals(0);
						fpms.setRed_cards(0);
						fpms.setYellow_cards(0);

						fpms.setMatchRecord(newRecord);
						fpms.setPlayer(fp);

						this.footballPlayerMatchStatisticService.saveFootballPlayerStatistic(fpms);
					}

					contador++;
					i++;
					j--;

				}

				Collections.rotate(equipos, 1);

				Calendar cal = Calendar.getInstance();
				cal.setTime(fechaPartido);

				cal.add(Calendar.HOUR, 44);
				fechaPartido = cal.getTime();

			}
		} else if (comp.getType().equals(CompetitionType.PLAYOFFS)) {
			try {
				comp.setStatus(true);
				this.competitionService.saveCompetition(comp);
			} catch (StatusException e) {
				model.addAttribute("statusErrorPO", true);
				model.addAttribute("competition", comp);
				model.addAttribute("size", comp.getClubs().size());
				return "/competitions/competitionDetails";
			}
			this.competitionService.createRounds(comp, true);
		}

		this.competitionService.saveCompetition(comp);

		return "redirect:/competitions/" + compId;
	}

	@RequestMapping(value = "/competition/{competitionId}/delete") //BORRAR COMPETICION
	public String processDeleteForm(@PathVariable("competitionId") final int competitionId, final Model model) throws DataAccessException, CredentialException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Competition thisComp = this.competitionService.findCompetitionById(competitionId);

		if (!thisComp.getCreator().equals(currentPrincipalName)) { //SEGURIDAD
			throw new CredentialException();
		}

		try {
			this.competitionService.deleteCompetition(thisComp);
		} catch (StatusException e) {
			model.addAttribute("statusError2", true);
			model.addAttribute("competition", thisComp);
			model.addAttribute("size", thisComp.getClubs().size());
			return "/competitions/competitionDetails";
		}

		return "redirect:/";
	}

	@GetMapping(value = "/competitions/{competitionId}/statistics") //LISTA DE MIS COMPETICIONES
	public String showCompetitionStats(@PathVariable("competitionId") final int competitionId, final Map<String, Object> model) {

		Competition comp = this.competitionService.findCompetitionById(competitionId);

		List<FootballPlayer> players = new ArrayList<>();

		List<Jornada> jornadas = new ArrayList<>();

		jornadas.addAll(this.jornadaService.findAllJornadasFromCompetitionId(competitionId));

		for (String a : comp.getClubs()) {

			FootballClub c = this.footballClubService.findFootballClubByName(a);

			players.addAll(this.footballPlayerService.findAllClubFootballPlayers(c.getId()));

		}

		//Lista de Goles

		List<Integer> goles = new ArrayList<Integer>();

		//Query de jugadorID y CompId

		for (FootballPlayer a : players) {

			Integer goals = 0;

			for (FootballPlayerMatchStatistic f : this.competitionService.findFPMSByPlayerIdAndCompId(a.getId(), comp.getId())) {

				goals = goals + f.getGoals();

			}

			goles.add(goals);

		}

		//Lista de Tarjetas AMARILLAS

		//Lista de Tarjetas ROJAS

		//LIsta de Asistencias

		//Lista de Goles Recibidos

		model.put("Players", players);
		model.put("Goals", goles);

		System.out.print(goles);

		return "competitions/competitionStats";
	}

}
