
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.MatchRefereeRequest;
import org.springframework.samples.petclinic.model.Referee;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.samples.petclinic.service.MatchRefereeRequestService;
import org.springframework.samples.petclinic.service.MatchService;
import org.springframework.samples.petclinic.service.RefereeService;
import org.springframework.samples.petclinic.service.UserService;
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

	private static final String					VIEWS_MATCH_REFEREE_REQUEST_CREATE_OR_UPDATE_FORM	= "matchRefereeRequests/createOrUpdateMatchRefereeRequestForm";

	private static final String					VIEWS_MATCH_REFEREE_LIST							= "matchRefereeRequests/refereeList";

	private static final String					VIEWS_MATCH_REFEREE_REQUEST_LIST					= "matchRefereeRequests/refereeRequestList";

	private final MatchRefereeRequestService	matchRefereeRequestService;

	private final RefereeService				refereeService;

	private final MatchService					matchService;


	@Autowired
	public MatchRefereeRequestController(final MatchRefereeRequestService matchRefereeRequestService, final RefereeService refereeService, final MatchService matchService, final UserService userService) {
		this.matchRefereeRequestService = matchRefereeRequestService;
		this.matchService = matchService;
		this.refereeService = refereeService;
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

	@GetMapping(value = "/matchRefereeRequest/list/{refereeName}")
	public String showMatchRefereeRequestList(final Map<String, Object> model, @PathVariable("refereeName") final String refereeName) {

		List<MatchRefereeRequest> matchRefereeRequests = new ArrayList<>();

		matchRefereeRequests.addAll(this.matchRefereeRequestService.findOnHoldMatchRefereeRequests(refereeName));

		model.put("matchRefereeRequests", matchRefereeRequests);

		return MatchRefereeRequestController.VIEWS_MATCH_REFEREE_REQUEST_LIST;
	}

	@GetMapping(value = "/matchRefereeRequest/list/accept/{username}/{matchId}")
	public String acceptMatchRefereeRequest(@Valid final MatchRefereeRequest matchRefereeRequest, final BindingResult result, @PathVariable("username") final String username, @PathVariable("matchId") final int matchId) throws DataAccessException {

		Referee ref = this.refereeService.findRefereeByUsername(username);
		Match match = this.matchService.findMatchById(matchId);
		MatchRefereeRequest mrr = this.matchRefereeRequestService.findMatchRefereeRequestByUsernameAndMatchId(username, matchId);

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
			if (m.getMatch().getId() == matchId) {
				this.matchRefereeRequestService.deleteMatchRefereeRequest(m);
			}
		}

		//
		return "redirect:/matchRefereeRequest/list/" + username;

	}

	@GetMapping(value = "/matchRefereeRequest/list/reject/{username}/{matchId}")
	public String rejectCompetitionAdminRequest(@Valid final MatchRefereeRequest matchRefereeRequest, final BindingResult result, @PathVariable("username") final String username, @PathVariable("matchId") final int matchId) throws DataAccessException {

		Referee ref = this.refereeService.findRefereeByUsername(username);
		Match match = this.matchService.findMatchById(matchId);
		MatchRefereeRequest mrr = this.matchRefereeRequestService.findMatchRefereeRequestByUsernameAndMatchId(username, matchId);

		matchRefereeRequest.setId(mrr.getId());
		matchRefereeRequest.setTitle(mrr.getTitle());
		matchRefereeRequest.setStatus(RequestStatus.REFUSE);
		matchRefereeRequest.setReferee(ref);
		matchRefereeRequest.setMatch(match);

		this.matchRefereeRequestService.saveMatchRefereeRequest(matchRefereeRequest);

		//
		return "redirect:/matchRefereeRequest/list/" + username;
	}

}
