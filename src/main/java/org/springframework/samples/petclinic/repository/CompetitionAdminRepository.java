
package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.CompetitionAdmin;

public interface CompetitionAdminRepository {

	CompetitionAdmin findById(int id) throws DataAccessException;

	CompetitionAdmin findByUsername(String userName) throws DataAccessException;

	void save(CompetitionAdmin authenticated) throws DataAccessException;

	Authenticated findAuthenticatedByUsername(String username) throws DataAccessException;

	void delete(CompetitionAdmin competitionAdmin) throws DataAccessException;

	int count() throws DataAccessException;

}
