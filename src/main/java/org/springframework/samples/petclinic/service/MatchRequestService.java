
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.MatchRequest;
import org.springframework.samples.petclinic.repository.MatchRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchRequestService {

	@Autowired
	MatchRequestRepository matchRequestRepository;


	@Autowired
	public MatchRequestService(final MatchRequestRepository matchRequestRepository) {
		this.matchRequestRepository = matchRequestRepository;
	}

	@Transactional(readOnly = true)
	public Collection<MatchRequest> findAllMatchRequests() throws DataAccessException {
		return this.matchRequestRepository.findAll();
	}

	@Transactional
	public Collection<MatchRequest> findAllMatchRequestsReceived(final String footballClubName) throws DataAccessException {
		return this.matchRequestRepository.findAllMatchRequestsReceived(footballClubName);
	}

	@Transactional(readOnly = true)
	public Collection<MatchRequest> findAllMatchRequestsSent(final String footballClubName) throws DataAccessException {
		return this.matchRequestRepository.findAllMatchRequestsSent(footballClubName);
	}

	@Transactional(readOnly = true)
	public MatchRequest findMatchRequestByFootballClubName(final String footballClubName) throws DataAccessException {
		return this.matchRequestRepository.findMatchRequestByFootballClubName(footballClubName);
	}

	@Transactional
	public void saveMatchRequest(final MatchRequest matchRequest) throws DataAccessException {
		this.matchRequestRepository.save(matchRequest);
	}

	@Transactional()
	public void deleteCompetitionAdmin(final MatchRequest matchRequest) throws DataAccessException {
		this.matchRequestRepository.delete(matchRequest);
	}

}
