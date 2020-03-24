
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.FootballPlayerStatistic;
import org.springframework.samples.petclinic.repository.FootballPlayerStatisticRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FootballPlayerStatisticService {

	private FootballPlayerStatisticRepository footballPlayerStatisticRepository;


	@Autowired
	public FootballPlayerStatisticService(final FootballPlayerStatisticRepository footballPlayerStatisticRepository) {
		this.footballPlayerStatisticRepository = footballPlayerStatisticRepository;
	}

	@Transactional(readOnly = true)
	public Collection<FootballPlayerStatistic> findAllFootballPlayerStatistics() throws DataAccessException {
		return this.footballPlayerStatisticRepository.findAllFootballPlayerStatistics();
	}

	@Transactional(readOnly = true)
	public Collection<FootballPlayerStatistic> findAllFootballPlayerStatisticsBySeason(final String season_start, final String season_end) throws DataAccessException {
		return this.footballPlayerStatisticRepository.findAllFootballPlayerStatisticsBySeason(season_start, season_end);
	}

	@Transactional(readOnly = true)
	public FootballPlayerStatistic findFootballPlayerStatisticById(final int id) throws DataAccessException {
		return this.footballPlayerStatisticRepository.findFootballPlayerStatisticById(id);
	}

	@Transactional(readOnly = true)
	public FootballPlayerStatistic findFootballPlayerStatisticByPlayerId(final int player_id) throws DataAccessException {
		return this.footballPlayerStatisticRepository.findFootballPlayerStatisticByPlayerId(player_id);
	}

	@Transactional
	public void saveFootballPlayerStatistic(final FootballPlayerStatistic footballPlayerStatistic) throws DataAccessException {
		this.footballPlayerStatisticRepository.save(footballPlayerStatistic);
	}

	@Transactional
	public void deleteFootballPlayerStatistic(final FootballPlayerStatistic footballPlayerStatistic) throws DataAccessException {
		this.footballPlayerStatisticRepository.delete(footballPlayerStatistic);
	}

	@Transactional()
	public int count() {
		return this.footballPlayerStatisticRepository.count();
	}
}
