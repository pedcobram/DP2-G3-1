
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Coach;

public interface CoachRepository {

	Collection<Coach> findAll() throws DataAccessException;

	Collection<Coach> findAllFreeAgents() throws DataAccessException;

	Coach findById(int id) throws DataAccessException;

	Coach findCoachByClubId(int clubId) throws DataAccessException;

	void save(Coach coach) throws DataAccessException;

	Collection<Coach> findAllWithClub() throws DataAccessException;

}
