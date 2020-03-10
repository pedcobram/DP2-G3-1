
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.CompAdminRequest;
import org.springframework.samples.petclinic.repository.CompAdminRequestRepository;

public interface SpringDataCompAdminRequestRepository extends CompAdminRequestRepository, Repository<CompAdminRequest, Integer> {

	@Override
	@Query("SELECT a FROM CompAdminRequest a WHERE a.id =:id")
	CompAdminRequest findById(@Param("id") int id) throws DataAccessException;

	@Override
	@Query("SELECT a FROM CompAdminRequest a WHERE a.user.username =:username AND a.status = 0")
	CompAdminRequest findByUsername(@Param("username") String username) throws DataAccessException;

	@Override
	@Query("SELECT COUNT(a) FROM CompAdminRequest a WHERE a.user.username =:username AND a.status = 0")
	int countByUsername(@Param("username") String username) throws DataAccessException;

	@Override
	@Query("select f from CompAdminRequest f WHERE f.status = 0")
	Collection<CompAdminRequest> findAll() throws DataAccessException;

	@Override
	@Query("SELECT COUNT(a) FROM CompAdminRequest a")
	int count() throws DataAccessException;
}
