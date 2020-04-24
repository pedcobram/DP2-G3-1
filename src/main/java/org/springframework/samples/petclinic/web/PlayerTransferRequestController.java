
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.PlayerTransferRequest;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.FootballPlayerService;
import org.springframework.samples.petclinic.service.PlayerTransferRequestService;
import org.springframework.samples.petclinic.service.PresidentService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PlayerTransferRequestController {

	private final FootballClubService			footballClubService;

	private final FootballPlayerService			footballPlayerService;

	private final PresidentService				presidentService;

	private final PlayerTransferRequestService	playerTransferRequestService;

	private static final String					VIEWS_PLAYER_TRANSFER_REQUEST_CREATE_OR_UPDATE_FORM	= "playerTransferRequest/createOrUpdatePlayerTransferRequestForm";

	private static final String					VIEWS_PLAYER_TRANSFER_REQUEST_LIST					= "playerTransferRequest/playerTransferRequestSentList";


	@Autowired
	public PlayerTransferRequestController(final FootballPlayerService footballPlayerService, final FootballClubService footballClubService, final PresidentService presidentService, final PlayerTransferRequestService playerTransferRequestService) {
		this.footballClubService = footballClubService;
		this.footballPlayerService = footballPlayerService;
		this.presidentService = presidentService;
		this.playerTransferRequestService = playerTransferRequestService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/transfers/players/request/{playerId}")
	public String requestTransferPlayer(@PathVariable("playerId") final int playerId, final Model model) throws DataAccessException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		PlayerTransferRequest ptr = new PlayerTransferRequest();

		FootballPlayer fp = this.footballPlayerService.findFootballPlayerById(playerId);
		President president = this.presidentService.findPresidentByUsername(currentPrincipalName);
		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		ptr.setPlayerValue(fp.getValue());
		ptr.setStatus(RequestStatus.ON_HOLD);
		ptr.setFootballPlayer(fp);
		ptr.setPresident(president);

		model.addAttribute("playerTransferRequest", ptr);
		model.addAttribute("fundsRemaining", footballClub.getMoney() - fp.getValue());

		return PlayerTransferRequestController.VIEWS_PLAYER_TRANSFER_REQUEST_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/transfers/players/request/{playerId}")
	public String processRequestTransferPlayer(@Valid final PlayerTransferRequest playerTransferRequest, @PathVariable("playerId") final int playerId, final BindingResult result, final ModelMap model) throws DataAccessException {

		if (result.hasErrors()) {
			return PlayerTransferRequestController.VIEWS_PLAYER_TRANSFER_REQUEST_CREATE_OR_UPDATE_FORM;
		} else {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();

			FootballPlayer fp = this.footballPlayerService.findFootballPlayerById(playerId);
			President president = this.presidentService.findPresidentByUsername(currentPrincipalName);

			playerTransferRequest.setPlayerValue(playerTransferRequest.getPlayerValue());
			playerTransferRequest.setStatus(RequestStatus.ON_HOLD);
			playerTransferRequest.setFootballPlayer(fp);
			playerTransferRequest.setPresident(president);

			try {

				this.playerTransferRequestService.savePlayerTransferRequest(playerTransferRequest);

			} catch (DataAccessException ide) {
				//result.rejectValue("matchDate", "code.error.validator.atleast1monthinadvance", "Match date must be at least one month from now");
				return PlayerTransferRequestController.VIEWS_PLAYER_TRANSFER_REQUEST_CREATE_OR_UPDATE_FORM;
			}

			return "redirect:/transfers/players";
		}
	}

	@GetMapping(value = "/transfers/players/requests/sent")
	public String transferPlayerSent(final Model model) throws DataAccessException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		List<PlayerTransferRequest> ptrs = new ArrayList<>();

		ptrs.addAll(this.playerTransferRequestService.findPlayerTransferRequestByPresident(currentPrincipalName));

		model.addAttribute("playerTransferRequests", ptrs);

		return PlayerTransferRequestController.VIEWS_PLAYER_TRANSFER_REQUEST_LIST;
	}

	@GetMapping(value = "/transfers/players/requests/sent/edit/{requestId}")
	public String initUpdateCompetitionAdminForm(final Model model, @PathVariable("requestId") final int requestId) {

		PlayerTransferRequest playerTransferRequest = this.playerTransferRequestService.findPlayerTransferRequestById(requestId);

		model.addAttribute(playerTransferRequest);

		return PlayerTransferRequestController.VIEWS_PLAYER_TRANSFER_REQUEST_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/transfers/players/requests/sent/edit/{requestId}")
	public String processUpdateCompetitionAdminForm(@Valid final PlayerTransferRequest playerTransferRequest, final BindingResult result, @PathVariable("requestId") final int requestId) {
		if (result.hasErrors()) {
			return PlayerTransferRequestController.VIEWS_PLAYER_TRANSFER_REQUEST_CREATE_OR_UPDATE_FORM;
		} else {

			//			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			//			String currentPrincipalName = authentication.getName();

			PlayerTransferRequest last_id = this.playerTransferRequestService.findPlayerTransferRequestById(requestId);

			playerTransferRequest.setId(last_id.getId());
			playerTransferRequest.setStatus(RequestStatus.ON_HOLD);
			playerTransferRequest.setFootballPlayer(last_id.getFootballPlayer());
			playerTransferRequest.setPresident(last_id.getPresident());

			this.playerTransferRequestService.savePlayerTransferRequest(playerTransferRequest);
			return "redirect:/transfers/players/requests/sent";
		}
	}

}
