
package org.springframework.samples.petclinic.service;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Coach;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.repository.CoachRepository;
import org.springframework.samples.petclinic.service.exceptions.DateException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.samples.petclinic.service.exceptions.MoneyClubException;
import org.springframework.samples.petclinic.service.exceptions.NumberOfPlayersAndCoachException;
import org.springframework.samples.petclinic.service.exceptions.SalaryException;
import org.springframework.samples.petclinic.service.exceptions.StatusException;
import org.springframework.samples.petclinic.service.exceptions.StatusRegisteringException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class CoachService {

	private CoachRepository coachRepository;


	@Autowired
	public CoachService(final CoachRepository coachRepository) {
		this.coachRepository = coachRepository;
	}

	//Buscar entrenador por id
	public Coach findCoachById(final int id) throws DataAccessException {
		return this.coachRepository.findById(id);
	}

	//Buscar todos los entrenadores
	public Collection<Coach> findAllCoachs() throws DataAccessException {
		return this.coachRepository.findAll();
	}

	//Buscar todos los entrenadores FA
	public Collection<Coach> findAllCoachsFA() throws DataAccessException {
		return this.coachRepository.findAllFreeAgents();
	}

	//Buscar entrenador por id de club
	public Coach findCoachByClubId(final int id) throws DataAccessException {
		return this.coachRepository.findCoachByClubId(id);
	}

	//REGISTRAR ENTRENADOR o FICHAR AGENTE LIBRE SI NO TENGO ENTRENADOR
	@Transactional(rollbackFor = {
		DuplicatedNameException.class, NumberOfPlayersAndCoachException.class, DateException.class, StatusException.class, SalaryException.class, MoneyClubException.class, StatusRegisteringException.class
	})
	public void saveCoach(@Valid final Coach coach, final FootballClub myClub)
		throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, MoneyClubException, SalaryException, DateException, StatusException, StatusRegisteringException {

		String firstname = coach.getFirstName().toLowerCase();
		String lastname = coach.getLastName().toLowerCase();
		Coach otherCoach = null;

		//Creamos un "otherPlayer" si existe uno en la db con el mismo nombre y diferente id
		for (Coach o : this.coachRepository.findAll()) {
			String ofirst = o.getFirstName().toLowerCase();
			String olast = o.getLastName().toLowerCase();
			ofirst = ofirst.toLowerCase();
			olast = olast.toLowerCase();
			if (ofirst.equals(firstname) && olast.equals(lastname) && !o.getId().equals(coach.getId())) {
				otherCoach = o;
			}
		}

		//RN: Nombre Duplicado
		if (StringUtils.hasLength(coach.getFirstName()) && StringUtils.hasLength(coach.getLastName()) && otherCoach != null && !otherCoach.getId().equals(coach.getId())) {
			throw new DuplicatedNameException();
		}

		Date now = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.YEAR, -18);
		now = cal.getTime();

		//RN: Debe tener al menos 18 años
		if (coach.getBirthDate().after(now)) {
			throw new DateException();
		}

		Coach thereIsACoach = this.findCoachByClubId(myClub.getId());

		//RN: Solo se puede registrar a un entrenador
		if (thereIsACoach != null) { //Validación Solo se puede tener un Coach
			throw new NumberOfPlayersAndCoachException();
		}

		//RN: El salario mínimo y máximo
		if (coach.getSalary() < 1000000 || coach.getSalary() > 25000000) {
			throw new SalaryException();
		}

		//RN: El salario no puede ser superior a los fondos del equipo
		if (myClub.getMoney() < coach.getSalary()) {
			throw new MoneyClubException();
		}

		//RN: No se puede fichar a un entrenador de Otro equipo si no tenemos entrenador
		if (myClub.getStatus() == true && coach.isNew()) {
			throw new StatusRegisteringException();
		}

		//RN: No se puede registrar a un entrenador si el club es público
		if (myClub.getStatus() == true && coach.getClub() != null) {
			throw new StatusException();
		}

		myClub.setMoney(myClub.getMoney() - coach.getSalary());
		coach.setClub(myClub);
		this.coachRepository.save(coach);

	}

	//FICHAR A OTRO ENTRENADOR SI TENGO ENTRENADOR
	@Transactional(rollbackFor = MoneyClubException.class)
	public void signCoach(@Valid final Coach coachToUpdate, final FootballClub myClub, final Integer clause) throws DataAccessException, MoneyClubException, SalaryException {

		//RN: El salario mínimo y máximo
		if (coachToUpdate.getSalary() < 1000000 || coachToUpdate.getSalary() > 25000000) {
			throw new SalaryException();
		}

		//RN: La transacción total no puede ser mayor a los fondos del club
		if (myClub.getMoney() < coachToUpdate.getSalary() + clause) {
			throw new MoneyClubException();
		}

		Coach myOldCoach = this.findCoachByClubId(myClub.getId());

		if (coachToUpdate.getClub() != null) { //Si nuestro fichaje tiene club
			myOldCoach.setClub(coachToUpdate.getClub()); //Mi entrenador se va al suyo
		} else { //Si es agente libre mi entrenador pasa a serlo
			myOldCoach.setClub(null);
			myOldCoach.setSalary(0);
			myOldCoach.setClause(0);
		}

		coachToUpdate.setClub(myClub);

		myClub.setMoney(myClub.getMoney() - (coachToUpdate.getSalary() + clause));

		this.coachRepository.save(coachToUpdate);

	}

	//DESPEDIR ENTRENADOR
	@Transactional(rollbackFor = MoneyClubException.class)
	public void fireCoach(@Valid final Coach coach) throws DataAccessException, MoneyClubException {

		//RN: La transacción total no puede ser mayor a los fondos del club

		if (coach.getClub().getMoney() < coach.getClause()) {
			throw new MoneyClubException();
		}

		coach.getClub().setMoney(coach.getClub().getMoney() - coach.getClause());
		coach.setClub(null);

		this.coachRepository.save(coach);

	}
}
