
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.samples.petclinic.model.PlayerTransferRequest;

public interface PlayerTransferRequestRepository {

	Collection<PlayerTransferRequest> findAll();

	Collection<PlayerTransferRequest> findByPresident(String userName);

	Collection<PlayerTransferRequest> findReceivedRequests(int clubId);

	PlayerTransferRequest findById(int id);

	PlayerTransferRequest findByPlayerId(int playerId);

	PlayerTransferRequest findOnlyByPlayerId(int playerId);

	PlayerTransferRequest findByPlayerIdAndStatusAccepted(int playerId);

	Integer countByPresidentAndPlayer(String presidentUsername, int playerId);

	Integer count();

	void save(PlayerTransferRequest playerTransferRequest);

	void delete(PlayerTransferRequest playerTransferRequest);
}
