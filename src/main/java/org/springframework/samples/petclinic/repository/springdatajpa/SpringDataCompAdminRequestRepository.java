
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
	CompAdminRequest findById(@Param("id") int id);

	@Override
	@Query("SELECT a FROM CompAdminRequest a WHERE a.user.username =:username AND a.status = false")
	CompAdminRequest findByUsername(@Param("username") String username);

	@Override
	@Query("SELECT COUNT(a) FROM CompAdminRequest a WHERE a.user.username =:username AND a.status = false")
	int countByUsername(@Param("username") String username);

	@Override
	@Query("select f from CompAdminRequest f")
	Collection<CompAdminRequest> findAll() throws DataAccessException;

}
