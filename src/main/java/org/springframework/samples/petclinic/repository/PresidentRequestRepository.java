
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.samples.petclinic.model.PresidentRequest;

public interface PresidentRequestRepository {

	Collection<PresidentRequest> findAll();

	PresidentRequest findById(int id);

	PresidentRequest findByUsername(String userName);

	void save(PresidentRequest compAdminRequest);

	void delete(PresidentRequest compAdminRequest);

	int countByUsername(String username);

	int count();
}
