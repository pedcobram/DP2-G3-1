
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Competition;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayerMatchStatistic;
import org.springframework.samples.petclinic.model.Match;

public interface CompetitionRepository {

	Competition findById(int id) throws DataAccessException;

	Collection<Competition> findAllPublishedCompetitions() throws DataAccessException;

	Collection<Competition> findMyCompetitions(final String username) throws DataAccessException;

	void save(Competition competition) throws DataAccessException;

	void delete(Competition competition) throws DataAccessException;

	Collection<FootballClub> findAllPublishedClubs();

	Collection<Match> findAllMatchByJornadaId(final Integer jornadaId) throws DataAccessException;

	Collection<Match> findAllMatchByCompetitionId(Integer compId) throws DataAccessException;

	Collection<Competition> findAllCompetition() throws DataAccessException;

	Collection<FootballPlayerMatchStatistic> findFPMSByPlayerIdAndCompId(final Integer playerId, final Integer compId);

}
