
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.PresidentRequest;
import org.springframework.samples.petclinic.repository.PresidentRequestRepository;

public interface SpringDataPresidentRequestRepository extends PresidentRequestRepository, Repository<PresidentRequest, Integer> {

	@Override
	@Query("SELECT a FROM PresidentRequest a WHERE a.id =:id")
	PresidentRequest findById(@Param("id") int id) throws DataAccessException;

	@Override
	@Query("SELECT a FROM PresidentRequest a WHERE a.user.username =:username AND a.status = 0")
	PresidentRequest findByUsername(@Param("username") String username) throws DataAccessException;

	@Override
	@Query("SELECT COUNT(a) FROM PresidentRequest a WHERE a.user.username =:username AND a.status = 0")
	int countByUsername(@Param("username") String username) throws DataAccessException;

	@Override
	@Query("select f from PresidentRequest f WHERE f.status = 0")
	Collection<PresidentRequest> findAll() throws DataAccessException;

	@Override
	@Query("SELECT COUNT(a) FROM PresidentRequest a")
	int count() throws DataAccessException;
}
