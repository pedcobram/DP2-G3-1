
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.FootballPlayerStatistic;
import org.springframework.samples.petclinic.repository.FootballPlayerStatisticRepository;

public interface SpringDataFootballPlayerStatisticsRepository extends FootballPlayerStatisticRepository, Repository<FootballPlayerStatistic, Integer> {

	@Override
	@Query("SELECT a FROM FootballPlayerStatistic a")
	Collection<FootballPlayerStatistic> findAllFootballPlayerStatistics() throws DataAccessException;

	@Override
	@Query("SELECT a FROM FootballPlayerStatistic a WHERE a.season_start =:season_start AND a.season_end =:season_end")
	Collection<FootballPlayerStatistic> findAllFootballPlayerStatisticsBySeason(@Param("season_start") String season_start, @Param("season_end") String season_end) throws DataAccessException;

	@Override
	@Query("SELECT a FROM FootballPlayerStatistic a WHERE a.id =:id")
	FootballPlayerStatistic findFootballPlayerStatisticById(@Param("id") int id) throws DataAccessException;

	@Override
	@Query("SELECT a FROM FootballPlayerStatistic a WHERE a.player.id =:player_id")
	FootballPlayerStatistic findFootballPlayerStatisticByPlayerId(@Param("player_id") int player_id) throws DataAccessException;

}
