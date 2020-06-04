
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.samples.petclinic.model.Competition;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayerMatchStatistic;
import org.springframework.samples.petclinic.model.Match;

public interface CompetitionRepository {

	Competition findById(int id);

	Collection<Competition> findAllPublishedCompetitions();

	Collection<Competition> findMyCompetitions(final String username);

	void save(Competition competition);

	void delete(Competition competition);

	Collection<FootballClub> findAllPublishedClubs();

	Collection<Match> findAllMatchByJornadaId(final Integer jornadaId);

	Collection<Match> findAllMatchByCompetitionId(Integer compId);

	Collection<Competition> findAllCompetition();

	Collection<FootballPlayerMatchStatistic> findFPMSByPlayerIdAndCompId(final Integer playerId, final Integer compId);

}
