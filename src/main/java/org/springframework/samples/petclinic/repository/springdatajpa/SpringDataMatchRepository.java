
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.repository.MatchRepository;

public interface SpringDataMatchRepository extends MatchRepository, Repository<Match, Integer> {

	@Override
	@Query("SELECT a FROM Match a")
	Collection<Match> findAllMatches();

	@Override
	@Query("SELECT a FROM Match a WHERE a.referee.user.username =:currentPrincipalName")
	Collection<Match> findAllMatchRequestsByReferee(@Param("currentPrincipalName") String currentPrincipalName);

	@Override
	@Query("SELECT a FROM Match a WHERE a.id =:id")
	Match findMatchById(@Param("id") int id) throws DataAccessException;

	@Override
	@Query("SELECT a FROM Match a WHERE a.footballClub1.name =:footballClubName1")
	Match findMatchByFootballClubName1(@Param("footballClubName1") String footballClubName1) throws DataAccessException;

	@Override
	@Query("SELECT a FROM Match a WHERE a.footballClub1.name =:footballClubName2")
	Match findMatchByFootballClubName2(@Param("footballClubName2") String footballClubName2) throws DataAccessException;

	@Override
	@Query("select m from Match m where m.footballClub1.president.user.username = ?1 or m.footballClub2.president.user.username = ?1")
	Collection<Match> findAllMyMatches(String currentPrincipalName);

	@Override
	@Query("SELECT COUNT(a) FROM Match a")
	int count() throws DataAccessException;

}
