
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.PlayerTransferRequest;

public interface PlayerTransferRequestRepository {

	Collection<PlayerTransferRequest> findAll() throws DataAccessException;

	Collection<PlayerTransferRequest> findByPresident(String userName) throws DataAccessException;

	Collection<PlayerTransferRequest> findReceivedRequests(int clubId) throws DataAccessException;

	PlayerTransferRequest findById(int id) throws DataAccessException;

	PlayerTransferRequest findByPlayerId(int playerId) throws DataAccessException;

	PlayerTransferRequest findOnlyByPlayerId(int playerId) throws DataAccessException;

	PlayerTransferRequest findByPlayerIdAndStatusAccepted(int playerId) throws DataAccessException;

	Integer countByPresidentAndPlayer(String presidentUsername, int playerId) throws DataAccessException;

	Integer count() throws DataAccessException;

	void save(PlayerTransferRequest playerTransferRequest) throws DataAccessException;

	void delete(PlayerTransferRequest playerTransferRequest) throws DataAccessException;
}
