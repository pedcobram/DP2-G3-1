
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.samples.petclinic.model.Jornada;

public interface JornadaRepository {

	void delete(Jornada Jornada);

	Collection<Jornada> findAllJornadasFromCompetitionId(Integer compId);

	Jornada findById(int id);

	void save(Jornada j);
}
