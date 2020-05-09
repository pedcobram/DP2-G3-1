/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Calendary;
import org.springframework.samples.petclinic.model.Competition;
import org.springframework.samples.petclinic.model.FootballPlayerMatchStatistic;
import org.springframework.samples.petclinic.model.Jornada;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.Enum.CompetitionType;
import org.springframework.samples.petclinic.model.Enum.MatchStatus;
import org.springframework.samples.petclinic.repository.CompetitionRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.samples.petclinic.service.exceptions.NotEnoughMoneyException;
import org.springframework.samples.petclinic.service.exceptions.StatusException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class CompetitionService {

	private CompetitionRepository				competitionRepository;

	@Autowired
	private MatchService						matchService;

	@Autowired
	private MatchRecordService					matchRecordService;

	@Autowired
	private FootballPlayerMatchStatisticService	playerMatchStatisticService;

	@Autowired
	private CalendaryService					calendaryService;

	@Autowired
	private JornadaService						jornadaService;


	@Autowired
	public CompetitionService(final CompetitionRepository competitionRepository) {
		this.competitionRepository = competitionRepository;

	}

	@Transactional
	public Competition findCompetitionById(final int id) throws DataAccessException {
		return this.competitionRepository.findById(id);
	}

	@Transactional
	public Collection<Competition> findAllPublishedCompetitions() throws DataAccessException {
		return this.competitionRepository.findAllPublishedCompetitions();
	}

	@Transactional
	public Collection<Match> findAllMatchByJornadaId(final Integer jornadaId) {
		return this.competitionRepository.findAllMatchByJornadaId(jornadaId);
	}

	@Transactional
	public Collection<Competition> findMyCompetitions(final String username) throws DataAccessException {
		return this.competitionRepository.findMyCompetitions(username);
	}

	@Transactional
	public void saveCompetition(final Competition competition) throws DataAccessException, DuplicatedNameException, NotEnoughMoneyException, StatusException {

		String name = competition.getName().toLowerCase();
		Competition otherComp = null;

		//Creamos un "otherFootClub" si existe uno en la db con el mismo nombre y diferente id
		for (Competition o : this.competitionRepository.findAllCompetition()) {
			String compName = o.getName();
			compName = compName.toLowerCase();
			if (compName.equals(name) && o.getId() != competition.getId()) {
				otherComp = o;
			}
		}

		//RN: El nombre no puede ser el mismo
		if (StringUtils.hasLength(competition.getName()) && otherComp != null && otherComp.getId() != competition.getId()) {
			throw new DuplicatedNameException();
		}

		//RN: La recompensa mínima deberá de ser de 5.000.000 €

		if (competition.getReward() < 5000000) {
			throw new NotEnoughMoneyException();
		}

		//RN: A la hora de publicar: Las ligas solo podrán ser de número Par y de 4 equipos en adelante (4, 6, 8, 10, etc...)
		if (competition.getStatus() == true && competition.getType().equals(CompetitionType.LEAGUE)) {
			if (competition.getClubs().size() < 4 || competition.getClubs().size() % 2 != 0) {
				competition.setStatus(false);
				throw new StatusException();
			}
		}

		this.competitionRepository.save(competition);

	}

	public Collection<String> findClubsById(final int competitionId) {

		Collection<String> res = this.competitionRepository.findAllPublishedClubs().stream().map(x -> x.getName()).collect(Collectors.toList());
		res.removeAll(this.competitionRepository.findById(competitionId).getClubs());
		return res;
	}

	@Transactional
	public void deleteCompetition(final Competition thisComp) throws DataAccessException, StatusException {

		//Borrar jornadas, calendario y partidos

		Collection<Match> matches = this.competitionRepository.findAllMatchByCompetitionId(thisComp.getId());

		for (Match a : matches) {

			//RN: Si se ha disputado algun partido de la competición ya no se podrá borrar.
			if (a.getMatchStatus().equals(MatchStatus.FINISHED)) {
				throw new StatusException();
			}

			Integer id = this.matchRecordService.findMatchRecordByMatchId(a.getId()).getId();

			Collection<FootballPlayerMatchStatistic> al = this.playerMatchStatisticService.findFootballPlayerMatchStatisticByMatchRecordId(id);

			for (FootballPlayerMatchStatistic sd : al) {
				this.playerMatchStatisticService.deleteFootballPlayerStatistic(sd);
			}

			this.matchRecordService.deleteMatchRecord(this.matchRecordService.findMatchRecordByMatchId(a.getId()));
			this.matchService.deleteMatch(a);
		}

		Collection<Jornada> jornadas = this.jornadaService.findAllJornadasFromCompetitionId(thisComp.getId());

		for (Jornada a : jornadas) {
			this.jornadaService.deleteJornada(a);
		}

		Calendary c = this.calendaryService.findCalendaryByCompetitionId(thisComp.getId());

		if (c != null) {
			this.calendaryService.deleteCalendary(c);
		}

		this.competitionRepository.delete(thisComp);

	}

}
