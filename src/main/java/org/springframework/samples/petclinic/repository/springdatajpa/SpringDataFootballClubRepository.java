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
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.repository.FootballClubRepository;
import org.springframework.samples.petclinic.repository.VetRepository;

/**
 * Spring Data JPA specialization of the {@link VetRepository} interface
 *
 * @author Michael Isvy
 * @since 15.1.2013
 */
public interface SpringDataFootballClubRepository extends FootballClubRepository, Repository<FootballClub, Integer> {

	@Override
	@Query("SELECT a FROM President a WHERE a.user.username =:username")
	President findPresidentByUsername(@Param("username") String username) throws DataAccessException;

	@Override
	@Query("SELECT a FROM FootballClub a WHERE a.president.user.username =:username")
	FootballClub findFootballClubByPresident(@Param("username") String username) throws DataAccessException;

	@Override
	@Query("SELECT a FROM FootballClub a WHERE a.id =:id")
	FootballClub findById(int id) throws DataAccessException;

	@Override
	@Query("select f from FootballClub f where f.status = true")
	Collection<FootballClub> findAll() throws DataAccessException;
}
