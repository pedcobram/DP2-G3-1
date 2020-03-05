
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.CompetitionAdmin;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CompetitionAdminService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
public class CompetitionAdminController {

	private static final String				VIEWS_COMPETITION_ADMIN_CREATE_OR_UPDATE_FORM	= "competitionAdmins/createOrUpdateCompetitionAdminForm";

	private final CompetitionAdminService	competitionAdminService;


	@Autowired
	public CompetitionAdminController(final CompetitionAdminService competitionAdminService, final UserService userService, final AuthoritiesService authoritiesService) {
		this.competitionAdminService = competitionAdminService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@RequestMapping(value = "/createCompetitionAdmin")
	public String createCompetitionAdmin() {

		//Obtenemos el username actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		//Creamos el Competition Admin
		CompetitionAdmin competitionAdmin = new CompetitionAdmin();

		//Obtenemos el authenticated actual conectado
		Authenticated thisUser = this.competitionAdminService.findAuthenticatedByUsername(currentPrincipalName);

		//Añadimos los datos del user al Competition Admin
		competitionAdmin.setFirstName(thisUser.getFirstName());
		competitionAdmin.setLastName(thisUser.getLastName());
		competitionAdmin.setDni(thisUser.getDni());
		competitionAdmin.setEmail(thisUser.getEmail());
		competitionAdmin.setTelephone(thisUser.getTelephone());
		competitionAdmin.setUser(thisUser.getUser());

		//Guardamos en la db el nuevo Competition Admin
		this.competitionAdminService.saveCompetitionAdmin(competitionAdmin);

		//CON ESTO CONSEGUIMOS QUE NO HAGA FALTA RELOGUEAR PARA GANAR LOS PRIVILEGIOS DE COMPETITION ADMIN
		Set<GrantedAuthority> authorities2 = new HashSet<>();
		authorities2.add(new SimpleGrantedAuthority("CompetitionAdmin"));
		Authentication reAuth = new UsernamePasswordAuthenticationToken(currentPrincipalName, thisUser.getUser().getPassword());
		SecurityContextHolder.getContext().setAuthentication(reAuth);

		//Redirigimos a la vista del perfil del Competition Admin
		return "redirect:/myCompetitionAdminProfile/" + currentPrincipalName;
	}

	//BORRAR PRESIDENTE

	@RequestMapping(value = "/deleteCompetitionAdmin")
	public String deleteCompetitionAdmin() {

		//Obtenemos el username actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		//Obtenemos el Competition Admin
		CompetitionAdmin competitionAdmin = this.competitionAdminService.findCompetitionAdminByUsername(currentPrincipalName);
		Authenticated user = this.competitionAdminService.findAuthenticatedByUsername(currentPrincipalName);

		//Guardamos en la db el nuevo presidente
		this.competitionAdminService.deleteCompetitionAdmin(competitionAdmin);

		//CON ESTO CONSEGUIMOS QUE NO HAGA FALTA RELOGUEAR PARA GANAR LOS PRIVILEGIOS DE PRESIDENTE

		Authentication reAuth = new UsernamePasswordAuthenticationToken(currentPrincipalName, user.getUser().getPassword());
		SecurityContextHolder.getContext().setAuthentication(reAuth);

		//Redirigimos a la vista del perfil del Competition Admin
		return "redirect:/myProfile/" + currentPrincipalName;
	}

	@GetMapping(value = "/competitionAdmins/find")
	public String initFindForm(final Map<String, Object> model) {
		model.put("president", new President());
		return "presidents/findPresidents";
	}

	@GetMapping(value = "/competitionAdmins")
	public String processFindForm(CompetitionAdmin competitionAdmin, final BindingResult result, final Map<String, Object> model) {

		// allow parameterless GET request for /presidents to return all records
		if (competitionAdmin.getLastName() == null) {
			competitionAdmin.setLastName(""); // empty string signifies broadest possible search
		}

		// find Competition Admins by last name
		Collection<CompetitionAdmin> results = this.competitionAdminService.findCompetitionAdminByLastName(competitionAdmin.getLastName());
		if (results.isEmpty()) {
			// no Competition Admin found
			result.rejectValue("lastName", "notFound", "not found");
			return "competitionAdmins/findCompetitionAdmins";
		} else if (results.size() == 1) {
			// 1 president found
			competitionAdmin = results.iterator().next();
			return "redirect:/competitionAdmins/" + competitionAdmin.getId();
		} else {
			// multiple presidents found
			model.put("selections", results);
			return "competitionAdmins/competitionAdminsList";
		}
	}

	@GetMapping(value = "/myCompetitionAdminProfile/{competitionAdminId}/edit")
	public String initUpdatePresidentForm(@PathVariable("competitionAdminId") final int competitionAdminId, final Model model) {
		CompetitionAdmin competitionAdmin = this.competitionAdminService.findCompetitionAdminById(competitionAdminId);
		model.addAttribute(competitionAdmin);
		return CompetitionAdminController.VIEWS_COMPETITION_ADMIN_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/myCompetitionAdminProfile/{competitionAdminId}/edit")
	public String processUpdateCompetitionAdminForm(@Valid final CompetitionAdmin competitionAdmin, final BindingResult result, @PathVariable("competitionAdminId") final int competitionAdminId) {
		if (result.hasErrors()) {
			return CompetitionAdminController.VIEWS_COMPETITION_ADMIN_CREATE_OR_UPDATE_FORM;
		} else {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();

			competitionAdmin.setId(competitionAdminId);
			this.competitionAdminService.saveCompetitionAdmin(competitionAdmin);
			return "redirect:/myCompetitionAdminProfile/" + currentPrincipalName;
		}
	}

	/**
	 * Custom handler for displaying an authenticated.
	 *
	 * @param authenticatedId
	 *            the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */

	@GetMapping("/competitionAdmins/{competitionAdminId}")
	public ModelAndView showCompetitionAdmin(@PathVariable("competitionAdminId") final int competitionAdminId) {
		ModelAndView mav = new ModelAndView("competitionAdmins/competitionAdminDetails");
		mav.addObject(this.competitionAdminService.findCompetitionAdminById(competitionAdminId));
		return mav;
	}

	//Añadir restricción de que solo el Principal actual puede ver su vista de edición
	@GetMapping("/myCompetitionAdminProfile/{competitionAdminUsername}")
	public ModelAndView showCompetitionAdminProfile(@PathVariable("competitionAdminUsername") final String competitionAdminUsername) {
		ModelAndView mav = new ModelAndView("competitionAdmins/competitionAdminDetails");
		mav.addObject(this.competitionAdminService.findCompetitionAdminByUsername(competitionAdminUsername));
		return mav;
	}

}
