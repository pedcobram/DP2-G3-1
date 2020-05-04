
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Competition;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.repository.CompetitionRepository;

public interface SpringDataCompetitionRepository extends CompetitionRepository, Repository<Competition, Integer> {

	@Override
	@Query("select c from Competition c where c.id = ?1")
	Competition findById(int id) throws DataAccessException;

	@Override
	@Query("select c from Competition c where c.status = true")
	Collection<Competition> findAllPublishedCompetitions() throws DataAccessException;

	@Override
	@Query("select c from Competition c where c.creator = ?1")
	Collection<Competition> findMyCompetitions(final String username) throws DataAccessException;

	//select c from FootballClub c where c != ALL ()
	@Override
	@Query("select f.clubs from Competition f where f.id = ?1")
	Collection<FootballClub> findClubsById(int competitionId);

	//Clubes públicos
	@Override
	@Query("select c from FootballClub c where c.status = true")
	Collection<FootballClub> findAllPublishedClubs();
}
