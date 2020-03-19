
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.CompAdminRequest;
import org.springframework.samples.petclinic.repository.CompAdminRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompAdminRequestService {

	private CompAdminRequestRepository compAdminRequestRepository;


	@Autowired
	public CompAdminRequestService(final CompAdminRequestRepository compAdminRequestRepository) {
		this.compAdminRequestRepository = compAdminRequestRepository;
	}

	@Transactional(readOnly = true)
	public Collection<CompAdminRequest> findCompAdminRequests() throws DataAccessException {
		return this.compAdminRequestRepository.findAll();
	}

	@Transactional(readOnly = true)
	public CompAdminRequest findCompAdminRequestById(final int id) throws DataAccessException {
		return this.compAdminRequestRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public CompAdminRequest findCompAdminRequestByUsername(final String userName) throws DataAccessException {
		return this.compAdminRequestRepository.findByUsername(userName);
	}

	@Transactional
	public void saveCompAdminRequest(final CompAdminRequest compAdminRequest) throws DataAccessException {
		this.compAdminRequestRepository.save(compAdminRequest);
	}

	public void deleteCompAdminRequest(final CompAdminRequest compAdminRequest) throws DataAccessException {
		compAdminRequest.setUser(null);
		this.compAdminRequestRepository.delete(compAdminRequest);
	}

	public int countCompAdminRequestByUsername(final String username) throws DataAccessException {
		return this.compAdminRequestRepository.countByUsername(username);
	}

	public int count() throws DataAccessException {
		return this.compAdminRequestRepository.count();
	}

}
