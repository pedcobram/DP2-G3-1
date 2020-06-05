
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.samples.petclinic.model.CompAdminRequest;

public interface CompAdminRequestRepository {

	Collection<CompAdminRequest> findAll();

	CompAdminRequest findById(int id);

	CompAdminRequest findByUsername(String userName);

	void save(CompAdminRequest compAdminRequest);

	void delete(CompAdminRequest compAdminRequest);

	int countByUsername(String username);

	int count();
}
