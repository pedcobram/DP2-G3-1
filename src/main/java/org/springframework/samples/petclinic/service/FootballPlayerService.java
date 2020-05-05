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

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.ContractPlayer;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.repository.FootballPlayerRepository;
import org.springframework.samples.petclinic.service.exceptions.DateException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.samples.petclinic.service.exceptions.MoneyClubException;
import org.springframework.samples.petclinic.service.exceptions.NumberOfPlayersAndCoachException;
import org.springframework.samples.petclinic.service.exceptions.SalaryException;
import org.springframework.samples.petclinic.service.exceptions.StatusException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class FootballPlayerService {

	@Autowired
	private FootballPlayerRepository	footRepository;

	@Autowired
	private ContractPlayerService		contractService;


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

	//Guardar jugador con validación de nombre duplicado
	@Transactional(rollbackFor = {
		DuplicatedNameException.class, NumberOfPlayersAndCoachException.class, DateException.class, StatusException.class, SalaryException.class, MoneyClubException.class
	})
	public void saveFootballPlayer(@Valid final FootballPlayer footballPlayer, @Valid final ContractPlayer newContract)
		throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, MoneyClubException, StatusException, DateException, SalaryException {

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

		//RN: Nombre Duplicado
		if (StringUtils.hasLength(footballPlayer.getFirstName()) && StringUtils.hasLength(footballPlayer.getLastName()) && otherPlayer != null && otherPlayer.getId() != footballPlayer.getId()) {
			throw new DuplicatedNameException();
		}

		Date now = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.YEAR, -16);
		now = cal.getTime();

		//RN: Debe tener al menos 16 años
		if (footballPlayer.getBirthDate().after(now)) {
			throw new DateException();
		}

		Collection<FootballPlayer> cp = this.findAllClubFootballPlayers(footballPlayer.getClub().getId());

		//RN: Solo se pueden registrar jugadores hasta que se tengan 7
		if (cp.size() >= 7 && footballPlayer.getClub().getStatus() == false) {
			throw new NumberOfPlayersAndCoachException();
		}

		//RN: El salario no puede ser superior a los fondos del equipo
		if (newContract.getClub().getMoney() < newContract.getSalary()) {
			throw new MoneyClubException();
		}

		//RN: Si el club es público no puede registrar a un jugador
		if (footballPlayer.getClub().getStatus() == true) {
			throw new StatusException();
		}

		newContract.getClub().setMoney(newContract.getClub().getMoney() - newContract.getSalary());
		this.footRepository.save(footballPlayer);
		this.contractService.saveContractPlayer(newContract);

	}

	@Transactional(rollbackFor = {
		DuplicatedNameException.class, NumberOfPlayersAndCoachException.class, DateException.class, StatusException.class, SalaryException.class, MoneyClubException.class
	})
	public void updateFootballPlayer(@Valid final FootballPlayer footballPlayer, @Valid final ContractPlayer newContract)
		throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, MoneyClubException, StatusException, DateException, SalaryException {

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

		//RN: Nombre Duplicado
		if (StringUtils.hasLength(footballPlayer.getFirstName()) && StringUtils.hasLength(footballPlayer.getLastName()) && otherPlayer != null && otherPlayer.getId() != footballPlayer.getId()) {
			throw new DuplicatedNameException();
		}

		Date now = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.YEAR, -16);
		now = cal.getTime();

		//RN: Debe tener al menos 16 años
		if (footballPlayer.getBirthDate().after(now)) {
			throw new DateException();
		}

		Collection<FootballPlayer> cp = this.findAllClubFootballPlayers(footballPlayer.getClub().getId());

		//RN: Solo se pueden registrar jugadores hasta que se tengan 7
		if (cp.size() >= 7 && footballPlayer.getClub().getStatus() == false) {
			throw new NumberOfPlayersAndCoachException();
		}

		//RN: El salario no puede ser superior a los fondos del equipo
		if (newContract.getClub().getMoney() < newContract.getSalary()) {
			throw new MoneyClubException();
		}

		newContract.getClub().setMoney(newContract.getClub().getMoney() - newContract.getSalary());
		this.footRepository.save(footballPlayer);
		this.contractService.saveContractPlayer(newContract);
	}
}
