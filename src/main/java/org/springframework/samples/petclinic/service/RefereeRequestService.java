
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.RefereeRequest;
import org.springframework.samples.petclinic.repository.RefereeRequestRepository;
import org.springframework.samples.petclinic.service.exceptions.PendingRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RefereeRequestService {

	private RefereeRequestRepository refereeRequestRepository;


	@Autowired
	public RefereeRequestService(final RefereeRequestRepository refereeRequestRepository) {
		this.refereeRequestRepository = refereeRequestRepository;
	}

	@Transactional(readOnly = true)
	public Collection<RefereeRequest> findRefereeRequests() throws DataAccessException {
		return this.refereeRequestRepository.findAll();
	}

	@Transactional(readOnly = true)
	public RefereeRequest findRefereeRequestById(final int id) throws DataAccessException {
		return this.refereeRequestRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public RefereeRequest findRefereeRequestByUsername(final String userName) throws DataAccessException {
		return this.refereeRequestRepository.findByUsername(userName);
	}

	@Transactional
	public void saveRefereeRequest(final RefereeRequest refereeRequest) throws DataAccessException {
		this.refereeRequestRepository.save(refereeRequest);
	}

	public void deleteRefereeRequest(final RefereeRequest refereeRequest) throws DataAccessException {
		refereeRequest.setUser(null);
		this.refereeRequestRepository.delete(refereeRequest);
	}

	public int countRefereeRequestByUsername(final String username) throws DataAccessException, PendingRequestException {

		//RN: Solo una petición pendiente de ser aceptada o rechazada
		if (this.refereeRequestRepository.countByUsername(username) > 0) {
			throw new PendingRequestException();
		}

		return this.refereeRequestRepository.countByUsername(username);
	}

	public int count() throws DataAccessException {
		return this.refereeRequestRepository.count();
	}

}
