
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.President;

public interface FootballClubRepository {

	Collection<FootballClub> findAll() throws DataAccessException;

	Collection<FootballClub> findAllPublished() throws DataAccessException;

	FootballClub findById(int id) throws DataAccessException;

	FootballClub findFootballClubByPresident(String principalUsername) throws DataAccessException;

	President findPresidentByUsername(String currentPrincipalName) throws DataAccessException;

	void save(FootballClub footballClub) throws DataAccessException;

	void delete(FootballClub footballClub) throws DataAccessException;

}
