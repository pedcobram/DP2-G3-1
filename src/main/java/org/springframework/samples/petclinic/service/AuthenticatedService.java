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
import org.springframework.samples.petclinic.model.Fan;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.AuthenticatedRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticatedService {

	private AuthenticatedRepository	authenticatedRepository;

	@Autowired
	private AuthoritiesService		authoritiesService;

	@Autowired
	private FanService				fanService;


	@Autowired
	public AuthenticatedService(final AuthenticatedRepository authenticatedRepository) {
		this.authenticatedRepository = authenticatedRepository;
	}

	@Transactional(readOnly = true)
	public Authenticated findAuthenticatedById(final int id) throws DataAccessException {
		return this.authenticatedRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Authenticated findAuthenticatedByUsername(final String userName) throws DataAccessException {
		return this.authenticatedRepository.findByUsername(userName);
	}

	@Transactional(readOnly = true)
	public Collection<Authenticated> findAuthenticatedByLastName(final String lastName) throws DataAccessException {
		return this.authenticatedRepository.findByLastName(lastName);
	}

	@Transactional
	public void saveAuthenticated(final Authenticated authenticated) throws DataAccessException, DuplicatedNameException {
		Authenticated au = this.authenticatedRepository.findByUsername(authenticated.getUser().getUsername());
		User user = authenticated.getUser();
		if (au == null) {
			user.setEnabled(true);
			//creating authenticated
			this.authenticatedRepository.save(authenticated);
			//creating authorities
			this.authoritiesService.saveAuthorities(authenticated.getUser().getUsername(), "authenticated");
		} else if (authenticated.getId() != au.getId() && user.getUsername() == au.getUser().getUsername()) {
			throw new DuplicatedNameException();
		} else {
			user.setEnabled(true);
			//creating authenticated
			this.authenticatedRepository.save(authenticated);
			//creating authorities
			this.authoritiesService.saveAuthorities(authenticated.getUser().getUsername(), "authenticated");

		}

	}

	@Transactional
	public void deleteAuthenticated(final Authenticated authenticated) throws DataAccessException {

		this.authoritiesService.deleteAuthorities(authenticated.getUser().getUsername(), "authenticated");
		Fan f = this.fanService.findByUserId(authenticated.getId());
		this.fanService.delete(f);
		this.authenticatedRepository.delete(authenticated);
	}

}
