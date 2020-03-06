
package org.springframework.samples.petclinic.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.ContractCommercial;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.service.ContractService;
import org.springframework.samples.petclinic.service.FootballClubService;
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
public class ContractCommercialController {

	private static final String			VIEWS_CONTRACT_COMMERCIAL_CREATE_OR_UPDATE_FORM	= "contracts/createOrUpdateContractCommercialForm";

	private final ContractService		contractService;
	private final FootballClubService	footballClubService;


	@Autowired
	public ContractCommercialController(final ContractService contractService, final FootballClubService footballClubService) {
		this.contractService = contractService;
		this.footballClubService = footballClubService;

	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	//Vista de Contrato Detallada de un commercial
	@GetMapping("/footballClub/contractCommercial/{contractCommercialId}")
	public ModelAndView showContractCommercial(@PathVariable("contractCommercialId") final int contractCommercialId) {

		//Creamos la vista de equipo con la url del archivo.jsp de la vista
		ModelAndView mav = new ModelAndView("contracts/contractCommercialDetails");

		//Añadimos los datos del contrato según la id del jugador
		mav.addObject(this.contractService.findContractCommercialById(contractCommercialId));

		return mav;
	}

	//Crear Contrato de Comercial - Get
	@GetMapping(value = "/footballClub/contractCommercials/new")
	public String initCreationForm(final Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);
		ContractCommercial contractCommercial = new ContractCommercial();

		model.addAttribute("contractCommercial", contractCommercial);
		model.addAttribute("clubName", footballClub.getName().toUpperCase());

		Date moment = new Date(System.currentTimeMillis() - 1);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String date = simpleDateFormat.format(moment);

		model.addAttribute("startDate", date);

		return ContractCommercialController.VIEWS_CONTRACT_COMMERCIAL_CREATE_OR_UPDATE_FORM;
	}

	//Crear Contrato de Commercial - Post
	@PostMapping(value = "/footballClub/contractCommercials/new")
	public String processCreationForm(@Valid final ContractCommercial contractCommercial, final BindingResult result, final ModelMap model) throws DataAccessException {

		if (result.hasErrors()) {
			model.addAttribute("contractCommercial", contractCommercial);
			return ContractCommercialController.VIEWS_CONTRACT_COMMERCIAL_CREATE_OR_UPDATE_FORM;
		} else {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();

			FootballClub thisClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

			Date moment = new Date(System.currentTimeMillis() - 1);

			contractCommercial.setClub(thisClub);
			contractCommercial.setStartDate(moment);
			contractCommercial.setClause(10000000);

			this.contractService.saveContractCommercial(contractCommercial);

			//Si todo sale bien vamos a la vista de mi club
			return "redirect:/myfootballClub/" + contractCommercial.getId();
		}
	}

}
