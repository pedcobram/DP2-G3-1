/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.PresidentService;
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
public class PresidentController {

	private static final String			VIEWS_PRESIDENT_CREATE_OR_UPDATE_FORM	= "presidents/createOrUpdatePresidentForm";

	private final PresidentService		presidentService;

	private final FootballClubService	footballClubService;


	@Autowired
	public PresidentController(final PresidentService presidentService, final FootballClubService footballClubService, final UserService userService, final AuthoritiesService authoritiesService) {
		this.presidentService = presidentService;
		this.footballClubService = footballClubService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	/**
	 * @GetMapping(value = "/presidents/new")
	 *                   public String initCreationForm(final Map<String, Object> model) {
	 *                   President president = new President();
	 *
	 *                   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	 *                   String currentPrincipalName = authentication.getName();
	 *
	 *                   Authenticated thisUser = this.presidentService.findAuthenticatedByUsername(currentPrincipalName);
	 *
	 *                   model.put("firstName", thisUser.getFirstName());
	 *                   model.put("lastName", thisUser.getLastName());
	 *                   model.put("dni", thisUser.getDni());
	 *                   model.put("email", thisUser.getEmail());
	 *                   model.put("telephone", thisUser.getTelephone());
	 *
	 *                   president.setFirstName(thisUser.getFirstName());
	 *                   president.setLastName(thisUser.getLastName());
	 *                   president.setDni(thisUser.getDni());
	 *                   president.setEmail(thisUser.getEmail());
	 *                   president.setTelephone(thisUser.getTelephone());
	 *                   president.setUser(thisUser.getUser());
	 *
	 *                   model.put("president", president);
	 *
	 *                   return PresidentController.VIEWS_PRESIDENT_CREATE_OR_UPDATE_FORM;
	 *                   }
	 **/
	@RequestMapping(value = "/createPresident")
	public String createPresident() {

		//Obtenemos el username actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		//Creamos el presidente
		President president = new President();

		//Obtenemos el authenticated actual conectado
		Authenticated thisUser = this.presidentService.findAuthenticatedByUsername(currentPrincipalName);

		//Añadimos los datos del user al presidente
		president.setFirstName(thisUser.getFirstName());
		president.setLastName(thisUser.getLastName());
		president.setDni(thisUser.getDni());
		president.setEmail(thisUser.getEmail());
		president.setTelephone(thisUser.getTelephone());
		president.setUser(thisUser.getUser());

		//Guardamos en la db el nuevo presidente
		this.presidentService.savePresident(president);

		//CON ESTO CONSEGUIMOS QUE NO HAGA FALTA RELOGUEAR PARA GANAR LOS PRIVILEGIOS DE PRESIDENTE
		Set<GrantedAuthority> authorities2 = new HashSet<>();
		authorities2.add(new SimpleGrantedAuthority("President"));
		Authentication reAuth = new UsernamePasswordAuthenticationToken(currentPrincipalName, thisUser.getUser().getPassword());
		SecurityContextHolder.getContext().setAuthentication(reAuth);

		//Redirigimos a la vista del perfil del presidente
		return "redirect:/myPresidentProfile/" + currentPrincipalName;
	}

	//BORRAR PRESIDENTE

	@RequestMapping(value = "/deletePresident")
	public String deletePresident() {

		//Obtenemos el username actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		//Obtenemos el presidente
		President president = this.presidentService.findPresidentByUsername(currentPrincipalName);
		Authenticated user = this.presidentService.findAuthenticatedByUsername(currentPrincipalName);

		//Buscamos si tiene un Club
		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		//Borramos el Club si existe
		if (footballClub != null) {
			this.footballClubService.deleteFootballClub(footballClub);
		}

		//Guardamos en la db el nuevo presidente
		this.presidentService.deletePresident(president);

		//CON ESTO CONSEGUIMOS QUE NO HAGA FALTA RELOGUEAR PARA GANAR LOS PRIVILEGIOS DE PRESIDENTE

		Authentication reAuth = new UsernamePasswordAuthenticationToken(currentPrincipalName, user.getUser().getPassword());
		SecurityContextHolder.getContext().setAuthentication(reAuth);

		//Redirigimos a la vista del perfil del presidente
		return "redirect:/myProfile/" + currentPrincipalName;
	}

	@GetMapping(value = "/presidents/find")
	public String initFindForm(final Map<String, Object> model) {
		model.put("president", new President());
		return "presidents/findPresidents";
	}

	@GetMapping(value = "/presidents")
	public String processFindForm(President president, final BindingResult result, final Map<String, Object> model) {

		// allow parameterless GET request for /presidents to return all records
		if (president.getLastName() == null) {
			president.setLastName(""); // empty string signifies broadest possible search
		}

		// find presidents by last name
		Collection<President> results = this.presidentService.findPresidentByLastName(president.getLastName());
		if (results.isEmpty()) {
			// no presidents found
			result.rejectValue("lastName", "notFound", "not found");
			return "presidents/findPresidents";
		} else if (results.size() == 1) {
			// 1 president found
			president = results.iterator().next();
			return "redirect:/presidents/" + president.getId();
		} else {
			// multiple presidents found
			model.put("selections", results);
			return "presidents/presidentsList";
		}
	}

	@GetMapping(value = "/myPresidentProfile/{presidentId}/edit")
	public String initUpdatePresidentForm(@PathVariable("presidentId") final int presidentId, final Model model) {
		President president = this.presidentService.findPresidentById(presidentId);
		model.addAttribute(president);
		return PresidentController.VIEWS_PRESIDENT_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/myPresidentProfile/{presidentId}/edit")
	public String processUpdatePresidentForm(@Valid final President president, final BindingResult result, @PathVariable("presidentId") final int presidentId) {
		if (result.hasErrors()) {
			return PresidentController.VIEWS_PRESIDENT_CREATE_OR_UPDATE_FORM;
		} else {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();

			president.setId(presidentId);
			this.presidentService.savePresident(president);
			return "redirect:/myPresidentProfile/" + currentPrincipalName;
		}
	}

	/**
	 * Custom handler for displaying an authenticated.
	 *
	 * @param authenticatedId
	 *            the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */

	@GetMapping("/presidents/{presidentId}")
	public ModelAndView showPresident(@PathVariable("presidentId") final int presidentId) {
		ModelAndView mav = new ModelAndView("presidents/presidentDetails");
		mav.addObject(this.presidentService.findPresidentById(presidentId));
		return mav;
	}

	//Añadir restricción de que solo el Principal actual puede ver su vista de edición
	@GetMapping("/myPresidentProfile/{presidentUsername}")
	public ModelAndView showPresidentProfile(@PathVariable("presidentUsername") final String presidentUsername) {
		ModelAndView mav = new ModelAndView("presidents/presidentDetails");
		mav.addObject(this.presidentService.findPresidentByUsername(presidentUsername));
		return mav;
	}

}
