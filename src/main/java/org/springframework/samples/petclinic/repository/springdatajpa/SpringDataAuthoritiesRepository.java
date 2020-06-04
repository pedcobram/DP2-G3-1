
package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.repository.AuthoritiesRepository;

public interface SpringDataAuthoritiesRepository extends AuthoritiesRepository, Repository<Authorities, String> {

	@Override
	@Query("SELECT a FROM Authorities a WHERE a.username =:username")
	Authorities findAuthoritiesByUsername(@Param("username") String username);
}
