
package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.MatchRecord;
import org.springframework.samples.petclinic.model.Enum.MatchRecordStatus;
import org.springframework.samples.petclinic.repository.MatchRecordRepository;
import org.springframework.samples.petclinic.service.exceptions.IllegalDateException;
import org.springframework.samples.petclinic.service.exceptions.MatchRecordResultException;
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
	public void saveMatchRecord(final MatchRecord matchRecord) throws IllegalDateException, MatchRecordResultException {

		// RN: Fecha de inicio de temporada menor que fecha de finalizacion
		if (matchRecord.getSeason_start() != null 
			&& matchRecord.getSeason_end() != null 
			&& !matchRecord.getSeason_start().isEmpty() 
			&& !matchRecord.getSeason_end().isEmpty()
			&& Integer.parseInt(matchRecord.getSeason_end()) <= Integer.parseInt(matchRecord.getSeason_start())) {
			throw new IllegalDateException();
		}

		// RN: Si el estado es publicado, el resultado no puede estar vacio
		if (matchRecord.getStatus() == MatchRecordStatus.PUBLISHED && (matchRecord.getResult().isEmpty() || matchRecord.getResult() == null)) {
			throw new MatchRecordResultException();
		}

		this.matchRecordRepository.save(matchRecord);
	}

	@Transactional()
	public void deleteMatchRecord(final MatchRecord matchRecord) {
		this.matchRecordRepository.delete(matchRecord);
	}

}
