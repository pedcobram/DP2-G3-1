
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Coach;
import org.springframework.samples.petclinic.repository.CoachRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
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

	//Guardar entrenador con validación de nombre duplicado
	@Transactional(rollbackFor = DuplicatedNameException.class)
	public void saveCoach(@Valid final Coach coach) throws DataAccessException, DuplicatedNameException {

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

		//Si el campo de nombre tiene contenido y el "otherFootClub" existe y no coincide el id con el actual lanzamos excepción
		if (StringUtils.hasLength(coach.getFirstName()) && StringUtils.hasLength(coach.getLastName()) && otherCoach != null && otherCoach.getId() != coach.getId()) {
			throw new DuplicatedNameException();
		} else {
			this.coachRepository.save(coach);

		}
	}
}
