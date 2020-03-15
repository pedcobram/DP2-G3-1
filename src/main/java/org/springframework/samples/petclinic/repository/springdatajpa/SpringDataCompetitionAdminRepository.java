
package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.CompetitionAdmin;
import org.springframework.samples.petclinic.repository.CompetitionAdminRepository;

public interface SpringDataCompetitionAdminRepository extends CompetitionAdminRepository, Repository<CompetitionAdmin, Integer> {

	@Override
	@Query("SELECT a FROM CompetitionAdmin a WHERE a.id =:id")
	CompetitionAdmin findById(@Param("id") int id) throws DataAccessException;

	@Override
	@Query("SELECT a FROM CompetitionAdmin a WHERE a.user.username =:username")
	CompetitionAdmin findByUsername(@Param("username") String username) throws DataAccessException;

	@Override
	@Query("SELECT a FROM Authenticated a WHERE a.user.username =:username")
	Authenticated findAuthenticatedByUsername(@Param("username") String username) throws DataAccessException;

	@Override
	@Query("SELECT COUNT(a) FROM CompetitionAdmin a")
	int count() throws DataAccessException;
}
