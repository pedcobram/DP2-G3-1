
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.PlayerTransferRequest;

public interface PlayerTransferRequestRepository {

	Collection<PlayerTransferRequest> findAll() throws DataAccessException;

	Collection<PlayerTransferRequest> findByPresident(String userName) throws DataAccessException;

	Collection<PlayerTransferRequest> findByFootballPlayer(String footballPlayer) throws DataAccessException;

	PlayerTransferRequest findById(int id) throws DataAccessException;

	void save(PlayerTransferRequest playerTransferRequest) throws DataAccessException;

	void delete(PlayerTransferRequest playerTransferRequest) throws DataAccessException;
}
