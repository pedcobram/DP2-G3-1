
package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.samples.petclinic.model.Match;

public interface MatchRepository {

	Collection<Match> findAllMatches();

	Collection<Match> findAllMyMatches(String currentPrincipalName);

	Collection<Match> findAllMatchesByReferee(String currentPrincipalName);

	Match findMatchById(int id);

	void save(Match match);

	void delete(Match match);

	int count();

	List<Match> findMatchByRoundId(int id);

}
