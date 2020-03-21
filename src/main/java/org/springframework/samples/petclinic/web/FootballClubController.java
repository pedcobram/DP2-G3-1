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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.CredentialException;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Coach;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.exceptions.DateException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.samples.petclinic.service.exceptions.NumberOfPlayersAndCoachException;
import org.springframework.samples.petclinic.web.validators.FootballClubValidator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FootballClubController {

	private static final String			VIEWS_CLUB_CREATE_OR_UPDATE_FORM	= "footballClubs/createOrUpdateFootballClubForm";

	@Autowired
	private final FootballClubService	footballClubService;


	@Autowired
	public FootballClubController(final FootballClubService footballClubService) {
		this.footballClubService = footballClubService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@InitBinder("footballClub")
	public void initFootballClubBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new FootballClubValidator());
	}

	@ModelAttribute("status")
	public List<Boolean> populateStatus() {
		List<Boolean> status = new ArrayList<>();
		status.add(true);
		status.add(false);
		return status;
	}

	@GetMapping(value = "/footballClubs/list") //LISTA DE EQUIPOS
	public String showFootballClubList(final Map<String, Object> model) {

		List<FootballClub> clubs = new ArrayList<>();
		clubs.addAll(this.footballClubService.findFootballClubs());

		model.put("footballClubs", clubs);

		return "footballClubs/footballClubList";
	}

	@GetMapping("/footballClubs/list/{footballClubId}") //VISTA DETALLADA DE EQUIPO
	public ModelAndView showFootballClub(@PathVariable("footballClubId") final int footballClubId) {

		ModelAndView mav = new ModelAndView("footballClubs/footballClubDetails");

		mav.addObject(this.footballClubService.findFootballClubById(footballClubId));
		mav.addObject(this.footballClubService.findCoachByClubId(footballClubId));

		//Quitamos el botón si no tiene equipo o no está público no puede hacer peticiones de partidos
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		FootballClub footballClub1 = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		if (footballClub1 == null || footballClub1.getStatus() == false) {
			mav.addObject("notHasAPublishedTeam", true);
		}

		return mav;
	}

	@GetMapping("/footballClubs/myClub/{principalUsername}") //VISTA DETALLADA DE MI EQUIPO
	public ModelAndView showMyFootballClub(@PathVariable("principalUsername") final String principalUsername) throws CredentialException {

		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(principalUsername);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		if (!currentPrincipalName.equals(principalUsername)) { //SEGURIDAD
			throw new CredentialException("Forbbiden Access");
		}

		if (footballClub == null) { //Si el presidente aun no tiene club se le manda a la ventana de crear club
			ModelAndView mav = new ModelAndView("footballClubs/myClubEmpty");
			return mav;
		} else {

			ModelAndView mav = new ModelAndView("footballClubs/myClubDetails");

			FootballClub club = this.footballClubService.findFootballClubByPresident(principalUsername);
			mav.addObject(club);

			Coach clubCoach = this.footballClubService.findCoachByClubId(club.getId());
			if (clubCoach != null) {
				mav.addObject(clubCoach);
			}
			return mav;
		}
	}

	@GetMapping(value = "/footballClubs/myClub/new") //CREAR CLUB - GET
	public String initCreationForm(final Map<String, Object> model) {
		FootballClub footballClub = new FootballClub();

		model.put("footballClub", footballClub);
		model.put("isNew", true);
		return FootballClubController.VIEWS_CLUB_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/footballClubs/myClub/new") //CREAR CLUB - POST
	public String processCreationForm(@Valid final FootballClub footballClub, final BindingResult result, final Map<String, Object> model)
		throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, CredentialException, DateException {

		model.put("isNew", true);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		President thisUser = this.footballClubService.findPresidentByUsername(currentPrincipalName);

		if (result.hasErrors()) {

			return FootballClubController.VIEWS_CLUB_CREATE_OR_UPDATE_FORM;
		} else {
			try {

				footballClub.setPresident(thisUser);
				footballClub.setFans(0);
				footballClub.setMoney(100000000);
				footballClub.setStatus(false);
				this.footballClubService.saveFootballClub(footballClub);

			} catch (DuplicatedNameException ex) {
				result.rejectValue("name", "duplicate", "already exists");
				return FootballClubController.VIEWS_CLUB_CREATE_OR_UPDATE_FORM;
			} catch (DateException ex) {
				result.rejectValue("foundationDate", "code.error.validator.past", "past date");
				return FootballClubController.VIEWS_CLUB_CREATE_OR_UPDATE_FORM;
			}
			return "redirect:/footballClubs/myClub/" + currentPrincipalName;
		}
	}

	@GetMapping(value = "/footballClubs/myClub/{principalUsername}/edit") //EDITAR CLUB - GET
	public String initUpdateFootballClubForm(@PathVariable("principalUsername") final String principalUsername, final Model model) throws CredentialException {

		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(principalUsername);
		model.addAttribute(footballClub);
		model.addAttribute("isEditing", true);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		if (!currentPrincipalName.equals(principalUsername) || footballClub.getStatus() == true) { //SEGURIDAD
			throw new CredentialException("Forbidden Access");
		}

		return FootballClubController.VIEWS_CLUB_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/footballClubs/myClub/{principalUsername}/edit") //EDITAR CLUB - POST
	public String processUpdateFootballClubForm(@Valid final FootballClub footballClub, final BindingResult result, @PathVariable("principalUsername") final String principalUsername, final Model model)
		throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, CredentialException {

		model.addAttribute("isEditing", true);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		if (!currentPrincipalName.equals(principalUsername)) { //SEGURIDAD
			throw new CredentialException("Forbidden Access");
		}

		if (result.hasErrors()) {
			return FootballClubController.VIEWS_CLUB_CREATE_OR_UPDATE_FORM;
		} else {

			FootballClub footballClubToUpdate = this.footballClubService.findFootballClubByPresident(principalUsername);

			BeanUtils.copyProperties(footballClub, footballClubToUpdate, "id", "president", "fans", "money");

			try {

				//A la hora de editar no captura el DuplicatedNameException así que he hecho este en el controller
				Collection<FootballClub> clubs = this.footballClubService.findAll();
				clubs.remove(footballClubToUpdate);
				for (FootballClub a : clubs) {
					if (a.getName().toLowerCase().equals(footballClubToUpdate.getName().toLowerCase())) {
						result.rejectValue("name", "duplicate", "already exists");
						return FootballClubController.VIEWS_CLUB_CREATE_OR_UPDATE_FORM;
					}
				}

				this.footballClubService.saveFootballClub(footballClubToUpdate);

			} catch (DuplicatedNameException ex) {
				result.rejectValue("name", "duplicate", "already exists");
				return FootballClubController.VIEWS_CLUB_CREATE_OR_UPDATE_FORM;
			} catch (NumberOfPlayersAndCoachException ex) {
				result.rejectValue("status", "min5players", "5 y 1");
				return FootballClubController.VIEWS_CLUB_CREATE_OR_UPDATE_FORM;
			} catch (DateException ex) {
				result.rejectValue("foundationDate", "code.error.validator.past", "past date");
				return FootballClubController.VIEWS_CLUB_CREATE_OR_UPDATE_FORM;
			}

			return "redirect:/footballClubs/myClub/" + currentPrincipalName;
		}
	}

	@RequestMapping(value = "/footballClubs/myClub/{principalUsername}/delete") //BORRAR CLUB
	public String processDeleteForm() throws DataAccessException, CredentialException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		FootballClub thisFootballCLub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		if (!thisFootballCLub.getPresident().getUser().getUsername().equals(currentPrincipalName)) { //SEGURIDAD
			throw new CredentialException("Forbidden Access");
		}

		this.footballClubService.deleteFootballClub(thisFootballCLub);

		return "redirect:/footballClubs/myClub/" + currentPrincipalName;
	}

}
