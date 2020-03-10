
package org.springframework.samples.petclinic.web;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.MatchRequest;
import org.springframework.samples.petclinic.model.MatchRequests;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.MatchRequestService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MatchRequestController {

	private static final String			VIEWS_MATCH_REQUEST_CREATE_OR_UPDATE_FORM	= "matchRequests/createOrUpdateMatchRequestForm";

	//private static final String			VIEWS_MATCH_REQUEST_DETAILS_FORM			= "matchRequests/MatchRequestDetails";

	private static final String			VIEWS_MATCH_REQUEST_LIST					= "matchRequests/matchRequestList";

	private final MatchRequestService	matchRequestService;

	private final FootballClubService	footballClubService;


	@Autowired
	public MatchRequestController(final MatchRequestService matchRequestService, final FootballClubService footballClubService, final UserService userService) {
		this.matchRequestService = matchRequestService;
		this.footballClubService = footballClubService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/matchRequests/sent/{presidentName}")
	public String showMatchRequestSentList(final Map<String, Object> model, @PathVariable("presidentName") final String presidentName) {

		String footballClub1 = this.footballClubService.findFootballClubByPresident(presidentName).getName();

		MatchRequests matchRequests = new MatchRequests();

		matchRequests.getMatchRequestList().addAll(this.matchRequestService.findAllMatchRequestsSent(footballClub1));

		model.put("matchRequests", matchRequests);

		return MatchRequestController.VIEWS_MATCH_REQUEST_LIST;
	}

	@GetMapping(value = "matchRequests/{presidentName}/new")
	public String initCreateMatchRequest(final Map<String, Object> model, @PathVariable("presidentName") final String presidentName) throws DataAccessException {

		//Obtenemos el username actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		FootballClub footballClub1 = this.footballClubService.findFootballClubByPresident(currentPrincipalName);
		FootballClub footballClub2 = this.footballClubService.findFootballClubByPresident(presidentName);

		MatchRequest matchRequest = new MatchRequest();
		List<String> stadiums = new ArrayList<String>();

		matchRequest.setFootballClub1(footballClub1);
		matchRequest.setFootballClub2(footballClub2);

		LocalDateTime now = LocalDateTime.now();
		Date now_date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
		matchRequest.setMatchDate(now_date);

		stadiums.add(footballClub1.getStadium());
		stadiums.add(footballClub2.getStadium());

		model.put("stadiums", stadiums);
		model.put("matchRequest", matchRequest);

		return MatchRequestController.VIEWS_MATCH_REQUEST_CREATE_OR_UPDATE_FORM;
	}

	//TODO: Controlar error de la fecha y limitar fecha a 1 mes más mínimo
	@PostMapping(value = "/matchRequests/{presidentName}/new")
	public String createMatchRequest(@Valid final MatchRequest matchRequest, final BindingResult result, @PathVariable("presidentName") final String presidentName, final ModelMap model) throws DataAccessException {

		//Obtenemos el username actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		FootballClub footballClub1 = this.footballClubService.findFootballClubByPresident(currentPrincipalName);
		FootballClub footballClub2 = this.footballClubService.findFootballClubByPresident(presidentName);

		List<String> stadiums = new ArrayList<String>();

		stadiums.add(footballClub1.getStadium());
		stadiums.add(footballClub2.getStadium());

		Date date = matchRequest.getMatchDate();

		model.put("stadiums", stadiums);

		if (result.hasErrors()) {
			return MatchRequestController.VIEWS_MATCH_REQUEST_CREATE_OR_UPDATE_FORM;
		}

		if (date.toString().isEmpty() || date == null) {

			result.rejectValue("matchDate", "errorMatchDate");
			return MatchRequestController.VIEWS_MATCH_REQUEST_CREATE_OR_UPDATE_FORM;

		} else {

			matchRequest.setId(matchRequest.getId());
			matchRequest.setMatchDate(matchRequest.getMatchDate());
			matchRequest.setStadium(matchRequest.getStadium());
			matchRequest.setStatus(RequestStatus.ON_HOLD);
			matchRequest.setTitle(matchRequest.getTitle());
			matchRequest.setFootballClub1(footballClub1);
			matchRequest.setFootballClub2(footballClub2);

			model.put("matchRequest", matchRequest);

			this.matchRequestService.saveMatchRequest(matchRequest);

			return "redirect:/";
		}
	}

}
