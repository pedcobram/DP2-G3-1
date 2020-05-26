
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.RefereeRequest;

public interface RefereeRequestRepository {

	Collection<RefereeRequest> findAll() throws DataAccessException;

	RefereeRequest findById(int id) throws DataAccessException;

	RefereeRequest findByUsername(String userName) throws DataAccessException;

	void save(RefereeRequest compAdminRequest) throws DataAccessException;

	void delete(RefereeRequest compAdminRequest) throws DataAccessException;

	int countByUsername(String username) throws DataAccessException;

	int count() throws DataAccessException;
}
