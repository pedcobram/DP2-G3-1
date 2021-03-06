
package org.springframework.samples.petclinic.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.CredentialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.ContractPlayer;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.PlayerTransferRequest;
import org.springframework.samples.petclinic.service.ContractPlayerService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.FootballPlayerService;
import org.springframework.samples.petclinic.service.PlayerTransferRequestService;
import org.springframework.samples.petclinic.service.exceptions.DateException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.samples.petclinic.service.exceptions.MoneyClubException;
import org.springframework.samples.petclinic.service.exceptions.NumberOfPlayersAndCoachException;
import org.springframework.samples.petclinic.service.exceptions.SalaryException;
import org.springframework.samples.petclinic.web.validators.ContractPlayerValidator;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContractPlayerController {

	private static final String					VIEWS_CONTRACT_PLAYER_CREATE_OR_UPDATE_FORM	= "contracts/createOrUpdateContractPlayerForm";

	@Autowired
	private final ContractPlayerService			contractService;

	@Autowired
	private final FootballPlayerService			footballPlayerService;

	@Autowired
	private final FootballClubService			footballClubService;

	@Autowired
	private final PlayerTransferRequestService	playerTransferRequestService;


	@Autowired
	public ContractPlayerController(final ContractPlayerService contractService, final FootballPlayerService footballPlayerService, final PlayerTransferRequestService playerTransferRequestService, final FootballClubService footballClubService) {
		this.contractService = contractService;
		this.footballPlayerService = footballPlayerService;
		this.footballClubService = footballClubService;
		this.playerTransferRequestService = playerTransferRequestService;

	}

	@InitBinder("contractPlayer")
	public void initContractPlayerBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new ContractPlayerValidator());
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/contractPlayer/list") //LISTA DE MIS CONTRATOS DE JUGADORES
	public String showContractPlayerList(final Map<String, Object> model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		if (footballClub == null) {
			return "footballClubs/myClubEmpty";
		}

		List<ContractPlayer> contracts = new ArrayList<>();
		contracts.addAll(this.contractService.findAllPlayerContractsByClubId(footballClub.getId()));
		model.put("contractPlayers", contracts);

		return "contracts/contractPlayerList";
	}

	@GetMapping("/contractPlayer/{footballPlayerId}") //VISTA DE CONTRATO DETALLADA
	public ModelAndView showContractPlayer(@PathVariable("footballPlayerId") final int footballPlayerId) throws CredentialException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);
		ContractPlayer contract = this.contractService.findContractPlayerByPlayerId(footballPlayerId);

		if (!contract.getClub().equals(footballClub)) { //SEGURIDAD
			throw new CredentialException();
		}

		ModelAndView mav = new ModelAndView("contracts/contractPlayerDetails");
		mav.addObject(this.contractService.findContractPlayerByPlayerId(footballPlayerId));

		return mav;
	}

	@GetMapping(value = "/contractPlayer/{footballPlayerId}/new") //FICHAR JUGADOR AGENTE LIBRE - GET
	public String initCreationForm(final Model model, @PathVariable("footballPlayerId") final int footballPlayerId) throws DataAccessException, DuplicatedNameException, CredentialException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		FootballPlayer footballPlayer = this.footballPlayerService.findFootballPlayerById(footballPlayerId);
		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		if (footballClub == null) {  //SEGURIDAD
			throw new CredentialException();
		}

		ContractPlayer contractPlayer = new ContractPlayer();

		Integer valor = footballPlayer.getValue();
		Integer salario = valor / 10;
		Integer clausula = valor / 2;

		model.addAttribute("salario", salario);
		model.addAttribute("valor", valor);
		model.addAttribute("clausula", clausula);

		model.addAttribute("contractPlayer", contractPlayer);
		model.addAttribute("playerName", footballPlayer.getFirstName().toUpperCase() + " " + footballPlayer.getLastName().toUpperCase());
		model.addAttribute("clubName", footballClub.getName().toUpperCase());

		Date moment = new Date(System.currentTimeMillis() - 1);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String date = simpleDateFormat.format(moment);

		model.addAttribute("startDate", date);

		return ContractPlayerController.VIEWS_CONTRACT_PLAYER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/contractPlayer/{footballPlayerId}/new") //FICHAR JUGADOR AGENTE LIBRE - POST
	public String processCreationForm(@Valid final ContractPlayer contractPlayer, final BindingResult result, @PathVariable("footballPlayerId") final int footballPlayerId, final Model model)
		throws DataAccessException, DuplicatedNameException, MoneyClubException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		FootballClub thisClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);
		FootballPlayer footballPlayer = this.footballPlayerService.findFootballPlayerById(footballPlayerId);

		Integer valor = footballPlayer.getValue();
		Integer salario = valor / 10;
		Integer clausula = valor / 2;

		Date moment3 = new Date(System.currentTimeMillis() - 1);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String date = simpleDateFormat.format(moment3);

		model.addAttribute("clausula", clausula);
		model.addAttribute("startDate", date);
		model.addAttribute("salario", salario);
		model.addAttribute("valor", valor);
		model.addAttribute("playerName", footballPlayer.getFirstName().toUpperCase() + " " + footballPlayer.getLastName().toUpperCase());
		model.addAttribute("clubName", thisClub.getName().toUpperCase());
		model.addAttribute("contractPlayer", contractPlayer);

		if (result.hasErrors()) {
			return ContractPlayerController.VIEWS_CONTRACT_PLAYER_CREATE_OR_UPDATE_FORM;
		} else {
			try {

				Date moment = new Date(System.currentTimeMillis() - 1);
				contractPlayer.setClub(thisClub);
				contractPlayer.setPlayer(footballPlayer);
				contractPlayer.setStartDate(moment);
				contractPlayer.setClause(clausula);

				this.contractService.saveContractPlayer(contractPlayer);
			} catch (NumberOfPlayersAndCoachException e) {
				result.rejectValue("clause", "max7players", "required");
				return ContractPlayerController.VIEWS_CONTRACT_PLAYER_CREATE_OR_UPDATE_FORM;
			} catch (MoneyClubException e) {
				result.rejectValue("salary", "code.error.validator.salary", "required");
				return ContractPlayerController.VIEWS_CONTRACT_PLAYER_CREATE_OR_UPDATE_FORM;
			} catch (SalaryException e) {
				result.rejectValue("salary", "code.error.validator.salaryPlayer", "required");
				return ContractPlayerController.VIEWS_CONTRACT_PLAYER_CREATE_OR_UPDATE_FORM;
			} catch (DateException e) {
				result.rejectValue("endDate", "code.error.validator.1YearContract", "required");
				return ContractPlayerController.VIEWS_CONTRACT_PLAYER_CREATE_OR_UPDATE_FORM;
			}

			//Si todo sale bien vamos a la vista de mi club
			return "redirect:/contractPlayer/" + footballPlayer.getId();
		}
	}

	//DESPEDIR JUGADOR (BORRAR CONTRATO)
	@RequestMapping(value = "/contractPlayer/{footballPlayerId}/delete")
	public String processDeleteForm(@PathVariable("footballPlayerId") final int footballPlayerId, final Model model) throws SalaryException, CredentialException, DataAccessException, MoneyClubException {

		//Obtenemos el username del usuario actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		ContractPlayer thisContract = this.contractService.findContractPlayerByPlayerId(footballPlayerId);
		FootballPlayer player = this.footballPlayerService.findFootballPlayerById(footballPlayerId);
		PlayerTransferRequest playerTransferRequest = this.playerTransferRequestService.findPlayerTransferRequestByPlayerIdAndStatusAccepted(footballPlayerId);

		if (playerTransferRequest != null) {
			playerTransferRequest.setContract(null);

			this.playerTransferRequestService.updatePlayerTransferRequest(playerTransferRequest);
		}

		if (player.getClub() == null) { //SEGURIDAD (Si el jugador es agente libre no se puede despedir)
			throw new CredentialException();
		}

		if (thisContract.getClub().getPresident().getUser().getUsername().compareTo(currentPrincipalName) != 0) { //SEGURIDAD
			throw new CredentialException();
		}

		try {
			this.contractService.deleteContract(thisContract);
		} catch (MoneyClubException e) {
			model.addAttribute("salaryError", true);
			model.addAttribute(player);
			model.addAttribute(thisContract);
			return "/contracts/contractPlayerDetails";
		}

		//Volvemos a la vista de mi club, en este caso sería la de "club empty"
		return "redirect:/footballClubs/myClub/" + currentPrincipalName;
	}

}
