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
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.repository.FootballClubRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class FootballClubService {

	private FootballClubRepository footRepository;


	@Autowired
	public FootballClubService(final FootballClubRepository footRepository) {
		this.footRepository = footRepository;

	}

	//Buscar todos los equipos
	@Transactional(readOnly = true)
	public Collection<FootballClub> findFootballClubs() throws DataAccessException {
		return this.footRepository.findAll();
	}

	//Buscar equipo por id
	@Transactional(readOnly = true)
	public FootballClub findFootballClubById(final int id) throws DataAccessException {
		return this.footRepository.findById(id);
	}

	//Guardar equipo con validación de nombre duplicado
	@Transactional(rollbackFor = DuplicatedNameException.class)
	public void saveFootballClub(final FootballClub footballClub) throws DataAccessException, DuplicatedNameException {

		String name = footballClub.getName().toLowerCase();
		FootballClub otherFootClub = null;

		//Creamos un "otherFootClub" si existe uno en la db con el mismo nombre y diferente id
		for (FootballClub o : this.footRepository.findAll()) {
			String compName = o.getName();
			compName = compName.toLowerCase();
			if (compName.equals(name) && o.getId() != footballClub.getId()) {
				otherFootClub = o;
			}
		}

		//Si el campo de nombre tiene contenido y el "otherFootClub" existe y no coincide el id con el actual lanzamos excepción
		if (StringUtils.hasLength(footballClub.getName()) && otherFootClub != null && otherFootClub.getId() != footballClub.getId()) {
			throw new DuplicatedNameException();
		} else {
			this.footRepository.save(footballClub);

		}
	}

	//Buscar presidente por username
	@Transactional(readOnly = true)
	public President findPresidentByUsername(final String currentPrincipalName) throws DataAccessException {
		return this.footRepository.findPresidentByUsername(currentPrincipalName);
	}

	//Buscar equipo por presidente
	@Transactional(readOnly = true)
	public FootballClub findFootballClubByPresident(final String principalUsername) throws DataAccessException {
		return this.footRepository.findFootballClubByPresident(principalUsername);
	}

	//Borrar equipo
	public void deleteFootballClub(final FootballClub footballClub) throws DataAccessException {
		this.footRepository.delete(footballClub);
	}

}
