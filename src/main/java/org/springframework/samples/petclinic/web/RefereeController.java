
package org.springframework.samples.petclinic.web;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.Referee;
import org.springframework.samples.petclinic.service.AuthenticatedService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.MatchService;
import org.springframework.samples.petclinic.service.RefereeService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
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
public class RefereeController {

	private static final String			VIEWS_REFEREE_CREATE_OR_UPDATE_FORM	= "referees/createOrUpdateRefereeForm";

	private final RefereeService		refereeService;

	private final AuthenticatedService	authenticatedService;


	@Autowired
	public RefereeController(final RefereeService refereeService, final AuthenticatedService authenticatedService, final MatchService matchService, final UserService userService, final AuthoritiesService authoritiesService) {
		this.refereeService = refereeService;
		this.authenticatedService = authenticatedService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@RequestMapping(value = "/createReferee")
	public String createReferee() {

		//Obtenemos el username actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		//Creamos el presidente
		Referee referee = new Referee();

		//Obtenemos el authenticated actual conectado
		Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(currentPrincipalName);

		//Añadimos los datos del user al presidente
		referee.setFirstName(thisUser.getFirstName());
		referee.setLastName(thisUser.getLastName());
		referee.setDni(thisUser.getDni());
		referee.setEmail(thisUser.getEmail());
		referee.setTelephone(thisUser.getTelephone());
		referee.setUser(thisUser.getUser());

		//Guardamos en la db el nuevo presidente
		this.refereeService.saveReferee(referee);
		this.authenticatedService.deleteAuthenticated(thisUser);

		//CON ESTO CONSEGUIMOS QUE NO HAGA FALTA RELOGUEAR PARA GANAR LOS PRIVILEGIOS DE PRESIDENTE
		Set<GrantedAuthority> authorities2 = new HashSet<>();
		authorities2.add(new SimpleGrantedAuthority("referee"));
		Authentication reAuth = new UsernamePasswordAuthenticationToken(currentPrincipalName, referee.getUser().getPassword());
		SecurityContextHolder.getContext().setAuthentication(reAuth);

		//Redirigimos a la vista del perfil del presidente
		return "redirect:/myRefereeProfile/" + currentPrincipalName;
	}

	@RequestMapping(value = "/deleteReferee/{username}")
	public String deleteReferee(@PathVariable("username") final String username) throws DataAccessException, DuplicatedNameException {

		//Obtenemos el username actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		//Obtenemos el Competition Admin
		Referee referee = this.refereeService.findRefereeByUsername(currentPrincipalName);
		Authenticated newAuth = new Authenticated();

		newAuth.setId(referee.getId());
		newAuth.setFirstName(referee.getFirstName());
		newAuth.setLastName(referee.getLastName());
		newAuth.setDni(referee.getDni());
		newAuth.setTelephone(referee.getTelephone());
		newAuth.setUser(referee.getUser());
		newAuth.setEmail(referee.getEmail());

		//Guardamos en la db el nuevo presidente
		this.authenticatedService.saveAuthenticated(newAuth);
		this.refereeService.deleteReferee(referee);

		Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(username);

		//CON ESTO CONSEGUIMOS QUE NO HAGA FALTA RELOGUEAR PARA GANAR LOS PRIVILEGIOS
		Set<GrantedAuthority> authorities2 = new HashSet<>();
		authorities2.add(new SimpleGrantedAuthority("authenticated"));
		Authentication reAuth = new UsernamePasswordAuthenticationToken(currentPrincipalName, thisUser.getUser().getPassword());
		SecurityContextHolder.getContext().setAuthentication(reAuth);

		//Redirigimos a la vista del perfil del Competition Admin
		return "redirect:/myProfile/" + currentPrincipalName;
	}

	@GetMapping(value = "/myRefereeProfile/{refereeId}/edit")
	public String initUpdateRefereeForm(@PathVariable("refereeId") final int refereeId, final Model model) {
		Referee referee = this.refereeService.findRefereeById(refereeId);
		model.addAttribute(referee);
		return RefereeController.VIEWS_REFEREE_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/myRefereeProfile/{refereeId}/edit")
	public String processUpdateRefereeForm(@Valid final Referee referee, final BindingResult result, @PathVariable("refereeId") final int refereeId) {

		if (result.hasErrors()) {
			return RefereeController.VIEWS_REFEREE_CREATE_OR_UPDATE_FORM;
		} else {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();

			referee.setId(refereeId);
			this.refereeService.saveReferee(referee);
			return "redirect:/myRefereeProfile/" + currentPrincipalName;
		}
	}

	//Añadir restricción de que solo el Principal actual puede ver su vista de edición
	@GetMapping("/myRefereeProfile/{refereeUsername}")
	public ModelAndView showRefereeProfile(@PathVariable("refereeUsername") final String refereeUsername) {
		ModelAndView mav = new ModelAndView("referees/refereeDetails");
		mav.addObject(this.refereeService.findRefereeByUsername(refereeUsername));
		return mav;
	}

}
