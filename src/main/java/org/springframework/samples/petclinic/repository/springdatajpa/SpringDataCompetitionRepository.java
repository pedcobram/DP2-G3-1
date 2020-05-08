
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Calendary;
import org.springframework.samples.petclinic.model.Competition;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.Jornada;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.repository.CompetitionRepository;

public interface SpringDataCompetitionRepository extends CompetitionRepository, Repository<Competition, Integer> {

	@Override
	@Query("select c from Competition c where c.id = ?1")
	Competition findById(int id);

	@Override
	@Query("select c from Competition c")
	Collection<Competition> findAllCompetition();

	@Override
	@Query("select c from Competition c where c.status = true")
	Collection<Competition> findAllPublishedCompetitions();

	@Override
	@Query("select c from Competition c where c.creator = ?1")
	Collection<Competition> findMyCompetitions(final String username);

	//Clubes públicos
	@Override
	@Query("select c from FootballClub c where c.status = true")
	Collection<FootballClub> findAllPublishedClubs();

	//Buscar Jornadas por CompeticiónID

	@Override
	@Query("select j from Jornada j where j.calendary.competition.id = ?1")
	Collection<Jornada> findAllJornadasFromCompetitionId(final Integer compId);

	@Override
	@Query("select m from Match m where m.jornada.id = ?1")
	Collection<Match> findAllMatchByJornadaId(final Integer jornadaId);

	@Override
	@Query("select m from Match m where m.jornada.calendary.competition.id = ?1")
	Collection<Match> findAllMatchByCompetitionId(final Integer compId);

	@Override
	@Query("select c from Calendary c where c.competition.id = ?1")
	Calendary findCalendaryByCompetitionId(int competitionId);

	@Override
	@Query("select j from Jornada j where j.id = ?1")
	Jornada findJornadaById(int jornadaId);
}
