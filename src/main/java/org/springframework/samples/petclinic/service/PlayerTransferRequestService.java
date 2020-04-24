
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.PlayerTransferRequest;
import org.springframework.samples.petclinic.repository.PlayerTransferRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerTransferRequestService {

	private PlayerTransferRequestRepository playerTransferRequestRepository;


	@Autowired
	public PlayerTransferRequestService(final PlayerTransferRequestRepository playerTransferRequestRepository) {
		this.playerTransferRequestRepository = playerTransferRequestRepository;
	}

	@Transactional(readOnly = true)
	public Collection<PlayerTransferRequest> findPlayerTransferRequest() throws DataAccessException {
		return this.playerTransferRequestRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Collection<PlayerTransferRequest> findPlayerTransferRequestByPresident(final String userName) throws DataAccessException {
		return this.playerTransferRequestRepository.findByPresident(userName);
	}

	@Transactional(readOnly = true)
	public Collection<PlayerTransferRequest> findPlayerTransferRequestByFootballPlayer(final String footballPlayerName) throws DataAccessException {
		return this.playerTransferRequestRepository.findByFootballPlayer(footballPlayerName);
	}

	@Transactional(readOnly = true)
	public PlayerTransferRequest findPlayerTransferRequestById(final int id) throws DataAccessException {
		return this.playerTransferRequestRepository.findById(id);
	}

	@Transactional
	public void savePlayerTransferRequest(final PlayerTransferRequest playerTransferRequest) throws DataAccessException {
		this.playerTransferRequestRepository.save(playerTransferRequest);
	}

	public void deletePlayerTransferRequest(final PlayerTransferRequest playerTransferRequest) throws DataAccessException {
		playerTransferRequest.setPresident(null);
		playerTransferRequest.setFootballPlayer(null);
		this.playerTransferRequestRepository.delete(playerTransferRequest);
	}

}
