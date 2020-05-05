
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.CredentialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Coach;
import org.springframework.samples.petclinic.model.CoachTransferRequest;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.samples.petclinic.service.CoachService;
import org.springframework.samples.petclinic.service.CoachTransferRequestService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.exceptions.AlreadyOneRequestOpenException;
import org.springframework.samples.petclinic.service.exceptions.CoachTransferRequestExistsException;
import org.springframework.samples.petclinic.service.exceptions.MoneyClubException;
import org.springframework.samples.petclinic.service.exceptions.SalaryException;
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
public class CoachTransferRequestController {

	private final CoachTransferRequestService	coachTransferRequestService;

	private final CoachService					coachService;

	private final FootballClubService			footballClubService;

	private static final String					VIEWS_COACH_TRANSFER_REQUEST_CREATE_OR_UPDATE_FORM	= "coachTransferRequest/createOrUpdateCoachTransferRequestForm";

	private static final String					VIEWS_COACH_TRANSFER_REQUEST_LIST					= "coachTransferRequest/coachTransferRequestSentList";

	private static final String					VIEWS_COACH_TRANSFER_REQUEST_RECEIVED				= "coachTransferRequest/coachTransferRequestReceivedList";


	@Autowired
	public CoachTransferRequestController(final CoachTransferRequestService coachTransferRequestService, final CoachService coachService, final FootballClubService footballClubService) {
		this.coachService = coachService;
		this.footballClubService = footballClubService;
		this.coachTransferRequestService = coachTransferRequestService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/transfers/coaches/request/{coachId}")
	public String showRequestTransferCoach(@PathVariable("coachId") final int coachId, final Model model) throws DataAccessException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Coach requestedCoach = this.coachService.findCoachById(coachId);
		FootballClub fc = this.footballClubService.findFootballClubByPresident(currentPrincipalName);
		Coach myCoach = this.coachService.findCoachByClubId(fc.getId());

		CoachTransferRequest ctr = new CoachTransferRequest();

		ctr.setOffer(1000000L);
		ctr.setStatus(RequestStatus.ON_HOLD);
		ctr.setMyCoach(myCoach);
		ctr.setRequestedCoach(requestedCoach);

		model.addAttribute("coachTransferRequest", ctr);

		return CoachTransferRequestController.VIEWS_COACH_TRANSFER_REQUEST_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/transfers/coaches/request/{coachId}")
	public String processRequestTransferCoach(final ModelMap model, @Valid final CoachTransferRequest coachTransferRequest, final BindingResult result, @PathVariable("coachId") final int coachId) throws DataAccessException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Coach requestedCoach = this.coachService.findCoachById(coachId);
		FootballClub fc = this.footballClubService.findFootballClubByPresident(currentPrincipalName);
		Coach myCoach = this.coachService.findCoachByClubId(fc.getId());

		coachTransferRequest.setStatus(RequestStatus.ON_HOLD);
		coachTransferRequest.setMyCoach(myCoach);
		coachTransferRequest.setRequestedCoach(requestedCoach);

		if (result.hasErrors()) {
			return CoachTransferRequestController.VIEWS_COACH_TRANSFER_REQUEST_CREATE_OR_UPDATE_FORM;
		} else {

			try {

				this.coachTransferRequestService.saveCoachTransferRequest(coachTransferRequest);

			} catch (CoachTransferRequestExistsException e) {
				result.rejectValue("offer", "code.error.transferAlreadyExists", "Transfer already exists");

				return CoachTransferRequestController.VIEWS_COACH_TRANSFER_REQUEST_CREATE_OR_UPDATE_FORM;
			} catch (AlreadyOneRequestOpenException e) {
				result.rejectValue("offer", "code.error.alreadyOneRequestOpen", "Already one transfer open");

				return CoachTransferRequestController.VIEWS_COACH_TRANSFER_REQUEST_CREATE_OR_UPDATE_FORM;
			}

			return "redirect:/transfers/coaches";
		}
	}

	@GetMapping(value = "/transfers/coaches/requests/sent")
	public String viewTransferCoachSentList(final Model model) throws DataAccessException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		List<CoachTransferRequest> ctrs = new ArrayList<>();

		ctrs.addAll(this.coachTransferRequestService.findAllCoachTransferRequestbyPresident(currentPrincipalName));

