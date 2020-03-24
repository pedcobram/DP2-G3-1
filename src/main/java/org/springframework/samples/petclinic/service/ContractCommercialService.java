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
import org.springframework.samples.petclinic.model.Contract;
import org.springframework.samples.petclinic.model.ContractCommercial;
import org.springframework.samples.petclinic.repository.ContractRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContractCommercialService {

	private ContractRepository contractRepository;


	@Autowired
	public ContractCommercialService(final ContractRepository contractRepository) {
		this.contractRepository = contractRepository;

	}

	//Buscar todos los contratos de comerciales
	@Transactional(readOnly = true)
	public Collection<ContractCommercial> findAllCommercialContracts() throws DataAccessException {
		return this.contractRepository.findAllCommercialContracts();
	}

	//Buscar contrato de comercial por id
	@Transactional(readOnly = true)
	public ContractCommercial findContractCommercialById(final int id) throws DataAccessException {
		return this.contractRepository.findContractCommercialById(id);
	}

	@Transactional(readOnly = true)
	public Collection<ContractCommercial> findAllCommercialContractsByClubId(final int id) throws DataAccessException {
		return this.contractRepository.findAllCommercialContractsByClubId(id);
	}

	@Transactional
	public void saveContractCommercial(final ContractCommercial contractCommercial) throws DataAccessException {
		this.contractRepository.save(contractCommercial);
	}

	@Transactional
	public void deleteContract(final Contract contract) throws DataAccessException {
		this.contractRepository.delete(contract);
	}
}
