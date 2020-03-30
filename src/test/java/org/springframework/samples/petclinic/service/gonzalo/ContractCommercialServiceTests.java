
package org.springframework.samples.petclinic.service.gonzalo;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

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
import org.springframework.samples.petclinic.service.exceptions.DateException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.samples.petclinic.service.exceptions.NoMultipleContractCommercialException;
import org.springframework.samples.petclinic.service.exceptions.NoStealContractCommercialException;
import org.springframework.samples.petclinic.service.exceptions.NotEnoughMoneyException;
import org.springframework.samples.petclinic.service.exceptions.NumberOfPlayersAndCoachException;
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
		Assertions.assertTrue(contracts.size() == 5);
	}

	@Test //CASO POSITIVO - Encontrar contrato commercial por id de club
	void shouldFindCommercialContractByClubId() throws DataAccessException, NoMultipleContractCommercialException, NoStealContractCommercialException, NotEnoughMoneyException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException {
		FootballClub myClub = this.footballClubService.findFootballClubById(1);
		ContractCommercial cc = this.contractService.findContractCommercialById(1);
		cc.setClub(myClub);
		this.contractService.saveContractCommercial(cc);

		ContractCommercial contract = this.contractService.findCommercialContractByClubId(1);
		Assertions.assertTrue(contract != null);
	}

	@Test //CASO NEGATIVO - Encontrar contrato commercial por id de club
	void should_NOT_FindCommercialContractByClubId() {
		ContractCommercial contract = this.contractService.findCommercialContractByClubId(1);
		Assertions.assertFalse(contract != null);
	}

	@Test //CASO POSITIVO - Encontrar contrato commercial por id
	void shouldFindContractCommercialById() {
		ContractCommercial c1 = this.contractService.findContractCommercialById(1);
		Assertions.assertTrue(c1 != null);
	}

	@Test //CASO NEGATIVO - Encontrar contrato commercial por id
	void should_NOT_FindContractCommercialById() {
		ContractCommercial c1 = this.contractService.findContractCommercialById(50);
		Assertions.assertFalse(c1 != null);
	}

	@Test //CASO POSITIVO - Guardar contrato commercial
	void shouldSaveContractCommercial() throws NoMultipleContractCommercialException, DataAccessException, NoStealContractCommercialException, NotEnoughMoneyException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException {
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

	@Test //CASO NEGATIVO - Guardar segundo contrato commercial de un mismo club
	void should_NOT_SaveContractCommercial_1() throws DataAccessException, NoMultipleContractCommercialException, NoStealContractCommercialException, NotEnoughMoneyException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException {
		Date date = new Date(System.currentTimeMillis() - 1);
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		ContractCommercial cc1 = new ContractCommercial();

		FootballClub myClub = this.footballClubService.findFootballClubById(1);

		cc1.setClub(myClub);
		cc1.setClause(100000);
		cc1.setMoney(10000);
		cc1.setStartDate(date);
		cc1.setEndDate(date);
		cc1.setPublicity("https://www.imagen.com.mx/assets/img/imagen_share.png");

		this.contractService.saveContractCommercial(cc1);

		ContractCommercial cc2 = new ContractCommercial();

		cc2.setClub(myClub);
		cc2.setClause(100000);
		cc2.setMoney(10000);
		cc2.setStartDate(date);
		cc2.setEndDate(date);
		cc2.setPublicity("https://www.imagen.com.mx/assets/img/imagen_share.png");

		Assertions.assertThrows(NoMultipleContractCommercialException.class, () -> {
			this.contractService.saveContractCommercial(cc2);
		});
	}

	@Test //CASO NEGATIVO - Terminar contrato sin dinero suficiente para pagar la clausula
	void should_NOT_SaveContractCommercial_2() throws DataAccessException, NoMultipleContractCommercialException, NoStealContractCommercialException, NotEnoughMoneyException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException {
		//New contract
		Date date = new Date(System.currentTimeMillis() - 1);
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		ContractCommercial cc = new ContractCommercial();

		FootballClub myClub = this.footballClubService.findFootballClubById(1);
		myClub.setMoney(10000); //Less money than clause

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		Date undiaDespues = cal.getTime();

		cc.setClub(myClub);
		cc.setClause(100000);
		cc.setMoney(10000);
		cc.setStartDate(date);
		cc.setEndDate(undiaDespues);
		cc.setPublicity("https://www.imagen.com.mx/assets/img/imagen_share.png");

		this.contractService.saveContractCommercial(cc);

		ContractCommercial ccclone = new ContractCommercial();
		ccclone.setId(cc.getId());
		ccclone.setClub(null);//Acabar el contrato instantaneamente
		ccclone.setClause(100000);
		ccclone.setMoney(10000);
		ccclone.setStartDate(date);
		ccclone.setEndDate(undiaDespues);
		ccclone.setPublicity("https://www.imagen.com.mx/assets/img/imagen_share.png");

		Assertions.assertThrows(NotEnoughMoneyException.class, () -> {
			this.contractService.saveContractCommercial(ccclone);
		});
	}

	@Test //CASO NEGATIVO - Guardar otro club en el contrato sin que el primero haya terminado el contrato (null)
	void should_NOT_SaveContractCommercial_3() throws DataAccessException, NoMultipleContractCommercialException, NoStealContractCommercialException, NotEnoughMoneyException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException {
		//New contract
		Date date = new Date(System.currentTimeMillis() - 1);
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		ContractCommercial cc = new ContractCommercial();

		FootballClub myClub = this.footballClubService.findFootballClubById(1);

		cc.setClub(myClub);
		cc.setClause(100000);
		cc.setMoney(10000);
		cc.setStartDate(date);
		cc.setEndDate(date);
		cc.setPublicity("https://www.imagen.com.mx/assets/img/imagen_share.png");

		this.contractService.saveContractCommercial(cc);

		ContractCommercial ccclone = new ContractCommercial();
		FootballClub myClub2 = this.footballClubService.findFootballClubById(2);
		ccclone.setId(cc.getId());
		ccclone.setClub(myClub2);
		ccclone.setClause(100000);
		ccclone.setMoney(10000);
		ccclone.setStartDate(date);
		ccclone.setEndDate(date);
		ccclone.setPublicity("https://www.imagen.com.mx/assets/img/imagen_share.png");

		Assertions.assertThrows(NoStealContractCommercialException.class, () -> {
			this.contractService.saveContractCommercial(ccclone);
		});
	}

	@Test //CASO POSITIVO - Eliminar un club deberia dejar todos los contractos commerciales con Club_id == null
	void shouldNullifyClubIDWhileDeletingAClub() throws NoMultipleContractCommercialException, DataAccessException, NoStealContractCommercialException, NotEnoughMoneyException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException {

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
