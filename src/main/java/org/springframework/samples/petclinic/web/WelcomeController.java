
package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.service.AuthenticatedService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.FanService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

	private final AuthenticatedService	authenticatedService;
	private final FootballClubService	footballClubService;
	private final FanService			fanService;


	@Autowired
	public WelcomeController(final FootballClubService footballClubService, final AuthenticatedService authenticatedService, final FanService fanService, final UserService userService, final AuthoritiesService authoritiesService) {
		this.fanService = fanService;
		this.authenticatedService = authenticatedService;
		this.footballClubService = footballClubService;

	}

	@GetMapping({
		"/", "/welcome"
	})
	public String welcome(final Map<String, Object> model) {

		//Obtenemos el username actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		if (authentication.isAuthenticated()) {
			//Obtenemos el authenticated actual conectado
			Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(currentPrincipalName);
			if (this.fanService.existFan(thisUser.getId())) {
				boolean VIP = this.fanService.findByUserId(thisUser.getId()).isVip();
				model.put("isVip", VIP);
				model.put("isFan", true);

			}
		}

		return "welcome";
	}
}
