
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.samples.petclinic.model.MatchRequest;

public interface MatchRequestRepository {

	Collection<MatchRequest> findAll();

	Collection<MatchRequest> findAllMatchRequestsReceived(String footballClubName);

	Collection<MatchRequest> findAllMatchRequestsSent(String footballClubName);

	MatchRequest findMatchRequestByFootballClubName(String footballClubName);

	MatchRequest findMatchRequestById(int matchRequestId);

	void save(MatchRequest matchRequest);

	void delete(MatchRequest matchRequest);

	int count();

}
