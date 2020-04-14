
package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Competition;
import org.springframework.samples.petclinic.repository.CompetitionRepository;

public interface SpringDataCompetitionRepository extends CompetitionRepository, Repository<Competition, Integer> {

	@Override
	@Query("select c from Competition c where c.id = ?1")
	Competition findById(int id) throws DataAccessException;

}
