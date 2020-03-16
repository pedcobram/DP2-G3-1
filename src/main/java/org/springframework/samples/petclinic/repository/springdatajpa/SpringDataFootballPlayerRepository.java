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

package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.repository.FootballPlayerRepository;

/**
 * Spring Data JPA specialization of the {@link VetRepository} interface
 *
 * @author Michael Isvy
 * @since 15.1.2013
 */
public interface SpringDataFootballPlayerRepository extends FootballPlayerRepository, Repository<FootballPlayer, Integer> {

	@Override
	@Query("select fp from FootballPlayer fp order by fp.club.name asc")
	Collection<FootballPlayer> findAll() throws DataAccessException;

	@Override
	@Query("select fp from FootballPlayer fp where fp.club = null")
	Collection<FootballPlayer> findAllFreeAgents() throws DataAccessException;

	@Override
	@Query("select fp from FootballPlayer fp where fp.club.id = ?1")
	Collection<FootballPlayer> findPlayersByClubId(int id) throws DataAccessException;

	@Override
	@Query("select fp from FootballPlayer fp where fp.id = ?1")
	FootballPlayer findById(int id) throws DataAccessException;

	@Override
	@Query("select fc from FootballClub fc where fc.id = ?1")
	FootballClub findClubByPlayerId(int id) throws DataAccessException;
}
