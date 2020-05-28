
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.FootballPlayerMatchStatistic;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.MatchRecord;
import org.springframework.samples.petclinic.model.MatchRefereeRequest;
import org.springframework.samples.petclinic.model.Referee;
import org.springframework.samples.petclinic.model.Enum.MatchRecordStatus;
import org.springframework.samples.petclinic.model.Enum.MatchStatus;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.samples.petclinic.service.FootballPlayerMatchStatisticService;
import org.springframework.samples.petclinic.service.FootballPlayerService;
import org.springframework.samples.petclinic.service.MatchRecordService;
import org.springframework.samples.petclinic.service.MatchRefereeRequestService;
import org.springframework.samples.petclinic.service.MatchService;
import org.springframework.samples.petclinic.service.RefereeService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.exceptions.AlreadyOneExists;
import org.springframework.samples.petclinic.service.exceptions.IllegalDateException;
import org.springframework.samples.petclinic.service.exceptions.MatchRecordResultException;
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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MatchRefereeRequestController {

	private static final String							VIEWS_MATCH_REFEREE_REQUEST_CREATE_OR_UPDATE_FORM	= "matchRefereeRequests/createOrUpdateMatchRefereeRequestForm";

	private static final String							VIEWS_MATCH_REFEREE_LIST							= "matchRefereeRequests/refereeList";

	private static final String							VIEWS_MATCH_REFEREE_REQUEST_LIST					= "matchRefereeRequests/refereeRequestList";

	private final MatchRefereeRequestService			matchRefereeRequestService;

	private final RefereeService						refereeService;

	private final MatchService							matchService;

	private final MatchRecordService					matchRecordService;

	private final FootballPlayerMatchStatisticService	footballPlayerMatchStatisticService;

	private final FootballPlayerService					footballPlayerService;


	@Autowired
	public MatchRefereeRequestController(final MatchRefereeRequestService matchRefereeRequestService, final FootballPlayerService footballPlayerService, final RefereeService refereeService,
		final FootballPlayerMatchStatisticService footballPlayerMatchStatisticService, final MatchRecordService matchRecordService, final MatchService matchService, final UserService userService) {
		this.matchRefereeRequestService = matchRefereeRequestService;
		this.matchService = matchService;
		this.refereeService = refereeService;
		this.matchRecordService = matchRecordService;
		this.footballPlayerMatchStatisticService = footballPlayerMatchStatisticService;
		this.footballPlayerService = footballPlayerService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@RequestMapping(value = "/matches/refereeRequest/refereeList/{matchId}")
	public String initMatchRefereeRequestList(@PathVariable("matchId") final int matchId, final Model model) throws DataAccessException {

		List<Referee> referees = new ArrayList<>();
		List<MatchRefereeRequest> matchRefereeRequests = new ArrayList<>();

		referees.addAll(this.refereeService.findAllReferees());
		matchRefereeRequests.addAll(this.matchRefereeRequestService.findAllOnHoldMatchRefereeRequests());

		ArrayList<Referee> toDelete = new ArrayList<>();

		for (Referee ref : referees) {
			for (MatchRefereeRequest mrr : matchRefereeRequests) {
				if (mrr.getMatch().getId() == matchId) {
					if (mrr.getReferee().getId() == ref.getId()) {
						toDelete.add(ref);
					}
				}
			}
		}

		referees.removeAll(toDelete);

		model.addAttribute("referees", referees);
		model.addAttribute("matchId", matchId);

		return MatchRefereeRequestController.VIEWS_MATCH_REFEREE_LIST;
	}

	@GetMapping(value = "/matches/refereeRequest/new/{matchId}/{refereeId}")
	public String initMatchRefereeRequestForm(@PathVariable("matchId") final int matchId, @PathVariable("refereeId") final int refereeId, final Model model) throws DataAccessException {

		MatchRefereeRequest matchRefereeRequest = new MatchRefereeRequest();

		Referee referee = this.refereeService.findRefereeById(refereeId);
		Match match = this.matchService.findMatchById(matchId);

		matchRefereeRequest.setStatus(RequestStatus.ON_HOLD);
		matchRefereeRequest.setReferee(referee);
		matchRefereeRequest.setMatch(match);

		model.addAttribute("matchRefereeRequest", matchRefereeRequest);

		return MatchRefereeRequestController.VIEWS_MATCH_REFEREE_REQUEST_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/matches/refereeRequest/new/{matchId}/{refereeId}")
	public String createMatchRefereeRequestForm(@Valid final MatchRefereeRequest matchRefereeRequest, final BindingResult result, @PathVariable("matchId") final int matchId, @PathVariable("refereeId") final int refereeId, final Model model)
		throws DataAccessException {

		if (result.hasErrors()) {
			return MatchRefereeRequestController.VIEWS_MATCH_REFEREE_REQUEST_CREATE_OR_UPDATE_FORM;
		}

		Referee referee = this.refereeService.findRefereeById(refereeId);
		Match match = this.matchService.findMatchById(matchId);

		matchRefereeRequest.setStatus(RequestStatus.ON_HOLD);
		matchRefereeRequest.setReferee(referee);
		matchRefereeRequest.setMatch(match);

		model.addAttribute("matchRefereeRequest", matchRefereeRequest);

		this.matchRefereeRequestService.saveMatchRefereeRequest(matchRefereeRequest);

		List<Referee> referees = new ArrayList<>();
		List<MatchRefereeRequest> matchRefereeRequests = new ArrayList<>();

		referees.addAll(this.refereeService.findAllReferees());
		matchRefereeRequests.addAll(this.matchRefereeRequestService.findAllOnHoldMatchRefereeRequests());

		ArrayList<Referee> toDelete = new ArrayList<>();

		for (Referee ref : referees) {
			for (MatchRefereeRequest mrr : matchRefereeRequests) {
				if (mrr.getMatch().getId() == matchId) {
					if (mrr.getReferee().getId() == ref.getId()) {
						toDelete.add(ref);
					}
				}
			}
		}

		referees.removeAll(toDelete);

		model.addAttribute("referees", referees);

		return MatchRefereeRequestController.VIEWS_MATCH_REFEREE_LIST;
	}

	@GetMapping(value = "/matchRefereeRequest/list")
	public String showMatchRefereeRequestList(final Map<String, Object> model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		List<MatchRefereeRequest> matchRefereeRequests = new ArrayList<>();

		matchRefereeRequests.addAll(this.matchRefereeRequestService.findOnHoldMatchRefereeRequests(currentPrincipalName));

		model.put("matchRefereeRequests", matchRefereeRequests);

		return MatchRefereeRequestController.VIEWS_MATCH_REFEREE_REQUEST_LIST;
	}

	@GetMapping(value = "/matchRefereeRequest/list/accept/{matchId}")
	public String acceptMatchRefereeRequest(@Valid final MatchRefereeRequest matchRefereeRequest, final BindingResult result, @PathVariable("matchId") final int matchId)
		throws DataAccessException, IllegalDateException, MatchRecordResultException, AlreadyOneExists {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Referee ref = this.refereeService.findRefereeByUsername(currentPrincipalName);
		Match match = this.matchService.findMatchById(matchId);
		MatchRefereeRequest mrr = this.matchRefereeRequestService.findMatchRefereeRequestByUsernameAndMatchId(currentPrincipalName, matchId);
		MatchRecord matchRecord = this.matchRecordService.findMatchRecordByMatchId(matchId);

		if (match.getMatchStatus() == MatchStatus.FINISHED || matchRecord != null) {
			throw new AlreadyOneExists();
		}

		matchRefereeRequest.setId(mrr.getId());
		matchRefereeRequest.setTitle(mrr.getTitle());
		matchRefereeRequest.setStatus(RequestStatus.ACCEPT);
		matchRefereeRequest.setReferee(ref);
		matchRefereeRequest.setMatch(match);

		this.matchRefereeRequestService.saveMatchRefereeRequest(matchRefereeRequest);

		// Añadimos al referee al partido en cuestión
		match.setReferee(ref);
		this.matchService.saveMatch(match);

		// Eliminamos el resto de Match Referee Requests para ese partido
		List<MatchRefereeRequest> matchRefereeRequests = new ArrayList<>();
		matchRefereeRequests.addAll(this.matchRefereeRequestService.findAllOnHoldMatchRefereeRequests());

		for (MatchRefereeRequest m : matchRefereeRequests) {
			if (m.getMatch().getId() == matchId && m.getReferee() != ref) {

				m.setId(m.getId());
				m.setTitle(m.getTitle());
				m.setStatus(RequestStatus.REFUSE);
				m.setReferee(m.getReferee());
				m.setMatch(m.getMatch());

				this.matchRefereeRequestService.saveMatchRefereeRequest(m);
			}
		}

		// Creamos un acta del partido
		MatchRecord mr = new MatchRecord();

		mr.setTitle(match.getTitle() + " Record");
		mr.setMatch(match);
		mr.setStatus(MatchRecordStatus.NOT_PUBLISHED);

		this.matchRecordService.saveMatchRecord(mr);

		// Añadimos los jugadores al acta
		List<FootballPlayer> fps = new ArrayList<>();

		fps.addAll(this.footballPlayerService.findAllClubFootballPlayers(match.getFootballClub1().getId()));
		fps.addAll(this.footballPlayerService.findAllClubFootballPlayers(match.getFootballClub2().getId()));

		for (FootballPlayer fp : fps) {
			FootballPlayerMatchStatistic fpms = new FootballPlayerMatchStatistic();

			fpms.setAssists(0);
			fpms.setGoals(0);
			fpms.setReceived_goals(0);
			fpms.setRed_cards(0);
			fpms.setYellow_cards(0);

			fpms.setMatchRecord(mr);
			fpms.setPlayer(fp);

			this.footballPlayerMatchStatisticService.saveFootballPlayerStatistic(fpms);
		}

		return "redirect:/matchRefereeRequest/list";
	}

	@GetMapping(value = "/matchRefereeRequest/list/reject/{matchId}")
	public String rejectCompetitionAdminRequest(@Valid final MatchRefereeRequest matchRefereeRequest, final BindingResult result, @PathVariable("matchId") final int matchId) throws DataAccessException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Referee ref = this.refereeService.findRefereeByUsername(currentPrincipalName);
		Match match = this.matchService.findMatchById(matchId);
		MatchRefereeRequest mrr = this.matchRefereeRequestService.findMatchRefereeRequestByUsernameAndMatchId(currentPrincipalName, matchId);

		matchRefereeRequest.setId(mrr.getId());
		matchRefereeRequest.setTitle(mrr.getTitle());
		matchRefereeRequest.setStatus(RequestStatus.REFUSE);
		matchRefereeRequest.setReferee(ref);
		matchRefereeRequest.setMatch(match);

		this.matchRefereeRequestService.saveMatchRefereeRequest(matchRefereeRequest);

		//
		return "redirect:/matchRefereeRequest/list";
	}

}
