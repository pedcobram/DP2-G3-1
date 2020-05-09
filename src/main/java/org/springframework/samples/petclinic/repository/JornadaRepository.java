
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Jornada;

public interface JornadaRepository {

	void delete(Jornada Jornada) throws DataAccessException;

	Collection<Jornada> findAllJornadasFromCompetitionId(Integer compId);

	Jornada findById(int id);

	void save(Jornada j);
}
