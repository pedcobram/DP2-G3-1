
package org.springframework.samples.petclinic.service;

import org.assertj.core.api.Assertions;
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
	protected CompetitionAdminService competitionAdminService;


	@Test
	void shouldFindCompetitionAdminById() {
		CompetitionAdmin compAdmin = null;
		compAdmin = this.competitionAdminService.findCompetitionAdminById(1);

		Assertions.assertThat(compAdmin != null);
	}

	@Test
	void shouldFindCompetitionAdminByUsername() {
		CompetitionAdmin compAdminByUsername = null;
		compAdminByUsername = this.competitionAdminService.findCompetitionAdminByUsername("pedro");

		Assertions.assertThat(compAdminByUsername != null);
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

		Assertions.assertThat(count == 1);
	}

	@Test
	void shouldCount() {
		Assertions.assertThat(this.competitionAdminService.count() == 0);
	}

	@Test
	void shouldDeleteCompetitionAdmin() {

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
		int pre_delete = this.competitionAdminService.count();

		Assertions.assertThat(pre_delete == 1);

		this.competitionAdminService.deleteCompetitionAdmin(compAdmin);
		int post_delete = this.competitionAdminService.count();

		Assertions.assertThat(post_delete == 0);

	}

}
