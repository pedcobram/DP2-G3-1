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

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballClubs;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.service.FootballClubService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FootballClubController {

	private static final String			VIEWS_CLUB_CREATE_OR_UPDATE_FORM	= "footballClubs/createOrUpdateFootballClubForm";

	private final FootballClubService	footballClubService;


	@Autowired
	public FootballClubController(final FootballClubService footballClubService) {
		this.footballClubService = footballClubService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = {
		"/footballClubs"
	})
	public String showFootballClubList(final Map<String, Object> model) {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for Object-Xml mapping
		FootballClubs footballClubs = new FootballClubs();
		footballClubs.getFootballClubList().addAll(this.footballClubService.findFootballClubs());
		model.put("footballClubs", footballClubs);
		return "footballClubs/footballClubList";
	}

	@GetMapping(value = {
		"/footballClubs.xml"
	})
	public @ResponseBody FootballClubs showResourcesFootballClubList() {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for JSon/Object mapping
		FootballClubs footballClub = new FootballClubs();
		footballClub.getFootballClubList().addAll(this.footballClubService.findFootballClubs());
		return footballClub;
	}

	//CREAR CLUB

	@GetMapping(value = "/footballClub/new")
	public String initCreationForm(final Map<String, Object> model) {
		FootballClub footballClub = new FootballClub();

		model.put("footballClub", footballClub);

		return FootballClubController.VIEWS_CLUB_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/footballClub/new")
	public String processCreationForm(@Valid final FootballClub footballClub) {

		//creating footballClub, user and authorities
		//Necesito un getPresident segun el username
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		President thisUser = this.footballClubService.findPresidentByUsername(currentPrincipalName);
		footballClub.setPresident(thisUser);

		this.footballClubService.saveFootballClub(footballClub);

		return "redirect:/myfootballClubs/" + currentPrincipalName;
	}

	//EDITAR CLUB

	@GetMapping(value = "/myfootballClubs/{principalUsername}/edit")
	public String initUpdateFootballClubForm(@PathVariable("principalUsername") final String principalUsername, final Model model) {
		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(principalUsername);
		model.addAttribute(footballClub);
		return FootballClubController.VIEWS_CLUB_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/myfootballClubs/{principalUsername}/edit")
	public String processUpdateFootballClubForm(@Valid final FootballClub footballClub, final BindingResult result, @PathVariable("principalUsername") final String principalUsername) {
		if (result.hasErrors()) {
			return FootballClubController.VIEWS_CLUB_CREATE_OR_UPDATE_FORM;
		} else {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();

			FootballClub footballClubToUpdate = this.footballClubService.findFootballClubByPresident(principalUsername);
			BeanUtils.copyProperties(footballClub, footballClubToUpdate, "id", "president");

			this.footballClubService.saveFootballClub(footballClubToUpdate);
			return "redirect:/myfootballClubs/" + currentPrincipalName;
		}
	}

	//VISTA CLUB

	@GetMapping("/footballClubs/{footballClubId}")
	public ModelAndView showFootballClub(@PathVariable("footballClubId") final int footballClubId) {
		ModelAndView mav = new ModelAndView("footballClubs/footballClubDetails");
		mav.addObject(this.footballClubService.findFootballClubById(footballClubId));
		return mav;
	}

	@GetMapping("/myfootballClubs/{principalUsername}")
	public ModelAndView showMyFootballClub(@PathVariable("principalUsername") final String principalUsername) {

		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(principalUsername);

		//Si no hay club se manda a la vista del perfil para que cree un club, o lo mando a la creacion?
		if (footballClub == null) {
			ModelAndView mav = new ModelAndView("footballClubs/myClubEmpty");
			return mav;
		} else {

			ModelAndView mav = new ModelAndView("footballClubs/myClubDetails");
			mav.addObject(this.footballClubService.findFootballClubByPresident(principalUsername));
			return mav;
		}
	}

	//BORRAR CLUB

	@RequestMapping(value = "/footballClub/delete")
	public String processDeleteForm() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		FootballClub thisFootballCLub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		this.footballClubService.deleteFootballClub(thisFootballCLub);

		return "redirect:/myfootballClubs/" + currentPrincipalName;
	}

}
