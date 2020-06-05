
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.MatchDateChangeRequest;
import org.springframework.samples.petclinic.repository.MatchDateChangeRequestRepository;

public interface SpringDataMatchDateChangeRequestRepository extends MatchDateChangeRequestRepository, Repository<MatchDateChangeRequest, Integer> {

	@Override
	@Query("SELECT m FROM MatchDateChangeRequest m WHERE m.id =:id")
	MatchDateChangeRequest findById(@Param("id") int id);

	@Override
	@Query("SELECT m FROM MatchDateChangeRequest m WHERE m.request_creator =:presidentUsername")
	Collection<MatchDateChangeRequest> findAllMatchDateChangeRequest(@Param("presidentUsername") String presidentUsername);

}
