
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.MatchRefereeRequest;

public interface MatchRefereeRequestRepository {

	Collection<MatchRefereeRequest> findAllMatchRefereeRequests();

	Collection<MatchRefereeRequest> findAllOnHoldMatchRefereeRequests();

	Collection<MatchRefereeRequest> findOnHoldMatchRefereeRequests(String refereeName);

	MatchRefereeRequest findById(int id);

	MatchRefereeRequest findByUsernameAndMatchId(String userName, int matchId);

	void save(MatchRefereeRequest matchRefereeRequest);

	Authenticated findAuthenticatedByUsername(String username);

	void delete(MatchRefereeRequest matchRefereeRequest);

	int count();
}
