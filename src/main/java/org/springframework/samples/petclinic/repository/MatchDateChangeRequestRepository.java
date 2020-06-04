
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.samples.petclinic.model.MatchDateChangeRequest;

public interface MatchDateChangeRequestRepository {

	Collection<MatchDateChangeRequest> findAllMatchDateChangeRequest(String presidentUsername);

	MatchDateChangeRequest findById(int id);

	void save(MatchDateChangeRequest matchDateChangeRequest);

	void delete(MatchDateChangeRequest matchDateChangeRequest);

}
