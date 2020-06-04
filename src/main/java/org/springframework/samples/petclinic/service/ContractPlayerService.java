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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Contract;
import org.springframework.samples.petclinic.model.ContractPlayer;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.PlayerTransferRequest;
import org.springframework.samples.petclinic.repository.ContractRepository;
import org.springframework.samples.petclinic.service.exceptions.DateException;
import org.springframework.samples.petclinic.service.exceptions.MoneyClubException;
import org.springframework.samples.petclinic.service.exceptions.NumberOfPlayersAndCoachException;
import org.springframework.samples.petclinic.service.exceptions.SalaryException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContractPlayerService {

	private ContractRepository				contractRepository;

	@Autowired
	private FootballPlayerService			footballPlayerService;

	@Autowired
	private PlayerTransferRequestService	playerTransferRequestService;


	@Autowired
	public ContractPlayerService(final ContractRepository contractRepository) {
		this.contractRepository = contractRepository;

	}

	//Buscar contrato de jugador por id
	@Transactional(readOnly = true)
	public ContractPlayer findContractPlayerById(final int id) throws DataAccessException {
		return this.contractRepository.findContractPlayerById(id);
	}

	//Buscar contrato de jugador por id del jugador
	@Transactional(readOnly = true)
	public ContractPlayer findContractPlayerByPlayerId(final int playerId) throws DataAccessException {
		return this.contractRepository.findContractPlayerByPlayerId(playerId);
	}

	//Buscar contratos por club
	@Transactional(readOnly = true)
	public Collection<ContractPlayer> findAllPlayerContractsByClubId(final int clubId) throws DataAccessException {
		return this.contractRepository.findAllPlayerContractsByClubId(clubId);
	}

	//Fichar agente libre
	@Transactional(rollbackFor = {
		NumberOfPlayersAndCoachException.class, DateException.class, SalaryException.class, MoneyClubException.class
	})
	public void saveContractPlayer(final ContractPlayer contractPlayer) throws DataAccessException, MoneyClubException, NumberOfPlayersAndCoachException, SalaryException, DateException {

		Collection<FootballPlayer> players = this.footballPlayerService.findAllClubFootballPlayers(contractPlayer.getClub().getId());

		//RN: Si el club no es público, solo puede tener a 7 jugadores como máximo
		if (players.size() >= 7 && !contractPlayer.getClub().getStatus()) {
			throw new NumberOfPlayersAndCoachException();
		}

		Integer valor = contractPlayer.getPlayer().getValue();
		Integer salario = valor / 10;

		//RN: Salario mínimo y máximo
		if (contractPlayer.getSalary() < salario || contractPlayer.getSalary() > valor) {
			throw new SalaryException();
		}

		//RN: La transacción total no puede ser mayor a los fondos del club
		if (contractPlayer.getClub().getMoney() < contractPlayer.getSalary()) {
			throw new MoneyClubException();
		}

		Date now = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.YEAR, +1);
		now = cal.getTime();

		//RN: El contrato debe tener al menos un año de duración
		if (contractPlayer.getEndDate().before(now)) {
			throw new DateException();
		}

		contractPlayer.getPlayer().setClub(contractPlayer.getClub());
		contractPlayer.getClub().setMoney(contractPlayer.getClub().getMoney() - contractPlayer.getSalary());

		this.contractRepository.save(contractPlayer);

	}

	//Despedir Jugador
	@Transactional
	public void deleteContract(final Contract contract) throws DataAccessException, MoneyClubException {

		ContractPlayer thisContract = this.findContractPlayerById(contract.getId());

		PlayerTransferRequest ptr = this.playerTransferRequestService.findPlayerTransferRequestByPlayerId(contract.getId());

		//RN: La transacción total no puede ser mayor a los fondos del club
		if (contract.getClub().getMoney() < contract.getClause()) {
			throw new MoneyClubException();
		}

		if (ptr != null) {
			this.playerTransferRequestService.deletePlayerTransferRequest(ptr);
		}

		contract.getClub().setMoney(contract.getClub().getMoney() - contract.getClause());
		thisContract.getPlayer().setClub(null);
		this.contractRepository.delete(contract);
	}

	//Borrar contrato si un club es borrado y se declara insolvente
	@Transactional
	public void deleteContractDeletingClub(final Contract contract) throws DataAccessException {
		contract.setClub(null);
		this.contractRepository.delete(contract);
	}
}
