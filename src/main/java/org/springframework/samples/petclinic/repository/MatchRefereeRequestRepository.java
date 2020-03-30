
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.MatchRefereeRequest;

public interface MatchRefereeRequestRepository {

	Collection<MatchRefereeRequest> findAllMatchRefereeRequests() throws DataAccessException;

	Collection<MatchRefereeRequest> findAllOnHoldMatchRefereeRequests() throws DataAccessException;

	Collection<MatchRefereeRequest> findOnHoldMatchRefereeRequests(String refereeName) throws DataAccessException;

	MatchRefereeRequest findById(int id) throws DataAccessException;

	MatchRefereeRequest findByUsernameAndMatchId(String userName, int matchId) throws DataAccessException;

	void save(MatchRefereeRequest matchRefereeRequest) throws DataAccessException;

	Authenticated findAuthenticatedByUsername(String username) throws DataAccessException;

	void delete(MatchRefereeRequest matchRefereeRequest) throws DataAccessException;

	int count() throws DataAccessException;
}
