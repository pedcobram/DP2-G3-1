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
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Coach;
import org.springframework.samples.petclinic.model.ContractCommercial;
import org.springframework.samples.petclinic.model.ContractPlayer;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.repository.FootballClubRepository;
import org.springframework.samples.petclinic.service.exceptions.DateException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.samples.petclinic.service.exceptions.NumberOfPlayersAndCoachException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class FootballClubService {

	private FootballClubRepository		footRepository;

	@Autowired
	private FootballPlayerService		footballPlayerService;

	@Autowired
	private ContractPlayerService		contractPlayerService;

	@Autowired
	private ContractCommercialService	contractCommercialService;

	@Autowired
	private CoachService				coachService;


	@Autowired
	public FootballClubService(final FootballClubRepository footRepository) {
		this.footRepository = footRepository;

	}

	//Buscar todos los equipos
	@Transactional(readOnly = true)
	public Collection<FootballClub> findAll() throws DataAccessException {
		return this.footRepository.findAll();
	}

	//Buscar todos los equipos Publicados
	//@Transactional(readOnly = true)
	public Collection<FootballClub> findFootballClubs() throws DataAccessException {
		return this.footRepository.findAllPublished();
	}

	//Buscar equipo por id
	@Transactional(readOnly = true)
	public FootballClub findFootballClubById(final int id) throws DataAccessException {
		return this.footRepository.findById(id);
	}

	//Buscar presidente por username
	@Transactional(readOnly = true)
	public President findPresidentByUsername(final String currentPrincipalName) throws DataAccessException {
		return this.footRepository.findPresidentByUsername(currentPrincipalName);
	}

	//Buscar entrenador por clubId
	@Transactional(readOnly = true)
	public Coach findCoachByClubId(final int id) throws DataAccessException {
		return this.coachService.findCoachByClubId(id);
	}

	//Buscar equipo por username
	@Transactional(readOnly = true)
	//@Cacheable("footballClubByPresident")
	public FootballClub findFootballClubByPresident(final String principalUsername) throws DataAccessException {
		return this.footRepository.findFootballClubByPresident(principalUsername);
	}

	@Transactional(readOnly = true)
	public FootballClub findFootballClubByName(final String name) throws DataAccessException {
		return this.footRepository.findFootballClubByName(name);
	}

	//Guardar equipo con validación de nombre duplicado
	@Transactional(rollbackFor = {
		DuplicatedNameException.class, NumberOfPlayersAndCoachException.class, DateException.class
	})
	public void saveFootballClub(@Valid final FootballClub footballClub) throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException {

		String name = footballClub.getName().toLowerCase();
		FootballClub otherFootClub = null;

		//Creamos un "otherFootClub" si existe uno en la db con el mismo nombre y diferente id
		for (FootballClub o : this.footRepository.findAll()) {
			String compName = o.getName();
			compName = compName.toLowerCase();
			if (compName.equals(name) && !o.getId().equals(footballClub.getId())) {
				otherFootClub = o;
			}
		}

		//RN: El nombre no puede ser el mismo
		if (StringUtils.hasLength(footballClub.getName()) && otherFootClub != null && !otherFootClub.getId().equals(footballClub.getId())) {
			throw new DuplicatedNameException();
		}

		Date now = new Date(System.currentTimeMillis() - 1);

		//RN: La fecha de fundación debe ser pasado
		if (footballClub.getFoundationDate().after(now)) {
			throw new DateException();
		}

		//RN: Minimo 5 jugadores y 1 entrenador
		if (footballClub.getId() != null) { //Para que no lo haga en el crear equipo
			Collection<FootballPlayer> cp = this.footballPlayerService.findAllClubFootballPlayers(footballClub.getId());
			Coach coach = this.coachService.findCoachByClubId(footballClub.getId());

			if (cp.size() < 5 && footballClub.getStatus() == true || coach == null && footballClub.getStatus() == true) {
				throw new NumberOfPlayersAndCoachException();
			}
		}

		this.footRepository.save(footballClub);

	}

	//Borrar equipo
	@Transactional
	public void deleteFootballClub(final FootballClub footballClub) throws DataAccessException {

		if (footballClub != null) {

			Collection<ContractPlayer> contractsPlayer = this.contractPlayerService.findAllPlayerContractsByClubId(footballClub.getId());
			for (ContractPlayer a : contractsPlayer) {
				this.contractPlayerService.deleteContractDeletingClub(a);
			}

			try {
				ContractCommercial cc = this.contractCommercialService.findCommercialContractByClubId(footballClub.getId());
				cc.setClub(null);
				this.contractCommercialService.saveContractCommercial(cc);
			} catch (Exception e) {
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
