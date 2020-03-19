
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.repository.MatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchService {

	private MatchRepository matchRepository;


	@Autowired
	public MatchService(final MatchRepository matchRepository) {
		this.matchRepository = matchRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Match> findAllMatchRequests() {
		return this.matchRepository.findAllMatches();
	}

	@Transactional(readOnly = true)
	public Collection<Match> findAllMatchRequestsByReferee(final String currentPrincipalName) throws DataAccessException {
		return this.matchRepository.findAllMatchRequestsByReferee(currentPrincipalName);
	}

	@Transactional(readOnly = true)
	public Collection<Match> findAllMyMatches(final String currentPrincipalName) throws DataAccessException {
		return this.matchRepository.findAllMyMatches(currentPrincipalName);
	}

	@Transactional(readOnly = true)
	public Match findMatchById(final int id) throws DataAccessException {
		return this.matchRepository.findMatchById(id);
	}

	@Transactional(readOnly = true)
	public Match findMatchByFootballClubName1(final String footballClubName1) throws DataAccessException {
		return this.matchRepository.findMatchByFootballClubName1(footballClubName1);
	}

	@Transactional(readOnly = true)
	public Match findMatchByFootballClubName2(final String footballClubName2) throws DataAccessException {
		return this.matchRepository.findMatchByFootballClubName2(footballClubName2);
	}

	@Transactional()
	public void saveMatch(final Match match) throws DataAccessException {
		this.matchRepository.save(match);
	}

	@Transactional()
	public void deleteMatch(final Match match) throws DataAccessException {
		this.matchRepository.delete(match);
	}

	public int count() throws DataAccessException {
		return this.matchRepository.count();
	}

}
