
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.samples.petclinic.model.RefereeRequest;

public interface RefereeRequestRepository {

	Collection<RefereeRequest> findAll();

	RefereeRequest findById(int id);

	RefereeRequest findByUsername(String userName);

	void save(RefereeRequest compAdminRequest);

	void delete(RefereeRequest compAdminRequest);

	int countByUsername(String username);

	int count();
}
