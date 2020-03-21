
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Coach;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.repository.CoachRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.samples.petclinic.service.exceptions.MoneyClubException;
import org.springframework.samples.petclinic.service.exceptions.NumberOfPlayersAndCoachException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class CoachService {

	@Autowired
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

	//Buscar todos los entrenadores con club
	public Collection<Coach> findAllCoachsWithClub() throws DataAccessException {
		return this.coachRepository.findAllWithClub();
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
		DuplicatedNameException.class, NumberOfPlayersAndCoachException.class, MoneyClubException.class
	})
	public void saveCoach(@Valid final Coach coach, final FootballClub myClub) throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, MoneyClubException {

		String firstname = coach.getFirstName().toLowerCase();
		String lastname = coach.getLastName().toLowerCase();
		Coach otherCoach = null;

		//Creamos un "otherPlayer" si existe uno en la db con el mismo nombre y diferente id
		for (Coach o : this.coachRepository.findAll()) {
			String ofirst = o.getFirstName().toLowerCase();
			String olast = o.getLastName().toLowerCase();
			ofirst = ofirst.toLowerCase();
			olast = olast.toLowerCase();
			if (ofirst.equals(firstname) && olast.equals(lastname) && o.getId() != coach.getId()) {
				otherCoach = o;
			}
		}

		//RN: Nombre Duplicado
		if (StringUtils.hasLength(coach.getFirstName()) && StringUtils.hasLength(coach.getLastName()) && otherCoach != null && otherCoach.getId() != coach.getId()) {
			throw new DuplicatedNameException();
		}

		Coach thereIsACoach = this.findCoachByClubId(myClub.getId());

		//RN: Solo se puede registrar a un entrenador
		if (thereIsACoach != null) { //Validación Solo se puede tener un Coach
			throw new NumberOfPlayersAndCoachException();
		}

		//RN: El salario no puede ser superior a los fondos del equipo
		if (myClub.getMoney() < coach.getSalary()) {
			throw new MoneyClubException();
		} else {
			myClub.setMoney(myClub.getMoney() - coach.getSalary());
		}

		coach.setClub(myClub);
		this.coachRepository.save(coach);

	}

	//FICHAR A OTRO ENTRENADOR SI TENGO ENTRENADOR
	@Transactional(rollbackFor = MoneyClubException.class)
	public void signCoach(@Valid final Coach coach, final Integer clause) throws DataAccessException, MoneyClubException {

		//RN: La transacción total no puede ser mayor a los fondos del club
		if (coach.getClub().getMoney() < coach.getSalary() + clause) {
			throw new MoneyClubException();
		} else {
			coach.getClub().setMoney(coach.getClub().getMoney() - (coach.getSalary() + clause));
		}

		this.coachRepository.save(coach);

	}

	//DESPEDIR ENTRENADOR
	@Transactional(rollbackFor = MoneyClubException.class)
	public void fireCoach(@Valid final Coach coach) throws DataAccessException, MoneyClubException {

		//RN: La transacción total no puede ser mayor a los fondos del club

		if (coach.getClub().getMoney() < coach.getClause()) {
			throw new MoneyClubException();
		} else {
			coach.getClub().setMoney(coach.getClub().getMoney() - coach.getClause());
		}
		coach.setClub(null);
		this.coachRepository.save(coach);

	}
}
