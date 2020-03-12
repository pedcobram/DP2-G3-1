
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Coach;
import org.springframework.samples.petclinic.repository.CoachRepository;

public interface SpringDataCoachRepository extends CoachRepository, Repository<Coach, Integer> {

	@Override
	@Query("select c from Coach c where c.club != null")
	Collection<Coach> findAll() throws DataAccessException;

	@Override
	@Query("select c from Coach c where c.club = null")
	Collection<Coach> findAllFreeAgents() throws DataAccessException;

	@Override
	@Query("select c from Coach c where c.club.id = ?1")
	Coach findCoachByClubId(int clubId) throws DataAccessException;

	@Override
	@Query("select c from Coach c where c.id = ?1")
	Coach findById(int id) throws DataAccessException;

}
