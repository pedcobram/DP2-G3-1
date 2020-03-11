
package org.springframework.samples.petclinic.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.ContractPlayer;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.service.ContractService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.FootballPlayerService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContractController {

	private static final String			VIEWS_CONTRACT_PLAYER_CREATE_OR_UPDATE_FORM	= "contracts/createOrUpdateContractPlayerForm";

	@Autowired
	private final ContractService		contractService;

	@Autowired
	private final FootballPlayerService	footballPlayerService;

	@Autowired
	private final FootballClubService	footballClubService;


	@Autowired
	public ContractController(final ContractService contractService, final FootballPlayerService footballPlayerService, final FootballClubService footballClubService) {
		this.contractService = contractService;
		this.footballPlayerService = footballPlayerService;
		this.footballClubService = footballClubService;

	}

	@InitBinder("contractPlayer")
	public void initContractPlayerBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new ContractPlayerValidator());
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	//Vista de la lista de contratos
	@GetMapping(value = "/contractPlayer/list")
	public String showContractPlayerList(final Map<String, Object> model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		List<ContractPlayer> contracts = new ArrayList<>();

		contracts.addAll(this.contractService.findAllPlayerContractsByClubId(footballClub.getId()));

		model.put("contractPlayers", contracts);

		return "contracts/contractPlayerList";
	}

	//Vista de Contrato Detallada de un jugador
	@GetMapping("/contractPlayer/{footballPlayerId}")
	public ModelAndView showContractPlayer(@PathVariable("footballPlayerId") final int footballPlayerId) {

		ModelAndView mav = new ModelAndView("contracts/contractPlayerDetails");

		mav.addObject(this.contractService.findContractPlayerByPlayerId(footballPlayerId));

		return mav;
	}

	//Crear Contrato de Jugador - Get
	@GetMapping(value = "/footballPlayers/{footballPlayerId}/contractPlayer/new")
	public String initCreationForm(final Model model, @PathVariable("footballPlayerId") final int footballPlayerId) throws DataAccessException, DuplicatedNameException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		FootballPlayer footballPlayer = this.footballPlayerService.findFootballPlayerById(footballPlayerId);
		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);
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

		return ContractController.VIEWS_CONTRACT_PLAYER_CREATE_OR_UPDATE_FORM;
	}

	//Crear Contrato de Jugador - Post
	@PostMapping(value = "/footballPlayers/{footballPlayerId}/contractPlayer/new")
	public String processCreationForm(@Valid final ContractPlayer contractPlayer, final BindingResult result, @PathVariable("footballPlayerId") final int footballPlayerId, final Model model) throws DataAccessException, DuplicatedNameException {

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

		//Validación número de jugadores

		Collection<FootballPlayer> cp = this.footballPlayerService.findAllClubFootballPlayers(thisClub.getId());

		if (cp.size() >= 7) {
			result.rejectValue("position", "max7players");
			return ContractController.VIEWS_CONTRACT_PLAYER_CREATE_OR_UPDATE_FORM;
		}

		if (result.hasErrors()) {
			model.addAttribute("contractPlayer", contractPlayer);
			return ContractController.VIEWS_CONTRACT_PLAYER_CREATE_OR_UPDATE_FORM;
		} else {

			//Validación de salario del jugador
			if (contractPlayer.getSalary() < salario) {
				result.rejectValue("salary", "code.error.validator.salaryMin", "required");
				model.addAttribute("contractPlayer", contractPlayer);
				return ContractController.VIEWS_CONTRACT_PLAYER_CREATE_OR_UPDATE_FORM;
			}

			//Validación de salario del jugador
			if (contractPlayer.getSalary() > valor) {
				result.rejectValue("salary", "code.error.validator.salaryMax", "required");
				model.addAttribute("contractPlayer", contractPlayer);
				return ContractController.VIEWS_CONTRACT_PLAYER_CREATE_OR_UPDATE_FORM;
			}

			footballPlayer.setClub(thisClub);
			//			thisClub.setMoney(thisClub.getMoney() - contractPlayer.getSalary());

			Date moment = new Date(System.currentTimeMillis() - 1);
			contractPlayer.setClub(thisClub);
			contractPlayer.setPlayer(footballPlayer);
			contractPlayer.setStartDate(moment);
			contractPlayer.setClause(clausula);

			this.contractService.saveContractPlayer(contractPlayer);

			//Si todo sale bien vamos a la vista de mi club
			return "redirect:/contractPlayer/" + footballPlayer.getId();
		}
	}

}
