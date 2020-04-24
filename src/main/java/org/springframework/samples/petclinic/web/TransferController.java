
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.security.auth.login.CredentialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Coach;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.service.CoachService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.FootballPlayerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;

@Controller
public class TransferController {

	private final FootballClubService	footballClubService;

	private final FootballPlayerService	footballPlayerService;

	private final CoachService			coachService;


	@Autowired
	public TransferController(final FootballPlayerService footballPlayerService, final CoachService coachService, final FootballClubService footballClubService) {
		this.footballClubService = footballClubService;
		this.coachService = coachService;
		this.footballPlayerService = footballPlayerService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/transfers/panel") //PANEL DE FICHAJES
	public String showTransferPanel(final Map<String, Object> model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		if (footballClub == null) {
			return "footballClubs/myClubEmpty";
		}

		model.put("status", footballClub.getStatus());

		return "transfers/transferPanel";
	}

	@GetMapping(value = "/transfers/coachs") //LISTA DE ENTRENADORES
	public String showCoachList(final Map<String, Object> model) throws CredentialException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		if (footballClub == null) {
			return "footballClubs/myClubEmpty";
		}

		if (footballClub.getStatus() == false) {
			throw new CredentialException();
		}

		Collection<Coach> coachs = new ArrayList<>();
		coachs.addAll(this.coachService.findAllCoachs());
		coachs.removeAll(this.coachService.findAllCoachsFA());
		coachs.remove(this.coachService.findCoachByClubId(footballClub.getId()));
		model.put("coachs", coachs);

		return "coachs/coachList";

	}

	@GetMapping(value = "/transfers/coachs/free-agents") //LISTA DE ENTRENADORES LIBRES
	public String showCoachFAList(final Map<String, Object> model) throws CredentialException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		if (footballClub == null) {
			return "footballClubs/myClubEmpty";
		}

		Collection<Coach> coachs = new ArrayList<>();
		coachs.addAll(this.coachService.findAllCoachsFA());
		model.put("coachs", coachs);

		return "coachs/coachList";

	}

	@GetMapping(value = "/transfers/players") //LISTA DE JUGADORES
	public String showPlayerList(final Map<String, Object> model) throws CredentialException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		if (footballClub == null) {
			return "footballClubs/myClubEmpty";
		}

		if (footballClub.getStatus() == false) {
			throw new CredentialException();
		}

		Collection<FootballPlayer> footballPlayers = new ArrayList<>();
		footballPlayers.addAll(this.footballPlayerService.findAllFootballPlayers());
		footballPlayers.removeAll(this.footballPlayerService.findAllFootballPlayersFA());
		footballPlayers.removeAll(this.footballPlayerService.findAllClubFootballPlayers(footballClub.getId()));
		model.put("footballPlayers", footballPlayers);

		return "transfers/footballPlayerList";

	}

	@GetMapping(value = "/transfers/players/free-agents") //LISTA DE JUGADORES LIBRES
	public String showPlayerFAList(final Map<String, Object> model) throws CredentialException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		if (footballClub == null) {
			return "footballClubs/myClubEmpty";
		}

		Collection<FootballPlayer> footballPlayers = new ArrayList<>();
		footballPlayers.addAll(this.footballPlayerService.findAllFootballPlayersFA());
		model.put("footballPlayers", footballPlayers);

		return "transfers/footballPlayerList";
	}

}
