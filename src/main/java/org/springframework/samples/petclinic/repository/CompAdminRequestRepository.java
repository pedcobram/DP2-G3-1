
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.CompAdminRequest;

public interface CompAdminRequestRepository {

	Collection<CompAdminRequest> findAll() throws DataAccessException;

	CompAdminRequest findById(int id) throws DataAccessException;

	CompAdminRequest findByUsername(String userName) throws DataAccessException;

	void save(CompAdminRequest compAdminRequest) throws DataAccessException;

	void delete(CompAdminRequest compAdminRequest) throws DataAccessException;

	int countByUsername(String username) throws DataAccessException;

}
