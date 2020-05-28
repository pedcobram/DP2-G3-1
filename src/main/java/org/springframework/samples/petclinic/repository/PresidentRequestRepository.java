
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.PresidentRequest;

public interface PresidentRequestRepository {

	Collection<PresidentRequest> findAll() throws DataAccessException;

	PresidentRequest findById(int id) throws DataAccessException;

	PresidentRequest findByUsername(String userName) throws DataAccessException;

	void save(PresidentRequest compAdminRequest) throws DataAccessException;

	void delete(PresidentRequest compAdminRequest) throws DataAccessException;

	int countByUsername(String username) throws DataAccessException;

	int count() throws DataAccessException;
}
