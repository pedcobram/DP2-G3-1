
package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Competition;

public interface CompetitionRepository {

	Competition findById(int id) throws DataAccessException;

	void save(Competition competition) throws DataAccessException;

}
