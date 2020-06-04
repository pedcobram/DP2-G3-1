
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.samples.petclinic.model.Coach;

public interface CoachRepository {

	Collection<Coach> findAll();

	Collection<Coach> findAllFreeAgents();

	Coach findById(int id);

	Coach findCoachByClubId(int clubId);

	void save(Coach coach);

}
