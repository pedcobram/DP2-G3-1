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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Coach;
import org.springframework.samples.petclinic.model.ContractPlayer;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.repository.FootballClubRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class FootballClubService {

	@Autowired
	private FootballClubRepository	footRepository;

	@Autowired
	private FootballPlayerService	footballPlayerService;

	@Autowired
	private ContractService			contractService;

	@Autowired
	private CoachService			coachService;


	@Autowired
	public FootballClubService(final FootballClubRepository footRepository) {
		this.footRepository = footRepository;

	}

	//Buscar todos los equipos

	public Collection<FootballClub> findFootballClubs() throws DataAccessException {
		return this.footRepository.findAllPublished();
	}

	//Buscar equipo por id

	public FootballClub findFootballClubById(final int id) throws DataAccessException {
		return this.footRepository.findById(id);
	}

	//Guardar equipo con validación de nombre duplicado
	@Transactional(rollbackFor = DuplicatedNameException.class)
	public void saveFootballClub(@Valid final FootballClub footballClub) throws DataAccessException, DuplicatedNameException {

		String name = footballClub.getName().toLowerCase();
		FootballClub otherFootClub = null;

		//Creamos un "otherFootClub" si existe uno en la db con el mismo nombre y diferente id
		for (FootballClub o : this.footRepository.findAll()) {
			String compName = o.getName();
			compName = compName.toLowerCase();
			if (compName.equals(name) && o.getId() != footballClub.getId()) {
				otherFootClub = o;
			}
		}

		//Si el campo de nombre tiene contenido y el "otherFootClub" existe y no coincide el id con el actual lanzamos excepción
		if (StringUtils.hasLength(footballClub.getName()) && otherFootClub != null && otherFootClub.getId() != footballClub.getId()) {
			throw new DuplicatedNameException();
		} else {
			this.footRepository.save(footballClub);

		}
	}

	//Buscar presidente por username

	public President findPresidentByUsername(final String currentPrincipalName) throws DataAccessException {
		return this.footRepository.findPresidentByUsername(currentPrincipalName);
	}

	//Buscar presidente por username

	public Coach findCoachByClubId(final int id) throws DataAccessException {
		return this.footRepository.findCoachByClubId(id);
	}

	//Buscar equipo por presidente

	public FootballClub findFootballClubByPresident(final String principalUsername) throws DataAccessException {
		return this.footRepository.findFootballClubByPresident(principalUsername);
	}

	//Buscar equipo por nombre de equipo
	@Transactional(readOnly = true)
	public FootballClub findFootballClubByName(final String FootballClubName) throws DataAccessException {
		return this.footRepository.findFootballClubByName(FootballClubName);
	}

	//Borrar equipo
	public void deleteFootballClub(final FootballClub footballClub) throws DataAccessException {

		if (footballClub != null) {

			Collection<ContractPlayer> contracts = this.contractService.findAllPlayerContractsByClubId(footballClub.getId());
			for (ContractPlayer a : contracts) {
				this.contractService.deleteContract(a);
			}

			Collection<FootballPlayer> players = this.footballPlayerService.findAllClubFootballPlayers(footballClub.getId());
			for (FootballPlayer a : players) {
				a.setClub(null);
			}

			Coach coach = this.coachService.findCoachByClubId(footballClub.getId());
			if (coach != null) {
				coach.setClub(null);
			}

			this.footRepository.delete(footballClub);
		}
	}

}
