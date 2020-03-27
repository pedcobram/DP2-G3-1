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

import javax.security.auth.login.CredentialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.service.AuthenticatedService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
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
public class AuthenticatedController {

	private static final String			VIEWS_AUTHENTICATED_CREATE_OR_UPDATE_FORM	= "authenticateds/createOrUpdateAuthenticatedForm";

	private final AuthenticatedService	authenticatedService;


	@Autowired
	public AuthenticatedController(final AuthenticatedService authenticatedService) {
		this.authenticatedService = authenticatedService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/authenticateds/new")
	public String initCreationForm(final Map<String, Object> model) {
		Authenticated authenticated = new Authenticated();
		model.put("authenticated", authenticated);
		return AuthenticatedController.VIEWS_AUTHENTICATED_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/authenticateds/new")
	public String processCreationForm(@Valid final Authenticated authenticated, final BindingResult result) {

		if (result.hasErrors()) {

			return AuthenticatedController.VIEWS_AUTHENTICATED_CREATE_OR_UPDATE_FORM;
		} else {
			//creating authenticated, user and authorities
			try {
				this.authenticatedService.saveAuthenticated(authenticated);
				return "redirect:/authenticateds/" + authenticated.getId();
			} catch (DataAccessException | DuplicatedNameException e) {
				result.rejectValue("user.username", "duplicate");

				return AuthenticatedController.VIEWS_AUTHENTICATED_CREATE_OR_UPDATE_FORM;
			}

		}
	}

	@GetMapping(value = "/authenticateds/find")
	public String initFindForm(final Map<String, Object> model) {
		model.put("authenticated", new Authenticated());
		return "authenticateds/findAuthenticateds";
	}

	@GetMapping(value = "/authenticateds")
	public String processFindForm(Authenticated authenticated, final BindingResult result, final Map<String, Object> model) {

		// allow parameterless GET request for /authenticateds to return all records
		if (authenticated.getLastName() == null) {
			authenticated.setLastName(""); // empty string signifies broadest possible search
		}

		// find authenticateds by last name
		Collection<Authenticated> results = this.authenticatedService.findAuthenticatedByLastName(authenticated.getLastName());
		if (results.isEmpty()) {
			// no authenticateds found
			result.rejectValue("lastName", "notFound", "not found");
			return "authenticateds/findAuthenticateds";
		} else if (results.size() == 1) {
			// 1 authenticated found
			authenticated = results.iterator().next();
			return "redirect:/authenticateds/" + authenticated.getId();
		} else {
			// multiple authenticateds found
			model.put("selections", results);
			return "authenticateds/authenticatedsList";
		}
	}

	@GetMapping(value = "/myProfile/{authenticatedId}/edit")
	public String initUpdateAuthenticatedForm(@PathVariable("authenticatedId") final int authenticatedId, final Model model) throws CredentialException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(currentPrincipalName);
		Authenticated authenticated = this.authenticatedService.findAuthenticatedById(authenticatedId);
		if (!thisUser.getId().equals(authenticated.getId())) { //SEGURIDAD
			throw new CredentialException("Forbidden Access");
		}

		model.addAttribute(authenticated);
		return AuthenticatedController.VIEWS_AUTHENTICATED_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/myProfile/{authenticatedId}/edit")
	public String processUpdateAuthenticatedForm(@Valid final Authenticated authenticated, final BindingResult result, @PathVariable("authenticatedId") final int authenticatedId) throws CredentialException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(currentPrincipalName);

		if (!thisUser.getId().equals(authenticatedId)) { //SEGURIDAD
			throw new CredentialException("Forbidden Access");
		}

		if (result.hasErrors()) {
			return AuthenticatedController.VIEWS_AUTHENTICATED_CREATE_OR_UPDATE_FORM;
		} else {

			authenticated.setId(authenticatedId);
			authenticated.getUser().setEnabled(true);

			try {
				this.authenticatedService.saveAuthenticated(authenticated);
			} catch (DataAccessException | DuplicatedNameException e) {
				result.rejectValue("username", "duplicate");
			}
			return "redirect:/myProfile/" + currentPrincipalName;
		}
	}

	/**
	 * Custom handler for displaying an authenticated.
	 *
	 * @param authenticatedId
	 *            the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 * @throws CredentialException
	 */

	@GetMapping("/authenticateds/{authenticatedId}")
	public ModelAndView showAuthenticated(@PathVariable("authenticatedId") final int authenticatedId) throws CredentialException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(currentPrincipalName);
		Authenticated authenticated = this.authenticatedService.findAuthenticatedById(authenticatedId);
		if (!thisUser.getId().equals(authenticated.getId())) { //SEGURIDAD
			throw new CredentialException("Forbidden Access");
		}

		ModelAndView mav = new ModelAndView("authenticateds/authenticatedDetails");
		mav.addObject(authenticated);
		return mav;
	}

	//Añadir restricción de que solo el Principal actual puede ver su vista de edición
	@GetMapping("/myProfile/{authenticatedUsername}")
	public ModelAndView showAuthenticatedProfile(@PathVariable("authenticatedUsername") final String authenticatedUsername) throws CredentialException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(currentPrincipalName);
		Authenticated authenticated = this.authenticatedService.findAuthenticatedByUsername(authenticatedUsername);
		if (!thisUser.getId().equals(authenticated.getId())) { //SEGURIDAD
			throw new CredentialException("Forbidden Access");
		}

		ModelAndView mav = new ModelAndView("authenticateds/authenticatedDetails");
		mav.addObject(this.authenticatedService.findAuthenticatedByUsername(authenticatedUsername));
		return mav;
	}

}
