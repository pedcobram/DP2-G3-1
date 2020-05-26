
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.RefereeRequest;
import org.springframework.samples.petclinic.repository.RefereeRequestRepository;

public interface SpringDataRefereeRequestRepository extends RefereeRequestRepository, Repository<RefereeRequest, Integer> {

	@Override
	@Query("SELECT a FROM RefereeRequest a WHERE a.id =:id")
	RefereeRequest findById(@Param("id") int id) throws DataAccessException;

	@Override
	@Query("SELECT a FROM RefereeRequest a WHERE a.user.username =:username AND a.status = 0")
	RefereeRequest findByUsername(@Param("username") String username) throws DataAccessException;

	@Override
	@Query("SELECT COUNT(a) FROM RefereeRequest a WHERE a.user.username =:username AND a.status = 0")
	int countByUsername(@Param("username") String username) throws DataAccessException;

	@Override
	@Query("select f from RefereeRequest f WHERE f.status = 0")
	Collection<RefereeRequest> findAll() throws DataAccessException;

	@Override
	@Query("SELECT COUNT(a) FROM RefereeRequest a")
	int count() throws DataAccessException;
}
