
package org.springframework.samples.petclinic.authenticated;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthenticatedService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
//@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AuthenticatedServiceTest {

	@Autowired
	protected AuthenticatedService authenticathedService;


	@Test //CASO POSITIVO
	void shouldFindAuthenticatedById() {

		Authenticated au = this.authenticathedService.findAuthenticatedById(1);

		Assertions.assertTrue(au != null);
	}
	@Test //CASO NEGATIVO
	void shouldNotFindAuthenticatedById() {

		Authenticated au = this.authenticathedService.findAuthenticatedById(100);

		Assertions.assertTrue(au == null);
	}

	@Test //CASO POSITIVO
	void shouldFindAuthenticatedByUsername() {

		Authenticated au = this.authenticathedService.findAuthenticatedByUsername("ignacio");

		Assertions.assertTrue(au != null);
	}
	@Test //CASO NEGATIVO
	void shouldNotFindAuthenticatedByUsername() {

		Authenticated au = this.authenticathedService.findAuthenticatedByUsername("");

		Assertions.assertTrue(au == null);
	}
	@Test //CASO POSITIVO
	void shouldFindAuthenticatedByLastName() {

		Collection<Authenticated> au = this.authenticathedService.findAuthenticatedByLastName("Rodríguez Flores");

		Assertions.assertFalse(au.isEmpty());
	}
	@Test //CASO NEGATIVO
	void shouldNotFindAuthenticatedByLastName() {

		Collection<Authenticated> au = this.authenticathedService.findAuthenticatedByLastName("JOrdan");

		Assertions.assertTrue(au.isEmpty());
	}

	@Test //CASO POSITIVO
	void shouldSaveAutheticated() throws DataAccessException, DuplicatedNameException {

		Authenticated au = new Authenticated();
		User us = new User();

		us.setUsername("juan1");
		us.setPassword("juan1");
		us.setEnabled(true);

		au.setUser(us);
		au.setDni("30246584T");
		au.setEmail("hola@gmail.com");
		au.setFirstName("Juan");
		au.setLastName("Muñoz");
		au.setTelephone("954789568");

		this.authenticathedService.saveAuthenticated(au);

		boolean b = this.authenticathedService.findAuthenticatedByUsername(us.getUsername()) != null;

		Assertions.assertTrue(b);
	}
	@Test //CASO NEGATIVO
	void shouldNotSaveAutheticated() {

		Authenticated au = new Authenticated();

		au.setDni("30246584T");
		au.setEmail("hola@gmail.com");
		au.setFirstName("Juan");
		au.setLastName("Muñoz");
		au.setTelephone("954789568");

		Assertions.assertThrows(NullPointerException.class, () -> {
			this.authenticathedService.saveAuthenticated(au);
		});
	}

	@Test //CASO NEGATIVO
	void shouldNotSaveAutheticatedUser() {
		Authenticated au1 = this.authenticathedService.findAuthenticatedByUsername("ignacio");
		Authenticated au = new Authenticated();
		User us = au1.getUser();

		au.setUser(us);
		au.setDni("30246584T");
		au.setEmail("hola@gmail.com");
		au.setFirstName("Ignacio José");
		au.setLastName("Muñoz");
		au.setTelephone("954789568");
		au.setId(500);

		Assertions.assertThrows(DuplicatedNameException.class, () -> {
			this.authenticathedService.saveAuthenticated(au);
		});
	}
	@Test //CASO POSITIVO
	void shouldDeleteAuthenticated() throws DataAccessException, DuplicatedNameException {

		Authenticated au = this.authenticathedService.findAuthenticatedByUsername("ignacio");
		this.authenticathedService.deleteAuthenticated(au);

		boolean b = this.authenticathedService.findAuthenticatedByUsername("ignacio") == null;
		Assertions.assertTrue(b);
	}
	@Test //CASO NEGATIVO
	void shouldNotDeleteAuthenticated() throws DataAccessException, DuplicatedNameException {

		Authenticated au = new Authenticated();

		Assertions.assertThrows(NullPointerException.class, () -> {
			this.authenticathedService.deleteAuthenticated(au);
		});
	}

}
