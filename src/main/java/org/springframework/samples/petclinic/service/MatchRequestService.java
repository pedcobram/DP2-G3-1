
package org.springframework.samples.petclinic.service;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.MatchRequest;
import org.springframework.samples.petclinic.repository.MatchRequestRepository;
import org.springframework.samples.petclinic.service.exceptions.IllegalDateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchRequestService {

	private MatchRequestRepository matchRequestRepository;


	@Autowired
	public MatchRequestService(final MatchRequestRepository matchRequestRepository) {
		this.matchRequestRepository = matchRequestRepository;
	}

	@Transactional(readOnly = true)
	public Collection<MatchRequest> findAllMatchRequests() throws DataAccessException {
		return this.matchRequestRepository.findAll();
	}

	@Transactional
	public Collection<MatchRequest> findAllMatchRequestsReceived(final String footballClubName) throws DataAccessException {
		return this.matchRequestRepository.findAllMatchRequestsReceived(footballClubName);
	}

	@Transactional(readOnly = true)
	public Collection<MatchRequest> findAllMatchRequestsSent(final String footballClubName) throws DataAccessException {
		return this.matchRequestRepository.findAllMatchRequestsSent(footballClubName);
	}

	public MatchRequest findMatchRequestByFootballClubName(final String footballClubName) throws DataAccessException {
		return this.matchRequestRepository.findMatchRequestByFootballClubName(footballClubName);
	}

	@Transactional(readOnly = true)
	public MatchRequest findMatchRequestById(final int matchRequestId) {
		return this.matchRequestRepository.findMatchRequestById(matchRequestId);
	}

	@Transactional()
	public void saveMatchRequest(final MatchRequest matchRequest) throws DataAccessException, IllegalDateException {

		Date date = matchRequest.getMatchDate();

		Date now = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_MONTH, 29);
		now = cal.getTime();

		if (date.before(now)) {
			throw new IllegalDateException();
		}

		this.matchRequestRepository.save(matchRequest);
	}

	@Transactional()
	public void deleteMatchRequest(final MatchRequest matchRequest) throws DataAccessException {

		matchRequest.setFootballClub1(null);
		matchRequest.setFootballClub2(null);

		this.matchRequestRepository.delete(matchRequest);
	}

	@Transactional()
	public int count() throws DataAccessException {
		return this.matchRequestRepository.count();
	}

}
