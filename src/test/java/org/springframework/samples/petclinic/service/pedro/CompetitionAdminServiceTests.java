
package org.springframework.samples.petclinic.service.pedro;

import javax.security.auth.login.CredentialException;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.CompetitionAdmin;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CompetitionAdminService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CompetitionAdminServiceTests {

	@Autowired
	protected CompetitionAdminService	competitionAdminService;

	@Autowired
	protected AuthoritiesService		authoritiesService;


	@Test //CASO POSITIVO
	void shouldFindCompetitionAdminById() {

		Boolean res = true;

		CompetitionAdmin compAdmin = this.competitionAdminService.findCompetitionAdminById(1);

		if (compAdmin == null) {
			res = false;
		}

		Assertions.assertTrue(res);
	}

	@Test //CASO NEGATIVO
	void shouldNotFindCompetitionAdminById() {

		Boolean res = true;

		CompetitionAdmin compAdmin = this.competitionAdminService.findCompetitionAdminById(100);

		if (compAdmin == null) {
			res = false;
		}

		Assertions.assertFalse(res);
	}

	@Test //CASO POSITIVO
	void shouldFindCompetitionAdminByUsername() {
		CompetitionAdmin compAdminByUsername = null;
		compAdminByUsername = this.competitionAdminService.findCompetitionAdminByUsername("pedro");

		Assertions.assertTrue(compAdminByUsername != null);
	}

	@Test //CASO NEGATIVO
	void shouldNotFindCompetitionAdminByUsername() {
		CompetitionAdmin compAdminByUsername = null;
		compAdminByUsername = this.competitionAdminService.findCompetitionAdminByUsername("pedroTest");

		Assertions.assertFalse(compAdminByUsername != null);
	}

	@Test //CASO POSITIVO
	void shouldSaveCompetitionAdmin() throws DataAccessException, CredentialException {

		User user = new User();

		user.setEnabled(true);
		user.setUsername("testUsername");
		user.setPassword("testUsername");

		CompetitionAdmin compAdmin = new CompetitionAdmin();

		compAdmin.setFirstName("Test");
		compAdmin.setLastName("Test");
		compAdmin.setEmail("Test@gmail.com");
		compAdmin.setTelephone("548927415");
		compAdmin.setDni("49589845T");
		compAdmin.setUser(user);

		this.competitionAdminService.saveCompetitionAdmin(compAdmin);

		Integer count = this.competitionAdminService.count();

		Assertions.assertTrue(count == 2);
	}

	@Test //CASO NEGATIVO
	void shouldNotSaveCompetitionAdmin() throws DataAccessException, CredentialException {

		User user = new User();

		user.setEnabled(true);
		user.setUsername("testUsername");
		user.setPassword("testUsername");

		CompetitionAdmin compAdmin = new CompetitionAdmin();

		compAdmin.setFirstName("");
		compAdmin.setLastName("Test");
		compAdmin.setEmail("Test@gmail.com");
		compAdmin.setTelephone("548927415");
		compAdmin.setDni("49589845T");
		compAdmin.setUser(user);

		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			this.competitionAdminService.saveCompetitionAdmin(compAdmin);
		});
	}

	@Test //CASO POSITIVO
	void shouldCount() {
		Assertions.assertTrue(this.competitionAdminService.count() == 1);
	}

	//	@Test //CASO POSITIVO
	//	void shouldDeleteCompetitionAdmin() throws DataAccessException, DuplicatedNameException {
	//
	//		CompetitionAdmin compAdmin = this.competitionAdminService.findCompetitionAdminByUsername("pedro");
	//
	//		int pre_delete = this.competitionAdminService.count();
	//		Assertions.assertTrue(pre_delete == 1);
	//
	//		this.competitionAdminService.deleteCompetitionAdmin(compAdmin);
	//
	//		int post_delete = this.competitionAdminService.count();
	//		Assertions.assertTrue(post_delete == 0);
	//	}

	@Test //CASO NEGATIVO
	void shouldNotDeleteCompetitionAdmin() {

		CompetitionAdmin compAdmin = this.competitionAdminService.findCompetitionAdminByUsername("pedroTest");

		Assertions.assertThrows(NullPointerException.class, () -> {
			this.competitionAdminService.deleteCompetitionAdmin(compAdmin);
		});
	}

}
