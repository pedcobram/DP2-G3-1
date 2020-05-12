
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Jornada;
import org.springframework.samples.petclinic.repository.JornadaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JornadaService {

	private JornadaRepository jornadaRepository;


	@Autowired
	public JornadaService(final JornadaRepository jornadaRepository) {
		this.jornadaRepository = jornadaRepository;

	}

	@Transactional
	public Collection<Jornada> findAllJornadasFromCompetitionId(final Integer compId) {
		return this.jornadaRepository.findAllJornadasFromCompetitionId(compId);
	}

	@Transactional
	public Jornada findJornadaById(final int id) throws DataAccessException {
		return this.jornadaRepository.findById(id);
	}

	@Transactional
	public void deleteJornada(final Jornada jornada) throws DataAccessException {
		this.jornadaRepository.delete(jornada);
	}

	@Transactional
	public void saveJornada(final Jornada j) throws DataAccessException {
		this.jornadaRepository.save(j);

	}

}
