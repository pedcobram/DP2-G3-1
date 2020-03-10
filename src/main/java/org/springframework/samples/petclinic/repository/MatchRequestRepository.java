
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.MatchRequest;

public interface MatchRequestRepository {

	Collection<MatchRequest> findAll() throws DataAccessException;

	Collection<MatchRequest> findAllMatchRequestsSent(String footballClubName) throws DataAccessException;

	MatchRequest findMatchRequestByFootballClubName(String footballClubName) throws DataAccessException;

	void save(MatchRequest matchRequest) throws DataAccessException;

	void delete(MatchRequest matchRequest) throws DataAccessException;

}
