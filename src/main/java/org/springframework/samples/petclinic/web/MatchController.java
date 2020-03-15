
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.Matches;
import org.springframework.samples.petclinic.model.Enum.MatchStatus;
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

@Controller
public class MatchController {

	private static final String	VIEWS_MATCH_LIST		= "matches/matchList";

	private static final String	VIEWS_UPDATE_MATCH_FORM	= "matches/updateMatchForm";

	private final MatchService	matchService;


	@Autowired
	public MatchController(final MatchService matchService, final UserService userService) {
		this.matchService = matchService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/matches/list")
	public String showMatchList(final Map<String, Object> model) {

		Matches matches = new Matches();

		matches.getMatchesList().addAll(this.matchService.findAllMatchRequests());

		model.put("matches", matches);

		return MatchController.VIEWS_MATCH_LIST;
	}

	@GetMapping(value = "/matches/edit/{matchId}")
	public String initUpdateMatchForm(@PathVariable("matchId") final int matchId, final Model model) {
		Match match = this.matchService.findMatchById(matchId);

		List<MatchStatus> status = new ArrayList<>();

		status.add(MatchStatus.TO_BE_PLAYED);
		status.add(MatchStatus.ONGOING);
		status.add(MatchStatus.FINISHED);

		model.addAttribute("status", status);
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
			match.setMatchDate(match1.getMatchDate());
			match.setStadium(match1.getStadium());
			match.setFootballClub1(match1.getFootballClub1());
			match.setFootballClub2(match1.getFootballClub2());
			match.setReferee(match1.getReferee());

			this.matchService.saveMatch(match);

			return "redirect:/matches/list";
		}
	}

	@GetMapping(value = "/matches/referee/list")
	public String showReceivedMatchRequestList(final Map<String, Object> model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Matches matches = new Matches();

		matches.getMatchesList().addAll(this.matchService.findAllMatchRequestsByReferee(currentPrincipalName));

		model.put("matches", matches);

		return MatchController.VIEWS_MATCH_LIST;
	}

}
