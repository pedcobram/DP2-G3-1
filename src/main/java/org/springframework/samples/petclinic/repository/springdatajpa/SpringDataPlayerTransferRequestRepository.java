
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.PlayerTransferRequest;
import org.springframework.samples.petclinic.repository.PlayerTransferRequestRepository;

public interface SpringDataPlayerTransferRequestRepository extends PlayerTransferRequestRepository, Repository<PlayerTransferRequest, Integer> {

	@Override
	@Query("SELECT a FROM PlayerTransferRequest a WHERE a.club.president.user.username =:username AND a.status = 0")
	Collection<PlayerTransferRequest> findByPresident(@Param("username") String username) throws DataAccessException;

	@Override
	@Query("SELECT f from PlayerTransferRequest f WHERE f.status = 0")
	Collection<PlayerTransferRequest> findAll() throws DataAccessException;

	@Override
	@Query("SELECT f from PlayerTransferRequest f WHERE f.footballPlayer.club.id =:clubId AND f.status = 0")
	Collection<PlayerTransferRequest> findReceivedRequests(int clubId) throws DataAccessException;

	@Override
	@Query("SELECT a FROM PlayerTransferRequest a WHERE a.id =:id")
	PlayerTransferRequest findById(@Param("id") int id) throws DataAccessException;

	@Override
	@Query("SELECT a FROM PlayerTransferRequest a WHERE a.footballPlayer.id =:playerId AND a.status = 0")
	PlayerTransferRequest findByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

	@Override
	@Query("SELECT a FROM PlayerTransferRequest a WHERE a.footballPlayer.id =:playerId")
	PlayerTransferRequest findOnlyByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

	@Override
	@Query("SELECT a FROM PlayerTransferRequest a WHERE a.footballPlayer.id =:playerId AND a.status = 1")
	PlayerTransferRequest findByPlayerIdAndStatusAccepted(@Param("playerId") int playerId) throws DataAccessException;

	@Override
	@Query("SELECT COUNT(a) FROM PlayerTransferRequest a WHERE a.club.president.user.username  =:presidentUsername AND a.footballPlayer.id =:playerId AND a.status = 0")
	Integer countByPresidentAndPlayer(@Param("presidentUsername") String presidentUsername, @Param("playerId") int playerId) throws DataAccessException;

	@Override
	@Query("SELECT COUNT(a) FROM PlayerTransferRequest a")
	Integer count() throws DataAccessException;
}
