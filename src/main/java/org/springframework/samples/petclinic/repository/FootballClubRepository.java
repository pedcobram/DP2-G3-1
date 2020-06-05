
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.President;

public interface FootballClubRepository {

	Collection<FootballClub> findAll();

	Collection<FootballClub> findAllPublished();

	FootballClub findById(int id);

	FootballClub findFootballClubByPresident(String principalUsername);

	President findPresidentByUsername(String currentPrincipalName);

	void save(FootballClub footballClub);

	void delete(FootballClub footballClub);

	FootballClub findFootballClubByName(String name);

}
