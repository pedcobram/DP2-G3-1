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

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Fan;
import org.springframework.samples.petclinic.repository.FanRepository;
import org.springframework.samples.petclinic.repository.PetRepository;

/**
 * Spring Data JPA specialization of the {@link PetRepository} interface
 *
 * @author Michael Isvy
 * @since 15.1.2013
 */
public interface SpringDataFanRepository extends FanRepository, Repository<Fan, Integer> {

	@Override
	@Query("SELECT CASE WHEN count(f)>0 THEN true ELSE false END FROM Fan f WHERE f.user.id =: auId")
	boolean existsByUserId(@Param("auId") int auId) throws DataAccessException;

	@Override
	@Query("SELECT f FROM Fan f WHERE f.user.id =: auId")
	Fan findByUserId(@Param("auId") int id) throws DataAccessException;

}
