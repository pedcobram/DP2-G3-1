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
import org.springframework.samples.petclinic.model.ContractCommercial;
import org.springframework.samples.petclinic.repository.ContractRepository;
import org.springframework.samples.petclinic.service.exceptions.NoMultipleContractCommercialException;
import org.springframework.samples.petclinic.service.exceptions.NoStealContractCommercialException;
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
	public ContractCommercial findCommercialContractByClubId(final int id) throws DataAccessException {
		return this.contractRepository.findCommercialContractByClubId(id);
	}

	@Transactional(rollbackFor = {
		NoMultipleContractCommercialException.class, NoStealContractCommercialException.class
	})
	public void saveContractCommercial(final ContractCommercial contractCommercial) throws DataAccessException, NoMultipleContractCommercialException, NoStealContractCommercialException {
		//Existe contrato commercial con este club
		Collection<ContractCommercial> contracts = this.contractRepository.findAllCommercialContracts();

		//Si se encuentra un contrato diferente con el mismo club(no null) .... Exception
		if (!contracts.stream().filter(x -> x.getClub() != null && x.getId() != contractCommercial.getId() && x.getClub() == contractCommercial.getClub()).collect(Collectors.toList()).isEmpty()) {
			throw new NoMultipleContractCommercialException();
		}

		//Si el contrato commercial ya existe
		try {
			ContractCommercial cAhora = this.contractRepository.findContractCommercialById(contractCommercial.getId());

			//Si el contrato no pasa de null a NoNull, de NoNull a null o de null a null significa que ha cambiado de club sin que termine el contrato....Exception
			if (cAhora.getClub() != null && contractCommercial.getClub() != null && cAhora.getClub() != contractCommercial.getClub()) {
				throw new NoStealContractCommercialException();
			}
		} catch (NullPointerException e) {
			//Si captura un null pointer exception significa que el contrato es nuevo
			// y como ha superado el NoMultipleContractException es seguro guardarlo
			this.contractRepository.save(contractCommercial);
		}

	}
}
