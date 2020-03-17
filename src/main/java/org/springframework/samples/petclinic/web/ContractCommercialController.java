
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.ContractCommercial;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.service.ContractService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
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

	//Vista de la lista de jugadores
	@GetMapping(value = "/contractsCommercial")
	public String showContractCommercialList(final Map<String, Object> model) {

		//Creamos una colección de jugadores
		List<ContractCommercial> contracts = new ArrayList<>();

		//La llenamos con todos los equipos de la db
		contracts.addAll(this.contractService.findAllCommercialContracts());

		//Ponemos en el modelo la colección de equipos
		model.put("contractsCommercial", contracts);

		//Mandamos a la vista de listado de equipos
		return "contracts/contractCommercialList";
	}

	@GetMapping(value = "/contractsCommercial.xml")
	public @ResponseBody Collection<ContractCommercial> showResourcesContractCommercialList() {

		Collection<ContractCommercial> contracts = new HashSet<ContractCommercial>();
		contracts.addAll(this.contractService.findAllCommercialContracts());

		return contracts;
	}

	//Vista de Contrato Detallada de un commercial
	@GetMapping(value = "/contractsCommercial/{contractCommercialId}")
	public ModelAndView showContractCommercial(@PathVariable("contractCommercialId") final int contractCommercialId) {

		ModelAndView mav = new ModelAndView("contracts/contractCommercialDetails");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		mav.addObject("contractCommercial", this.contractService.findContractCommercialById(contractCommercialId));

		try {
			mav.addObject("footballClub", this.footballClubService.findFootballClubByPresident(currentPrincipalName));
		} catch (Exception e) {
			mav.addObject("footballClub", null);
		}

		return mav;
	}

	//Crear Contrato de Comercial - Get
	@GetMapping(value = "/contractsCommercial/{contractCommercialId}/addToMyClub")
	public String addContractToMyClub(@PathVariable("contractCommercialId") final int contractCommercialId) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);
		ContractCommercial contractCommercial = this.contractService.findContractCommercialById(contractCommercialId);

		contractCommercial.setClub(footballClub);

		this.contractService.saveContractCommercial(contractCommercial);

		return "redirect:/myfootballClub/" + currentPrincipalName;
	}

}
