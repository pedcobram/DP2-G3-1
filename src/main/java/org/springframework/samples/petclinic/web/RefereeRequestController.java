
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.CredentialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.CompAdminRequest;
import org.springframework.samples.petclinic.model.PresidentRequest;
import org.springframework.samples.petclinic.model.Referee;
import org.springframework.samples.petclinic.model.RefereeRequest;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.samples.petclinic.service.AuthenticatedService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CompAdminRequestService;
import org.springframework.samples.petclinic.service.PresidentRequestService;
import org.springframework.samples.petclinic.service.RefereeRequestService;
import org.springframework.samples.petclinic.service.RefereeService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.exceptions.PendingRequestException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RefereeRequestController {

	private static final String				VIEWS_REFEREE_REQUEST_CREATE_OR_UPDATE_FORM	= "refereeRequests/createOrUpdateRefereeRequestForm";

	private final RefereeRequestService		refereeRequestService;

	private final PresidentRequestService	presidentRequestService;

	private final CompAdminRequestService	compAdminRequestService;

	private final RefereeService			refereeService;

	private final AuthenticatedService		authenticatedService;

	private final AuthoritiesService		authoritiesService;


	@Autowired
	public RefereeRequestController(final RefereeRequestService refereeRequestService, final PresidentRequestService presidentRequestService, final CompAdminRequestService compAdminRequestService, final AuthoritiesService authoritiesService,
		final RefereeService refereeService, final UserService userService, final AuthenticatedService authenticatedService) {
		this.refereeRequestService = refereeRequestService;
		this.presidentRequestService = presidentRequestService;
		this.compAdminRequestService = compAdminRequestService;
		this.authenticatedService = authenticatedService;
		this.refereeService = refereeService;
		this.authoritiesService = authoritiesService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/refereeRequest/list")
	public String showRefereeRequestList(final Map<String, Object> model) {

		List<RefereeRequest> refereeRequests = new ArrayList<>();

		refereeRequests.addAll(this.refereeRequestService.findRefereeRequests());

		model.put("refereeRequests", refereeRequests);

		return "refereeRequests/refereeRequestList";
	}

	@GetMapping(value = "/refereeRequest/new")
	public String initRefereeCreationForm(final Map<String, Object> model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		try {
			this.refereeRequestService.countRefereeRequestByUsername(currentPrincipalName);
		} catch (PendingRequestException pre) {
			return "redirect:/myRefereeRequest";
		}

		RefereeRequest refereeRequest = new RefereeRequest();

		model.put("refereeRequest", refereeRequest);

		return RefereeRequestController.VIEWS_REFEREE_REQUEST_CREATE_OR_UPDATE_FORM;
	}

	//Crear Competition Admin Request - Post
	@PostMapping(value = "/refereeRequest/new")
	public String processCreationForm(@Valid final RefereeRequest refereeRequest, final BindingResult result) throws DataAccessException {

		//Obtenemos el username del usuario actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(currentPrincipalName);

		//Si hay errores seguimos en la vista de creación
		if (result.hasErrors()) {
			return RefereeRequestController.VIEWS_REFEREE_REQUEST_CREATE_OR_UPDATE_FORM;
		} else {
			refereeRequest.setUser(thisUser.getUser());
			refereeRequest.setStatus(RequestStatus.ON_HOLD);

			this.refereeRequestService.saveRefereeRequest(refereeRequest);

			//Si todo sale bien vamos a la vista de mi club
			return "redirect:/myRefereeRequest";
		}
	}

	@GetMapping(value = "/myRefereeRequest/edit")
	public String initUpdateRefereeForm(final Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		RefereeRequest refereeRequest = this.refereeRequestService.findRefereeRequestByUsername(currentPrincipalName);
		model.addAttribute(refereeRequest);
		return RefereeRequestController.VIEWS_REFEREE_REQUEST_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/myRefereeRequest/edit")
	public String processUpdateRefereeForm(@Valid final RefereeRequest refereeRequest, final BindingResult result) {
		if (result.hasErrors()) {
			return RefereeRequestController.VIEWS_REFEREE_REQUEST_CREATE_OR_UPDATE_FORM;
		} else {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();

			Authenticated auth = this.authenticatedService.findAuthenticatedByUsername(currentPrincipalName);
			RefereeRequest last_id = this.refereeRequestService.findRefereeRequestByUsername(currentPrincipalName);

			refereeRequest.setId(last_id.getId());
			refereeRequest.setStatus(RequestStatus.ON_HOLD);
			refereeRequest.setUser(auth.getUser());

			this.refereeRequestService.saveRefereeRequest(refereeRequest);
			return "redirect:/myRefereeRequest";
		}
	}

	@RequestMapping(value = "/deleteRefereeRequest")
	public String deleteRefereeRequest() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(currentPrincipalName);

		RefereeRequest tbdeleted = this.refereeRequestService.findRefereeRequestByUsername(currentPrincipalName);
		this.refereeRequestService.deleteRefereeRequest(tbdeleted);

		Authentication reAuth = new UsernamePasswordAuthenticationToken(currentPrincipalName, thisUser.getUser().getPassword());
		SecurityContextHolder.getContext().setAuthentication(reAuth);

		return "redirect:/myProfile/" + currentPrincipalName;
	}

	//Vista de Competition Admin Request por Id - Authenticateds
	@GetMapping("/myRefereeRequest")
	public ModelAndView showRefereeRequest() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		ModelAndView mav = new ModelAndView("refereeRequests/refereeRequestDetails");
		mav.addObject(this.refereeRequestService.findRefereeRequestByUsername(currentPrincipalName));
		return mav;
	}

	@GetMapping(value = "/refereeRequest/accept/{username}")
	public String acceptRefereeRequest(@Valid final RefereeRequest refereeRequest, final BindingResult result, @PathVariable("username") final String username) throws DataAccessException, CredentialException {

		//
		RefereeRequest preRefereeRequest = this.refereeRequestService.findRefereeRequestByUsername(username);

		PresidentRequest otherPresidentRequest = this.presidentRequestService.findPresidentRequestByUsername(username);
		CompAdminRequest otherCompAdminRequest = this.compAdminRequestService.findCompAdminRequestByUsername(username);

		Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(username);
		Referee newR = new Referee();

		// Ponemos el status a aceptado
		refereeRequest.setId(preRefereeRequest.getId());
		refereeRequest.setTitle(preRefereeRequest.getTitle());
		refereeRequest.setDescription(preRefereeRequest.getDescription());
		refereeRequest.setStatus(RequestStatus.ACCEPT);
		refereeRequest.setUser(preRefereeRequest.getUser());

		// Guardamos la request actualizada
		this.refereeRequestService.saveRefereeRequest(refereeRequest);

		if (otherCompAdminRequest != null) {
			this.compAdminRequestService.deleteCompAdminRequest(otherCompAdminRequest);
		}

		if (otherPresidentRequest != null) {
			this.presidentRequestService.deletePresidentRequest(otherPresidentRequest);
		}

		// Añadimos los valores del usuario al nuevo Competition Admin
		newR.setFirstName(thisUser.getFirstName());
		newR.setLastName(thisUser.getLastName());
		newR.setDni(thisUser.getDni());
		newR.setEmail(thisUser.getEmail());
		newR.setTelephone(thisUser.getTelephone());
		newR.setUser(thisUser.getUser());
		thisUser.getUser().setEnabled(true);

		//
		this.authenticatedService.deleteAuthenticated(thisUser);
		this.authoritiesService.deleteAuthorities(username, "authenticated");
		this.authoritiesService.saveAuthorities(username, "referee");
		this.refereeService.saveReferee(newR);

		//
		return "redirect:/refereeRequest/list";
	}

	@GetMapping(value = "/refereeRequest/reject/{username}")
	public String rejectRefereeRequest(@Valid final RefereeRequest refereeRequest, final BindingResult result, @PathVariable("username") final String username) throws DataAccessException {

		RefereeRequest preRefereeRequest = this.refereeRequestService.findRefereeRequestByUsername(username);
		Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(username);

		//
		refereeRequest.setId(preRefereeRequest.getId());
		refereeRequest.setTitle(preRefereeRequest.getTitle());
		refereeRequest.setDescription(preRefereeRequest.getDescription());
		refereeRequest.setStatus(RequestStatus.REFUSE);
		refereeRequest.setUser(thisUser.getUser());

		this.refereeRequestService.saveRefereeRequest(refereeRequest);

		//
		return "redirect:/refereeRequest/list";
	}

}
