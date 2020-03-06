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
import org.springframework.samples.petclinic.model.ContractCommercial;
import org.springframework.samples.petclinic.model.ContractPlayer;
import org.springframework.samples.petclinic.repository.ContractRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContractService {

	private ContractRepository ContractRepository;


	@Autowired
	public ContractService(final ContractRepository ContractRepository) {
		this.ContractRepository = ContractRepository;

	}

	//Buscar todos los contratos de jugadores
	@Transactional(readOnly = true)
	public Collection<ContractPlayer> findAllPlayerContracts() throws DataAccessException {
		return this.ContractRepository.findAllPlayerContracts();
	}

	//Buscar contrato de jugador por id
	@Transactional(readOnly = true)
	public ContractPlayer findContractPlayerById(final int id) throws DataAccessException {
		return this.ContractRepository.findContractPlayerById(id);
	}

	//Buscar contrato de jugador por id del jugador
	@Transactional(readOnly = true)
	public ContractPlayer findContractPlayerByPlayerId(final int playerId) throws DataAccessException {
		return this.ContractRepository.findContractPlayerByPlayerId(playerId);
	}

	@Transactional
	public void saveContractPlayer(final ContractPlayer contractPlayer) {
		this.ContractRepository.save(contractPlayer);
	}

	//Buscar todos los contratos de comerciales
	@Transactional(readOnly = true)
	public Collection<ContractCommercial> findAllCommercialContracts() throws DataAccessException {
		return this.ContractRepository.findAllCommercialContracts();
	}

	//Buscar contrato de comercial por id
	@Transactional(readOnly = true)
	public ContractCommercial findContractCommercialById(final int id) throws DataAccessException {
		return this.ContractRepository.findContractCommercialById(id);
	}

	@Transactional
	public void saveContractCommercial(final ContractCommercial contractCommercial) {
		this.ContractRepository.save(contractCommercial);
	}
}
