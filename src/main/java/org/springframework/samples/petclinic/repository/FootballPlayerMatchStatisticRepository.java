
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.samples.petclinic.model.FootballPlayerMatchStatistic;

public interface FootballPlayerMatchStatisticRepository {

	Collection<FootballPlayerMatchStatistic> findAllFootballPlayerMatchStatistics();

	Collection<FootballPlayerMatchStatistic> findAllFootballPlayerMatchStatisticsBySeason(String season_start, String season_end);

	Collection<FootballPlayerMatchStatistic> findFootballPlayerMatchStatisticByMatchRecordId(int match_record_id);

	FootballPlayerMatchStatistic findFootballPlayerMatchStatisticById(int id);

	Collection<FootballPlayerMatchStatistic> findFootballPlayerMatchStatisticByPlayerId(int player_id);

	FootballPlayerMatchStatistic findFootballPlayerMatchStatisticByPlayerIdAndMatchRecordId(int playerId, int matchRecordId);

	void save(FootballPlayerMatchStatistic footballPlayerMatchStatistic);

	void delete(FootballPlayerMatchStatistic footballPlayerMatchStatistic);

}
