
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
import org.springframework.samples.petclinic.model.Coach;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.service.CoachService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.exceptions.DateException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.samples.petclinic.service.exceptions.MoneyClubException;
import org.springframework.samples.petclinic.service.exceptions.NumberOfPlayersAndCoachException;
import org.springframework.samples.petclinic.service.exceptions.SalaryException;
import org.springframework.samples.petclinic.service.exceptions.StatusException;
import org.springframework.samples.petclinic.service.exceptions.StatusRegisteringException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CoachServiceTests {

	@Autowired
	private FootballClubService	footballClubService;

	@Autowired
	private CoachService		coachService;


	@Test //CASO POSITIVO - Buscar Entrenador por ID
	void shouldFindCoachById() {
		Coach coach = null;
		coach = this.coachService.findCoachById(1);
		Assertions.assertTrue(coach != null);
	}

	@Test //CASO NEGATIVO - Buscar Entrenador por ID
	void shouldNotFindCoachById() {
		Coach coach = null;
		coach = this.coachService.findCoachById(99);
		Assertions.assertFalse(coach != null);
	}

	@Test //CASO POSITIVO - Buscar Entrenador por ID de Club
	void shouldFindCoachByClubId() {
		Coach coach = null;
		coach = this.coachService.findCoachByClubId(1);
		Assertions.assertTrue(coach != null);
	}

	@Test //CASO NEGATIVO - Buscar Entrenador por ID de Club
	void shouldNotFindCoachByClubId() {
		Coach coach = null;
		coach = this.coachService.findCoachByClubId(99);
		Assertions.assertFalse(coach != null);
	}

	@Test //CASO POSITIVO - Buscar todos los Entrenadores
	void shouldFindAllCoachs() {

		List<Coach> coachs = new ArrayList<>();

		coachs.addAll(this.coachService.findAllCoachs());

		int count = coachs.size();

		Assertions.assertTrue(count == 12);

	}

	@Test //CASO POSITIVO - Buscar todos los Entrenadores con Club
	void shouldFindAllCoachsWithClub() {

		List<Coach> coachs = new ArrayList<>();

		coachs.addAll(this.coachService.findAllCoachsWithClub());

		int count = coachs.size();

		Assertions.assertTrue(count == 9);

	}

	@Test //CASO POSITIVO - Buscar todos los Jugadores Agentes Libres
	void shouldFindAllCoachsFreeAgents() {

		List<Coach> coachs = new ArrayList<>();

		coachs.addAll(this.coachService.findAllCoachsFA());

		int count = coachs.size();

		Assertions.assertTrue(count == 3);

	}

	@Test //CASO POSITIVO - Guardar Entrenador 
	void shouldSaveCoachAndGenerateId() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException, SalaryException, StatusRegisteringException {

		Coach coach1 = this.coachService.findCoachById(13);
		Assertions.assertFalse(coach1 != null); //Vemos que no existe el entrenador

		FootballClub club = this.footballClubService.findFootballClubById(10);
		Assertions.assertTrue(club.getStatus() == false); //Vemos que no está publicado

		Coach clubCoach = this.coachService.findCoachByClubId(10);
		Assertions.assertFalse(clubCoach != null); //Vemos que no tiene entrenador	

		Date age = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(age);
		cal.add(Calendar.YEAR, -18);
		age = cal.getTime();

		Coach coach = new Coach();
		coach.setBirthDate(age);
		coach.setClause(10000);
		coach.setFirstName("Qui-Gon");
		coach.setLastName("Jinn");
		coach.setSalary(1000000);

		this.coachService.saveCoach(coach, club);
		clubCoach = this.coachService.findCoachByClubId(10);

		Assertions.assertTrue(clubCoach != null); //Vemos que ahora SI tiene entrenador
		coach1 = this.coachService.findCoachById(13);

		Assertions.assertTrue(coach1 != null); //Vemos que ahora SI existe el entrenador
		Assertions.assertTrue(coach1.getId() != null);
	}

	@Test //CASO NEGATIVO - Guardar Entrenador si se deja un campo en blanco
	void shouldThrowExceptionIfHastEmptyFields() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException, SalaryException {

		FootballClub club = this.footballClubService.findFootballClubById(10);
		Assertions.assertTrue(club.getStatus() == false); //Vemos que no está publicado

		Date age = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(age);
		cal.add(Calendar.YEAR, -18);
		age = cal.getTime();

		Coach coach = new Coach();
		coach.setBirthDate(age);
		coach.setClause(10000);
		coach.setFirstName(""); //DEJAMOS EN BLANCO PARA QUE FALLE
		coach.setLastName("Jinn");
		coach.setSalary(1000000);

		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			this.coachService.saveCoach(coach, club);
		});
	}

	@Test //CASO NEGATIVO - Guardar Entrenador si el club ya es público - RN: Si el club es público no puede registrar a un entrenador
	void shouldThrowExceptionIfFootballClubIsPublished() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException, SalaryException {

		FootballClub club = this.footballClubService.findFootballClubById(11);
		Assertions.assertTrue(club.getStatus() == true); //Vemos que si está publicado

		Coach clubCoach = this.coachService.findCoachByClubId(11);
		Assertions.assertFalse(clubCoach != null); //Vemos que no tiene entrenador	

		Date age = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(age);
		cal.add(Calendar.YEAR, -18);
		age = cal.getTime();

		Coach coach = new Coach();
		coach.setBirthDate(age);
		coach.setClause(10000);
		coach.setFirstName("Qui-Gon");
		coach.setLastName("Jinn");
		coach.setSalary(1000000);

		Assertions.assertThrows(StatusRegisteringException.class, () -> {
			this.coachService.saveCoach(coach, club);
		});
	}

	@Test //CASO NEGATIVO - Fichar Entrenador si el club ya es público y no tiene entrenador - RN: Si el club es público no puede registrar a un entrenador o fichar a uno de otro equipo (debe fichar a un agente libre)
	void shouldThrowExceptionIfFootballClubIsPublishedAndIFiredMyCoachAndNoHaveOne() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException, SalaryException {

		FootballClub club = this.footballClubService.findFootballClubById(1);
		Assertions.assertTrue(club.getStatus() == true); //Vemos que si está publicado

		Coach myCoach = this.coachService.findCoachByClubId(1);
		Assertions.assertTrue(myCoach != null); //Vemos que si tiene entrenador	

		Coach coachToSign = this.coachService.findCoachByClubId(2);
		Assertions.assertTrue(coachToSign != null); //Vemos que si tiene entrenador	

		this.coachService.fireCoach(myCoach); //Despedimos a mi entrenador

		Assertions.assertThrows(StatusException.class, () -> {
			this.coachService.saveCoach(coachToSign, club);
		});
	}

	@Test //CASO NEGATIVO - Guardar Entrenador si el club ya tiene entrenador - RN: Solo se puede registrar a un entrenador
	void shouldThrowExceptionIfFootballClubAlreadyHasCoach() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException, SalaryException {

		FootballClub club = this.footballClubService.findFootballClubById(8);
		Assertions.assertTrue(club.getStatus() == true); //Vemos que si está publicado

		Coach clubCoach = this.coachService.findCoachByClubId(8);
		Assertions.assertTrue(clubCoach != null); //Vemos que si tiene entrenador	

		Date age = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(age);
		cal.add(Calendar.YEAR, -18);
		age = cal.getTime();

		Coach coach = new Coach();
		coach.setBirthDate(age);
		coach.setClause(10000);
		coach.setFirstName("Qui-Gon");
		coach.setLastName("Jinn");
		coach.setSalary(1000000);

		Assertions.assertThrows(NumberOfPlayersAndCoachException.class, () -> {
			this.coachService.saveCoach(coach, club);
		});
	}

	@Test //CASO NEGATIVO - Guardar Entrenador si el club no existe
	void shouldThrowExceptionIfFootballClubNotExists() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException, SalaryException {

		FootballClub club = this.footballClubService.findFootballClubById(99);
		Assertions.assertTrue(club == null); //Vemos que no existe

		Date age = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(age);
		cal.add(Calendar.YEAR, -18);
		age = cal.getTime();

		Coach coach = new Coach();
		coach.setBirthDate(age);
		coach.setClause(10000);
		coach.setFirstName("Qui-Gon");
		coach.setLastName("Jinn");
		coach.setSalary(1000000);

		Assertions.assertThrows(NullPointerException.class, () -> {
			this.coachService.saveCoach(coach, club);
		});
	}

	@Test //CASO NEGATIVO - Guardar Entrenador si tiene el mismo nombre - RN: Nombre Duplicado
	void shouldThrowExceptionIfSameName() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException, SalaryException {

		FootballClub club = this.footballClubService.findFootballClubById(10);
		Assertions.assertTrue(club.getStatus() == false); //Vemos que no está publicado

		Date age = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(age);
		cal.add(Calendar.YEAR, -18);
		age = cal.getTime();

		Coach coach = new Coach();
		coach.setBirthDate(age);
		coach.setClause(10000);
		coach.setFirstName("Unai");
		coach.setLastName("Emery");
		coach.setSalary(1000000);

		Assertions.assertThrows(DuplicatedNameException.class, () -> {
			this.coachService.saveCoach(coach, club);
		});
	}

	@Test //CASO NEGATIVO - Guardar Entrenador si el salario es mayor a los fondos del club - RN: El salario no puede ser superior a los fondos del equipo
	void shouldThrowExceptionIfFootballClubMoneyIsLessThanSalary() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException, SalaryException {

		FootballClub club = this.footballClubService.findFootballClubById(10);
		Assertions.assertTrue(club.getStatus() == false); //Vemos que no está publicado

		Date age = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(age);
		cal.add(Calendar.YEAR, -18);
		age = cal.getTime();

		Coach coach = new Coach();
		coach.setBirthDate(age);
		coach.setClause(10000);
		coach.setFirstName("Qui-Gon");
		coach.setLastName("Jinn");
		coach.setSalary(25000000);

		Assertions.assertThrows(MoneyClubException.class, () -> {
			this.coachService.saveCoach(coach, club);
		});
	}

	@Test //CASO NEGATIVO - Guardar Entrenador si el salario es menor al Mínimo - RN: El salario mínimo y máximo
	void shouldThrowExceptionIfSalaryIsLessThanMinimum() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException, SalaryException {

		FootballClub club = this.footballClubService.findFootballClubById(10);
		Assertions.assertTrue(club.getStatus() == false); //Vemos que no está publicado

		Date age = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(age);
		cal.add(Calendar.YEAR, -18);
		age = cal.getTime();

		Coach coach = new Coach();
		coach.setBirthDate(age);
		coach.setClause(10000);
		coach.setFirstName("Qui-Gon");
		coach.setLastName("Jinn");
		coach.setSalary(999999);

		Assertions.assertThrows(SalaryException.class, () -> {
			this.coachService.saveCoach(coach, club);
		});
	}

	@Test //CASO NEGATIVO - Guardar Entrenador si el salario es mayor al Máximo - RN: El salario mínimo y máximo
	void shouldThrowExceptionIfSalaryIsMoreThanMaximum() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException, SalaryException {

		FootballClub club = this.footballClubService.findFootballClubById(10);
		Assertions.assertTrue(club.getStatus() == false); //Vemos que no está publicado

		Date age = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(age);
		cal.add(Calendar.YEAR, -18);
		age = cal.getTime();

		Coach coach = new Coach();
		coach.setBirthDate(age);
		coach.setClause(10000);
		coach.setFirstName("Qui-Gon");
		coach.setLastName("Jinn");
		coach.setSalary(25000001);

		Assertions.assertThrows(SalaryException.class, () -> {
			this.coachService.saveCoach(coach, club);
		});
	}

	@Test //CASO NEGATIVO - Guardar Entrenador si tiene menos de 18 años - RN: Debe tener al menos 18 años
	void shouldThrowExceptionIfisNot18YearsOld() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException, SalaryException {

		FootballClub club = this.footballClubService.findFootballClubById(10);
		Assertions.assertTrue(club.getStatus() == false); //Vemos que no está publicado

		Date age = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(age);
		cal.add(Calendar.YEAR, -17);
		age = cal.getTime();

		Coach coach = new Coach();
		coach.setBirthDate(age);
		coach.setClause(10000);
		coach.setFirstName("Qui-Gon");
		coach.setLastName("Jinn");
		coach.setSalary(1000000);

		Assertions.assertThrows(DateException.class, () -> {
			this.coachService.saveCoach(coach, club);
		});
	}

	@Test //CASO POSITIVO - Fichar a otro Entrenador
	void shouldSignACoach() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException, SalaryException {

		FootballClub myClub = this.footballClubService.findFootballClubById(7);
		Assertions.assertTrue(myClub.getStatus() == true); //Vemos que si está publicado
		Assertions.assertTrue(myClub.getMoney() == 350000000); //Vemos que sus fondos son de 10mill€

		Coach myCoach = this.coachService.findCoachByClubId(7);
		Assertions.assertTrue(myCoach != null); //Vemos que si existe el entrenador

		Coach coachToSing = this.coachService.findCoachByClubId(2);
		Assertions.assertTrue(coachToSing != null); //Vemos que si existe el entrenador
		String coachToSingName = coachToSing.getFirstName();
		String coachToSingLastName = coachToSing.getLastName();

		Integer coachToSingClause = coachToSing.getClause();
		Assertions.assertTrue(coachToSing.getClause() == 10000000); //Vemos que su cláusula es de 10mill€
		Integer newSalary = 25000000; //25mill€

		coachToSing.setSalary(newSalary);

		this.coachService.signCoach(coachToSing, myClub, coachToSingClause);

		myCoach = this.coachService.findCoachByClubId(7); //Vemos que nuestro entrenador ha cambiado por el que queríamos fichar

		Assertions.assertTrue(coachToSingName.equals(myCoach.getFirstName()) && coachToSingLastName.equals(myCoach.getLastName()));

	}

	@Test //CASO NEGATIVO - Fichar a otro Entrenador si el salario + la cláusula del otro Entrenador es mayor a los fondos del club - RN: La transacción total no puede ser mayor a los fondos del club
	void shouldThrowExceptionIfFootballClubMoneyIsLessThanSalaryPlusClause() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException, SalaryException {

		FootballClub myClub = this.footballClubService.findFootballClubById(8);
		Assertions.assertTrue(myClub.getStatus() == true); //Vemos que si está publicado
		Assertions.assertTrue(myClub.getMoney() == 10000000); //Vemos que sus fondos son de 10mill€

		Coach myCoach = this.coachService.findCoachByClubId(8);
		Assertions.assertTrue(myCoach != null); //Vemos que si existe el entrenador

		Coach coachToSing = this.coachService.findCoachByClubId(2);
		Assertions.assertTrue(coachToSing != null); //Vemos que si existe el entrenador

		Integer coachToSingClause = coachToSing.getClause();
		Assertions.assertTrue(coachToSing.getClause() == 10000000); //Vemos que su cláusula es de 10mill€
		Integer newSalary = 1000000; //1mill€

		coachToSing.setSalary(newSalary);

		Assertions.assertThrows(MoneyClubException.class, () -> {
			this.coachService.signCoach(coachToSing, myClub, coachToSingClause);
		});
	}

	@Test //CASO NEGATIVO - Fichar a otro Entrenador si el salario es menor al Mínimo - RN: El salario mínimo y máximo
	void shouldThrowExceptionSigningIfSalaryLessThanMinimum() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException, SalaryException {

		FootballClub myClub = this.footballClubService.findFootballClubById(7);
		Assertions.assertTrue(myClub.getStatus() == true); //Vemos que si está publicado
		Assertions.assertTrue(myClub.getMoney() == 350000000); //Vemos que sus fondos son de 10mill€

		Coach myCoach = this.coachService.findCoachByClubId(7);
		Assertions.assertTrue(myCoach != null); //Vemos que si existe el entrenador

		Coach coachToSing = this.coachService.findCoachByClubId(2);
		Assertions.assertTrue(coachToSing != null); //Vemos que si existe el entrenador

		Integer coachToSingClause = coachToSing.getClause();
		Assertions.assertTrue(coachToSing.getClause() == 10000000); //Vemos que su cláusula es de 10mill€
		Integer newSalary = 999999; //999.999,00€

		coachToSing.setSalary(newSalary);

		Assertions.assertThrows(SalaryException.class, () -> {
			this.coachService.signCoach(coachToSing, myClub, coachToSingClause);
		});
	}

	@Test //CASO NEGATIVO - Fichar a otro Entrenador si el salario es mayor al Maximun - RN: El salario mínimo y máximo
	void shouldThrowExceptionSigningIfSalaryMoreThanMaximum() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException, SalaryException {

		FootballClub myClub = this.footballClubService.findFootballClubById(7);
		Assertions.assertTrue(myClub.getStatus() == true); //Vemos que si está publicado
		Assertions.assertTrue(myClub.getMoney() == 350000000); //Vemos que sus fondos son de 10mill€

		Coach myCoach = this.coachService.findCoachByClubId(7);
		Assertions.assertTrue(myCoach != null); //Vemos que si existe el entrenador

		Coach coachToSing = this.coachService.findCoachByClubId(2);
		Assertions.assertTrue(coachToSing != null); //Vemos que si existe el entrenador

		Integer coachToSingClause = coachToSing.getClause();
		Assertions.assertTrue(coachToSing.getClause() == 10000000); //Vemos que su cláusula es de 10mill€
		Integer newSalary = 25000001; //25mill€ + 1€

		coachToSing.setSalary(newSalary);

		Assertions.assertThrows(SalaryException.class, () -> {
			this.coachService.signCoach(coachToSing, myClub, coachToSingClause);
		});
	}

	@Test //CASO POSITIVO - Despedir a mi Entrenador
	void shouldFireACoach() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException, SalaryException {

		FootballClub myClub = this.footballClubService.findFootballClubById(1);
		Assertions.assertTrue(myClub.getStatus() == true); //Vemos que si está publicado
		Assertions.assertTrue(myClub.getMoney() == 150000000); //Vemos que sus fondos son de 150mill€

		Coach myCoach = this.coachService.findCoachByClubId(1);

		Assertions.assertTrue(myCoach != null); //Vemos que si existe el entrenador

		Assertions.assertTrue(myCoach.getClause() == 6000000); //Vemos que su cláusula es de 6mill€

		this.coachService.fireCoach(myCoach);

		myCoach = this.coachService.findCoachByClubId(1);

		Assertions.assertFalse(myCoach != null); //Vemos que no existe el entrenador vinculado al club

	}

	@Test //CASO NEGATIVO - Despedir a mi Entrenador si los fondos de mi club son menores a la cláusula de mi entrenador
	void shouldThrowExceptionFiringACoach() throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException, MoneyClubException, StatusException, SalaryException {

		FootballClub myClub = this.footballClubService.findFootballClubById(8);
		Assertions.assertTrue(myClub.getStatus() == true); //Vemos que si está publicado
		Assertions.assertTrue(myClub.getMoney() == 10000000); //Vemos que sus fondos son de 10mill€

		Coach myCoach = this.coachService.findCoachByClubId(8);

		Assertions.assertTrue(myCoach != null); //Vemos que si existe el entrenador

		Assertions.assertTrue(myCoach.getClause() == 15000000); //Vemos que su cláusula es de 15mill€

		Assertions.assertThrows(MoneyClubException.class, () -> {
			this.coachService.fireCoach(myCoach);
		});
	}

}
