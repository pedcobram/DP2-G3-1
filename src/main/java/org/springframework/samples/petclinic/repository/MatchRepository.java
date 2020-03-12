
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Match;

public interface MatchRepository {

	Collection<Match> findAllMatches();

	Collection<Match> findAllMatchRequestsByReferee(String currentPrincipalName);

	Match findMatchById(int id) throws DataAccessException;

	Match findMatchByFootballClubName1(String footballClubName1) throws DataAccessException;

	Match findMatchByFootballClubName2(String footballClubName2) throws DataAccessException;

	void save(Match match) throws DataAccessException;

	void delete(Match match) throws DataAccessException;

}
