
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.samples.petclinic.model.FootballPlayerStatistic;

public interface FootballPlayerStatisticRepository {

	Collection<FootballPlayerStatistic> findAllFootballPlayerStatistics();

	Collection<FootballPlayerStatistic> findAllFootballPlayerStatisticsBySeason(String season_start, String season_end);

	FootballPlayerStatistic findFootballPlayerStatisticById(int id);

	FootballPlayerStatistic findFootballPlayerStatisticByPlayerId(int player_id);

	int count();

	void save(FootballPlayerStatistic footballPlayerStatistics);

	void delete(FootballPlayerStatistic footballPlayerStatistics);

}
