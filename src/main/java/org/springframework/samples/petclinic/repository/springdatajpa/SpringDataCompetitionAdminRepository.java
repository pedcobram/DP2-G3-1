
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.CompetitionAdmin;
import org.springframework.samples.petclinic.repository.CompetitionAdminRepository;

public interface SpringDataCompetitionAdminRepository extends CompetitionAdminRepository, Repository<CompetitionAdmin, Integer> {

	@Override
	@Query("SELECT DISTINCT a FROM CompetitionAdmin a WHERE a.lastName LIKE :lastName%")
	Collection<CompetitionAdmin> findByLastName(@Param("lastName") String lastName);

	@Override
	@Query("SELECT a FROM CompetitionAdmin a WHERE a.id =:id")
	CompetitionAdmin findById(@Param("id") int id);

	@Override
	@Query("SELECT a FROM CompetitionAdmin a WHERE a.user.username =:username")
	CompetitionAdmin findByUsername(@Param("username") String username);

	@Override
	@Query("SELECT a FROM Authenticated a WHERE a.user.username =:username")
	Authenticated findAuthenticatedByUsername(@Param("username") String username);
}
