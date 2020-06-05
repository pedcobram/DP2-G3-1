
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.MatchRecord;
import org.springframework.samples.petclinic.repository.MatchRecordRepository;

public interface SpringDataMatchRecordRepository extends MatchRecordRepository, Repository<MatchRecord, Integer> {

	@Override
	@Query("SELECT a FROM MatchRecord a WHERE a.id =:id")
	MatchRecord findMatchRecordById(@Param("id") int id);

	@Override
	@Query("SELECT a FROM MatchRecord a WHERE a.match.id =:matchid")
	MatchRecord findMatchRecordByMatchId(@Param("matchid") int matchid);

	@Override
	@Query("select m from MatchRecord m where m.match.footballClub1.id =:clubid or m.match.footballClub2.id =:clubid order by m.match.matchDate")
	Collection<MatchRecord> findLastMatches(@Param("clubid") int clubid);

}
