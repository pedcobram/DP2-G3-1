
package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.Fan;
import org.springframework.samples.petclinic.service.AuthenticatedService;
import org.springframework.samples.petclinic.service.FanService;
import org.springframework.samples.petclinic.service.MatchRecordService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

	private final AuthenticatedService	authenticatedService;
	private final MatchRecordService	matchRecordService;
	private final FanService			fanService;


	@Autowired
	public WelcomeController(final MatchRecordService matchRecordService, final AuthenticatedService authenticatedService, final FanService fanService) {
		this.fanService = fanService;
		this.authenticatedService = authenticatedService;
		this.matchRecordService = matchRecordService;
	}

	@GetMapping({
		"/", "/welcome"
	})
	public String welcome(final Map<String, Object> model) {

		//bloque try por si el usuario no esta logueado
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (authentication.isAuthenticated()) {

				//Obtenemos el authenticated actual conectado
				String currentPrincipalName = authentication.getName();
				Authenticated thisUser = this.authenticatedService.findAuthenticatedByUsername(currentPrincipalName);
				//Comprobamos si es Fan y si es asi comprobamos si es VIP
				if (this.fanService.existFan(thisUser.getId())) {
					Fan f = this.fanService.findByUserId(thisUser.getId());
					boolean VIP = f.isVip();
					model.put("isVip", VIP);
					model.put("isFan", true);
					//info, sobre el club que es fan, que queramos mostrar en inicio
					model.put("club", f.getClub());
					model.put("lastMatches", this.matchRecordService.findLastMatches(f.getClub().getId()));

				}
			}
			return "welcome";

		} catch (NullPointerException n) {
			// si no esta logueado va la vista Welcome con isVip y isFan a FALSE
			model.put("isVip", false);
			model.put("isFan", false);

			return "welcome";

		}
	}
}
