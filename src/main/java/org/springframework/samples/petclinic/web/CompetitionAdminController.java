
package org.springframework.samples.petclinic.web;

import java.util.HashSet;
import java.util.Set;

import javax.security.auth.login.CredentialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.CompetitionAdmin;
import org.springframework.samples.petclinic.service.AuthenticatedService;
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

	private final AuthenticatedService		authenticatedService;


	@Autowired
	public CompetitionAdminController(final CompetitionAdminService competitionAdminService, final AuthenticatedService authenticatedService, final UserService userService, final AuthoritiesService authoritiesService) {
		this.competitionAdminService = competitionAdminService;
		this.authenticatedService = authenticatedService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@RequestMapping(value = "/deleteCompetitionAdmin/{username}")
	public String deleteCompetitionAdmin(@PathVariable("username") final String username) {

		//Obtenemos el username actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		//Obtenemos el Competition Admin
		CompetitionAdmin competitionAdmin = this.competitionAdminService.findCompetitionAdminByUsername(currentPrincipalName);
		Authenticated newAuth = new Authenticated();

		newAuth.setId(competitionAdmin.getId());
		newAuth.setFirstName(competitionAdmin.getFirstName());
		newAuth.setLastName(competitionAdmin.getLastName());
		newAuth.setDni(competitionAdmin.getDni());
		newAuth.setTelephone(competitionAdmin.getTelephone());
		newAuth.setUser(competitionAdmin.getUser());
		newAuth.setEmail(competitionAdmin.getEmail());

		//Guardamos en la db el nuevo presidente
		this.authenticatedService.saveAuthenticated(newAuth);
		this.competitionAdminService.deleteCompetitionAdmin(competitionAdmin);

		Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(username);

		//CON ESTO CONSEGUIMOS QUE NO HAGA FALTA RELOGUEAR PARA GANAR LOS PRIVILEGIOS
		Set<GrantedAuthority> authorities2 = new HashSet<>();
		authorities2.add(new SimpleGrantedAuthority("authenticated"));
		Authentication reAuth = new UsernamePasswordAuthenticationToken(currentPrincipalName, thisUser.getUser().getPassword());
		SecurityContextHolder.getContext().setAuthentication(reAuth);

		//Redirigimos a la vista del perfil del Competition Admin
		return "redirect:/myProfile/" + currentPrincipalName;
	}

	@GetMapping(value = "/myCompetitionAdminProfile/{competitionAdminId}/edit")
	public String initUpdatePresidentForm(@PathVariable("competitionAdminId") final int competitionAdminId, final Model model) {
		CompetitionAdmin competitionAdmin = this.competitionAdminService.findCompetitionAdminById(competitionAdminId);
		model.addAttribute(competitionAdmin);
		return CompetitionAdminController.VIEWS_COMPETITION_ADMIN_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/myCompetitionAdminProfile/{competitionAdminId}/edit")
	public String processUpdateCompetitionAdminForm(@Valid final CompetitionAdmin competitionAdmin, final BindingResult result, @PathVariable("competitionAdminId") final int competitionAdminId) throws DataAccessException, CredentialException {

		if (result.hasErrors()) {
			return CompetitionAdminController.VIEWS_COMPETITION_ADMIN_CREATE_OR_UPDATE_FORM;
		} else {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();

			competitionAdmin.setId(competitionAdminId);
			competitionAdmin.getUser().setEnabled(true);

			this.competitionAdminService.saveCompetitionAdmin(competitionAdmin);
			return "redirect:/myCompetitionAdminProfile/" + currentPrincipalName;
		}
	}

	//Añadir restricción de que solo el Principal actual puede ver su vista de edición
	@GetMapping("/myCompetitionAdminProfile/{competitionAdminUsername}")
	public ModelAndView showCompetitionAdminProfile(@PathVariable("competitionAdminUsername") final String competitionAdminUsername) {
		ModelAndView mav = new ModelAndView("competitionAdmins/competitionAdminDetails");
		mav.addObject(this.competitionAdminService.findCompetitionAdminByUsername(competitionAdminUsername));
		return mav;
	}

}
