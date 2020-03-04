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
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.repository.FootballPlayerRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class FootballPlayerService {

	private FootballPlayerRepository footRepository;


	@Autowired
	public FootballPlayerService(final FootballPlayerRepository footRepository) {
		this.footRepository = footRepository;

	}

	//Buscar todos los jugadores
	@Transactional(readOnly = true)
	public Collection<FootballPlayer> findAllFootballPlayers() throws DataAccessException {
		return this.footRepository.findAll();
	}

	//Buscar todos los jugadores libres
	@Transactional(readOnly = true)
	public Collection<FootballPlayer> findAllFootballPlayersFA() throws DataAccessException {
		return this.footRepository.findAllFreeAgents();
	}

	//Buscar todos los jugadores de un club
	@Transactional(readOnly = true)
	public Collection<FootballPlayer> findAllClubFootballPlayers(final int id) throws DataAccessException {
		return this.footRepository.findPlayersByClubId(id);
	}

	//Buscar jugador por id
	@Transactional(readOnly = true)
	public FootballPlayer findFootballPlayerById(final int id) throws DataAccessException {
		return this.footRepository.findById(id);
	}

	//Buscar equipo por id de jugador
	@Transactional(readOnly = true)
	public FootballClub findFootballClubByFootballPlayerId(final int id) throws DataAccessException {
		return this.footRepository.findClubByPlayerId(id);
	}

	//Guardar jugador con validación de nombre duplicado
	@Transactional(rollbackFor = DuplicatedNameException.class)
	public void saveFootballPlayer(@Valid final FootballPlayer footballPlayer) throws DataAccessException, DuplicatedNameException {

		String firstname = footballPlayer.getFirstName().toLowerCase();
		String lastname = footballPlayer.getLastName().toLowerCase();
		FootballPlayer otherPlayer = null;

		//Creamos un "otherPlayer" si existe uno en la db con el mismo nombre y diferente id
		for (FootballPlayer o : this.footRepository.findAll()) {
			String ofirst = o.getFirstName().toLowerCase();
			String olast = o.getLastName().toLowerCase();
			ofirst = ofirst.toLowerCase();
			olast = olast.toLowerCase();
			if (ofirst.equals(firstname) && olast.equals(lastname) && o.getId() != footballPlayer.getId()) {
				otherPlayer = o;
			}
		}

		//Si el campo de nombre tiene contenido y el "otherFootClub" existe y no coincide el id con el actual lanzamos excepción
		if (StringUtils.hasLength(footballPlayer.getFirstName()) && StringUtils.hasLength(footballPlayer.getLastName()) && otherPlayer != null && otherPlayer.getId() != footballPlayer.getId()) {
			throw new DuplicatedNameException();
		} else {
			this.footRepository.save(footballPlayer);

		}
	}

	@Transactional
	public void saveFootballPlayerClub(final FootballPlayer footballPlayer, final FootballClub footballClub) throws DataAccessException {

		footballPlayer.setClub(footballClub);

		this.footRepository.save(footballPlayer);
	}

	//TODO: Borrar jugador

}
