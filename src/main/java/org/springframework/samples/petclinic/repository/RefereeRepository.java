
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.Referee;

public interface RefereeRepository {

	Collection<Referee> findAllReferees();

	Referee findRefereeById(int id);

	Referee findRefereeByUsername(String userName);

	Authenticated findAuthenticatedByUsername(String userName);

	void save(Referee referee);

	void delete(Referee referee);

	int count();

}
