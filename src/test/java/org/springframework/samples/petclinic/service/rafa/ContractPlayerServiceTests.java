
package org.springframework.samples.petclinic.service.rafa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.ContractPlayer;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.service.ContractPlayerService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.FootballPlayerService;
import org.springframework.samples.petclinic.service.exceptions.DateException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.samples.petclinic.service.exceptions.MoneyClubException;
import org.springframework.samples.petclinic.service.exceptions.NumberOfPlayersAndCoachException;
import org.springframework.samples.petclinic.service.exceptions.SalaryException;
import org.springframework.samples.petclinic.service.exceptions.StatusException;
import org.springframework.samples.petclinic.service.exceptions.StatusRegisteringException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ContractPlayerServiceTests {

	@Autowired
	private ContractPlayerService	contractService;

	@Autowired
	private FootballPlayerService	footballPlayerService;

	@Autowired
	private FootballClubService		footballClubService;


	@Test //CASO POSITIVO - Buscar ContractPlayer por ID
	void shouldFindContractPlayerById() {
		ContractPlayer contractPlayer = null;
		contractPlayer = this.contractService.findContractPlayerById(1);
		Assertions.assertTrue(contractPlayer != null);
	}

	@Test //CASO NEGATIVO - Buscar ContractPlayer por ID
	void shouldNotFindContractPlayerById() {
		ContractPlayer contractPlayer = null;
		contractPlayer = this.contractService.findContractPlayerById(99);
		Assertions.assertFalse(contractPlayer != null);
	}

	@Test //CASO POSITIVO - Buscar ContractPlayer por ID de Jugador
	void shouldFindContractPlayerByPlayerId() {
		ContractPlayer contractPlayer = null;
		contractPlayer = this.contractService.findContractPlayerByPlayerId(1);
		Assertions.assertTrue(contractPlayer != null);
	}

	@Test //CASO NEGATIVO - Buscar ContractPlayer por ID de Jugador
	void shouldNotFindContractPlayerByPlayerId() {
		ContractPlayer contractPlayer = null;
		contractPlayer = this.contractService.findContractPlayerByPlayerId(99);
		Assertions.assertFalse(contractPlayer != null);
	}

	@Test //CASO POSITIVO - Buscar todos contratos de jugadores por equipo
	void shouldFindAllContractPlayerByClubId() {

		List<ContractPlayer> contracts = new ArrayList<>();

		contracts.addAll(this.contractService.findAllPlayerContractsByClubId(1));

		int count = contracts.size();

		Assertions.assertTrue(count == 7);

	}

	@Test //CASO POSITIVO - Crear Contrato (Fichar Jugador)
	void shouldSaveContractPlayerAndGenerateId() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException, SalaryException, StatusRegisteringException {

		ContractPlayer contractPlayer = this.contractService.findContractPlayerById(8);
		Assertions.assertFalse(contractPlayer != null); //Vemos que no existe

		FootballPlayer player = this.footballPlayerService.findFootballPlayerById(64); //Buscamos un jugador sin contrato (Free Agent)
		Assertions.assertTrue(this.contractService.findContractPlayerByPlayerId(player.getId()) == null); //Vemos que no tiene contrato
		Assertions.assertTrue(player.getValue() == 10000000); //Vemos que el valor es de 10mill€

		FootballClub club = this.footballClubService.findFootballClubById(1);

		Date now = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.YEAR, +3);
		Date end = cal.getTime();

		ContractPlayer contract = new ContractPlayer();

		contract.setClub(club);
		contract.setPlayer(player);
		contract.setStartDate(now);
		contract.setEndDate(end);
		contract.setClause(3000000);
		contract.setSalary(1000000);

		this.contractService.saveContractPlayer(contract);

		contractPlayer = this.contractService.findContractPlayerById(8);
		Assertions.assertTrue(contractPlayer != null); //Vemos que ahora SI existe el contrato
		Assertions.assertTrue(contractPlayer.getId() != null);
		Assertions.assertTrue(this.contractService.findContractPlayerByPlayerId(player.getId()) != null); //Vemos que ahora si tiene contrato

	}

	@Test //CASO NEGATIVO - Crear Contrato (Fichar Jugador) dejando un campo en blanco
	void shouldThrowExceptionIfHastEmptyFields() throws DataAccessException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, SalaryException {

		ContractPlayer contractPlayer = this.contractService.findContractPlayerById(8);
		Assertions.assertFalse(contractPlayer != null); //Vemos que no existe

		FootballPlayer player = this.footballPlayerService.findFootballPlayerById(64); //Buscamos un jugador sin contrato (Free Agent)
		Assertions.assertTrue(this.contractService.findContractPlayerByPlayerId(player.getId()) == null); //Vemos que no tiene contrato
		Assertions.assertTrue(player.getValue() == 10000000); //Vemos que el valor es de 10mill€

		FootballClub club = this.footballClubService.findFootballClubById(1);

		Date now = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.YEAR, +3);
		Date end = cal.getTime();

		ContractPlayer contract = new ContractPlayer();

		contract.setClub(club);
		contract.setPlayer(player);
		contract.setStartDate(now);
		contract.setEndDate(end);
		contract.setClause(3000000);
		contract.setSalary(null);  //Ponemos en null para que falle

		Assertions.assertThrows(NullPointerException.class, () -> {
			this.contractService.saveContractPlayer(contract);
		});
	}

	@Test //CASO NEGATIVO - Crear Contrato (Fichar Jugador) si el club no es público y ya tiene siete jugadores //RN: Si el club no es público, solo puede tener a 7 jugadores como máximo
	void shouldThrowExceptionIfClubIsNotPublishedAndHasSevenPlayers() throws DataAccessException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException {

		ContractPlayer contractPlayer = this.contractService.findContractPlayerById(8);
		Assertions.assertFalse(contractPlayer != null); //Vemos que no existe

		FootballPlayer player = this.footballPlayerService.findFootballPlayerById(64); //Buscamos un jugador sin contrato (Free Agent)
		Assertions.assertTrue(this.contractService.findContractPlayerByPlayerId(player.getId()) == null); //Vemos que no tiene contrato
		Assertions.assertTrue(player.getValue() == 10000000); //Vemos que el valor es de 10mill€

		FootballClub club = this.footballClubService.findFootballClubById(9);
		Collection<FootballPlayer> players = this.footballPlayerService.findAllClubFootballPlayers(club.getId());

		Assertions.assertTrue(club.getStatus() == false); //Vemos que NO es público		
		Assertions.assertTrue(players.size() == 7); //Vemos que tiene 7 jugadores

		Date now = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.YEAR, +3);
		Date end = cal.getTime();

		ContractPlayer contract = new ContractPlayer();

		contract.setClub(club);
		contract.setPlayer(player);
		contract.setStartDate(now);
		contract.setEndDate(end);
		contract.setClause(3000000);
		contract.setSalary(1000000);  //Ponemos en null para que falle

		Assertions.assertThrows(NumberOfPlayersAndCoachException.class, () -> {
			this.contractService.saveContractPlayer(contract);
		});
	}

	@Test //CASO NEGATIVO - Crear Contrato (Fichar Jugador) si el salario es menor al mínimo exigido //RN: Salario mínimo y máximo
	void shouldThrowExceptionIfSalaryIsLessThanMinimum() throws DataAccessException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, SalaryException {

		ContractPlayer contractPlayer = this.contractService.findContractPlayerById(8);
		Assertions.assertFalse(contractPlayer != null); //Vemos que no existe

		FootballPlayer player = this.footballPlayerService.findFootballPlayerById(64); //Buscamos un jugador sin contrato (Free Agent)
		Assertions.assertTrue(this.contractService.findContractPlayerByPlayerId(player.getId()) == null); //Vemos que no tiene contrato
		Assertions.assertTrue(player.getValue() == 10000000); //Vemos que el valor es de 10mill€

		FootballClub club = this.footballClubService.findFootballClubById(1);

		Date now = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.YEAR, +3);
		Date end = cal.getTime();

		ContractPlayer contract = new ContractPlayer();

		contract.setClub(club);
		contract.setPlayer(player);
		contract.setStartDate(now);
		contract.setEndDate(end);
		contract.setClause(3000000);

		//El salario mínimo es el valor/10, en este caso 1mill€

		contract.setSalary(999999);  //Ponemos el salario por debajo del mínimo: 999.999,00 €

		Assertions.assertThrows(SalaryException.class, () -> {
			this.contractService.saveContractPlayer(contract);
		});
	}

	@Test //CASO NEGATIVO - Crear Contrato (Fichar Jugador) si el salario es mayor al máximo exigido //RN: Salario mínimo y máximo
	void shouldThrowExceptionIfSalaryIsMoreThanMaximum() throws DataAccessException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, SalaryException {

		ContractPlayer contractPlayer = this.contractService.findContractPlayerById(8);
		Assertions.assertFalse(contractPlayer != null); //Vemos que no existe

		FootballPlayer player = this.footballPlayerService.findFootballPlayerById(64); //Buscamos un jugador sin contrato (Free Agent)
		Assertions.assertTrue(this.contractService.findContractPlayerByPlayerId(player.getId()) == null); //Vemos que no tiene contrato
		Assertions.assertTrue(player.getValue() == 10000000); //Vemos que el valor es de 10mill€

		FootballClub club = this.footballClubService.findFootballClubById(1);

		Date now = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.YEAR, +3);
		Date end = cal.getTime();

		ContractPlayer contract = new ContractPlayer();

		contract.setClub(club);
		contract.setPlayer(player);
		contract.setStartDate(now);
		contract.setEndDate(end);
		contract.setClause(3000000);

		//El salario máximo es igual al valor del jugador, en este caso 10mill€

		contract.setSalary(10000001);  //Ponemos el salario por encima del máximo: 10.000.001,00 €

		Assertions.assertThrows(SalaryException.class, () -> {
			this.contractService.saveContractPlayer(contract);
		});
	}

	@Test //CASO NEGATIVO - Crear Contrato (Fichar Jugador) si el salario es mayor a los fondos del club //RN: La transacción total no puede ser mayor a los fondos del club
	void shouldThrowExceptionIfClubHasNotEnoughMoney() throws DataAccessException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, SalaryException {

		ContractPlayer contractPlayer = this.contractService.findContractPlayerById(8);
		Assertions.assertFalse(contractPlayer != null); //Vemos que no existe

		FootballPlayer player = this.footballPlayerService.findFootballPlayerById(66); //Buscamos un jugador sin contrato (Free Agent)
		Assertions.assertTrue(this.contractService.findContractPlayerByPlayerId(player.getId()) == null); //Vemos que no tiene contrato
		Assertions.assertTrue(player.getValue() == 99999999); //Vemos que el valor es de 99.999.999,00 €

		FootballClub club = this.footballClubService.findFootballClubById(10);
		Assertions.assertTrue(club.getMoney() == 2000000); //Vemos que los fondos son de 2.000.000,00 €

		Date now = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.YEAR, +3);
		Date end = cal.getTime();

		ContractPlayer contract = new ContractPlayer();

		contract.setClub(club);
		contract.setPlayer(player);
		contract.setStartDate(now);
		contract.setEndDate(end);
		contract.setClause(3000000);

		contract.setSalary(50000000);  //Ponemos el salario por encima de los fondos del club: 50.000.000,00 €

		Assertions.assertThrows(MoneyClubException.class, () -> {
			this.contractService.saveContractPlayer(contract);
		});
	}

	@Test //CASO NEGATIVO - Crear Contrato (Fichar Jugador) si la duración del contrato es menor a un año. //RN: El contrato debe tener al menos un año de duración
	void shouldThrowExceptionIfEndDateIsLessThan1Year() throws DataAccessException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, SalaryException {

		ContractPlayer contractPlayer = this.contractService.findContractPlayerById(8);
		Assertions.assertFalse(contractPlayer != null); //Vemos que no existe

		FootballPlayer player = this.footballPlayerService.findFootballPlayerById(64); //Buscamos un jugador sin contrato (Free Agent)
		Assertions.assertTrue(this.contractService.findContractPlayerByPlayerId(player.getId()) == null); //Vemos que no tiene contrato
		Assertions.assertTrue(player.getValue() == 10000000); //Vemos que el valor es de 10mill€

		FootballClub club = this.footballClubService.findFootballClubById(1);

		Date now = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.MONTH, +11);
		Date end = cal.getTime();

		ContractPlayer contract = new ContractPlayer();

		contract.setClub(club);
		contract.setPlayer(player);
		contract.setStartDate(now);
		contract.setEndDate(end);
		contract.setClause(3000000);
		contract.setSalary(10000000);

		Assertions.assertThrows(DateException.class, () -> {
			this.contractService.saveContractPlayer(contract);
		});
	}

	@Test //CASO POSITIVO - Despedir a mi Jugador
	void shouldDeleteAContractPlayer() throws DataAccessException, MoneyClubException {

		ContractPlayer contractPlayer = this.contractService.findContractPlayerById(1);
		Assertions.assertTrue(contractPlayer != null); //Vemos que Si existe

		this.contractService.deleteContract(contractPlayer);

		contractPlayer = this.contractService.findContractPlayerById(1);
		Assertions.assertTrue(contractPlayer == null); //Vemos que ahora NO existe el contrato
	}

	@Test //CASO NEGATIVO - Despedir a mi jugador si los fondos de mi club son menores a la cláusula de mi entrenador. //RN: La transacción total no puede ser mayor a los fondos del club
	void shouldThrowExceptionFiringAPlayerIfClubHasNotEnoughMoney() throws DataAccessException, MoneyClubException {

		FootballClub myClub = this.footballClubService.findFootballClubById(1);
		myClub.setMoney(8000000); //Ponemos los fondos del club a 8mill€

		ContractPlayer contractPlayer = this.contractService.findContractPlayerById(1);
		Assertions.assertTrue(contractPlayer.getClause() == 9000000); //Vemos que su cláusula de rescisión es de 9mill€

		Assertions.assertThrows(MoneyClubException.class, () -> {
			this.contractService.deleteContract(contractPlayer);
			;
		});
	}

	@Test //CASO POSITIVO - Despedir a mi Jugador cuando se borra a un equipo y se declara insolvente (ignora los fondos del club)
	void shouldDeleteAContractPlayerWhileDeletingAClub() throws DataAccessException {

		FootballClub myClub = this.footballClubService.findFootballClubById(1);
		myClub.setMoney(8000000); //Ponemos los fondos del club a 8mill€

		ContractPlayer contractPlayer = this.contractService.findContractPlayerById(1);
		Assertions.assertTrue(contractPlayer != null); //Vemos que existe
		Assertions.assertTrue(contractPlayer.getClause() == 9000000); //Vemos que su cláusula de rescisión es de 9mill€

		this.contractService.deleteContractDeletingClub(contractPlayer);

		contractPlayer = this.contractService.findContractPlayerById(1);
		Assertions.assertTrue(contractPlayer == null); //Vemos que ahora NO existe el contrato
	}

}
