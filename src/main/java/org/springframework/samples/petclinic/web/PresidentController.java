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
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.ContractPlayer;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.service.AuthenticatedService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ContractService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.FootballPlayerService;
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

	@Autowired
	private final AuthenticatedService	authenticatedService;

	@Autowired
	private final PresidentService		presidentService;

	@Autowired
	private final FootballClubService	footballClubService;

	@Autowired
	private final FootballPlayerService	footballPlayerService;

	@Autowired
	private final ContractService		contractService;


	@Autowired
	public PresidentController(final PresidentService presidentService, final AuthenticatedService authenticatedService, final FootballClubService footballClubService, final UserService userService, final AuthoritiesService authoritiesService,
		final FootballPlayerService footballPlayerService, final ContractService contractService) {
		this.presidentService = presidentService;
		this.authenticatedService = authenticatedService;
		this.footballClubService = footballClubService;
		this.footballPlayerService = footballPlayerService;
		this.contractService = contractService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@RequestMapping(value = "/createPresident")
	public String createPresident() {

		//Obtenemos el username actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		//Creamos el presidente
		President president = new President();

		//Obtenemos el authenticated actual conectado
		Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(currentPrincipalName);

		//Añadimos los datos del user al presidente
		president.setFirstName(thisUser.getFirstName());
		president.setLastName(thisUser.getLastName());
		president.setDni(thisUser.getDni());
		president.setEmail(thisUser.getEmail());
		president.setTelephone(thisUser.getTelephone());
		president.setUser(thisUser.getUser());

		//Guardamos en la db el nuevo presidente
		this.presidentService.savePresident(president);
		this.authenticatedService.deleteAuthenticated(thisUser);

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

		//Buscamos si tiene un Club
		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		//Borramos el Club si existe
		if (footballClub != null) {

			//Borramos los contratos del equipo
			Collection<ContractPlayer> contracts = this.contractService.findAllPlayerContractsByClubId(footballClub.getId());

			for (ContractPlayer a : contracts) {
				this.contractService.deleteContract(a);
			}

			//Pasamos a los jugadores a free agents
			Collection<FootballPlayer> players = this.footballPlayerService.findAllClubFootballPlayers(footballClub.getId());

			for (FootballPlayer a : players) {
				a.setClub(null);
			}

			this.footballClubService.deleteFootballClub(footballClub);
		}

		//Pasamos a ser un Authenticated
		Authenticated user = new Authenticated();
		user.setFirstName(president.getFirstName());
		user.setLastName(president.getLastName());
		user.setDni(president.getDni());
		user.setEmail(president.getEmail());
		user.setTelephone(president.getTelephone());
		user.setUser(president.getUser());
		this.authenticatedService.saveAuthenticated(user);

		//Guardamos en la db el nuevo presidente
		this.presidentService.deletePresident(president);

		//CON ESTO CONSEGUIMOS QUE NO HAGA FALTA RELOGUEAR PARA GANAR LOS PRIVILEGIOS DE PRESIDENTE

		Authentication reAuth = new UsernamePasswordAuthenticationToken(currentPrincipalName, user.getUser().getPassword());
		SecurityContextHolder.getContext().setAuthentication(reAuth);

		//Redirigimos a la vista del perfil del presidente
		return "redirect:/myProfile/" + currentPrincipalName;
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
