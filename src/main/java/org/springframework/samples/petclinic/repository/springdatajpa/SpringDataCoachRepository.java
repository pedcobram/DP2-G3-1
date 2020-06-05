
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Coach;
import org.springframework.samples.petclinic.repository.CoachRepository;

public interface SpringDataCoachRepository extends CoachRepository, Repository<Coach, Integer> {

	@Override
	@Query("select c from Coach c")
	Collection<Coach> findAll();

	@Override
	@Query("select c from Coach c where c.club = null")
	Collection<Coach> findAllFreeAgents();

	@Override
	@Query("select c from Coach c where c.club.id = ?1")
	Coach findCoachByClubId(int clubId);

	@Override
	@Query("select c from Coach c where c.id = ?1")
	Coach findById(int id);

}
