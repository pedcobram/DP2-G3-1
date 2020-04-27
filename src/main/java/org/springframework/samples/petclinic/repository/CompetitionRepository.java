
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Competition;

public interface CompetitionRepository {

	Competition findById(int id) throws DataAccessException;

	Collection<Competition> findAllPublishedCompetitions() throws DataAccessException;

	Collection<Competition> findMyCompetitions(final String username) throws DataAccessException;

	void save(Competition competition) throws DataAccessException;

}
