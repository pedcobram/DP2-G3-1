
package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.FootballPlayerMatchStatistic;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.Round;
import org.springframework.samples.petclinic.model.Enum.MatchStatus;
import org.springframework.samples.petclinic.repository.RoundRepository;
import org.springframework.samples.petclinic.service.exceptions.StatusException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoundService {

	private RoundRepository						roundRepository;

	@Autowired
	private MatchRecordService					matchRecordService;

	@Autowired
	private MatchService						matchService;

	@Autowired
	private FootballPlayerMatchStatisticService	playerMatchStatisticService;


	@Autowired
	public RoundService(final RoundRepository roundRepository) {
		this.roundRepository = roundRepository;

	}
	@Transactional(readOnly = true)
	public List<Round> findByCompetitionId(final int id) {
		return this.roundRepository.findByCompetitionId(id);
	}
	@Transactional(readOnly = true)
	public Round findById(final int id) {
		Optional<Round> round = this.roundRepository.findById(id);
		if (round.isPresent()) {
			return round.get();
		} else {
			return null;
		}
	}

	@Transactional()
	public void delete(@Valid final Round r) throws DataAccessException, StatusException {

		List<Match> lm = this.matchService.findMatchByRoundId(r.getId());
		if (lm.size() != 0) {
			for (Match a : lm) {

				//RN: Si se ha disputado algun partido de la competición ya no se podrá borrar.
				if (a.getMatchStatus().equals(MatchStatus.FINISHED)) {
					throw new StatusException();
				}
			}
			//Antes borramos los partidos con sus MatchRecords,y MatchStatistic
			for (Match a : lm) {

				Integer id = this.matchRecordService.findMatchRecordByMatchId(a.getId()).getId();

				Collection<FootballPlayerMatchStatistic> al = this.playerMatchStatisticService.findFootballPlayerMatchStatisticByMatchRecordId(id);

				for (FootballPlayerMatchStatistic sd : al) {
					this.playerMatchStatisticService.deleteFootballPlayerStatistic(sd);
				}

				this.matchRecordService.deleteMatchRecord(this.matchRecordService.findMatchRecordByMatchId(a.getId()));

				this.matchService.deleteMatch(a);
			}
		}
		this.roundRepository.deleteById(r.getId());

	}
	@Transactional()
	public void deleteAll(final int compId) throws DataAccessException, StatusException {

		List<Round> rs = this.roundRepository.findByCompetitionId(compId);
		for (Round a : rs) {
			this.delete(a);
		}

	}

	@Transactional()
	public void save(final Round r1) {
		this.roundRepository.save(r1);

	}

}
