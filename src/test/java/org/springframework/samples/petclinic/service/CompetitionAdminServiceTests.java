
package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.CompetitionAdmin;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CompetitionAdminServiceTests {

	@Autowired
	protected CompetitionAdminService	competitionAdminService;

	@Autowired
	protected AuthoritiesService		authoritiesService;


	@Test
	void shouldFindCompetitionAdminById() {
		CompetitionAdmin compAdmin = null;
		compAdmin = this.competitionAdminService.findCompetitionAdminById(1);

		Assertions.assertTrue(compAdmin != null);
	}

	@Test
	void shouldFindCompetitionAdminByUsername() {
		CompetitionAdmin compAdminByUsername = null;
		compAdminByUsername = this.competitionAdminService.findCompetitionAdminByUsername("pedro");

		Assertions.assertTrue(compAdminByUsername != null);
	}

	@Test
	void shouldSaveCompetitionAdmin() {

		User rafa = this.competitionAdminService.findAuthenticatedByUsername("rafa").getUser();

		CompetitionAdmin compAdmin = new CompetitionAdmin();

		compAdmin.setId(100);
		compAdmin.setFirstName("Test");
		compAdmin.setLastName("Test");
		compAdmin.setEmail("Test@gmail.com");
		compAdmin.setTelephone("548927415");
		compAdmin.setDni("49589845T");
		compAdmin.setUser(rafa);

		this.competitionAdminService.saveCompetitionAdmin(compAdmin);

		int count = this.competitionAdminService.count();

		Assertions.assertTrue(count == 2);
	}

	@Test
	void shouldCount() {
		Assertions.assertTrue(this.competitionAdminService.count() == 1);
	}

	@Test
	void shouldDeleteCompetitionAdmin() {

		CompetitionAdmin compAdmin = this.competitionAdminService.findCompetitionAdminByUsername("pedro");

		int pre_delete = this.competitionAdminService.count();
		Assertions.assertTrue(pre_delete == 1);

		this.competitionAdminService.deleteCompetitionAdmin(compAdmin);
		int post_delete = this.competitionAdminService.count();

		Assertions.assertTrue(post_delete == 0);

	}

}
