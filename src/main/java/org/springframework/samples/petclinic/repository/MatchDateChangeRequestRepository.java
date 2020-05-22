
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.MatchDateChangeRequest;

public interface MatchDateChangeRequestRepository {

	Collection<MatchDateChangeRequest> findAllMatchDateChangeRequest(String presidentUsername) throws DataAccessException;

	MatchDateChangeRequest findById(int id) throws DataAccessException;

	void save(MatchDateChangeRequest matchDateChangeRequest) throws DataAccessException;

	void delete(MatchDateChangeRequest matchDateChangeRequest) throws DataAccessException;

}
