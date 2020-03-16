
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Coach;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.President;

public interface FootballClubRepository {

	Collection<FootballClub> findAll() throws DataAccessException;

	Collection<FootballClub> findAllPublished() throws DataAccessException;

	FootballClub findById(int id) throws DataAccessException;

	void save(FootballClub footballClub) throws DataAccessException;

	President findPresidentByUsername(String currentPrincipalName) throws DataAccessException;

	FootballClub findFootballClubByPresident(String principalUsername) throws DataAccessException;

	void delete(FootballClub footballClub) throws DataAccessException;

	FootballClub findFootballClubByName(String footballClubName) throws DataAccessException;

	Coach findCoachByClubId(int clubId) throws DataAccessException;

}
