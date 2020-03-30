
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.FootballPlayerMatchStatistic;
import org.springframework.samples.petclinic.repository.FootballPlayerMatchStatisticRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FootballPlayerMatchStatisticService {

	private FootballPlayerMatchStatisticRepository footballPlayerMatchStatisticRepository;


	@Autowired
	public FootballPlayerMatchStatisticService(final FootballPlayerMatchStatisticRepository footballPlayerMatchStatisticRepository) {
		this.footballPlayerMatchStatisticRepository = footballPlayerMatchStatisticRepository;
	}

	@Transactional(readOnly = true)
	public Collection<FootballPlayerMatchStatistic> findAllFootballPlayerMatchStatistics() throws DataAccessException {
		return this.footballPlayerMatchStatisticRepository.findAllFootballPlayerMatchStatistics();
	}

	@Transactional(readOnly = true)
	public Collection<FootballPlayerMatchStatistic> findAllFootballPlayerMatchStatisticsBySeason(final String season_start, final String season_end) throws DataAccessException {
		return this.footballPlayerMatchStatisticRepository.findAllFootballPlayerMatchStatisticsBySeason(season_start, season_end);
	}

	@Transactional(readOnly = true)
	public Collection<FootballPlayerMatchStatistic> findFootballPlayerMatchStatisticByMatchRecordId(final int match_record_id) throws DataAccessException {
		return this.footballPlayerMatchStatisticRepository.findFootballPlayerMatchStatisticByMatchRecordId(match_record_id);
	}

	@Transactional(readOnly = true)
	public FootballPlayerMatchStatistic findFootballPlayerMatchStatisticById(final int id) throws DataAccessException {
		return this.footballPlayerMatchStatisticRepository.findFootballPlayerMatchStatisticById(id);
	}

	@Transactional(readOnly = true)
	public FootballPlayerMatchStatistic findFootballPlayerMatchStatisticByPlayerId(final int player_id) throws DataAccessException {
		return this.footballPlayerMatchStatisticRepository.findFootballPlayerMatchStatisticByPlayerId(player_id);
	}

	@Transactional(readOnly = true)
	public FootballPlayerMatchStatistic findFootballPlayerMatchStatisticByPlayerIdAndMatchRecordId(final int playerId, final int matchRecordId) {
		return this.footballPlayerMatchStatisticRepository.findFootballPlayerMatchStatisticByPlayerIdAndMatchRecordId(playerId, matchRecordId);
	}

	public void saveFootballPlayerStatistic(final FootballPlayerMatchStatistic footballPlayerMatchStatistic) throws DataAccessException {
		this.footballPlayerMatchStatisticRepository.save(footballPlayerMatchStatistic);
	}

	public void deleteFootballPlayerStatistic(final FootballPlayerMatchStatistic footballPlayerMatchStatistic) throws DataAccessException {
		this.footballPlayerMatchStatisticRepository.delete(footballPlayerMatchStatistic);
	}
}
