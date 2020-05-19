
package org.springframework.samples.petclinic.web;

import java.util.Collections;
import java.util.List;

import javax.security.auth.login.CredentialException;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.Round;
import org.springframework.samples.petclinic.service.CompetitionService;
import org.springframework.samples.petclinic.service.MatchService;
import org.springframework.samples.petclinic.service.RoundService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RoundController {

	private static final String			VIEWS_ROUND_LIST	= "round/roundList";
	private static final String			VIEWS_ROUND_DETAILS	= "round/roundDetails";

	private final RoundService			roundService;

	private final CompetitionService	competitionService;

	private final MatchService			matchService;


	@Autowired
	public RoundController(final RoundService roundService, final CompetitionService competitionService, final MatchService matchService) {
		this.roundService = roundService;
		this.competitionService = competitionService;
		this.matchService = matchService;

	}

	@GetMapping("/competitions/{competitionId}/rounds") //VISTA DETALLADA DE CALENDARIO
	public ModelAndView showRounds(@PathVariable("competitionId") final int competitionId) {

		ModelAndView mav = new ModelAndView(RoundController.VIEWS_ROUND_LIST);

		List<Round> rs = this.roundService.findByCompetitionId(competitionId);
		Collections.reverse(rs);
		mav.addObject("rounds", rs);
		mav.addObject("competition", this.competitionService.findCompetitionById(competitionId));
		return mav;
	}
	@GetMapping("/competitions/{competitionId}/round/{roundId}") //VISTA DETALLADA DE CALENDARIO
	public ModelAndView showRound(@PathVariable("competitionId") final int competitionId, @PathVariable("roundId") final int roundId) {

		ModelAndView mav = new ModelAndView(RoundController.VIEWS_ROUND_DETAILS);

		mav.addObject("round", this.roundService.findById(roundId));
		mav.addObject("roundMatches", this.matchService.findMatchByRoundId(roundId));
		return mav;
	}
	@GetMapping("/competitions/{competitionId}/round/{roundId}/match/{matchId}") //VISTA DETALLADA DE PARTIDO
	public ModelAndView showMatch(@PathVariable("matchId") final int matchId) throws CredentialException {

		Match match = this.matchService.findMatchById(matchId);

		//Para modificar fecha y hora del partido
		//		Calendar c = Calendar.getInstance();
		//		c.setTime(match.getMatchDate());
		//		String matchDay = c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH);
		//		String matchHour = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
		ModelAndView mav = new ModelAndView("competitions/matchDetails");

		//		mav.addObject("matchDay", matchDay);
		//		mav.addObject("matchHour", matchHour);
		mav.addObject(match);

		return mav;
	}
	@PostMapping("/competitions/{competitionId}/round/{roundId}/match/{matchId}") //VISTA DETALLADA DE PARTIDO
	public ModelAndView editMatch(@PathVariable("matchId") final int matchId, @PathParam("match.matchDate") final String matchDate) throws CredentialException {

		Match match = this.matchService.findMatchById(matchId);

		ModelAndView mav = new ModelAndView("competitions/matchDetails");

		mav.addObject(match);

		return mav;
	}

}
