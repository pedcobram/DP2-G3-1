
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.CredentialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.model.PresidentRequest;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.samples.petclinic.service.AuthenticatedService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.PresidentRequestService;
import org.springframework.samples.petclinic.service.PresidentService;
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
public class PresidentRequestController {

	private static final String				VIEWS_PRESIDENT_REQUEST_CREATE_OR_UPDATE_FORM	= "presidentRequests/createOrUpdatePresidentRequestForm";

	private final PresidentRequestService	presidentRequestService;

	private final PresidentService			presidentService;

	private final AuthenticatedService		authenticatedService;

	private final AuthoritiesService		authoritiesService;


	@Autowired
	public PresidentRequestController(final PresidentRequestService presidentRequestService, final AuthoritiesService authoritiesService, final PresidentService presidentService, final UserService userService,
		final AuthenticatedService authenticatedService) {
		this.presidentRequestService = presidentRequestService;
		this.authenticatedService = authenticatedService;
		this.presidentService = presidentService;
		this.authoritiesService = authoritiesService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/presidentRequest/list")
	public String showPresidentRequestList(final Map<String, Object> model) {

		List<PresidentRequest> presidentRequests = new ArrayList<>();

		presidentRequests.addAll(this.presidentRequestService.findPresidentRequests());

		model.put("presidentRequests", presidentRequests);

		return "presidentRequests/presidentRequestList";
	}

	@GetMapping(value = "/presidentRequest/new")
	public String initPresidentCreationForm(final Map<String, Object> model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		try {
			this.presidentRequestService.countPresidentRequestByUsername(currentPrincipalName);
		} catch (PendingRequestException pre) {
			return "redirect:/myPresidentRequest";
		}

		PresidentRequest presidentRequest = new PresidentRequest();

		model.put("presidentRequest", presidentRequest);

		return PresidentRequestController.VIEWS_PRESIDENT_REQUEST_CREATE_OR_UPDATE_FORM;
	}

	//Crear Competition Admin Request - Post
	@PostMapping(value = "/presidentRequest/new")
	public String processCreationForm(@Valid final PresidentRequest presidentRequest, final BindingResult result) throws DataAccessException {

		//Obtenemos el username del usuario actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(currentPrincipalName);

		//Si hay errores seguimos en la vista de creación
		if (result.hasErrors()) {
			return PresidentRequestController.VIEWS_PRESIDENT_REQUEST_CREATE_OR_UPDATE_FORM;
		} else {
			presidentRequest.setUser(thisUser.getUser());
			presidentRequest.setStatus(RequestStatus.ON_HOLD);

			this.presidentRequestService.savePresidentRequest(presidentRequest);

			//Si todo sale bien vamos a la vista de mi club
			return "redirect:/myPresidentRequest";
		}
	}

	@GetMapping(value = "/myPresidentRequest/edit")
	public String initUpdatePresidentForm(final Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		PresidentRequest presidentRequest = this.presidentRequestService.findPresidentRequestByUsername(currentPrincipalName);
		model.addAttribute(presidentRequest);
		return PresidentRequestController.VIEWS_PRESIDENT_REQUEST_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/myPresidentRequest/edit")
	public String processUpdatePresidentForm(@Valid final PresidentRequest presidentRequest, final BindingResult result) {
		if (result.hasErrors()) {
			return PresidentRequestController.VIEWS_PRESIDENT_REQUEST_CREATE_OR_UPDATE_FORM;
		} else {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();

			Authenticated auth = this.authenticatedService.findAuthenticatedByUsername(currentPrincipalName);
			PresidentRequest last_id = this.presidentRequestService.findPresidentRequestByUsername(currentPrincipalName);

			presidentRequest.setId(last_id.getId());
			presidentRequest.setStatus(RequestStatus.ON_HOLD);
			presidentRequest.setUser(auth.getUser());

			this.presidentRequestService.savePresidentRequest(presidentRequest);
			return "redirect:/myPresidentRequest";
		}
	}

	@RequestMapping(value = "/deletePresidentRequest")
	public String deletePresidentRequest() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(currentPrincipalName);

		PresidentRequest tbdeleted = this.presidentRequestService.findPresidentRequestByUsername(currentPrincipalName);
		this.presidentRequestService.deletePresidentRequest(tbdeleted);

		Authentication reAuth = new UsernamePasswordAuthenticationToken(currentPrincipalName, thisUser.getUser().getPassword());
		SecurityContextHolder.getContext().setAuthentication(reAuth);

		return "redirect:/myProfile/" + currentPrincipalName;
	}

	//Vista de Competition Admin Request por Id - Authenticateds
	@GetMapping("/myPresidentRequest")
	public ModelAndView showPresidentRequest() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		ModelAndView mav = new ModelAndView("presidentRequests/presidentRequestDetails");
		mav.addObject(this.presidentRequestService.findPresidentRequestByUsername(currentPrincipalName));
		return mav;
	}

	@GetMapping(value = "/presidentRequest/accept/{username}")
	public String acceptPresidentRequest(@Valid final PresidentRequest presidentRequest, final BindingResult result, @PathVariable("username") final String username) throws DataAccessException, CredentialException {

		//
		PresidentRequest prePresidentRequest = this.presidentRequestService.findPresidentRequestByUsername(username);
		Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(username);
		President newP = new President();

		// Ponemos el status a aceptado
		presidentRequest.setId(prePresidentRequest.getId());
		presidentRequest.setTitle(prePresidentRequest.getTitle());
		presidentRequest.setDescription(prePresidentRequest.getDescription());
		presidentRequest.setStatus(RequestStatus.ACCEPT);
		presidentRequest.setUser(prePresidentRequest.getUser());

		// Guardamos la request actualizada
		this.presidentRequestService.savePresidentRequest(presidentRequest);

		// Añadimos los valores del usuario al nuevo Competition Admin
		newP.setFirstName(thisUser.getFirstName());
		newP.setLastName(thisUser.getLastName());
		newP.setDni(thisUser.getDni());
		newP.setEmail(thisUser.getEmail());
		newP.setTelephone(thisUser.getTelephone());
		newP.setUser(thisUser.getUser());
		thisUser.getUser().setEnabled(true);

		//
		this.authenticatedService.deleteAuthenticated(thisUser);
		this.authoritiesService.deleteAuthorities(username, "authenticated");
		this.authoritiesService.saveAuthorities(username, "president");
		this.presidentService.savePresident(newP);

		//
		return "redirect:/presidentRequest/list";
	}

	@GetMapping(value = "/presidentRequest/reject/{username}")
	public String rejectPresidentRequest(@Valid final PresidentRequest presidentRequest, final BindingResult result, @PathVariable("username") final String username) throws DataAccessException {

		PresidentRequest prePresidentRequest = this.presidentRequestService.findPresidentRequestByUsername(username);
		Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(username);

		//
		presidentRequest.setId(prePresidentRequest.getId());
		presidentRequest.setTitle(prePresidentRequest.getTitle());
		presidentRequest.setDescription(prePresidentRequest.getDescription());
		presidentRequest.setStatus(RequestStatus.REFUSE);
		presidentRequest.setUser(thisUser.getUser());

		this.presidentRequestService.savePresidentRequest(presidentRequest);

		//
		return "redirect:/presidentRequest/list";
	}

}
