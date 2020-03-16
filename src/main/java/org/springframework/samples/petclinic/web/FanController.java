
package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.Fan;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.service.AuthenticatedService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.FanService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FanController {

	private static final String			VIEWS_FAN_CREATE_OR_UPDATE_FORM	= "fan/createOrUpdateFanForm";

	private final AuthenticatedService	authenticatedService;
	private final FootballClubService	footballClubService;
	private final FanService			fanService;


	@Autowired
	public FanController(final FootballClubService footballClubService, final AuthenticatedService authenticatedService, final FanService fanService, final UserService userService, final AuthoritiesService authoritiesService) {
		this.fanService = fanService;
		this.authenticatedService = authenticatedService;
		this.footballClubService = footballClubService;

	}

	@GetMapping(value = "/footballClub/{clubId}/fan/new")
	public String initCreationForm(@PathVariable final Integer clubId, final Map<String, Object> model) {
		//Obtenemos el username actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		//Obtenemos el authenticated actual conectado
		Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(currentPrincipalName);
		//Obtenemos club
		FootballClub thisClub = this.footballClubService.findFootballClubById(clubId);

		//Comprobamos que no es fan de otro equipo
		if (this.fanService.existFan(thisUser.getId())) {
			// si ya es fan vuelve a la vista de club con existFan igual a true
			model.put("existFan", true);
			model.put("footballClub", this.footballClubService.findFootballClubById(clubId));

			return "footballClubs/footballClubDetails";
		} else {
			//inicializamos el fan
			Fan f = new Fan();
			f.setUser(thisUser);
			f.setClub(thisClub);

			model.put("fan", f);

			return FanController.VIEWS_FAN_CREATE_OR_UPDATE_FORM;

		}

	}

	@PostMapping(value = "/footballClub/{clubId}/fan/new")
	public String processCreationForm(@PathVariable final Integer clubId, @Valid final Fan f, final BindingResult result) {
		if (result.hasErrors()) {
			return FanController.VIEWS_FAN_CREATE_OR_UPDATE_FORM;
		} else {
			//Obtenemos el username actual conectado
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();
			//Obtenemos el authenticated actual conectado
			Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(currentPrincipalName);
			//Obtenemos club
			FootballClub thisClub = this.footballClubService.findFootballClubById(clubId);
			//Creamos el fan

			f.setUser(thisUser);
			f.setClub(thisClub);
			f.setVip(true);

			this.fanService.saveFan(f);

			return "redirect:/";
		}
	}

	@GetMapping(value = "/footballClub/{clubId}/createFanNoVip")
	public String createFanNoVip(@PathVariable final Integer clubId) {

		//Obtenemos el username actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		//Creamos el fan
		Fan f = new Fan();

		//Obtenemos el authenticated actual conectado
		Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(currentPrincipalName);

		//Obtenemos el club del que quiere ser fan
		FootballClub club = this.footballClubService.findFootballClubById(clubId);

		//Añadimos los datos del user y el club al fan
		f.setClub(club);
		f.setUser(thisUser);

		//Guardamos en la db el nuevo presidente
		this.fanService.saveFan(f);

		//Redirigimos a la vista welcome
		return "redirect:/";
	}

}
