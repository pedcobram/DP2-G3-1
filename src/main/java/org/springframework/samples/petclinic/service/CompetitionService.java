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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Competition;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.repository.CompetitionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompetitionService {

	private CompetitionRepository competitionRepository;


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
	public Collection<Competition> findMyCompetitions(final String username) throws DataAccessException {
		return this.competitionRepository.findMyCompetitions(username);
	}

	@Transactional
	public Collection<FootballClub> findAllPublishedClubs() throws DataAccessException {
		return this.competitionRepository.findAllPublishedClubs();
	}

	@Transactional
	public void saveCompetition(final Competition competition) throws DataAccessException {
		this.competitionRepository.save(competition);

	}

	public Collection<FootballClub> findClubsById(final int competitionId) {

		Collection<FootballClub> res = this.competitionRepository.findAllPublishedClubs();
		res.removeAll(this.competitionRepository.findClubsById(competitionId));
		return res;
	}

}
