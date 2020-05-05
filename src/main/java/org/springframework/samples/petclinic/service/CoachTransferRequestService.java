
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.CoachTransferRequest;
import org.springframework.samples.petclinic.repository.CoachTransferRequestRepository;
import org.springframework.samples.petclinic.service.exceptions.CoachTransferRequestExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoachTransferRequestService {

	private CoachTransferRequestRepository coachTransferRequestRepository;


	@Autowired
	public CoachTransferRequestService(final CoachTransferRequestRepository coachTransferRequestRepository) {
		this.coachTransferRequestRepository = coachTransferRequestRepository;
	}

	//
	@Transactional(readOnly = true)
	public Collection<CoachTransferRequest> findAllCoachTransferRequest() throws DataAccessException {
		return this.coachTransferRequestRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Collection<CoachTransferRequest> findAllReceivedCoachTransferRequestByMyCoachId(final int myCoachId) throws DataAccessException {
		return this.coachTransferRequestRepository.findAllReceivedRequests(myCoachId);
	}

	@Transactional(readOnly = true)
	public Collection<CoachTransferRequest> findAllCoachTransferRequestbyPresident(final String presidentUsername) throws DataAccessException {
		return this.coachTransferRequestRepository.findAllByPresident(presidentUsername);
	}

	@Transactional(readOnly = true)
	public Collection<CoachTransferRequest> findAllCoachTransferRequestByMyCoachId(final int myCoachId) throws DataAccessException {
		return this.coachTransferRequestRepository.findAllByMyCoachId(myCoachId);
	}

	@Transactional(readOnly = true)
	public Collection<CoachTransferRequest> findAllCoachTransferRequestByRequestedCoachId(final int requestedCoachId) throws DataAccessException {
		return this.coachTransferRequestRepository.findAllByRequestedCoachId(requestedCoachId);
	}

	//

	@Transactional(readOnly = true)
	public CoachTransferRequest findCoachTransferRequestById(final int id) throws DataAccessException {
		return this.coachTransferRequestRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public CoachTransferRequest findCoachTransferRequestByRequestedCoachIdAndMyCoachId(final int myCoachId, final int requestedCoachId) throws DataAccessException {
		return this.coachTransferRequestRepository.findByRequestedCoachIdAndMyCoachId(myCoachId, requestedCoachId);
	}

	@Transactional(readOnly = true)
	public CoachTransferRequest findCoachTransferRequestByMyCoachId(final int playerId) throws DataAccessException {
		return this.coachTransferRequestRepository.findByMyCoachId(playerId);
	}

	@Transactional(readOnly = true)
	public CoachTransferRequest findCoachTransferRequestByRequestedCoachId(final int playerId) throws DataAccessException {
		return this.coachTransferRequestRepository.findByRequestedCoachId(playerId);
	}

	@Transactional(readOnly = true)
	public Integer count() throws DataAccessException {
		return this.coachTransferRequestRepository.count();
	}

	@Transactional
	public void saveCoachTransferRequest(final CoachTransferRequest coachTransferRequest) throws DataAccessException, CoachTransferRequestExistsException {

		//RN: Solo una petición por árbitro
		CoachTransferRequest ctr = this.findCoachTransferRequestByRequestedCoachIdAndMyCoachId(coachTransferRequest.getMyCoach().getId(), coachTransferRequest.getRequestedCoach().getId());

		if (ctr != null) {
			throw new CoachTransferRequestExistsException();
		}

		this.coachTransferRequestRepository.save(coachTransferRequest);
	}

	public void deleteCoachTransferRequest(final CoachTransferRequest coachTransferRequest) throws DataAccessException {
		coachTransferRequest.setMyCoach(null);
		coachTransferRequest.setRequestedCoach(null);
		this.coachTransferRequestRepository.delete(coachTransferRequest);
	}

}
