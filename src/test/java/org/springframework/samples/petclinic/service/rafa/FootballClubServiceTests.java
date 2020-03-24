
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.petclinic.model.Coach;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.service.CoachService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.FootballPlayerService;
import org.springframework.samples.petclinic.service.exceptions.DateException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.samples.petclinic.service.exceptions.NumberOfPlayersAndCoachException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class FootballClubServiceTests {

	@Autowired
	private FootballClubService		footballClubService;

	@Autowired
	private FootballPlayerService	footballPlayerService;

	@Autowired
	private CoachService			coachService;


	@Test //CASO POSITIVO - Buscar Club por ID
	void shouldFindFootballClubById() {
		FootballClub footballClub = null;
		footballClub = this.footballClubService.findFootballClubById(1);
		Assertions.assertTrue(footballClub != null);
	}

	@Test //CASO NEGATIVO - Buscar Club por ID
	void shouldNotFindFootballClubById() {
		FootballClub footballClub = null;
		footballClub = this.footballClubService.findFootballClubById(99);
		Assertions.assertFalse(footballClub != null);
	}

	@Test //CASO POSITIVO - Buscar Club por Username
	void shouldFindFootballClubByUsername() {
		FootballClub footballClub = null;
		footballClub = this.footballClubService.findFootballClubByPresident("presidente1");
		Assertions.assertTrue(footballClub != null);
	}

	@Test //CASO NEGATIVO - Buscar Club por Username
	void shouldNotFindFootballClubByUsername() {
		FootballClub footballClub = null;
		footballClub = this.footballClubService.findFootballClubByPresident("pedro");
		Assertions.assertFalse(footballClub != null);
	}

	@Test //CASO POSITIVO - Buscar Presidente por Username
	void shouldFindPresidentByUsername() {
		President president = null;
		president = this.footballClubService.findPresidentByUsername("rafa");
		Assertions.assertTrue(president != null);
	}

	@Test //CASO NEGATIVO - Buscar Presidente por Username
	void shouldNotFindPresidentByUsername() {
		President president = null;
		president = this.footballClubService.findPresidentByUsername("pedro");
		Assertions.assertFalse(president != null);
	}

	@Test //CASO POSITIVO - Buscar Entrenador por ID de Club
	void shouldFindCoachByClubId() {
		Coach coach = null;
		coach = this.footballClubService.findCoachByClubId(1);
		Assertions.assertTrue(coach != null);
	}

	@Test //CASO NEGATIVO - Buscar Entrenador por ID de Club
	void shouldNotFindCoachByClubId() {
		Coach coach = null;
		coach = this.footballClubService.findCoachByClubId(99);
		Assertions.assertFalse(coach != null);
	}

	@Test //CASO POSITIVO - Buscar todos los Clubes
	void shouldFindAllFootballClubs() {

		List<FootballClub> clubs = new ArrayList<>();

		clubs.addAll(this.footballClubService.findAll());

		int count = clubs.size();

		Assertions.assertTrue(count == 11);

	}

	@Test //CASO POSITIVO - Buscar todos los Clubes Públicos
	void shouldFindAllPublishedFootballClubs() {

		List<FootballClub> clubs = new ArrayList<>();

		clubs.addAll(this.footballClubService.findFootballClubs());

		int count = clubs.size();

		Assertions.assertTrue(count == 9);

	}

	@Test //CASO POSITIVO - Guardar Equipo en la DB y se genera el Id
	void shouldSaveFootballClubAndGenerateId() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException {

		President president = this.footballClubService.findPresidentByUsername("rafa");

		Date now = new Date(System.currentTimeMillis() - 1);

		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.YEAR, -1);
		now = cal.getTime();

		FootballClub footballClub = new FootballClub();

		footballClub.setCity("Midgar");
		footballClub.setFans(0);
		footballClub.setFoundationDate(now);
		footballClub.setMoney(100);
		footballClub.setName("Shinra Inc");
		footballClub.setPresident(president);
		footballClub.setStadium("Suburbios Stadium");
		footballClub.setStatus(false);

		this.footballClubService.saveFootballClub(footballClub);

		FootballClub newFootballClub = this.footballClubService.findFootballClubByPresident("rafa");

		Assertions.assertTrue(newFootballClub != null);
		Assertions.assertTrue(newFootballClub.getId() != null);
	}

	@Test //CASO NEGATIVO - Guardar Equipo en la DB si el presidente ya tiene un Equipo
	void shouldThrowExceptionIfPresidentAlreadyHasOneClub() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException {

		President president = this.footballClubService.findPresidentByUsername("presidente1");

		Date now = new Date(System.currentTimeMillis() - 1);

		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.YEAR, -1);
		now = cal.getTime();

		FootballClub footballClub = new FootballClub();

		footballClub.setId(999);
		footballClub.setCity("Midgar");
		footballClub.setFans(0);
		footballClub.setFoundationDate(now);
		footballClub.setMoney(100);
		footballClub.setName("Shinra Inc");
		footballClub.setPresident(president);
		footballClub.setStadium("Suburbios Stadium");
		footballClub.setStatus(false);

		Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
			this.footballClubService.saveFootballClub(footballClub);
		});
	}

	@Test //CASO NEGATIVO - Guardar Equipo en la DB si ponemos un nombre ya existente
	void shouldThrowExceptionWithTheSameName() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException {

		President president = this.footballClubService.findPresidentByUsername("rafa");

		Date now = new Date(System.currentTimeMillis() - 1);

		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.YEAR, -1);
		now = cal.getTime();

		FootballClub footballClub = new FootballClub();

		footballClub.setId(999);
		footballClub.setCity("Midgar");
		footballClub.setFans(0);
		footballClub.setFoundationDate(now);
		footballClub.setMoney(100);
		footballClub.setName("Sevilla Fútbol Club");
		footballClub.setPresident(president);
		footballClub.setStadium("Suburbios Stadium");
		footballClub.setStatus(false);

		Assertions.assertThrows(DuplicatedNameException.class, () -> {
			this.footballClubService.saveFootballClub(footballClub);
		});
	}

	@Test //CASO NEGATIVO - Guardar Equipo en la DB si ponemos una fecha futura
	void shouldThrowExceptionWithFutureFoundationDate() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException {

		President president = this.footballClubService.findPresidentByUsername("rafa");

		Date now = new Date(System.currentTimeMillis() - 1);

		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.YEAR, +1);
		now = cal.getTime();

		FootballClub footballClub = new FootballClub();

		footballClub.setId(999);
		footballClub.setCity("Midgar");
		footballClub.setFans(0);
		footballClub.setFoundationDate(now);
		footballClub.setMoney(100);
		footballClub.setName("Shinra Inc");
		footballClub.setPresident(president);
		footballClub.setStadium("Suburbios Stadium");
		footballClub.setStatus(false);

		Assertions.assertThrows(DateException.class, () -> {
			this.footballClubService.saveFootballClub(footballClub);
		});
	}

	@Test //CASO POSITIVO - Actualizar Equipo de la DB 
	void shouldUpdateFootballClubData() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException {

		FootballClub footballClub = this.footballClubService.findFootballClubById(9);

		String oldName = footballClub.getName();
		String newName = oldName + "X";

		footballClub.setName(newName);

		this.footballClubService.saveFootballClub(footballClub);

		footballClub = this.footballClubService.findFootballClubById(9);

		Assertions.assertTrue(footballClub.getName().equals(newName));
	}

	@Test //CASO NEGATIVO - Actualizar Equipo de la DB con mismo nombre
	void shouldThrowExceptionUpdatingWithTheSameName() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException {

		FootballClub footballClub = this.footballClubService.findFootballClubById(9);

		String newName = "Sevilla Fútbol Club";

		footballClub.setName(newName);

		//A la hora de actualizar un club el saveClub del Service ignora el DuplicatedNameException
		Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
			this.footballClubService.saveFootballClub(footballClub);
		});
	}

	@Test //CASO NEGATIVO - Actualizar Equipo de la DB con fecha Futura
	void shouldThrowExceptionUpdatingWithFutureDate() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException {

		FootballClub footballClub = this.footballClubService.findFootballClubById(9);

		Date now = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.YEAR, +1);
		now = cal.getTime();

		footballClub.setFoundationDate(now);

		Assertions.assertThrows(DateException.class, () -> {
			this.footballClubService.saveFootballClub(footballClub);
		});
	}

	@Test //CASO POSITIVO - Publicar Equipo de la DB con mínimo de 5 jugadores y 1 entrenador
	void shouldPublishFootballClub() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException {

		FootballClub footballClub = this.footballClubService.findFootballClubById(10);

		Assertions.assertFalse(footballClub.getStatus() == true);

		Collection<FootballPlayer> players = this.footballPlayerService.findAllFootballPlayersFA();
		for (FootballPlayer a : players) {
			a.setClub(footballClub);
		}

		Coach coach = this.coachService.findCoachById(10);
		coach.setClub(footballClub);

		footballClub.setStatus(true);

		this.footballClubService.saveFootballClub(footballClub);

		footballClub = this.footballClubService.findFootballClubById(10);

		Assertions.assertTrue(footballClub.getStatus() == true);
	}

	@Test //CASO NEGATIVO - Publicar Equipo de la DB sin mínimo de 5 jugadores y 1 entrenador
	void shouldThrowExceptionPublishingWiththoutMinimunPlayersAndCoach() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException {

		FootballClub footballClub = this.footballClubService.findFootballClubById(10);

		footballClub.setStatus(true);

		Assertions.assertThrows(NumberOfPlayersAndCoachException.class, () -> {
			this.footballClubService.saveFootballClub(footballClub);
		});
	}

	@Test //CASO POSITIVO - Borrar Equipo (No hay caso negativo)
	void shouldDeleteFootballClub() throws DataAccessException {

		FootballClub footballClub = this.footballClubService.findFootballClubById(10);

		Assertions.assertTrue(footballClub != null);

		this.footballClubService.deleteFootballClub(footballClub);

		footballClub = this.footballClubService.findFootballClubById(10);

		Assertions.assertFalse(footballClub != null);
	}

}
