
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.CompetitionAdmin;

public interface CompetitionAdminRepository {

	Collection<CompetitionAdmin> findByLastName(String lastName) throws DataAccessException;

	CompetitionAdmin findById(int id) throws DataAccessException;

	CompetitionAdmin findByUsername(String userName) throws DataAccessException;

	void save(CompetitionAdmin competitionAdmin) throws DataAccessException;

	void delete(CompetitionAdmin competitionAdmin) throws DataAccessException;

}
