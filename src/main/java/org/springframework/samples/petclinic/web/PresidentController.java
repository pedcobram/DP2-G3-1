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

import javax.security.auth.login.CredentialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.service.AuthenticatedService;
import org.springframework.samples.petclinic.service.PresidentService;
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
public class PresidentController {

	private static final String			VIEWS_PRESIDENT_CREATE_OR_UPDATE_FORM	= "presidents/createOrUpdatePresidentForm";

	@Autowired
	private final AuthenticatedService	authenticatedService;

	@Autowired
	private final PresidentService		presidentService;


	@Autowired
	public PresidentController(final PresidentService presidentService, final AuthenticatedService authenticatedService) {
		this.presidentService = presidentService;
		this.authenticatedService = authenticatedService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@RequestMapping(value = "/presidents/new") //SER PRESIDENTE
	public String createPresident() throws DataAccessException, CredentialException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(currentPrincipalName);

		President president = new President();
		president.setFirstName(thisUser.getFirstName());
		president.setLastName(thisUser.getLastName());
		president.setDni(thisUser.getDni());
		president.setEmail(thisUser.getEmail());
		president.setTelephone(thisUser.getTelephone());
		president.setUser(thisUser.getUser());

		this.presidentService.savePresident(president);
		this.authenticatedService.deleteAuthenticated(thisUser);

		Authentication reAuth = new UsernamePasswordAuthenticationToken(president.getUser().getUsername(), president.getUser().getPassword());
		SecurityContextHolder.getContext().setAuthentication(reAuth);

		return "redirect:/presidents/" + currentPrincipalName;
	}

	@RequestMapping(value = "/presidents/delete") //BORRAR PRESIDENTE
	public String deletePresident() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		President president = this.presidentService.findPresidentByUsername(currentPrincipalName);

		this.presidentService.deletePresident(president);

		return "redirect:/myProfile/" + currentPrincipalName;
	}

	@GetMapping(value = "/presidents/{presidentUsername}/edit") //EDITAR PRESIDENTE - GET
	public String initUpdatePresidentForm(final Model model, @PathVariable("presidentUsername") final String presidentUsername) throws CredentialException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		if (!presidentUsername.equals(currentPrincipalName)) { //SEGURIDAD
			throw new CredentialException("Forbidden Access");
		}

		President president = this.presidentService.findPresidentByUsername(presidentUsername);
		model.addAttribute(president);

		return PresidentController.VIEWS_PRESIDENT_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/presidents/{presidentUsername}/edit") //EDITAR PRESIDENTE - POST
	public String processUpdatePresidentForm(@Valid final President president, final BindingResult result, @PathVariable("presidentUsername") final String presidentUsername) throws CredentialException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		if (!presidentUsername.equals(currentPrincipalName)) { //SEGURIDAD
			throw new CredentialException("Forbidden Access");
		}

		if (result.hasErrors()) {
			return PresidentController.VIEWS_PRESIDENT_CREATE_OR_UPDATE_FORM;
		} else {

			president.setId(this.presidentService.findPresidentByUsername(presidentUsername).getId());
			president.getUser().setEnabled(true);
			this.presidentService.savePresident(president);
			return "redirect:/presidents/" + presidentUsername;
		}
	}

	@GetMapping("/presidents/{presidentUsername}") //VISTA DE PRESIDENTE
	public ModelAndView showPresidentProfile(@PathVariable("presidentUsername") final String presidentUsername) throws CredentialException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		if (!presidentUsername.equals(currentPrincipalName)) { //SEGURIDAD
			throw new CredentialException("Forbidden Access");
		}

		ModelAndView mav = new ModelAndView("presidents/presidentDetails");
		mav.addObject(this.presidentService.findPresidentByUsername(presidentUsername));
		return mav;
	}

}
