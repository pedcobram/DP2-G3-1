
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.FootballPlayerMatchStatistic;

public interface FootballPlayerMatchStatisticRepository {

	Collection<FootballPlayerMatchStatistic> findAllFootballPlayerMatchStatistics() throws DataAccessException;

	Collection<FootballPlayerMatchStatistic> findAllFootballPlayerMatchStatisticsBySeason(String season_start, String season_end) throws DataAccessException;

	Collection<FootballPlayerMatchStatistic> findFootballPlayerMatchStatisticByMatchRecordId(int match_record_id) throws DataAccessException;

	FootballPlayerMatchStatistic findFootballPlayerMatchStatisticById(int id) throws DataAccessException;

	Collection<FootballPlayerMatchStatistic> findFootballPlayerMatchStatisticByPlayerId(int player_id) throws DataAccessException;

	FootballPlayerMatchStatistic findFootballPlayerMatchStatisticByPlayerIdAndMatchRecordId(int playerId, int matchRecordId) throws DataAccessException;

	void save(FootballPlayerMatchStatistic footballPlayerMatchStatistic);

	void delete(FootballPlayerMatchStatistic footballPlayerMatchStatistic);

}
