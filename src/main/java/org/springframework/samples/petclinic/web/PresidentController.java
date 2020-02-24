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
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.service.AuthenticatedService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.PresidentService;
import org.springframework.samples.petclinic.service.UserService;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PresidentController {

	private static final String			VIEWS_PRESIDENT_CREATE_OR_UPDATE_FORM	= "presidents/createOrUpdatePresidentForm";

	private final PresidentService		presidentService;

	private final AuthenticatedService	authenticatedService;


	@Autowired
	public PresidentController(final PresidentService presidentService, final AuthenticatedService authenticatedService, final UserService userService, final AuthoritiesService authoritiesService) {
		this.presidentService = presidentService;
		this.authenticatedService = authenticatedService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/presidents/new")
	public String initCreationForm(final Map<String, Object> model) {
		President president = new President();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Authenticated thisUser = this.presidentService.findAuthenticatedByUsername(currentPrincipalName);

		model.put("firstName", thisUser.getFirstName());
		model.put("lastName", thisUser.getLastName());
		model.put("dni", thisUser.getDni());
		model.put("email", thisUser.getEmail());
		model.put("telephone", thisUser.getTelephone());

		president.setFirstName(thisUser.getFirstName());
		president.setLastName(thisUser.getLastName());
		president.setDni(thisUser.getDni());
		president.setEmail(thisUser.getEmail());
		president.setTelephone(thisUser.getTelephone());
		president.setUser(thisUser.getUser());

		model.put("president", president);

		return PresidentController.VIEWS_PRESIDENT_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/presidents/new")
	public String processCreationForm(@Valid final President president) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Authenticated thisUser = this.presidentService.findAuthenticatedByUsername(currentPrincipalName);

		president.setFirstName(thisUser.getFirstName());
		president.setLastName(thisUser.getLastName());
		president.setDni(thisUser.getDni());
		president.setEmail(thisUser.getEmail());
		president.setTelephone(thisUser.getTelephone());
		president.setUser(thisUser.getUser());

		//creating president, user and authorities
		this.presidentService.savePresident(president);
		//		this.authenticatedService.deleteAuthenticated(thisUser);

		return "redirect:/logout";
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
