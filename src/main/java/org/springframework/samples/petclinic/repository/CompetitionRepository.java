
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Calendary;
import org.springframework.samples.petclinic.model.Competition;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.Jornada;
import org.springframework.samples.petclinic.model.Match;

public interface CompetitionRepository {

	Competition findById(int id) throws DataAccessException;

	Collection<Competition> findAllPublishedCompetitions() throws DataAccessException;

	Collection<Competition> findMyCompetitions(final String username) throws DataAccessException;

	void save(Competition competition) throws DataAccessException;

	Collection<FootballClub> findAllPublishedClubs();

	void save(Calendary calendary) throws DataAccessException;

	void save(Match newMatch) throws DataAccessException;

	void save(Jornada j) throws DataAccessException;

	Collection<Jornada> findAllJornadasFromCompetitionId(final Integer compId) throws DataAccessException;

	Collection<Match> findAllMatchByJornadaId(final Integer jornadaId) throws DataAccessException;

	Collection<Match> findAllMatchByCompetitionId(Integer compId) throws DataAccessException;

	Calendary findCalendaryByCompetitionId(int competitionId) throws DataAccessException;

	Jornada findJornadaById(int jornadaId) throws DataAccessException;

}
