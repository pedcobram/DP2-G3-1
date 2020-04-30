
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.security.auth.login.CredentialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.ContractPlayer;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.PlayerTransferRequest;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.samples.petclinic.service.ContractPlayerService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.FootballPlayerService;
import org.springframework.samples.petclinic.service.PlayerTransferRequestService;
import org.springframework.samples.petclinic.service.exceptions.DateException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.samples.petclinic.service.exceptions.MoneyClubException;
import org.springframework.samples.petclinic.service.exceptions.NumberOfPlayersAndCoachException;
import org.springframework.samples.petclinic.service.exceptions.SalaryException;
import org.springframework.samples.petclinic.service.exceptions.StatusException;
import org.springframework.samples.petclinic.service.exceptions.TooManyPlayerRequestsException;
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

	private final PlayerTransferRequestService	playerTransferRequestService;

	private final ContractPlayerService			contractPlayerService;

	private static final String					VIEWS_PLAYER_TRANSFER_REQUEST_CREATE_OR_UPDATE_FORM	= "playerTransferRequest/createOrUpdatePlayerTransferRequestForm";

	private static final String					VIEWS_PLAYER_TRANSFER_REQUEST_LIST					= "playerTransferRequest/playerTransferRequestSentList";

	private static final String					VIEWS_PLAYER_TRANSFER_REQUEST_RECEIVED				= "playerTransferRequest/playerTransferRequestReceivedList";


	@Autowired
	public PlayerTransferRequestController(final FootballPlayerService footballPlayerService, final FootballClubService footballClubService, final PlayerTransferRequestService playerTransferRequestService,
		final ContractPlayerService contractPlayerService) {
		this.footballClubService = footballClubService;
		this.footballPlayerService = footballPlayerService;
		this.playerTransferRequestService = playerTransferRequestService;
		this.contractPlayerService = contractPlayerService;
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
		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);
		ContractPlayer contractPlayer = this.contractPlayerService.findContractPlayerByPlayerId(playerId);

		ptr.setOffer(Long.valueOf(fp.getValue()));
		ptr.setStatus(RequestStatus.ON_HOLD);
		ptr.setFootballPlayer(fp);
		ptr.setClub(footballClub);
		ptr.setContract(contractPlayer);

		model.addAttribute("playerTransferRequest", ptr);

		return PlayerTransferRequestController.VIEWS_PLAYER_TRANSFER_REQUEST_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/transfers/players/request/{playerId}")
	public String processRequestTransferPlayer(final ModelMap model, @Valid final PlayerTransferRequest playerTransferRequest, final BindingResult result, @PathVariable("playerId") final int playerId) throws DataAccessException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		FootballPlayer fp = this.footballPlayerService.findFootballPlayerById(playerId);
		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);
		ContractPlayer contractPlayer = this.contractPlayerService.findContractPlayerByPlayerId(playerId);

		playerTransferRequest.setOffer(playerTransferRequest.getOffer());
		playerTransferRequest.setStatus(RequestStatus.ON_HOLD);
		playerTransferRequest.setFootballPlayer(fp);
		playerTransferRequest.setClub(footballClub);
		playerTransferRequest.setContract(contractPlayer);
		playerTransferRequest.setContractTime(playerTransferRequest.getContractTime());

		model.addAttribute("currentClubFunds", footballClub.getMoney());

		if (result.hasErrors()) {
			return PlayerTransferRequestController.VIEWS_PLAYER_TRANSFER_REQUEST_CREATE_OR_UPDATE_FORM;
		} else {

			try {

				this.playerTransferRequestService.savePlayerTransferRequest(playerTransferRequest);

			} catch (MoneyClubException mce) {
				result.rejectValue("offer", "code.error.validator.notEnoughMoneyToMakeOffer", "Your club does not have enough funds to make this offer");

				return PlayerTransferRequestController.VIEWS_PLAYER_TRANSFER_REQUEST_CREATE_OR_UPDATE_FORM;

			} catch (TooManyPlayerRequestsException tmpre) {
				result.rejectValue("offer", "code.error.validator.tooManyPlayerRequestsException", "You can only have one open player request at a time");

				return PlayerTransferRequestController.VIEWS_PLAYER_TRANSFER_REQUEST_CREATE_OR_UPDATE_FORM;
			} catch (SalaryException se) {
				result.rejectValue("offer", "code.error.validator.incorrectSalary", "Salary has to be more than value/10 and less than its total value");

				return PlayerTransferRequestController.VIEWS_PLAYER_TRANSFER_REQUEST_CREATE_OR_UPDATE_FORM;
			}

			return "redirect:/transfers/players";
		}
	}

	@GetMapping(value = "/transfers/players/requests/sent")
	public String viewTransferPlayerSentList(final Model model) throws DataAccessException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		List<PlayerTransferRequest> ptrs = new ArrayList<>();

		ptrs.addAll(this.playerTransferRequestService.findPlayerTransferRequestByPresident(currentPrincipalName));

		model.addAttribute("playerTransferRequests", ptrs);

		return PlayerTransferRequestController.VIEWS_PLAYER_TRANSFER_REQUEST_LIST;
	}

	@GetMapping(value = "/transfers/players/requests/sent/delete/{requestId}")
	public String deleteTransferPlayerRequest(final Model model, @PathVariable("requestId") final int requestId) throws CredentialException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		PlayerTransferRequest playerTransferRequest = this.playerTransferRequestService.findPlayerTransferRequestById(requestId);

		if (playerTransferRequest.getClub().getPresident().getUser().getUsername().compareTo(currentPrincipalName) != 0) {
			throw new CredentialException();
		}

		this.playerTransferRequestService.deletePlayerTransferRequest(playerTransferRequest);

		return "redirect:/transfers/players/requests/sent";
	}

	@GetMapping(value = "/transfers/players/requests/received")
	public String viewTransferPlayerReceivedList(final Model model) throws DataAccessException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		FootballClub fc = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		List<PlayerTransferRequest> ptrs = new ArrayList<>();

		ptrs.addAll(this.playerTransferRequestService.findPlayerTransferRequestsReceived(fc.getId()));

		model.addAttribute("playerTransferRequests", ptrs);

		return PlayerTransferRequestController.VIEWS_PLAYER_TRANSFER_REQUEST_RECEIVED;
	}

	@GetMapping(value = "/transfers/players/requests/received/reject/{playerId}")
	public String rejectTransferPlayerRequest(final Model model, @PathVariable("playerId") final int playerId) throws DataAccessException, CredentialException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		PlayerTransferRequest ptr = this.playerTransferRequestService.findPlayerTransferRequestByPlayerId(playerId);

		if (ptr.getFootballPlayer().getClub().getPresident().getUser().getUsername().compareTo(currentPrincipalName) != 0) {
			throw new CredentialException();
		}

		ptr.setStatus(RequestStatus.REFUSE);

		try {
			this.playerTransferRequestService.savePlayerTransferRequest(ptr);
		} catch (MoneyClubException | TooManyPlayerRequestsException | SalaryException e) {
			return "redirect:/localhost:8080/";
		}

		return "redirect:/transfers/players/requests/received";
	}

	@GetMapping(value = "/transfers/players/requests/received/accept/{playerId}")
	public String acceptTransferPlayerRequest(final Model model, @PathVariable("playerId") final int playerId) throws CredentialException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		PlayerTransferRequest ptr = this.playerTransferRequestService.findPlayerTransferRequestByPlayerId(playerId);

		if (ptr.getFootballPlayer().getClub().getPresident().getUser().getUsername().compareTo(currentPrincipalName) != 0) {
			throw new CredentialException();
		}

		FootballPlayer footballPlayer = this.footballPlayerService.findFootballPlayerById(playerId);
		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(ptr.getClub().getPresident().getUser().getUsername());

		// Aceptamos la peticion
		ptr.setId(ptr.getId());
		ptr.setStatus(RequestStatus.ACCEPT);

		try {
			this.playerTransferRequestService.savePlayerTransferRequest(ptr);
		} catch (DataAccessException | MoneyClubException | TooManyPlayerRequestsException e3) {
			throw new CredentialException();
		} catch (SalaryException se) {
			return "redirect:/aaaaaaaaa";
		}

		ContractPlayer contract = this.contractPlayerService.findContractPlayerByPlayerId(playerId);

		//Restamos al club destino la cantidad pagada
		footballClub.setId(footballClub.getId());
		footballClub.setMoney(footballClub.getMoney() - ptr.getOffer().intValue() - contract.getClause()); // - contract.getClause()

		try {
			this.footballClubService.saveFootballClub(footballClub);
		} catch (DataAccessException | DuplicatedNameException | NumberOfPlayersAndCoachException e2) {
			throw new CredentialException();
		} catch (DateException de) {
			de.getMessage();
		}

		FootballClub fc1 = this.footballClubService.findFootballClubByPresident(ptr.getClub().getPresident().getUser().getUsername());

		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();

		Calendar calendar2 = Calendar.getInstance();

		int contractTime = ptr.getContractTime();

		calendar2.add(Calendar.YEAR, contractTime);
		Date datePlusContractTime = calendar2.getTime();

		contract.setId(contract.getId());
		contract.setClub(fc1);
		contract.setStartDate(currentDate);
		contract.setEndDate(datePlusContractTime);
		contract.setSalary(ptr.getOffer().intValue());
		contract.setPlayer(footballPlayer);
		contract.setClause(ptr.getOffer().intValue() * 6);

		try {
			this.contractPlayerService.saveContractPlayer(contract);
		} catch (DataAccessException | MoneyClubException | NumberOfPlayersAndCoachException | SalaryException | DateException e1) {
			throw new CredentialException();
		}

		//Asignamos al jugador al nuevo club
		footballPlayer.setId(footballPlayer.getId());
		footballPlayer.setClub(fc1);

		try {
			this.footballPlayerService.updateFootballPlayer(footballPlayer, contract);
		} catch (DataAccessException | DuplicatedNameException | NumberOfPlayersAndCoachException | MoneyClubException | StatusException e) {
			throw new CredentialException();
		} catch (DateException e1) {
			e1.getMessage();
		} catch (SalaryException se) {
			se.getMessage();
		}

		return "redirect:/transfers/players/requests/received";
	}

}
