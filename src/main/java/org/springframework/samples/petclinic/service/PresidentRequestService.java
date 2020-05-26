
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.PresidentRequest;
import org.springframework.samples.petclinic.repository.PresidentRequestRepository;
import org.springframework.samples.petclinic.service.exceptions.PendingRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PresidentRequestService {

	private PresidentRequestRepository presidentRequestRepository;


	@Autowired
	public PresidentRequestService(final PresidentRequestRepository presidentRequestRepository) {
		this.presidentRequestRepository = presidentRequestRepository;
	}

	@Transactional(readOnly = true)
	public Collection<PresidentRequest> findPresidentRequests() throws DataAccessException {
		return this.presidentRequestRepository.findAll();
	}

	@Transactional(readOnly = true)
	public PresidentRequest findPresidentRequestById(final int id) throws DataAccessException {
		return this.presidentRequestRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public PresidentRequest findPresidentRequestByUsername(final String userName) throws DataAccessException {
		return this.presidentRequestRepository.findByUsername(userName);
	}

	@Transactional
	public void savePresidentRequest(final PresidentRequest presidentRequest) throws DataAccessException {
		this.presidentRequestRepository.save(presidentRequest);
	}

	public void deletePresidentRequest(final PresidentRequest presidentRequest) throws DataAccessException {
		presidentRequest.setUser(null);
		this.presidentRequestRepository.delete(presidentRequest);
	}

	public int countPresidentRequestByUsername(final String username) throws DataAccessException, PendingRequestException {

		//RN: Solo una petición pendiente de ser aceptada o rechazada
		if (this.presidentRequestRepository.countByUsername(username) > 0) {
			throw new PendingRequestException();
		}

		return this.presidentRequestRepository.countByUsername(username);
	}

	public int count() throws DataAccessException {
		return this.presidentRequestRepository.count();
	}

}
