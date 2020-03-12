
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.MatchRefereeRequest;
import org.springframework.samples.petclinic.repository.MatchRefereeRequestRepository;

public interface SpringDataMatchRefereeRequestRepository extends MatchRefereeRequestRepository, Repository<MatchRefereeRequest, Integer> {

	@Override
	@Query("SELECT a FROM MatchRefereeRequest a WHERE a.status = 0")
	Collection<MatchRefereeRequest> findAllOnHoldMatchRefereeRequests() throws DataAccessException;

	@Override
	@Query("SELECT a FROM MatchRefereeRequest a WHERE a.referee.user.username =:refereeName AND a.status = 0")
	Collection<MatchRefereeRequest> findOnHoldMatchRefereeRequests(@Param("refereeName") String refereeName) throws DataAccessException;

	@Override
	@Query("SELECT a FROM MatchRefereeRequest a WHERE a.id =:id")
	MatchRefereeRequest findById(@Param("id") int id) throws DataAccessException;

	@Override
	@Query("SELECT a FROM MatchRefereeRequest a WHERE a.referee.user.username =:username AND a.match.id =:matchId")
	MatchRefereeRequest findByUsernameAndMatchId(@Param("username") String username, @Param("matchId") int matchId) throws DataAccessException;

	@Override
	@Query("SELECT a FROM Authenticated a WHERE a.user.username =:username")
	Authenticated findAuthenticatedByUsername(@Param("username") String username) throws DataAccessException;

	@Override
	@Query("SELECT COUNT(a) FROM MatchRefereeRequest a")
	int count() throws DataAccessException;

}
