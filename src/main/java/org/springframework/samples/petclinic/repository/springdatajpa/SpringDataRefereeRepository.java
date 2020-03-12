
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.Referee;
import org.springframework.samples.petclinic.repository.RefereeRepository;

public interface SpringDataRefereeRepository extends RefereeRepository, Repository<Referee, Integer> {

	@Override
	@Query("SELECT DISTINCT a FROM Referee a WHERE a.lastName LIKE :lastName%")
	Collection<Referee> findRefereeByLastName(@Param("lastName") String lastName);

	@Override
	@Query("SELECT a FROM Referee a")
	Collection<Referee> findAllReferees();

	@Override
	@Query("SELECT a FROM Referee a WHERE a.id =:id")
	Referee findRefereeById(@Param("id") int id);

	@Override
	@Query("SELECT a FROM Referee a WHERE a.user.username =:username")
	Referee findRefereeByUsername(@Param("username") String userName);

	@Override
	@Query("SELECT a FROM Authenticated a WHERE a.user.username =:username")
	Authenticated findAuthenticatedByUsername(@Param("username") String username);

}
