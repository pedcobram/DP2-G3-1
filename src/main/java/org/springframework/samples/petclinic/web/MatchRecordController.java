
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.FootballPlayerMatchStatistic;
import org.springframework.samples.petclinic.model.FootballPlayerMatchStatistics;
import org.springframework.samples.petclinic.model.FootballPlayerStatistic;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.MatchRecord;
import org.springframework.samples.petclinic.model.Enum.MatchRecordStatus;
import org.springframework.samples.petclinic.service.FootballPlayerMatchStatisticService;
import org.springframework.samples.petclinic.service.FootballPlayerStatisticService;
import org.springframework.samples.petclinic.service.MatchRecordService;
import org.springframework.samples.petclinic.service.MatchService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MatchRecordController {

	private static final String							VIEWS_MATCH_RECORD_DETAIL					= "matchRecords/matchRecordDetail";

	private static final String							VIEWS_CREATE_OR_UPDATE_MATCH_RECORD_FORM	= "matchRecords/createOrUpdateMatchRecordForm";

	private final MatchRecordService					matchRecordService;

	private final MatchService							matchService;

	private final FootballPlayerStatisticService		footballPlayerStatisticService;

	private final FootballPlayerMatchStatisticService	footballPlayerMatchStatisticService;


	@Autowired
	public MatchRecordController(final MatchRecordService matchRecordService, final MatchService matchService, final FootballPlayerMatchStatisticService footballPlayerMatchStatisticService,
		final FootballPlayerStatisticService footballPlayerStatisticService, final UserService userService) {
		this.matchRecordService = matchRecordService;
		this.matchService = matchService;
		this.footballPlayerStatisticService = footballPlayerStatisticService;
		this.footballPlayerMatchStatisticService = footballPlayerMatchStatisticService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/matches/matchRecord/{matchId}/new")
	public String initCreateMatchRequest(final ModelMap model) throws DataAccessException {

		MatchRecord matchRecord = new MatchRecord();
		List<MatchRecordStatus> matchStatus = new ArrayList<MatchRecordStatus>();

		matchStatus.add(MatchRecordStatus.NOT_PUBLISHED);
		matchStatus.add(MatchRecordStatus.PUBLISHED);

		model.addAttribute("matchStatus", matchStatus);
		model.addAttribute("matchRecord", matchRecord);

		return MatchRecordController.VIEWS_CREATE_OR_UPDATE_MATCH_RECORD_FORM;
	}

	@PostMapping(value = "/matches/matchRecord/{matchId}/new")
	public String processCreationForm(@Valid final MatchRecord matchRecord, final BindingResult result, final ModelMap model, @PathVariable("matchId") final int matchId) throws DataAccessException {

		List<MatchRecordStatus> matchStatus = new ArrayList<MatchRecordStatus>();

		matchStatus.add(MatchRecordStatus.NOT_PUBLISHED);
		matchStatus.add(MatchRecordStatus.PUBLISHED);

		model.addAttribute("matchStatus", matchStatus);

		if (result.hasErrors()) {
			return MatchRecordController.VIEWS_CREATE_OR_UPDATE_MATCH_RECORD_FORM;
		} else {

			Match m = this.matchService.findMatchById(matchId);

			matchRecord.setMatch(m);

			this.matchRecordService.saveMatchRecord(matchRecord);
		}

		return "redirect:/myfootballClub/";
	}

	@GetMapping(value = "/matches/matchRecord/{matchId}/edit")
	public String initUpdateMatchRecordForm(@PathVariable("matchId") final int matchId, final Model model) {

		MatchRecord mr = this.matchRecordService.findMatchRecordByMatchId(matchId);

		List<MatchRecordStatus> matchStatus = new ArrayList<MatchRecordStatus>();

		matchStatus.add(MatchRecordStatus.NOT_PUBLISHED);
		matchStatus.add(MatchRecordStatus.PUBLISHED);

		model.addAttribute("matchStatus", matchStatus);
		model.addAttribute(mr);

		return MatchRecordController.VIEWS_CREATE_OR_UPDATE_MATCH_RECORD_FORM;
	}

	@PostMapping(value = "/matches/matchRecord/{matchId}/edit")
	public String processUpdateMatchRecordForm(@Valid final MatchRecord matchRecord, final BindingResult result, final ModelMap model, @PathVariable("matchId") final int matchId) {

		List<MatchRecordStatus> matchStatus = new ArrayList<MatchRecordStatus>();

		matchStatus.add(MatchRecordStatus.NOT_PUBLISHED);
		matchStatus.add(MatchRecordStatus.PUBLISHED);

		model.addAttribute("matchStatus", matchStatus);

		if (result.hasErrors()) {
			return MatchRecordController.VIEWS_CREATE_OR_UPDATE_MATCH_RECORD_FORM;
		} else {

			MatchRecord mr = this.matchRecordService.findMatchRecordByMatchId(matchId);

			matchRecord.setId(mr.getId());
			matchRecord.setMatch(mr.getMatch());

			this.matchRecordService.saveMatchRecord(matchRecord);

			if (mr.getStatus() == MatchRecordStatus.PUBLISHED) {

				FootballPlayerMatchStatistics fpmss = new FootballPlayerMatchStatistics();
				fpmss.getFootballPlayerStatisticsList().addAll(this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticByMatchId(mr.getId()));

				for (FootballPlayerMatchStatistic fpms : fpmss.getFootballPlayerStatisticsList()) {
					FootballPlayerStatistic fps = this.footballPlayerStatisticService.findFootballPlayerStatisticByPlayerId(fpms.getPlayer().getId());
					if (fpms.getPlayer().getId() == fps.getId()) {
						fps.setId(fps.getId());
						fps.setAssists(fpms.getAssists());
						fps.setGoals(fpms.getGoals());
						fps.setPlayer(fpms.getPlayer());
						fps.setReceived_goals(fpms.getReceived_goals());
						fps.setRed_cards(fpms.getRed_cards());
						fps.setSeason_end(fpms.getSeason_end());
						fps.setSeason_start(fpms.getSeason_start());
						fps.setYellow_cards(fpms.getYellow_cards());

						this.footballPlayerStatisticService.saveFootballPlayerStatistic(fps);
					}
				}

			}

			return "redirect:/matches/referee/list/";
		}
	}

	@RequestMapping(value = "/matches/matchRecord/{matchId}/view")
	public String viewMatchRecord(@PathVariable("matchId") final int matchId, final Model model) {

		MatchRecord mr = this.matchRecordService.findMatchRecordByMatchId(matchId);

		FootballPlayerMatchStatistics fpms = new FootballPlayerMatchStatistics();
		fpms.getFootballPlayerStatisticsList().addAll(this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticByMatchId(matchId));

		model.addAttribute("matchRecord", mr);
		model.addAttribute("footballPlayers", fpms);

		return MatchRecordController.VIEWS_MATCH_RECORD_DETAIL;
	}

	@RequestMapping(value = "/matches/matchRecord/goal/add/{matchRecordId}/{playerId}")
	public String addGoalMatchRecord(@PathVariable("matchRecordId") final int matchRecordId, @PathVariable("playerId") final int playerId, final Model model) {

		FootballPlayerMatchStatistic fpms = this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticByPlayerIdAndMatchRecordId(playerId, matchRecordId);

		fpms.setGoals(fpms.getGoals() + 1);

		this.footballPlayerMatchStatisticService.saveFootballPlayerStatistic(fpms);

		return "redirect:/matches/matchRecord/" + fpms.getMatchRecord().getId() + "/view";
	}

	@RequestMapping(value = "/matches/matchRecord/goal/substract/{matchRecordId}/{playerId}")
	public String substractGoalMatchRecord(@PathVariable("matchRecordId") final int matchRecordId, @PathVariable("playerId") final int playerId, final Model model) {

		FootballPlayerMatchStatistic fpms = this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticByPlayerIdAndMatchRecordId(playerId, matchRecordId);

		fpms.setGoals(fpms.getGoals() - 1);

		if (fpms.getGoals() < 0) {
			fpms.setGoals(0);
		}

		this.footballPlayerMatchStatisticService.saveFootballPlayerStatistic(fpms);

		return "redirect:/matches/matchRecord/" + fpms.getMatchRecord().getId() + "/view";
	}

	@RequestMapping(value = "/matches/matchRecord/assist/add/{matchRecordId}/{playerId}")
	public String addAssistMatchRecord(@PathVariable("matchRecordId") final int matchRecordId, @PathVariable("playerId") final int playerId, final Model model) {

		FootballPlayerMatchStatistic fpms = this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticByPlayerIdAndMatchRecordId(playerId, matchRecordId);

		fpms.setAssists(fpms.getAssists() + 1);

		this.footballPlayerMatchStatisticService.saveFootballPlayerStatistic(fpms);

		return "redirect:/matches/matchRecord/" + fpms.getMatchRecord().getId() + "/view";
	}

	@RequestMapping(value = "/matches/matchRecord/assist/substract/{matchRecordId}/{playerId}")
	public String substractAssistMatchRecord(@PathVariable("matchRecordId") final int matchRecordId, @PathVariable("playerId") final int playerId, final Model model) {

		FootballPlayerMatchStatistic fpms = this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticByPlayerIdAndMatchRecordId(playerId, matchRecordId);

		fpms.setAssists(fpms.getAssists() - 1);

		if (fpms.getAssists() < 0) {
			fpms.setAssists(0);
		}

		this.footballPlayerMatchStatisticService.saveFootballPlayerStatistic(fpms);

		return "redirect:/matches/matchRecord/" + fpms.getMatchRecord().getId() + "/view";
	}

	@RequestMapping(value = "/matches/matchRecord/redCard/add/{matchRecordId}/{playerId}")
	public String addRedCardMatchRecord(@PathVariable("matchRecordId") final int matchRecordId, @PathVariable("playerId") final int playerId, final Model model) {

		FootballPlayerMatchStatistic fpms = this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticByPlayerIdAndMatchRecordId(playerId, matchRecordId);

		fpms.setRed_cards(fpms.getRed_cards() + 1);

		this.footballPlayerMatchStatisticService.saveFootballPlayerStatistic(fpms);

		return "redirect:/matches/matchRecord/" + fpms.getMatchRecord().getId() + "/view";
	}

	@RequestMapping(value = "/matches/matchRecord/redCard/substract/{matchRecordId}/{playerId}")
	public String substractRedCardMatchRecord(@PathVariable("matchRecordId") final int matchRecordId, @PathVariable("playerId") final int playerId, final Model model) {

		FootballPlayerMatchStatistic fpms = this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticByPlayerIdAndMatchRecordId(playerId, matchRecordId);

		fpms.setRed_cards(fpms.getRed_cards() - 1);

		if (fpms.getRed_cards() < 0) {
			fpms.setRed_cards(0);
		}

		this.footballPlayerMatchStatisticService.saveFootballPlayerStatistic(fpms);

		return "redirect:/matches/matchRecord/" + fpms.getMatchRecord().getId() + "/view";
	}

	@RequestMapping(value = "/matches/matchRecord/yellowCard/add/{matchRecordId}/{playerId}")
	public String addYellowCardMatchRecord(@PathVariable("matchRecordId") final int matchRecordId, @PathVariable("playerId") final int playerId, final Model model) {

		FootballPlayerMatchStatistic fpms = this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticByPlayerIdAndMatchRecordId(playerId, matchRecordId);

		fpms.setYellow_cards(fpms.getYellow_cards() + 1);

		this.footballPlayerMatchStatisticService.saveFootballPlayerStatistic(fpms);

		return "redirect:/matches/matchRecord/" + fpms.getMatchRecord().getId() + "/view";
	}

	@RequestMapping(value = "/matches/matchRecord/yellowCard/substract/{matchRecordId}/{playerId}")
	public String substractYellowCardMatchRecord(@PathVariable("matchRecordId") final int matchRecordId, @PathVariable("playerId") final int playerId, final Model model) {

		FootballPlayerMatchStatistic fpms = this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticByPlayerIdAndMatchRecordId(playerId, matchRecordId);

		fpms.setYellow_cards(fpms.getYellow_cards() - 1);

		if (fpms.getYellow_cards() < 0) {
			fpms.setYellow_cards(0);
		}

		this.footballPlayerMatchStatisticService.saveFootballPlayerStatistic(fpms);

		return "redirect:/matches/matchRecord/" + fpms.getMatchRecord().getId() + "/view";
	}

	@RequestMapping(value = "/matches/matchRecord/receivedGoals/add/{matchRecordId}/{playerId}")
	public String addReceivedGoalMatchRecord(@PathVariable("matchRecordId") final int matchRecordId, @PathVariable("playerId") final int playerId, final Model model) {

		FootballPlayerMatchStatistic fpms = this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticByPlayerIdAndMatchRecordId(playerId, matchRecordId);

		fpms.setReceived_goals(fpms.getReceived_goals() + 1);

		this.footballPlayerMatchStatisticService.saveFootballPlayerStatistic(fpms);

		return "redirect:/matches/matchRecord/" + fpms.getMatchRecord().getId() + "/view";
	}

	@RequestMapping(value = "/matches/matchRecord/receivedGoals/substract/{matchRecordId}/{playerId}")
	public String substractReceivedGoalMatchRecord(@PathVariable("matchRecordId") final int matchRecordId, @PathVariable("playerId") final int playerId, final Model model) {

		FootballPlayerMatchStatistic fpms = this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticByPlayerIdAndMatchRecordId(playerId, matchRecordId);

		fpms.setReceived_goals(fpms.getReceived_goals() - 1);

		if (fpms.getReceived_goals() < 0) {
			fpms.setReceived_goals(0);
		}

		this.footballPlayerMatchStatisticService.saveFootballPlayerStatistic(fpms);

		return "redirect:/matches/matchRecord/" + fpms.getMatchRecord().getId() + "/view";
	}

}
