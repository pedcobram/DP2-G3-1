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
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.repository.PresidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PresidentService {

	private PresidentRepository	presidentRepository;

	@Autowired
	private UserService			userService;

	@Autowired
	private AuthoritiesService	authoritiesService;


	@Autowired
	public PresidentService(final PresidentRepository presidentRepository) {
		this.presidentRepository = presidentRepository;
	}

	@Transactional(readOnly = true)
	public President findPresidentById(final int id) throws DataAccessException {
		return this.presidentRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public President findPresidentByUsername(final String userName) throws DataAccessException {
		return this.presidentRepository.findByUsername(userName);
	}

	@Transactional(readOnly = true)
	public Collection<President> findPresidentByLastName(final String lastName) throws DataAccessException {
		return this.presidentRepository.findByLastName(lastName);
	}

	@Transactional
	public void savePresident(final President president) throws DataAccessException {
		//creating president
		this.presidentRepository.save(president);
		//creating authorities
		this.authoritiesService.saveAuthorities(president.getUser().getUsername(), "president");
	}

	@Transactional(readOnly = true)
	public Authenticated findAuthenticatedByUsername(final String userName) throws DataAccessException {
		return this.presidentRepository.findAuthenticatedByUsername(userName);
	}

}
