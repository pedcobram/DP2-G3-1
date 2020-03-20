
package org.springframework.samples.petclinic.service.rafa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.ContractPlayer;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.Enum.FootballPlayerPosition;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.FootballPlayerService;
import org.springframework.samples.petclinic.service.exceptions.DateException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.samples.petclinic.service.exceptions.MoneyClubException;
import org.springframework.samples.petclinic.service.exceptions.NumberOfPlayersAndCoachException;
import org.springframework.samples.petclinic.service.exceptions.StatusException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class FootballPlayerServiceTests {

	@Autowired
	private FootballClubService		footballClubService;

	@Autowired
	private FootballPlayerService	footballPlayerService;


	@Test //CASO POSITIVO - Buscar Jugador por ID
	void shouldFindFootballPlayerById() {
		FootballPlayer player = null;
		player = this.footballPlayerService.findFootballPlayerById(1);
		Assertions.assertTrue(player != null);
	}

	@Test //CASO NEGATIVO - Buscar Jugador por ID
	void shouldNotFindFootballPlayerById() {
		FootballPlayer player = null;
		player = this.footballPlayerService.findFootballPlayerById(99);
		Assertions.assertFalse(player != null);
	}

	@Test //CASO POSITIVO - Buscar todos los Jugadores
	void shouldFindAllFootballPlayers() {

		List<FootballPlayer> players = new ArrayList<>();

		players.addAll(this.footballPlayerService.findAllFootballPlayers());

		int count = players.size();

		Assertions.assertTrue(count == 68);

	}

	@Test //CASO POSITIVO - Buscar todos los Jugadores Agentes Libres
	void shouldFindAllFootballPlayersFreeAgents() {

		List<FootballPlayer> players = new ArrayList<>();

		players.addAll(this.footballPlayerService.findAllFootballPlayersFA());

		int count = players.size();

		Assertions.assertTrue(count == 5);

	}

	@Test //CASO POSITIVO - Buscar todos los Jugadores por ID de equipo
	void shouldFindAllFootballPlayersByClubId() {

		List<FootballPlayer> players = new ArrayList<>();

		players.addAll(this.footballPlayerService.findAllClubFootballPlayers(1));

		int count = players.size();

		Assertions.assertTrue(count == 7);

	}

	@Test //CASO NEGATIVO - Buscar todos los Jugadores por ID de equipo
	void shouldNotFindAllFootballPlayersByClubIdIfNotExists() {

		List<FootballPlayer> players = new ArrayList<>();

		players.addAll(this.footballPlayerService.findAllClubFootballPlayers(99));

		int count = players.size();

		Assertions.assertTrue(count == 0);

	}

	@Test //CASO POSITIVO - Registrar Jugador
	void shouldRegisterFootballPlayerAndGenerateId() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException {

		FootballPlayer player = this.footballPlayerService.findFootballPlayerById(69);

		Assertions.assertFalse(player != null); //Vemos que no existe el jugador

		FootballClub club = this.footballClubService.findFootballClubById(10);

		Assertions.assertTrue(club.getStatus() == false); //Vemos que no está publicado

		List<FootballPlayer> players = new ArrayList<>();
		players.addAll(this.footballPlayerService.findAllClubFootballPlayers(10));
		int count = players.size();

		Assertions.assertTrue(count == 0); //Vemos que no tiene jugadores	

		Date age = new Date(System.currentTimeMillis() - 1);
		Date end = new Date(System.currentTimeMillis() - 1);
		Date start = new Date(System.currentTimeMillis() - 1);

		Calendar cal = Calendar.getInstance();
		cal.setTime(age);
		cal.add(Calendar.YEAR, -17);
		age = cal.getTime();

		cal.setTime(end);
		cal.add(Calendar.YEAR, +5);
		end = cal.getTime();

		FootballPlayer footballPlayer = new FootballPlayer();

		footballPlayer.setBirthDate(age);
		footballPlayer.setClub(club);
		footballPlayer.setFirstName("Vincent");
		footballPlayer.setLastName("Valentine");
		footballPlayer.setPosition(FootballPlayerPosition.STRIKER);
		footballPlayer.setValue(1000);

		ContractPlayer newContract = new ContractPlayer();
		newContract.setClause(5000000);
		newContract.setClub(club);
		newContract.setPlayer(footballPlayer);
		newContract.setEndDate(end);
		newContract.setStartDate(start);
		newContract.setSalary(1000000);

		this.footballPlayerService.saveFootballPlayer(footballPlayer, newContract);

		players.addAll(this.footballPlayerService.findAllClubFootballPlayers(10));
		count = players.size();

		Assertions.assertTrue(count == 1); //Vemos que ahora tiene el jugador creado

		player = this.footballPlayerService.findFootballPlayerById(69);
		Assertions.assertTrue(player != null); //Vemos que ahora existe el jugador creado
		Assertions.assertTrue(player.getId() != null);
	}

	@Test //CASO NEGATIVO - Registrar Jugador si se deja un campo en blanco
	void shouldThrowExceptionIfFootballPlayerHasEmptyFields() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException {

		FootballClub club = this.footballClubService.findFootballClubById(10);

		Assertions.assertTrue(club.getStatus() == false); //Vemos que no está publicado

		Date age = new Date(System.currentTimeMillis() - 1);
		Date end = new Date(System.currentTimeMillis() - 1);
		Date start = new Date(System.currentTimeMillis() - 1);

		Calendar cal = Calendar.getInstance();
		cal.setTime(age);
		cal.add(Calendar.YEAR, -17);
		age = cal.getTime();

		cal.setTime(end);
		cal.add(Calendar.YEAR, +5);
		end = cal.getTime();

		FootballPlayer footballPlayer = new FootballPlayer();

		footballPlayer.setBirthDate(age);
		footballPlayer.setClub(club);
		footballPlayer.setFirstName("");  //Dejamos el campo en blanco para que falle
		footballPlayer.setLastName("Valentine");
		footballPlayer.setPosition(FootballPlayerPosition.STRIKER);
		footballPlayer.setValue(1000);

		ContractPlayer newContract = new ContractPlayer();
		newContract.setClause(5000000);
		newContract.setClub(club);
		newContract.setPlayer(footballPlayer);
		newContract.setEndDate(end);
		newContract.setStartDate(start);
		newContract.setSalary(1000000);

		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			this.footballPlayerService.saveFootballPlayer(footballPlayer, newContract);
		});
	}

	@Test //CASO NEGATIVO - Registrar Jugador si el club al que se le añade ya es público
	void shouldThrowExceptionIfFootballClubIsPublished() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException {

		FootballClub club = this.footballClubService.findFootballClubById(1);

		Assertions.assertTrue(club.getStatus() == true); //Vemos que si está publicado

		Date age = new Date(System.currentTimeMillis() - 1);
		Date end = new Date(System.currentTimeMillis() - 1);
		Date start = new Date(System.currentTimeMillis() - 1);

		Calendar cal = Calendar.getInstance();
		cal.setTime(age);
		cal.add(Calendar.YEAR, -17);
		age = cal.getTime();

		cal.setTime(end);
		cal.add(Calendar.YEAR, +5);
		end = cal.getTime();

		FootballPlayer footballPlayer = new FootballPlayer();

		footballPlayer.setBirthDate(age);
		footballPlayer.setClub(club);
		footballPlayer.setFirstName("Vincent");
		footballPlayer.setLastName("Valentine");
		footballPlayer.setPosition(FootballPlayerPosition.STRIKER);
		footballPlayer.setValue(1000);

		ContractPlayer newContract = new ContractPlayer();
		newContract.setClause(5000000);
		newContract.setClub(club);
		newContract.setPlayer(footballPlayer);
		newContract.setEndDate(end);
		newContract.setStartDate(start);
		newContract.setSalary(1000000);

		Assertions.assertThrows(StatusException.class, () -> {
			this.footballPlayerService.saveFootballPlayer(footballPlayer, newContract);
		});
	}

	@Test //CASO NEGATIVO - Registrar Jugador si tiene el mismo nombre
	void shouldThrowExceptionIfFootballPlayerHasSameName() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException {

		FootballClub club = this.footballClubService.findFootballClubById(10);

		Assertions.assertTrue(club.getStatus() == false); //Vemos que no está publicado

		Date age = new Date(System.currentTimeMillis() - 1);
		Date end = new Date(System.currentTimeMillis() - 1);
		Date start = new Date(System.currentTimeMillis() - 1);

		Calendar cal = Calendar.getInstance();
		cal.setTime(age);
		cal.add(Calendar.YEAR, -17);
		age = cal.getTime();

		cal.setTime(end);
		cal.add(Calendar.YEAR, +5);
		end = cal.getTime();

		FootballPlayer footballPlayer = new FootballPlayer();

		footballPlayer.setBirthDate(age);
		footballPlayer.setClub(club);
		footballPlayer.setFirstName("Luuk");
		footballPlayer.setLastName("De Jong");
		footballPlayer.setPosition(FootballPlayerPosition.STRIKER);
		footballPlayer.setValue(1000);

		ContractPlayer newContract = new ContractPlayer();
		newContract.setClause(5000000);
		newContract.setClub(club);
		newContract.setPlayer(footballPlayer);
		newContract.setEndDate(end);
		newContract.setStartDate(start);
		newContract.setSalary(1000000);

		Assertions.assertThrows(DuplicatedNameException.class, () -> {
			this.footballPlayerService.saveFootballPlayer(footballPlayer, newContract);
		});
	}

	@Test //CASO NEGATIVO - Registrar Jugador si el club ya tiene 7 jugadores
	void shouldThrowExceptionIfFootballClubHasAlreadySevenPlayers() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException {

		FootballClub club = this.footballClubService.findFootballClubById(9);

		Assertions.assertTrue(club.getStatus() == false); //Vemos que no está publicado

		Date age = new Date(System.currentTimeMillis() - 1);
		Date end = new Date(System.currentTimeMillis() - 1);
		Date start = new Date(System.currentTimeMillis() - 1);

		Calendar cal = Calendar.getInstance();
		cal.setTime(age);
		cal.add(Calendar.YEAR, -17);
		age = cal.getTime();

		cal.setTime(end);
		cal.add(Calendar.YEAR, +5);
		end = cal.getTime();

		FootballPlayer footballPlayer = new FootballPlayer();

		footballPlayer.setBirthDate(age);
		footballPlayer.setClub(club);
		footballPlayer.setFirstName("Vincent");
		footballPlayer.setLastName("Valentine");
		footballPlayer.setPosition(FootballPlayerPosition.STRIKER);
		footballPlayer.setValue(1000);

		ContractPlayer newContract = new ContractPlayer();
		newContract.setClause(5000000);
		newContract.setClub(club);
		newContract.setPlayer(footballPlayer);
		newContract.setEndDate(end);
		newContract.setStartDate(start);
		newContract.setSalary(1000000);

		Assertions.assertThrows(NumberOfPlayersAndCoachException.class, () -> {
			this.footballPlayerService.saveFootballPlayer(footballPlayer, newContract);
		});
	}

	@Test //CASO NEGATIVO - Registrar Jugador si el salario es mayor a los fondos del club
	void shouldThrowExceptionIfFootballClubMoneyIsLessThanSalary() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException {

		FootballClub club = this.footballClubService.findFootballClubById(10);

		Assertions.assertTrue(club.getStatus() == false); //Vemos que no está publicado

		Date age = new Date(System.currentTimeMillis() - 1);
		Date end = new Date(System.currentTimeMillis() - 1);
		Date start = new Date(System.currentTimeMillis() - 1);

		Calendar cal = Calendar.getInstance();
		cal.setTime(age);
		cal.add(Calendar.YEAR, -17);
		age = cal.getTime();

		cal.setTime(end);
		cal.add(Calendar.YEAR, +5);
		end = cal.getTime();

		FootballPlayer footballPlayer = new FootballPlayer();

		footballPlayer.setBirthDate(age);
		footballPlayer.setClub(club);
		footballPlayer.setFirstName("Vincent");
		footballPlayer.setLastName("Valentine");
		footballPlayer.setPosition(FootballPlayerPosition.STRIKER);
		footballPlayer.setValue(1000000);

		ContractPlayer newContract = new ContractPlayer();
		newContract.setClause(5000000);
		newContract.setClub(club);
		newContract.setPlayer(footballPlayer);
		newContract.setEndDate(end);
		newContract.setStartDate(start);
		newContract.setSalary(1000001); //Ponemos el salario superior a los fondos del club(1000000)

		Assertions.assertThrows(MoneyClubException.class, () -> {
			this.footballPlayerService.saveFootballPlayer(footballPlayer, newContract);
		});
	}

	@Test //CASO NEGATIVO - Registrar Jugador si la edad no supera los 16 años
	void shouldThrowExceptionIfFootballPlayerAgeIsLessThan16() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException {

		FootballClub club = this.footballClubService.findFootballClubById(10);

		Assertions.assertTrue(club.getStatus() == false); //Vemos que no está publicado

		Date age = new Date(System.currentTimeMillis() - 1);
		Date end = new Date(System.currentTimeMillis() - 1);
		Date start = new Date(System.currentTimeMillis() - 1);

		Calendar cal = Calendar.getInstance();
		cal.setTime(age);
		cal.add(Calendar.YEAR, -10);
		age = cal.getTime();

		cal.setTime(end);
		cal.add(Calendar.YEAR, +5);
		end = cal.getTime();

		FootballPlayer footballPlayer = new FootballPlayer();

		footballPlayer.setBirthDate(age);
		footballPlayer.setClub(club);
		footballPlayer.setFirstName("Vincent");
		footballPlayer.setLastName("Valentine");
		footballPlayer.setPosition(FootballPlayerPosition.STRIKER);
		footballPlayer.setValue(1000000);

		ContractPlayer newContract = new ContractPlayer();
		newContract.setClause(5000000);
		newContract.setClub(club);
		newContract.setPlayer(footballPlayer);
		newContract.setEndDate(end);
		newContract.setStartDate(start);
		newContract.setSalary(1000000); //Ponemos el salario superior a los fondos del club(1000000)

		Assertions.assertThrows(DateException.class, () -> {
			this.footballPlayerService.saveFootballPlayer(footballPlayer, newContract);
		});
	}

}
