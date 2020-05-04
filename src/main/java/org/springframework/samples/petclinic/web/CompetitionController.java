
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.security.auth.login.CredentialException;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Competition;
import org.springframework.samples.petclinic.model.Enum.CompetitionType;
import org.springframework.samples.petclinic.service.CompetitionService;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CompetitionController {

	private static final String			VIEWS_COMPETITION_CREATE_OR_UPDATE_FORM	= "competitions/createOrUpdateCompetitionForm";

	@Autowired
	private final CompetitionService	competitionService;


	@Autowired
	public CompetitionController(final CompetitionService competitionService) {
		this.competitionService = competitionService;
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
	public ModelAndView showCompetition(@PathVariable("competitionId") final int competitionId) {

		ModelAndView mav = new ModelAndView("competitions/competitionDetails");
		mav.addObject(this.competitionService.findCompetitionById(competitionId));

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
	public String processCreationForm(@Valid final Competition competition, final BindingResult result, final Model model) throws DataAccessException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		if (result.hasErrors()) {
			model.addAttribute("competition", competition);
			model.addAttribute("isNew", true);
			return CompetitionController.VIEWS_COMPETITION_CREATE_OR_UPDATE_FORM;
		} else {

			competition.setStatus(false);
			competition.setCreator(currentPrincipalName);
			this.competitionService.saveCompetition(competition);
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

	@PostMapping(value = "/competition/{competitionId}/edit") //EDITAR CLUB - POST
	public String processUpdateFootballClubForm(@Valid final Competition competition, final BindingResult result, @PathVariable("competitionId") final Integer compId, final Model model) throws DataAccessException, CredentialException {

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

			BeanUtils.copyProperties(competition, compToUpdate, "id", "creator");

			this.competitionService.saveCompetition(compToUpdate);

			return "redirect:/competitions/" + compId;

		}
	}

	@GetMapping("/competitions/{competitionId}/footballClubs") //AÑADIR EQUIPOS A COMPETICIÓN
	public ModelAndView showClubs(@PathVariable("competitionId") final int competitionId) {

		Collection<String> allclubsName = this.competitionService.findAllPublishedClubs().stream().map(x -> x.getName()).collect(Collectors.toList());

		Competition thisComp = this.competitionService.findCompetitionById(competitionId);

		Collection<String> thisclubsName = thisComp.getClubs();

		for (String a : thisclubsName) {
			allclubsName.remove(a);
		}

		ModelAndView mav = new ModelAndView("competitions/listClubs");
		mav.addObject("clubsName", allclubsName);
		mav.addObject("size", allclubsName.size());
		mav.addObject(thisComp);

		return mav;
	}
}
