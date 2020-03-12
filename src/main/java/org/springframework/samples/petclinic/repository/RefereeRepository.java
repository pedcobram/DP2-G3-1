
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.Referee;

public interface RefereeRepository {

	Collection<Referee> findRefereeByLastName(String lastName) throws DataAccessException;

	Collection<Referee> findAllReferees();

	Referee findRefereeById(int id) throws DataAccessException;

	Referee findRefereeByUsername(String userName) throws DataAccessException;

	Authenticated findAuthenticatedByUsername(String userName) throws DataAccessException;

	void save(Referee referee) throws DataAccessException;

	void delete(Referee referee) throws DataAccessException;

}