		model.addAttribute("coachTransferRequests", ctrs);

		return CoachTransferRequestController.VIEWS_COACH_TRANSFER_REQUEST_LIST;
	}

	@GetMapping(value = "/transfers/coaches/requests/sent/delete/{requestId}")
	public String deleteTransferCoachRequest(final Model model, @PathVariable("requestId") final int requestId) throws CredentialException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		CoachTransferRequest coachTransferRequest = this.coachTransferRequestService.findCoachTransferRequestById(requestId);

		if (coachTransferRequest.getMyCoach().getClub().getPresident().getUser().getUsername().compareTo(currentPrincipalName) != 0) {
			throw new CredentialException();
		}

		this.coachTransferRequestService.deleteCoachTransferRequest(coachTransferRequest);

		return "redirect:/transfers/coaches/requests/sent";
	}

	@GetMapping(value = "/transfers/coaches/requests/received")
	public String viewTransferPlayerReceivedList(final Model model) throws DataAccessException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		FootballClub fc = this.footballClubService.findFootballClubByPresident(currentPrincipalName);
		Coach coach = this.coachService.findCoachByClubId(fc.getId());

		List<CoachTransferRequest> ctrs = new ArrayList<>();

		ctrs.addAll(this.coachTransferRequestService.findAllCoachTransferRequestByRequestedCoachId(coach.getId()));

		model.addAttribute("coachTransferRequests", ctrs);

		return CoachTransferRequestController.VIEWS_COACH_TRANSFER_REQUEST_RECEIVED;
	}

	@GetMapping(value = "/transfers/coaches/requests/received/reject/{coachRequestId}")
	public String rejectTransferPlayerRequest(final Model model, @PathVariable("coachRequestId") final int coachRequestId) throws Exception {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		CoachTransferRequest ctr = this.coachTransferRequestService.findCoachTransferRequestById(coachRequestId);

		if (ctr.getRequestedCoach().getClub().getPresident().getUser().getUsername().compareTo(currentPrincipalName) != 0) {
			throw new CredentialException();
		}

		ctr.setStatus(RequestStatus.REFUSE);

		try {
			this.coachTransferRequestService.saveCoachTransferRequest(ctr);
		} catch (CoachTransferRequestExistsException e) {
			throw new Exception();
		}

		return "redirect:/transfers/coaches/requests/received";
	}

	@GetMapping(value = "/transfers/coaches/requests/received/accept/{coachRequestId}")
	public String acceptTransferPlayerRequest(final Model model, @PathVariable("coachRequestId") final int coachRequestId) throws DataAccessException, CredentialException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		CoachTransferRequest ctr = this.coachTransferRequestService.findCoachTransferRequestById(coachRequestId);

		if (ctr.getRequestedCoach().getClub().getPresident().getUser().getUsername().compareTo(currentPrincipalName) != 0) {
			throw new CredentialException();
		}

		ctr.setStatus(RequestStatus.ACCEPT);

		try {
			this.coachTransferRequestService.saveCoachTransferRequest(ctr);
		} catch (CoachTransferRequestExistsException | AlreadyOneRequestOpenException e) {
			throw new CredentialException();
		}

		FootballClub fc_requestedCoach = this.footballClubService.findFootballClubByPresident(currentPrincipalName);
		Coach requestedCoach = ctr.getMyCoach();

		try {
			this.coachService.signCoach(requestedCoach, fc_requestedCoach, ctr.getOffer().intValue());
		} catch (MoneyClubException | SalaryException e) {
			throw new CredentialException();
		}

		// Si tenía una peticion abierta de un equipo que he aceptado, la rechazo pues es redundante
		List<CoachTransferRequest> ctrs = new ArrayList<>();

		ctrs.addAll(this.coachTransferRequestService.findAllCoachTransferRequestOnHold());
		ctrs.remove(ctr);

		for (CoachTransferRequest i : ctrs) {

			if (i.getRequestedCoach().equals(ctr.getMyCoach())) {
				i.setStatus(RequestStatus.REFUSE);

				try {
					this.coachTransferRequestService.saveCoachTransferRequest(i);
				} catch (CoachTransferRequestExistsException | AlreadyOneRequestOpenException e) {
					throw new CredentialException();
				}
			}
		}

		return "redirect:/transfers/coaches/requests/received";
	}

}
