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

import javax.security.auth.login.CredentialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.PresidentRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PresidentService {

	@Autowired
	private PresidentRepository		presidentRepository;

	@Autowired
	private AuthoritiesService		authoritiesService;

	@Autowired
	private FootballClubService		footballClubService;

	@Autowired
	private AuthenticatedService	authenticatedService;


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

	@Transactional
	public void savePresident(final President president) throws DataAccessException, CredentialException {

		/**
		 * Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 * String currentPrincipalName = authentication.getName();
		 * 
		 * if (!president.getUser().getUsername().equals(currentPrincipalName)) { //SEGURIDAD
		 * throw new CredentialException("Forbidden Access");
		 * }
		 **/
		this.presidentRepository.save(president);
		this.authoritiesService.saveAuthorities(president.getUser().getUsername(), "president");
	}

	@Transactional
	public void deletePresident(final President president) throws DataAccessException, CredentialException {

		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(president.getUser().getUsername());
		this.footballClubService.deleteFootballClub(footballClub);

		User user = president.getUser();
		Authenticated auth = new Authenticated();
		auth.setFirstName(president.getFirstName());
		auth.setLastName(president.getLastName());
		auth.setDni(president.getDni());
		auth.setEmail(president.getEmail());
		auth.setTelephone(president.getTelephone());
		auth.setUser(user);

		this.authenticatedService.saveAuthenticated(auth);
		this.authoritiesService.deleteAuthorities(president.getUser().getUsername(), "president");
		this.authoritiesService.saveAuthorities(president.getUser().getUsername(), "authenticated");
		this.presidentRepository.delete(president);

		Authentication reAuth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
		SecurityContextHolder.getContext().setAuthentication(reAuth);
	}
}
