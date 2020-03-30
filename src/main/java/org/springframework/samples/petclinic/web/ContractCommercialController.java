
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.CredentialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.ContractCommercial;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.service.ContractCommercialService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.exceptions.DateException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.samples.petclinic.service.exceptions.NoMultipleContractCommercialException;
import org.springframework.samples.petclinic.service.exceptions.NoStealContractCommercialException;
import org.springframework.samples.petclinic.service.exceptions.NotEnoughMoneyException;
import org.springframework.samples.petclinic.service.exceptions.NumberOfPlayersAndCoachException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContractCommercialController {

	private final ContractCommercialService	contractService;
	private final FootballClubService		footballClubService;


	@Autowired
	public ContractCommercialController(final ContractCommercialService contractService, final FootballClubService footballClubService) {
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

	//Vista de Contrato Detallada de un commercial
	@GetMapping(value = "/contractsCommercial/{contractCommercialId}")
	public ModelAndView showContractCommercial(@PathVariable("contractCommercialId") final int contractCommercialId) {

		ModelAndView mav = new ModelAndView("contracts/contractCommercialDetails");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Collection<ContractCommercial> clist = this.contractService.findAllCommercialContracts();

		boolean presidentAlreadyHasContract = false;

		for (ContractCommercial c : clist) {
			if (c.getClub() != null && c.getClub().getPresident().getUser().getUsername() == currentPrincipalName) {
				presidentAlreadyHasContract = true;
				break;
			}
		}

		mav.addObject("hasAlreadyContract", presidentAlreadyHasContract);

		mav.addObject("contractCommercial", this.contractService.findContractCommercialById(contractCommercialId));

		try {
			mav.addObject("footballClub", this.footballClubService.findFootballClubByPresident(currentPrincipalName));

		} catch (Exception e) {
			mav.addObject("footballClub", null);
		}

		return mav;
	}

	@GetMapping(value = "/contractsCommercial/{contractCommercialId}/addToMyClub")
	public String initAddContractToMyClub(@PathVariable("contractCommercialId") final int contractCommercialId)
		throws DataAccessException, NoMultipleContractCommercialException, NoStealContractCommercialException, NotEnoughMoneyException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);
		ContractCommercial contractCommercial = this.contractService.findContractCommercialById(contractCommercialId);

		ContractCommercial cc = new ContractCommercial();
		cc.setClause(contractCommercial.getClause());
		cc.setClub(contractCommercial.getClub());
		cc.setEndDate(contractCommercial.getEndDate());
		cc.setId(contractCommercial.getId());
		cc.setStartDate(contractCommercial.getStartDate());
		cc.setMoney(contractCommercial.getMoney());
		cc.setPublicity(contractCommercial.getPublicity());
		cc.setClub(footballClub);

		return this.processAddContractToMyClub(cc);
	}

	//Crear Contrato de Comercial - Get
	@PostMapping(value = "/contractsCommercial/{contractCommercialId}/addToMyClub")
	public String processAddContractToMyClub(@Valid final ContractCommercial contractCommercial)
		throws DataAccessException, NoMultipleContractCommercialException, NoStealContractCommercialException, NotEnoughMoneyException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		this.contractService.saveContractCommercial(contractCommercial);

		return "redirect:/footballClubs/myClub/" + currentPrincipalName;
	}

	@GetMapping(value = "/contractsCommercial/{contractCommercialId}/removeFromMyClub")
	public String initremoveContractFromMyClub(@PathVariable("contractCommercialId") final int contractCommercialId)
		throws DataAccessException, NoMultipleContractCommercialException, NoStealContractCommercialException, NotEnoughMoneyException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, CredentialException {
		ContractCommercial contractCommercial = this.contractService.findContractCommercialById(contractCommercialId);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		if (!contractCommercial.getClub().getPresident().getUser().getUsername().equals(currentPrincipalName)) { //SEGURIDAD
			throw new CredentialException("Forbidden Access");
		}

		ContractCommercial cc = new ContractCommercial();
		cc.setClause(contractCommercial.getClause());
		cc.setClub(contractCommercial.getClub());
		cc.setEndDate(contractCommercial.getEndDate());
		cc.setId(contractCommercial.getId());
		cc.setStartDate(contractCommercial.getStartDate());
		cc.setMoney(contractCommercial.getMoney());
		cc.setPublicity(contractCommercial.getPublicity());
		cc.setClub(null);

		return this.processremoveContractFromMyClub(cc);
	}

	@PostMapping(value = "/contractsCommercial/{contractCommercialId}/removeFromMyClub")
	public String processremoveContractFromMyClub(@Valid final ContractCommercial contractCommercial)
		throws DataAccessException, NoMultipleContractCommercialException, NoStealContractCommercialException, NotEnoughMoneyException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		this.contractService.saveContractCommercial(contractCommercial);

		return "redirect:/footballClubs/myClub/" + currentPrincipalName;
	}

}
