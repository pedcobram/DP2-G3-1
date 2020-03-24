
package org.springframework.samples.petclinic.service.gonzalo;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.ContractCommercial;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.service.ContractCommercialService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.exceptions.NoMultipleContractCommercialException;
import org.springframework.samples.petclinic.service.exceptions.NoStealContractCommercialException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ContractCommercialServiceTests {

	@Autowired
	protected ContractCommercialService	contractService;

	@Autowired
	private FootballClubService			footballClubService;


	@Test
	void shouldFindAllCommercialContracts() {
		Collection<ContractCommercial> contracts = this.contractService.findAllCommercialContracts();
		Assertions.assertTrue(contracts.size() == 2);
	}

	@Test
	void shouldFindCommercialContractByClubId() {
		ContractCommercial contract = this.contractService.findCommercialContractByClubId(1);
		Assertions.assertTrue(contract == null);
	}

	@Test //CASO POSITIVO - Encontrar contrato commercial
	void shouldFindContractCommercialById_1() {
		ContractCommercial c1 = this.contractService.findContractCommercialById(1);
		Assertions.assertTrue(c1 != null);
	}

	@Test //CASO POSITIVO - Encontrar contrato commercial
	void shouldFindContractCommercialById_2() {
		ContractCommercial c1 = this.contractService.findContractCommercialById(2);
		Assertions.assertTrue(c1 != null);
	}

	@Test //CASO NEGATIVO - Encontrar contrato commercial
	void should_NOT_FindContractCommercialById() {
		ContractCommercial c1 = this.contractService.findContractCommercialById(50);
		Assertions.assertFalse(c1 != null);
	}

	@Test //CASO POSITIVO - Guardar contrato commercial
	void shouldSaveContractCommercial() throws NoMultipleContractCommercialException, DataAccessException, NoStealContractCommercialException {
		Collection<ContractCommercial> contracts = this.contractService.findAllCommercialContracts();
		int cantidadIni = contracts.size();

		//New contract
		Date date = new Date(System.currentTimeMillis() - 1);
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		ContractCommercial cc = new ContractCommercial();

		cc.setClause(100000);
		cc.setMoney(10000);
		cc.setStartDate(date);
		cc.setEndDate(date);
		cc.setPublicity("https://www.imagen.com.mx/assets/img/imagen_share.png");

		this.contractService.saveContractCommercial(cc);

		contracts = this.contractService.findAllCommercialContracts();
		Assertions.assertTrue(contracts.size() == cantidadIni + 1);
	}

	@Test //CASO NEGATIVO - Guardar contrato commercial
	void should_NOT_SaveContractCommercial_1() {
		//New contract
		Date date = new Date(System.currentTimeMillis() - 1);
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		ContractCommercial cc = new ContractCommercial();

		cc.setClause(null);
		cc.setMoney(10000);
		cc.setStartDate(date);
		cc.setEndDate(date);
		cc.setPublicity("https://www.imagen.com.mx/assets/img/imagen_share.png");

		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			this.contractService.saveContractCommercial(cc);
		});
	}

	@Test //CASO NEGATIVO - Guardar contrato commercial
	void should_NOT_SaveContractCommercial_2() {
		//New contract
		Date date = new Date(System.currentTimeMillis() - 1);
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		ContractCommercial cc = new ContractCommercial();

		FootballClub myClub = this.footballClubService.findFootballClubById(1);

		cc.setClub(myClub);
		cc.setClause(100000);
		cc.setStartDate(date);
		cc.setEndDate(date);
		cc.setPublicity("https://www.imagen.com.mx/assets/img/imagen_share.png");

		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			this.contractService.saveContractCommercial(cc);
		});
	}

	@Test //CASO POSITIVO - Eliminar un club deberia dejar todos los contractos commerciales con Club_id == null
	void shouldNullifyClubIDWhileDeletingAClub() throws NoMultipleContractCommercialException, DataAccessException, NoStealContractCommercialException {

		FootballClub myClub = this.footballClubService.findFootballClubById(1);
		Assertions.assertTrue(myClub != null); //Vemos que existe

		ContractCommercial contractCommercial = this.contractService.findContractCommercialById(1);
		Assertions.assertTrue(contractCommercial != null); //Vemos que existe

		contractCommercial.setClub(myClub); //Enlazamos el club con el contrato commercial

		this.contractService.saveContractCommercial(contractCommercial); //Guardamos

		ContractCommercial contractAfterSave = this.contractService.findContractCommercialById(1);

		//Comprobamos que el club_id esta guardado
		Assertions.assertTrue(contractAfterSave.getClub().getId() == 1);

		this.footballClubService.deleteFootballClub(myClub); //Eliminamos el club

		ContractCommercial contractAfterDeleteClub = this.contractService.findContractCommercialById(1);

		//Comprobamos que el club_id es null despues de elimnar el club
		Assertions.assertTrue(contractAfterDeleteClub.getClub() == null);
	}

}
