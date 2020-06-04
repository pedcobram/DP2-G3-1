
package org.springframework.samples.petclinic.repository;

import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.CompetitionAdmin;

public interface CompetitionAdminRepository {

	CompetitionAdmin findById(int id);

	CompetitionAdmin findByUsername(String userName);

	void save(CompetitionAdmin authenticated);

	Authenticated findAuthenticatedByUsername(String username);

	void delete(CompetitionAdmin competitionAdmin);

	int count();

}
