
package org.springframework.samples.petclinic.web;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.CredentialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.MatchRequest;
import org.springframework.samples.petclinic.model.Enum.MatchStatus;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.MatchRequestService;
import org.springframework.samples.petclinic.service.MatchService;
import org.springframework.samples.petclinic.service.RefereeService;
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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MatchRequestController {

	private static final String			VIEWS_MATCH_REQUEST_CREATE_OR_UPDATE_FORM	= "matchRequests/createOrUpdateMatchRequestForm";

	//private static final String			VIEWS_MATCH_REQUEST_DETAILS_FORM			= "matchRequests/MatchRequestDetails";

	private static final String			VIEWS_MATCH_REQUEST_LIST					= "matchRequests/matchRequestList";

	private final MatchRequestService	matchRequestService;

	private final FootballClubService	footballClubService;

	private final MatchService			matchService;

	//private final RefereeService		refereeService;


	@Autowired
	public MatchRequestController(final MatchRequestService matchRequestService, final FootballClubService footballClubService, final RefereeService refereeService, final MatchService matchService, final UserService userService) {
		this.matchRequestService = matchRequestService;
		this.footballClubService = footballClubService;
		this.matchService = matchService;
		//this.refereeService = refereeService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/matchRequests/sent/{presidentName}")
	public String showSentMatchRequestList(final Map<String, Object> model, @PathVariable("presidentName") final String presidentName) {

		FootballClub footballClub1 = this.footballClubService.findFootballClubByPresident(presidentName);

		List<MatchRequest> matchRequests = new ArrayList<>();

		//Si el club no existe o no es público la lista estará vacía por razones obvias
		if (footballClub1 != null && footballClub1.getStatus() == true) {
			matchRequests.addAll(this.matchRequestService.findAllMatchRequestsSent(footballClub1.getName()));
		}

		model.put("matchRequests", matchRequests);
		model.put("receivedRequests", true);

		return MatchRequestController.VIEWS_MATCH_REQUEST_LIST;
	}

	@GetMapping(value = "/matchRequests/received/{presidentName}")
	public String showReceivedMatchRequestList(final Map<String, Object> model, @PathVariable("presidentName") final String presidentName) {

		FootballClub footballClub1 = this.footballClubService.findFootballClubByPresident(presidentName);

		List<MatchRequest> matchRequests = new ArrayList<>();

		//Si el club no existe o no es público la lista estará vacía por razones obvias
		if (footballClub1 != null && footballClub1.getStatus() == true) {
			matchRequests.addAll(this.matchRequestService.findAllMatchRequestsReceived(footballClub1.getName()));
		}

		model.put("matchRequests", matchRequests);
		model.put("receivedRequests", false);

		return MatchRequestController.VIEWS_MATCH_REQUEST_LIST;
	}

	@GetMapping(value = "matchRequests/{presidentName}/new")
	public String initCreateMatchRequest(final Map<String, Object> model, @PathVariable("presidentName") final String presidentName) throws DataAccessException, CredentialException {

		//Obtenemos el username actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		FootballClub footballClub1 = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		//Validación: Si no tiene equipo o no está público no puede hacer peticiones de partidos
		if (footballClub1 == null || footballClub1.getStatus() == false) {
			//		return "redirect:/exception/forbidden"; ---> también se puede usar esta opción para ir a la pag de error forbidden
			throw new CredentialException("Forbidden Access");
		}

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

		String title = footballClub1.getName() + " vs " + footballClub2.getName() + " ";

		model.put("titleMatch", title);

		matchRequest.setTitle(title);

		return MatchRequestController.VIEWS_MATCH_REQUEST_CREATE_OR_UPDATE_FORM;
	}

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

		String title = footballClub1.getName() + " vs " + footballClub2.getName() + " ";

		model.put("stadiums", stadiums);
		model.put("titleMatch", title);

		Date now = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_MONTH, 29);
		now = cal.getTime();

		if (result.hasErrors()) {
			return MatchRequestController.VIEWS_MATCH_REQUEST_CREATE_OR_UPDATE_FORM;
		}

		if (date.toString().isEmpty() || date == null) {
			result.rejectValue("matchDate", "errorMatchDate");
			return MatchRequestController.VIEWS_MATCH_REQUEST_CREATE_OR_UPDATE_FORM;
		} else if (date.before(now)) {
			result.rejectValue("matchDate", "code.error.validator.requiredAtLeast1MonthMatchDate", "required");
			return MatchRequestController.VIEWS_MATCH_REQUEST_CREATE_OR_UPDATE_FORM;
		} else {

			matchRequest.setStatus(RequestStatus.ON_HOLD);
			matchRequest.setTitle(title);
			matchRequest.setFootballClub1(footballClub1);
			matchRequest.setFootballClub2(footballClub2);
			matchRequest.setCreator(currentPrincipalName); //Creador del Request

			model.put("matchRequest", matchRequest);

			this.matchRequestService.saveMatchRequest(matchRequest);

			return MatchRequestController.VIEWS_MATCH_REQUEST_LIST;
		}
	}

	@RequestMapping(value = "/matchRequests/delete/{id}/{presidentName}")
	public String processDeleteMatchRequest(@PathVariable("id") final int matchRequestId, @PathVariable("presidentName") final String presidentName, final ModelMap model) {

		List<MatchRequest> matchRequests = new ArrayList<>();

		MatchRequest matchRequest = this.matchRequestService.findMatchRequestById(matchRequestId);

		matchRequest.setFootballClub1(null);
		matchRequest.setFootballClub2(null);

		this.matchRequestService.deleteMatchRequest(matchRequest);

		String footballClub1 = this.footballClubService.findFootballClubByPresident(presidentName).getName();

		matchRequests.addAll(this.matchRequestService.findAllMatchRequestsSent(footballClub1));
		model.put("matchRequests", matchRequests);
		model.put("receivedRequests", true);

		return MatchRequestController.VIEWS_MATCH_REQUEST_LIST;
	}

	@RequestMapping(value = "/matchRequests/accept/{id}/{presidentName}")
	public String processAcceptMatchRequest(@PathVariable("id") final int matchRequestId, @PathVariable("presidentName") final String presidentName, final ModelMap model) {

		List<MatchRequest> matchRequests = new ArrayList<>();

		MatchRequest matchRequest = this.matchRequestService.findMatchRequestById(matchRequestId);

		matchRequest.setStatus(RequestStatus.ACCEPT);

		this.matchRequestService.saveMatchRequest(matchRequest);

		String footballClub1 = this.footballClubService.findFootballClubByPresident(presidentName).getName();

		matchRequests.addAll(this.matchRequestService.findAllMatchRequestsReceived(footballClub1));
		model.put("matchRequests", matchRequests);
		model.put("receivedRequests", false);

		Match match = new Match();

		match.setTitle(matchRequest.getTitle());
		match.setMatchDate(matchRequest.getMatchDate());
		match.setMatchStatus(MatchStatus.TO_BE_PLAYED);
		match.setStadium(matchRequest.getStadium());
		match.setFootballClub1(matchRequest.getFootballClub1());
		match.setFootballClub2(matchRequest.getFootballClub2());

		match.setCreator(matchRequest.getCreator());

		this.matchService.saveMatch(match);

		return MatchRequestController.VIEWS_MATCH_REQUEST_LIST;
	}

	@RequestMapping(value = "/matchRequests/reject/{id}/{presidentName}")
	public String processRejectMatchRequest(@PathVariable("id") final int matchRequestId, @PathVariable("presidentName") final String presidentName, final ModelMap model) {

		List<MatchRequest> matchRequests = new ArrayList<>();

		MatchRequest matchRequest = this.matchRequestService.findMatchRequestById(matchRequestId);

		matchRequest.setStatus(RequestStatus.REFUSE);

		this.matchRequestService.saveMatchRequest(matchRequest);

		String footballClub1 = this.footballClubService.findFootballClubByPresident(presidentName).getName();

		matchRequests.addAll(this.matchRequestService.findAllMatchRequestsReceived(footballClub1));
		model.put("matchRequests", matchRequests);
		model.put("receivedRequests", false);

		return MatchRequestController.VIEWS_MATCH_REQUEST_LIST;
	}

}