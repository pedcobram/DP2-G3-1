
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.MatchDateChangeRequest;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.samples.petclinic.service.MatchDateChangeRequestService;
import org.springframework.samples.petclinic.service.MatchService;
import org.springframework.samples.petclinic.service.exceptions.AlreadyOneRequestOpenException;
import org.springframework.samples.petclinic.service.exceptions.IllegalDateException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MatchDateChangeRequestController {

	private final MatchDateChangeRequestService	matchDateChangeRequestService;

	private final MatchService					matchService;

	private static final String					VIEWS_MATCH_DATE_CHANGE_REQUEST_CREATE_OR_UPDATE_FORM	= "matchDateChangeRequest/createOrUpdateMatchDateChangeRequestForm";

	private static final String					VIEWS_MATCH_DATE_CHANGE_REQUEST_LIST					= "matchDateChangeRequest/matchDateChangeRequestList";


	@Autowired
	public MatchDateChangeRequestController(final MatchDateChangeRequestService matchDateChangeRequestService, final MatchService matchService) {
		this.matchDateChangeRequestService = matchDateChangeRequestService;
		this.matchService = matchService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/matches/edit/date/{matchId}")
	public String requestMatchDateChangeRequest(@PathVariable("matchId") final int matchId, final Model model) throws DataAccessException, IllegalAccessException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Match match = this.matchService.findMatchById(matchId);

		if (match.getFootballClub1().getPresident().getUser().getUsername().compareTo(currentPrincipalName) != 0 && match.getFootballClub2().getPresident().getUser().getUsername().compareTo(currentPrincipalName) != 0) {
			throw new IllegalAccessException();
		}

		MatchDateChangeRequest mdcr = new MatchDateChangeRequest();

		mdcr.setTitle(match.getTitle());
		mdcr.setNew_date(match.getMatchDate());
		mdcr.setStatus(RequestStatus.ON_HOLD);
		mdcr.setReason(" ");
		mdcr.setRequest_creator(currentPrincipalName);
		mdcr.setMatch(match);

		model.addAttribute("matchDateChangeRequest", mdcr);

		return MatchDateChangeRequestController.VIEWS_MATCH_DATE_CHANGE_REQUEST_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/matches/edit/date/{matchId}")
	public String processMatchDateChangeRequest(final ModelMap model, @Valid final MatchDateChangeRequest matchDateChangeRequest, final BindingResult result, @PathVariable("matchId") final int matchId) throws DataAccessException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Match match = this.matchService.findMatchById(matchId);

		matchDateChangeRequest.setStatus(RequestStatus.ON_HOLD);
		matchDateChangeRequest.setRequest_creator(currentPrincipalName);
		matchDateChangeRequest.setMatch(match);

		if (result.hasErrors()) {
			return MatchDateChangeRequestController.VIEWS_MATCH_DATE_CHANGE_REQUEST_CREATE_OR_UPDATE_FORM;
		} else {

			try {
				this.matchDateChangeRequestService.saveMatchDateChangeRequest(matchDateChangeRequest);
			} catch (IllegalDateException e) {
				result.rejectValue("new_date", "error.matchdatechangerequest.illegaldate", "The selected date can't be equal or before the current match date");

				return MatchDateChangeRequestController.VIEWS_MATCH_DATE_CHANGE_REQUEST_CREATE_OR_UPDATE_FORM;
			} catch (AlreadyOneRequestOpenException aoroe) {
				result.rejectValue("title", "error.matchdatechangerequest.alreadyoneopen", "There's already one request open or completed for this match");

				return MatchDateChangeRequestController.VIEWS_MATCH_DATE_CHANGE_REQUEST_CREATE_OR_UPDATE_FORM;
			}

			return "redirect:/matches/list";
		}
	}

	@GetMapping(value = "/matches/date-request/list")
	public String showMatchDateChangeRequestList(final Map<String, Object> model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		List<MatchDateChangeRequest> matchDateRequest = new ArrayList<>();
		matchDateRequest.addAll(this.matchDateChangeRequestService.findAllMatchDateChangeRequests(currentPrincipalName));
		model.put("matchDateChangeRequest", matchDateRequest);

		return MatchDateChangeRequestController.VIEWS_MATCH_DATE_CHANGE_REQUEST_LIST;
	}

	@GetMapping(value = "/matches/date-request/delete/{matchId}")
	public String deleteMatchDateChangeRequest(final Map<String, Object> model, @PathVariable("matchId") final int matchId) {

		MatchDateChangeRequest mdcr = this.matchDateChangeRequestService.findMatchDateChangeRequestById(matchId);

		this.matchDateChangeRequestService.deleteMatchDateChangeRequest(mdcr);

		return "redirect:/matches/date-request/list";
	}

}
