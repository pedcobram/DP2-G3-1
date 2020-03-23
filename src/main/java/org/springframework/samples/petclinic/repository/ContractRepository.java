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

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Contract;
import org.springframework.samples.petclinic.model.ContractPlayer;

public interface ContractRepository {

	Collection<Contract> findAll() throws DataAccessException;

	Contract findById(int id) throws DataAccessException;

	ContractPlayer findContractPlayerById(int id) throws DataAccessException;

	ContractPlayer findContractPlayerByPlayerId(int playerId) throws DataAccessException;

	Collection<ContractPlayer> findAllPlayerContractsByClubId(int clubId) throws DataAccessException;

	void delete(Contract contractPlayer) throws DataAccessException;

	void save(ContractPlayer contractPlayer) throws DataAccessException;
}
