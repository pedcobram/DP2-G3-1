
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.MatchRecord;

public interface MatchRecordRepository {

	MatchRecord findMatchRecordById(int id) throws DataAccessException;

	MatchRecord findMatchRecordByMatchId(int match_id) throws DataAccessException;

	void save(MatchRecord matchRecord);

	void delete(MatchRecord matchRecord);

	Collection<MatchRecord> findLastMatches(int club_id);

}
