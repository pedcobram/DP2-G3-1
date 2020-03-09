
package org.springframework.samples.petclinic.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.ui.ModelMap;
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

		//Creamos la vista de equipo con la url del archivo.jsp de la vista
		ModelAndView mav = new ModelAndView("contracts/contractPlayerDetails");

		//Añadimos los datos del contrato según la id del jugador
		mav.addObject(this.contractService.findContractPlayerByPlayerId(footballPlayerId));

		return mav;
	}

	//Crear Contrato de Jugador - Get
	@GetMapping(value = "/footballPlayers/{footballPlayerId}/contractPlayer/new")
	public String initCreationForm(final Model model, @PathVariable("footballPlayerId") final int footballPlayerId) throws DataAccessException, DuplicatedNameException {

		FootballPlayer footballPlayer = this.footballPlayerService.findFootballPlayerById(footballPlayerId);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);
		ContractPlayer contractPlayer = new ContractPlayer();

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
	@SuppressWarnings("deprecation")
	@PostMapping(value = "/footballPlayers/{footballPlayerId}/contractPlayer/new")
	public String processCreationForm(@Valid final ContractPlayer contractPlayer, @PathVariable("footballPlayerId") final int footballPlayerId, final BindingResult result, final ModelMap model) throws DataAccessException, DuplicatedNameException {

		if (result.hasErrors()) {
			model.addAttribute("contractPlayer", contractPlayer);
			return ContractController.VIEWS_CONTRACT_PLAYER_CREATE_OR_UPDATE_FORM;
		} else {

			Date now = new Date(System.currentTimeMillis() - 1);

			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			cal.add(Calendar.YEAR, +1);

			now = cal.getTime();

			//Validación EndDate: Me saltaba error haciendolas con un ContractValidator
			if (contractPlayer.getEndDate() == null || contractPlayer.getEndDate().before(now)) {
				result.rejectValue("endDate", "code.error.validator.requiredAnd1YearContract", "required");
				return ContractController.VIEWS_CONTRACT_PLAYER_CREATE_OR_UPDATE_FORM;
			}

			//TODO: //Validación de salario del jugador

			FootballPlayer footballPlayer = this.footballPlayerService.findFootballPlayerById(footballPlayerId);
			//	Integer valor = footballPlayer.getValue();
			//	Integer salario = valor / 10;

			FootballPlayer v2 = new FootballPlayer();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();

			FootballClub thisClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

			v2.setClub(thisClub);
			v2.setFirstName(footballPlayer.getFirstName());
			v2.setLastName(footballPlayer.getLastName());
			v2.setPosition(footballPlayer.getPosition());
			v2.setValue(footballPlayer.getValue());
			Date a = footballPlayer.getBirthDate();
			Date moment2 = new Date(a.getTime());
			v2.setBirthDate(moment2);

			this.footballPlayerService.delete(footballPlayer);

			this.footballPlayerService.saveFootballPlayer(v2);

			Date moment = new Date(System.currentTimeMillis() - 1);
			contractPlayer.setClub(thisClub);
			contractPlayer.setPlayer(v2);
			contractPlayer.setStartDate(moment);
			contractPlayer.setClause(10000000);

			this.contractService.saveContractPlayer(contractPlayer);

			//Si todo sale bien vamos a la vista de mi club
			return "redirect:/contractPlayer/" + v2.getId();
		}
	}

}
