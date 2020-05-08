
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Jornada;
import org.springframework.samples.petclinic.repository.JornadaRepository;

public interface SpringDataJornadaRepository extends JornadaRepository, Repository<Jornada, Integer> {

	//Buscar Jornadas por CompeticiónID

	@Override
	@Query("select j from Jornada j where j.calendary.competition.id = ?1")
	Collection<Jornada> findAllJornadasFromCompetitionId(final Integer compId);

}
