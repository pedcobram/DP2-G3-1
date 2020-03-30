
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.FootballPlayerStatistic;

public interface FootballPlayerStatisticRepository {

	Collection<FootballPlayerStatistic> findAllFootballPlayerStatistics() throws DataAccessException;

	Collection<FootballPlayerStatistic> findAllFootballPlayerStatisticsBySeason(String season_start, String season_end) throws DataAccessException;

	FootballPlayerStatistic findFootballPlayerStatisticById(int id) throws DataAccessException;

	FootballPlayerStatistic findFootballPlayerStatisticByPlayerId(int player_id) throws DataAccessException;

	int count() throws DataAccessException;

	void save(FootballPlayerStatistic footballPlayerStatistics) throws DataAccessException;

	void delete(FootballPlayerStatistic footballPlayerStatistics) throws DataAccessException;

}
