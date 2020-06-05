
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Competition;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayerMatchStatistic;
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

	@Override
	@Query("select m from Match m where m.jornada.id = ?1")
	Collection<Match> findAllMatchByJornadaId(final Integer jornadaId);

	@Override
	@Query("select m from Match m where m.jornada.calendary.competition.id = ?1")
	Collection<Match> findAllMatchByCompetitionId(final Integer compId);

	@Override
	@Query("select f from FootballPlayerMatchStatistic f where f.player.id = ?1 and f.matchRecord.match.jornada.calendary.competition.id = ?2")
	Collection<FootballPlayerMatchStatistic> findFPMSByPlayerIdAndCompId(final Integer playerId, final Integer compId);

}
