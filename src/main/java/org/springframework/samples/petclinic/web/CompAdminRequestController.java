
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
import org.springframework.samples.petclinic.model.CompetitionAdmin;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.samples.petclinic.service.AuthenticatedService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CompAdminRequestService;
import org.springframework.samples.petclinic.service.CompetitionAdminService;
import org.springframework.samples.petclinic.service.UserService;
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
public class CompAdminRequestController {

	private static final String				VIEWS_COMP_ADMIN_REQUEST_CREATE_OR_UPDATE_FORM	= "compAdminRequests/createOrUpdateCompAdminRequestForm";

	private final CompAdminRequestService	compAdminRequestService;

	private final CompetitionAdminService	competitionAdminService;

	private final AuthenticatedService		authenticatedService;

	private final AuthoritiesService		authoritiesService;


	@Autowired
	public CompAdminRequestController(final CompAdminRequestService compAdminRequestService, final AuthoritiesService authoritiesService, final CompetitionAdminService competitionAdminService, final UserService userService,
		final AuthenticatedService authenticatedService) {
		this.compAdminRequestService = compAdminRequestService;
		this.authenticatedService = authenticatedService;
		this.competitionAdminService = competitionAdminService;
		this.authoritiesService = authoritiesService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/competitionAdminRequest/list")
	public String showCompetitionAdminRequestList(final Map<String, Object> model) {

		List<CompAdminRequest> compAdminRequests = new ArrayList<>();

		compAdminRequests.addAll(this.compAdminRequestService.findCompAdminRequests());

		model.put("compAdminRequests", compAdminRequests);

		return "compAdminRequests/compAdminRequestList";
	}

	//Crear Competition Admin Request - Get
	@GetMapping(value = "/competitionAdminRequest/new")
	public String initCreationForm(final Map<String, Object> model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		int count = this.compAdminRequestService.countCompAdminRequestByUsername(currentPrincipalName);

		CompAdminRequest compAdminRequest = new CompAdminRequest();

		model.put("compAdminRequest", compAdminRequest);

		if (count > 0) {
			return "redirect:/myCompetitionAdminRequest/" + currentPrincipalName;
		}

		return CompAdminRequestController.VIEWS_COMP_ADMIN_REQUEST_CREATE_OR_UPDATE_FORM;
	}

	//Crear Competition Admin Request - Post
	@PostMapping(value = "/competitionAdminRequest/new")
	public String processCreationForm(@Valid final CompAdminRequest compAdminRequest, final BindingResult result) throws DataAccessException {

		//Obtenemos el username del usuario actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(currentPrincipalName);

		//Si hay errores seguimos en la vista de creación
		if (result.hasErrors()) {
			return CompAdminRequestController.VIEWS_COMP_ADMIN_REQUEST_CREATE_OR_UPDATE_FORM;
		} else {
			compAdminRequest.setUser(thisUser.getUser());
			compAdminRequest.setStatus(RequestStatus.ON_HOLD);

			this.compAdminRequestService.saveCompAdminRequest(compAdminRequest);

			//Si todo sale bien vamos a la vista de mi club
			return "redirect:/myCompetitionAdminRequest/" + currentPrincipalName;
		}
	}

	@GetMapping(value = "/competitionAdminRequest/{compAdminRequestId}/edit")
	public String initUpdatePresidentForm(@PathVariable("compAdminRequestId") final int compAdminRequestId, final Model model) {
		CompAdminRequest compAdminRequest = this.compAdminRequestService.findCompAdminRequestById(compAdminRequestId);
		model.addAttribute(compAdminRequest);
		return CompAdminRequestController.VIEWS_COMP_ADMIN_REQUEST_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/competitionAdminRequest/{compAdminRequestId}/edit")
	public String processUpdateCompetitionAdminForm(@Valid final CompAdminRequest compAdminRequest, final BindingResult result, @PathVariable("compAdminRequestId") final int compAdminRequestId) {
		if (result.hasErrors()) {
			return CompAdminRequestController.VIEWS_COMP_ADMIN_REQUEST_CREATE_OR_UPDATE_FORM;
		} else {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();
			Authenticated auth = this.authenticatedService.findAuthenticatedByUsername(currentPrincipalName);
			CompAdminRequest last_id = this.compAdminRequestService.findCompAdminRequestByUsername(currentPrincipalName);

			compAdminRequest.setId(last_id.getId());
			compAdminRequest.setStatus(RequestStatus.ON_HOLD);
			compAdminRequest.setUser(auth.getUser());

			this.compAdminRequestService.saveCompAdminRequest(compAdminRequest);
			return "redirect:/myCompetitionAdminRequest/" + currentPrincipalName;
		}
	}

	@RequestMapping(value = "/deleteCompAdminRequest/{id}")
	public String deleteCompAdminRequest(@PathVariable("id") final Integer id) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(currentPrincipalName);

		CompAdminRequest tbdeleted = this.compAdminRequestService.findCompAdminRequestById(id);
		tbdeleted.setUser(null); //Si no es null da error referencial
		this.compAdminRequestService.deleteCompAdminRequest(tbdeleted);

		Authentication reAuth = new UsernamePasswordAuthenticationToken(currentPrincipalName, thisUser.getUser().getPassword());
		SecurityContextHolder.getContext().setAuthentication(reAuth);

		return "redirect:/myProfile/" + currentPrincipalName;
	}

	//Vista de Competition Admin Request por Id - Authenticateds
	@GetMapping("/myCompetitionAdminRequest/{username}")
	public ModelAndView showCompAdminRequest(@PathVariable("username") final String username) {
		ModelAndView mav = new ModelAndView("compAdminRequests/compAdminRequestDetails");
		mav.addObject(this.compAdminRequestService.findCompAdminRequestByUsername(username));
		return mav;
	}

	@GetMapping(value = "/competitionAdminRequest/accept/{username}")
	public String acceptCompetitionAdminRequest(@Valid final CompAdminRequest compAdminRequest, final BindingResult result, @PathVariable("username") final String username) throws DataAccessException, CredentialException {

		//
		CompAdminRequest preCompAdminRequest = this.compAdminRequestService.findCompAdminRequestByUsername(username);
		Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(username);
		CompetitionAdmin newCA = new CompetitionAdmin();

		// Ponemos el status a aceptado
		compAdminRequest.setId(preCompAdminRequest.getId());
		compAdminRequest.setTitle(preCompAdminRequest.getTitle());
		compAdminRequest.setDescription(preCompAdminRequest.getDescription());
		compAdminRequest.setStatus(RequestStatus.ACCEPT);
		compAdminRequest.setUser(preCompAdminRequest.getUser());

		// Guardamos la request actualizada
		this.compAdminRequestService.saveCompAdminRequest(compAdminRequest);

		// Añadimos los valores del usuario al nuevo Competition Admin
		newCA.setFirstName(thisUser.getFirstName());
		newCA.setLastName(thisUser.getLastName());
		newCA.setDni(thisUser.getDni());
		newCA.setEmail(thisUser.getEmail());
		newCA.setTelephone(thisUser.getTelephone());
		newCA.setUser(thisUser.getUser());
		thisUser.getUser().setEnabled(true);

		//
		this.authenticatedService.deleteAuthenticated(thisUser);
		this.authoritiesService.deleteAuthorities(username, "authenticated");
		this.authoritiesService.saveAuthorities(username, "competitionAdmin");
		this.competitionAdminService.saveCompetitionAdmin(newCA);

		//
		return "redirect:/competitionAdminRequest/list";

	}

	@GetMapping(value = "/competitionAdminRequest/reject/{username}")
	public String rejectCompetitionAdminRequest(@Valid final CompAdminRequest compAdminRequest, final BindingResult result, @PathVariable("username") final String username) throws DataAccessException {

		CompAdminRequest preCompAdminRequest = this.compAdminRequestService.findCompAdminRequestByUsername(username);
		Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(username);

		//
		compAdminRequest.setId(preCompAdminRequest.getId());
		compAdminRequest.setTitle(preCompAdminRequest.getTitle());
		compAdminRequest.setDescription(preCompAdminRequest.getDescription());
		compAdminRequest.setStatus(RequestStatus.REFUSE);
		compAdminRequest.setUser(thisUser.getUser());

		this.compAdminRequestService.saveCompAdminRequest(compAdminRequest);

		//
		return "redirect:/competitionAdminRequest/list";

	}

}
