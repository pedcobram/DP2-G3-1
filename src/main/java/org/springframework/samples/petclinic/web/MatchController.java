
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.CredentialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.Enum.MatchStatus;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.MatchService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MatchController {

	private static final String			VIEWS_MATCH_LIST		= "matches/matchList";

	private static final String			VIEWS_UPDATE_MATCH_FORM	= "matches/updateMatchForm";

	private final MatchService			matchService;

	@Autowired
	private final FootballClubService	footballClubService;


	@Autowired
	public MatchController(final MatchService matchService, final FootballClubService footballClubService, final UserService userService) {
		this.matchService = matchService;
		this.footballClubService = footballClubService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/matches/list")
	public String showMatchList(final Map<String, Object> model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		if (footballClub == null) {
			return "footballClubs/myClubEmpty";
		}

		List<Match> matches = new ArrayList<>();
		matches.addAll(this.matchService.findAllMyMatches(currentPrincipalName));
		model.put("matches", matches);

		return MatchController.VIEWS_MATCH_LIST;
	}

	@GetMapping("/matches/{matchId}")
	public ModelAndView showMatch(@PathVariable("matchId") final int matchId) throws CredentialException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Match match = this.matchService.findMatchById(matchId);
		FootballClub myClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		//Si no se es de uno de los dos equipos = acceso prohibido
		if (!match.getFootballClub1().equals(myClub) && !match.getFootballClub2().equals(myClub)) {
			throw new CredentialException("Forbidden Access");
		}

		//Mandar el nombre del creador a la vista para el botón
		ModelAndView mav = new ModelAndView("matches/matchDetails");

		mav.addObject(match);
		mav.addObject("creatorName", match.getCreator());

		if (!match.getMatchStatus().equals(MatchStatus.FINISHED)) {
			mav.addObject("matchIsNotFinished", true);
		}

		return mav;
	}

	@GetMapping(value = "/matches/edit/{matchId}")
	public String initUpdateMatchForm(@PathVariable("matchId") final int matchId, final Model model) throws CredentialException {
		Match match = this.matchService.findMatchById(matchId);

		//Obtenemos el username actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		//Validación: Si no es el creador no puede editarlo
		if (!match.getCreator().equals(currentPrincipalName) || match.getMatchStatus().equals(MatchStatus.FINISHED)) {
			throw new CredentialException("Forbidden Access");
		}

		List<String> stadiums = new ArrayList<String>();
		stadiums.add(match.getFootballClub1().getStadium());
		stadiums.add(match.getFootballClub2().getStadium());
		model.addAttribute("stadiums", stadiums);

		model.addAttribute(match);

		return MatchController.VIEWS_UPDATE_MATCH_FORM;
	}

	@PostMapping(value = "/matches/edit/{matchId}")
	public String processUpdateMatchForm(@Valid final Match match, @PathVariable("matchId") final int matchId, final BindingResult result) {

		if (result.hasErrors()) {
			return MatchController.VIEWS_UPDATE_MATCH_FORM;
		} else {

			Match match1 = this.matchService.findMatchById(matchId);

			match.setId(match1.getId());
			match.setFootballClub1(match1.getFootballClub1());
			match.setFootballClub2(match1.getFootballClub2());
			match.setReferee(match1.getReferee());
			match.setCreator(match1.getCreator());

			this.matchService.saveMatch(match);

			return "redirect:/matches/list";
		}
	}

	@GetMapping(value = "/matches/referee/list")
	public String showReceivedMatchRequestList(final Map<String, Object> model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		List<Match> matches = new ArrayList<>();

		matches.addAll(this.matchService.findAllMatchesByReferee(currentPrincipalName));

		model.put("matches", matches);

		return MatchController.VIEWS_MATCH_LIST;
	}

}
