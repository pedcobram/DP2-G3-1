
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.FootballPlayerMatchStatistic;
import org.springframework.samples.petclinic.repository.FootballPlayerMatchStatisticRepository;

public interface SpringDataFootballPlayerMatchStatisticsRepository extends FootballPlayerMatchStatisticRepository, Repository<FootballPlayerMatchStatistic, Integer> {

	@Override
	@Query("SELECT a FROM FootballPlayerMatchStatistic a")
	Collection<FootballPlayerMatchStatistic> findAllFootballPlayerMatchStatistics() throws DataAccessException;

	@Override
	@Query("SELECT a FROM FootballPlayerMatchStatistic a WHERE a.season_start =:season_start AND a.season_end =:season_end")
	Collection<FootballPlayerMatchStatistic> findAllFootballPlayerMatchStatisticsBySeason(@Param("season_start") String season_start, @Param("season_end") String season_end) throws DataAccessException;

	@Override
	@Query("SELECT a FROM FootballPlayerMatchStatistic a WHERE a.matchRecord.id =:match_record_id")
	Collection<FootballPlayerMatchStatistic> findFootballPlayerMatchStatisticByMatchRecordId(@Param("match_record_id") int match_record_id) throws DataAccessException;

	@Override
	@Query("SELECT a FROM FootballPlayerMatchStatistic a WHERE a.id =:id")
	FootballPlayerMatchStatistic findFootballPlayerMatchStatisticById(@Param("id") int id) throws DataAccessException;

	@Override
	@Query("SELECT a FROM FootballPlayerMatchStatistic a WHERE a.player.id =:player_id")
	Collection<FootballPlayerMatchStatistic> findFootballPlayerMatchStatisticByPlayerId(@Param("player_id") int player_id) throws DataAccessException;

	@Override
	@Query("SELECT a FROM FootballPlayerMatchStatistic a WHERE a.player.id =:playerId AND a.matchRecord.id =:matchRecordId")
	FootballPlayerMatchStatistic findFootballPlayerMatchStatisticByPlayerIdAndMatchRecordId(@Param("playerId") int playerId, @Param("matchRecordId") int matchRecordId) throws DataAccessException;

}
