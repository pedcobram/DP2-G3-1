
package org.springframework.samples.petclinic.web;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FanController {

	private final AuthenticatedService	authenticatedService;
	private final FootballClubService	footballClubService;
	private final FanService			fanService;


	@Autowired
	public FanController(final FootballClubService footballClubService, final AuthenticatedService authenticatedService, final FanService fanService, final UserService userService, final AuthoritiesService authoritiesService) {
		this.fanService = fanService;
		this.authenticatedService = authenticatedService;
		this.footballClubService = footballClubService;

	}

	@GetMapping(value = "/footballClub/{clubId}/createFan")
	public String createFan(@PathVariable final Integer clubId) {

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

		//Redirigimos a la vista del perfil del presidente
		return "redirect:/";
	}

}
