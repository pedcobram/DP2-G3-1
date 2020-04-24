
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
	@Query("SELECT a FROM PlayerTransferRequest a WHERE a.id =:id")
	PlayerTransferRequest findById(@Param("id") int id) throws DataAccessException;

	@Override
	@Query("SELECT a FROM PlayerTransferRequest a WHERE a.president.user.username =:username AND a.status = 0")
	Collection<PlayerTransferRequest> findByPresident(@Param("username") String username) throws DataAccessException;

	@Override
	@Query("SELECT a FROM PlayerTransferRequest a WHERE a.footballPlayer.firstName =:footballPlayer AND a.status = 0")
	Collection<PlayerTransferRequest> findByFootballPlayer(@Param("footballPlayer") String footballPlayer) throws DataAccessException;

	@Override
	@Query("SELECT f from PlayerTransferRequest f WHERE f.status = 0")
	Collection<PlayerTransferRequest> findAll() throws DataAccessException;
}
