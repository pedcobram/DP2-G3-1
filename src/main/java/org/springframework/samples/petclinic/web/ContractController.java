
package org.springframework.samples.petclinic.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.ContractPlayer;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.service.ContractService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.FootballPlayerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContractController {

	private static final String			VIEWS_CONTRACT_PLAYER_CREATE_OR_UPDATE_FORM	= "contracts/createOrUpdateContractPlayerForm";

	private final ContractService		contractService;
	private final FootballPlayerService	footballPlayerService;
	private final FootballClubService	footballClubService;


	@Autowired
	public ContractController(final ContractService contractService, final FootballPlayerService footballPlayerService, final FootballClubService footballClubService) {
		this.contractService = contractService;
		this.footballPlayerService = footballPlayerService;
		this.footballClubService = footballClubService;

	}

	@ModelAttribute("footballPlayer")
	public FootballPlayer findFootballPlayer(@PathVariable("footballPlayerId") final int footballPlayerId) {
		return this.footballPlayerService.findFootballPlayerById(footballPlayerId);
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
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
	public String initCreationForm(final Model model, final FootballPlayer footballPlayer) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);
		ContractPlayer contractPlayer = new ContractPlayer();

		footballPlayer.setClub(footballClub);

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
	public String processCreationForm(final FootballPlayer footballPlayer, @Valid final ContractPlayer contractPlayer, final BindingResult result, final ModelMap model) throws DataAccessException {

		if (result.hasErrors()) {
			model.addAttribute("contractPlayer", contractPlayer);
			return ContractController.VIEWS_CONTRACT_PLAYER_CREATE_OR_UPDATE_FORM;
		} else {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();

			FootballClub thisClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);
			footballPlayer.setClub(thisClub); //ESTO PROVOCA EL ERROR

			Date moment = new Date(System.currentTimeMillis() - 1);

			contractPlayer.setClub(thisClub);
			contractPlayer.setPlayer(footballPlayer);
			contractPlayer.setStartDate(moment);
			contractPlayer.setClause(10000000);

			this.contractService.saveContractPlayer(contractPlayer);

			//Si todo sale bien vamos a la vista de mi club
			return "redirect:/contractPlayer/" + footballPlayer.getId();
		}
	}

}
