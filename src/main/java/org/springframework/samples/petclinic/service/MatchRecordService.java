
package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.MatchRecord;
import org.springframework.samples.petclinic.repository.MatchRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchRecordService {

	private MatchRecordRepository matchRecordRepository;


	@Autowired
	public MatchRecordService(final MatchRecordRepository matchRecordRepository) {
		this.matchRecordRepository = matchRecordRepository;
	}

	@Transactional(readOnly = true)
	public MatchRecord findMatchRecordById(final int id) throws DataAccessException {
		return this.matchRecordRepository.findMatchRecordById(id);
	}

	@Transactional(readOnly = true)
	public MatchRecord findMatchRecordByMatchId(final int match_id) throws DataAccessException {
		return this.matchRecordRepository.findMatchRecordByMatchId(match_id);
	}

	@Transactional()
	public void saveMatchRecord(final MatchRecord matchRecord) {
		this.matchRecordRepository.save(matchRecord);
	}

	@Transactional()
	public void deleteMatchRecord(final MatchRecord matchRecord) {
		this.matchRecordRepository.delete(matchRecord);
	}

}
