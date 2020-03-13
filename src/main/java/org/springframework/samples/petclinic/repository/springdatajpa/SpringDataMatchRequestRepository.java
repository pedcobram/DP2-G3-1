
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.MatchRequest;
import org.springframework.samples.petclinic.repository.MatchRequestRepository;

public interface SpringDataMatchRequestRepository extends MatchRequestRepository, Repository<MatchRequest, Integer> {

	@Override
	@Query("SELECT f FROM MatchRequest f")
	Collection<MatchRequest> findAll() throws DataAccessException;

	@Override
	@Query("SELECT a FROM MatchRequest a WHERE a.footballClub2.name =:footballClub")
	Collection<MatchRequest> findAllMatchRequestsReceived(@Param("footballClub") String footballClubName) throws DataAccessException;

	@Override
	@Query("SELECT f FROM MatchRequest f WHERE f.footballClub1.name =:footballClub")
	Collection<MatchRequest> findAllMatchRequestsSent(@Param("footballClub") String footballClub) throws DataAccessException;

	@Override
	@Query("SELECT a FROM MatchRequest a WHERE a.footballClub1.name =:footballClub OR a.footballClub2.name =:footballClub")
	MatchRequest findMatchRequestByFootballClubName(@Param("footballClub") String footballClub) throws DataAccessException;

	@Override
	@Query("SELECT a FROM MatchRequest a WHERE a.id =:matchRequestId")
	MatchRequest findMatchRequestById(int matchRequestId) throws DataAccessException;

	@Override
	@Query("SELECT COUNT(a) FROM MatchRequest a")
	int count() throws DataAccessException;
}
