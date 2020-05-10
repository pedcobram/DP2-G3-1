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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Calendary;
import org.springframework.samples.petclinic.model.Competition;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.FootballPlayerMatchStatistic;
import org.springframework.samples.petclinic.model.Jornada;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.MatchRecord;
import org.springframework.samples.petclinic.model.Round;
import org.springframework.samples.petclinic.model.Enum.CompetitionType;
import org.springframework.samples.petclinic.model.Enum.MatchRecordStatus;
import org.springframework.samples.petclinic.model.Enum.MatchStatus;
import org.springframework.samples.petclinic.repository.CompetitionRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.samples.petclinic.service.exceptions.IllegalDateException;
import org.springframework.samples.petclinic.service.exceptions.MatchRecordResultException;
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
	private FootballClubService					footballClubService;

	@Autowired
	private RefereeService						refereeService;
	@Autowired
	private FootballPlayerService				footballPlayerService;
	@Autowired
	private FootballPlayerMatchStatisticService	footballPlayerMatchStatisticService;

	@Autowired
	private RoundService						roundService;


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

		} else if (competition.getStatus() == true && competition.getType().equals(CompetitionType.PLAYOFFS)) {
			if (Integer.bitCount(competition.getClubs().size()) != 1 || competition.getClubs().size() < 4) {
				competition.setStatus(false);
				throw new StatusException();
			}
		}

		this.competitionRepository.save(competition);

	}

	public void createRoundFinish(final MatchRecord mr) throws IllegalDateException, MatchRecordResultException {

		//Comprobamos si es un partido de playoff y que no es una ronda final
		if (!mr.getMatch().getRound().equals(null) && mr.getMatch().getRound().getMatches().size() != 1) {
			Round r = mr.getMatch().getRound();
			Boolean res = false;
			List<Match> lm = new ArrayList<>(r.getMatches());
			List<String> winners = new ArrayList<>();

			//Comprobamos si todos los partidos han terminado
			for (int i = 0; i < lm.size(); i++) {

				if (lm.get(i).getMatchStatus() == MatchStatus.TO_BE_PLAYED) {
					res = false;
				} else if (lm.get(i).getMatchStatus() == MatchStatus.FINISHED) {
					res = true;
					winners.add(i, this.matchRecordService.findMatchRecordByMatchId(lm.get(i).getId()).getWinner());
				}

			}

			//si es asi editamos los equipo de la competicion
			if (res) {
				Competition c = r.getCompetition();
				c.setClubs(winners);
				this.createRounds(c, false);
			}

		}

	}

	public void createRounds(final Competition c, final boolean nw) throws IllegalDateException, MatchRecordResultException {

		List<String> equipos = new ArrayList<String>(c.getClubs());
		List<Match> partidos = new ArrayList<Match>();
		//Creamos la primera ronda
		Round r1 = new Round();
		r1.nameRounds(equipos.size());
		r1.setCompetition(c);
		//guardamos ronda creada
		this.roundService.save(r1);
		//creamos los partidos de la ronda
		int totalE = equipos.size();
		Random random = new Random();
		for (int i = 0; i < totalE; i = i + 2) {
			FootballClub fc1;
			FootballClub fc2;
			if (nw == true) {
				//Obtenemos los dos equipos al azar
				int randomIndex1 = random.nextInt(equipos.size());
				fc1 = this.footballClubService.findFootballClubByName(equipos.get(randomIndex1));
				equipos.remove(randomIndex1);

				int randomIndex2 = random.nextInt(equipos.size());
				fc2 = this.footballClubService.findFootballClubByName(equipos.get(randomIndex2));
				equipos.remove(randomIndex2);
			} else {

				// sino seguimos el orden de los index
				fc1 = this.footballClubService.findFootballClubByName(equipos.get(i));
				fc2 = this.footballClubService.findFootballClubByName(equipos.get(i + 1));

			}
			Match newMatch = new Match();
			newMatch.setCreator(c.getCreator());
			newMatch.setFootballClub1(fc1);
			newMatch.setFootballClub2(fc2);
			newMatch.setMatchDate(new Date(System.currentTimeMillis()));
			newMatch.setMatchStatus(MatchStatus.TO_BE_PLAYED);
			newMatch.setStadium(fc1.getStadium());
			newMatch.setTitle("Partido de " + r1.getName());
			newMatch.setReferee(this.refereeService.findRefereeById(1));
			newMatch.setRound(r1);

			this.matchService.saveMatch(newMatch);

			partidos.add(newMatch);

			MatchRecord newRecord = new MatchRecord();

			newRecord.setMatch(newMatch);
			newRecord.setSeason_start("2020");
			newRecord.setSeason_end("2021");
			newRecord.setTitle("Acta del partido: " + fc1.getName() + " - " + fc2.getName() + " de " + r1.getName() + "de " + c.getName());
			newRecord.setStatus(MatchRecordStatus.NOT_PUBLISHED);

			this.matchRecordService.saveMatchRecord(newRecord);

			// Añadimos los jugadores al acta

			List<FootballPlayer> fps = new ArrayList<>();

			fps.addAll(this.footballPlayerService.findAllClubFootballPlayers(newMatch.getFootballClub1().getId()));
			fps.addAll(this.footballPlayerService.findAllClubFootballPlayers(newMatch.getFootballClub2().getId()));

			for (FootballPlayer fp : fps) {
				FootballPlayerMatchStatistic fpms = new FootballPlayerMatchStatistic();

				fpms.setAssists(0);
				fpms.setGoals(0);
				fpms.setReceived_goals(0);
				fpms.setRed_cards(0);
				fpms.setYellow_cards(0);

				fpms.setMatchRecord(newRecord);
				fpms.setPlayer(fp);

				this.footballPlayerMatchStatisticService.saveFootballPlayerStatistic(fpms);
			}

		}
		//guardamos ronda creada
		r1.setMatches(partidos);
		this.roundService.save(r1);

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
