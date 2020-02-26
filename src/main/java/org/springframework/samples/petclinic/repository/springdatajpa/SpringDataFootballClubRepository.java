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
	President findPresidentByUsername(@Param("username") String username);

	@Override
	@Query("SELECT a FROM FootballClub a WHERE a.president.user.username =:username")
	FootballClub findFootballClubByPresident(@Param("username") String username);

	@Override
	@Query("select a from FootballClub a where a.name = ?1")
	FootballClub findFootballClubByName(String name);

	@Override
	@Query("select a from FootballClub a where a.name = ?1 and a.id != ?2")
	FootballClub findFootballClubByNameAndDifferentId(String name, Integer id);

}
