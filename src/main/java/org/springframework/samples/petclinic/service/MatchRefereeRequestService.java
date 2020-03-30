
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.MatchRefereeRequest;
import org.springframework.samples.petclinic.repository.MatchRefereeRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchRefereeRequestService {

	private MatchRefereeRequestRepository matchRefereeRequestRepository;


	@Autowired
	public MatchRefereeRequestService(final MatchRefereeRequestRepository matchRefereeRequestRepository) {
		this.matchRefereeRequestRepository = matchRefereeRequestRepository;
	}

	public Collection<MatchRefereeRequest> findOnHoldMatchRefereeRequests(final String refereeName) throws DataAccessException {
		return this.matchRefereeRequestRepository.findOnHoldMatchRefereeRequests(refereeName);
	}

	public Collection<MatchRefereeRequest> findAllOnHoldMatchRefereeRequests() throws DataAccessException {
		return this.matchRefereeRequestRepository.findAllOnHoldMatchRefereeRequests();
	}

	@Transactional(readOnly = true)
	public Collection<MatchRefereeRequest> findAllMatchRefereeRequests() throws DataAccessException {
		return this.matchRefereeRequestRepository.findAllMatchRefereeRequests();
	}

	@Transactional(readOnly = true)
	public MatchRefereeRequest findMatchRefereeRequestById(final int id) throws DataAccessException {
		return this.matchRefereeRequestRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public MatchRefereeRequest findMatchRefereeRequestByUsernameAndMatchId(final String userName, final int matchId) throws DataAccessException {
		return this.matchRefereeRequestRepository.findByUsernameAndMatchId(userName, matchId);
	}

	@Transactional
	public void saveMatchRefereeRequest(final MatchRefereeRequest matchRefereeRequest) throws DataAccessException {
		this.matchRefereeRequestRepository.save(matchRefereeRequest);
	}

	@Transactional
	public void deleteMatchRefereeRequest(final MatchRefereeRequest matchRefereeRequest) throws DataAccessException {
		this.matchRefereeRequestRepository.delete(matchRefereeRequest);
	}

	@Transactional(readOnly = true)
	public int count() {
		return this.matchRefereeRequestRepository.count();
	}

}
