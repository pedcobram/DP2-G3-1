
package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Match;

public interface MatchRepository {

	Collection<Match> findAllMatches();

	Collection<Match> findAllMyMatches(String currentPrincipalName);

	Collection<Match> findAllMatchesByReferee(String currentPrincipalName);

	Match findMatchById(int id) throws DataAccessException;

	void save(Match match) throws DataAccessException;

	void delete(Match match) throws DataAccessException;

	int count() throws DataAccessException;

	List<Match> findMatchByRoundId(int id);

}
