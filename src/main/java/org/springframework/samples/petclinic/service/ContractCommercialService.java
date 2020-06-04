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

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.ContractCommercial;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.repository.ContractRepository;
import org.springframework.samples.petclinic.service.exceptions.DateException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.samples.petclinic.service.exceptions.NoMultipleContractCommercialException;
import org.springframework.samples.petclinic.service.exceptions.NoStealContractCommercialException;
import org.springframework.samples.petclinic.service.exceptions.NotEnoughMoneyException;
import org.springframework.samples.petclinic.service.exceptions.NumberOfPlayersAndCoachException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContractCommercialService {

	private ContractRepository	contractRepository;

	@Autowired
	private FootballClubService	footballClubService;


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
	@Cacheable("contractById")
	public ContractCommercial findContractCommercialById(final int id) throws DataAccessException {
		return this.contractRepository.findContractCommercialById(id);
	}

	@Transactional(readOnly = true)
	public ContractCommercial findCommercialContractByClubId(final int id) throws DataAccessException {
		return this.contractRepository.findCommercialContractByClubId(id);
	}

	@Transactional(rollbackFor = {
		NoMultipleContractCommercialException.class, NoStealContractCommercialException.class, NotEnoughMoneyException.class
	})
	@CacheEvict(value = "contractById", allEntries = true)
	public void saveContractCommercial(final ContractCommercial contractCommercial)
		throws DataAccessException, NoMultipleContractCommercialException, NoStealContractCommercialException, NotEnoughMoneyException, DuplicatedNameException, NumberOfPlayersAndCoachException, DateException {
		//Existe contrato commercial con este club
		Collection<ContractCommercial> contracts = this.findAllCommercialContracts();

		//Si se encuentra un contrato diferente con el mismo club(no null) .... Exception
		if (!contracts.stream().filter(x -> x.getClub() != null && x.getId() != contractCommercial.getId() && x.getClub() == contractCommercial.getClub()).collect(Collectors.toList()).isEmpty()) {
			throw new NoMultipleContractCommercialException();
		}

		FootballClub clubNuevo = null;
		FootballClub clubViejo = null;
		try {
			clubNuevo = contractCommercial.getClub();
		} catch (Exception e) {
		}
		try {
			clubViejo = this.findContractCommercialById(contractCommercial.getId()).getClub();
		} catch (Exception e) {
		}
		//Si no son nulos y son diferentes significa que intentan cambiar de club sin terminar contrato
		if (clubNuevo != null && clubViejo != null && clubNuevo != clubViejo) {
			throw new NoStealContractCommercialException();
		}

		//Si se intenta terminar el contrato (Club -> No Club)
		if (clubNuevo == null && clubViejo != null) {
			if (contractCommercial.getEndDate().getTime() - Date.valueOf(LocalDate.now()).getTime() > 0) {
				long duracion = contractCommercial.getEndDate().getTime() - contractCommercial.getStartDate().getTime();
				long tiempollevado = Date.valueOf(LocalDate.now()).getTime() - contractCommercial.getStartDate().getTime();
				double percent = ((double) duracion - (double) tiempollevado) / duracion;
				int clausulaApagar = (int) (contractCommercial.getClause() * percent);
				//Si tienes que pagar mas de lo que tienes .... Exception
				if (clausulaApagar > clubViejo.getMoney()) {
					throw new NotEnoughMoneyException();
				}
				clubViejo.setMoney(clubViejo.getMoney() - clausulaApagar);
				this.footballClubService.saveFootballClub(clubViejo);
			}
		}
		this.contractRepository.save(contractCommercial);
	}
}
