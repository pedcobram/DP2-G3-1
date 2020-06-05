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

package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.samples.petclinic.model.Contract;
import org.springframework.samples.petclinic.model.ContractCommercial;
import org.springframework.samples.petclinic.model.ContractPlayer;

public interface ContractRepository {

	Collection<Contract> findAll();

	Contract findById(int id);

	void save(ContractCommercial contractCommercial);

	void delete(Contract contract);

	Collection<ContractCommercial> findAllCommercialContracts();

	ContractCommercial findCommercialContractByClubId(int id);

	ContractCommercial findContractCommercialById(int id);

	void save(ContractPlayer contractPlayer);

	Collection<ContractPlayer> findAllPlayerContracts();

	ContractPlayer findContractPlayerById(int id);

	ContractPlayer findContractPlayerByPlayerId(int playerId);

	Collection<ContractPlayer> findAllPlayerContractsByClubId(int clubId);
}
