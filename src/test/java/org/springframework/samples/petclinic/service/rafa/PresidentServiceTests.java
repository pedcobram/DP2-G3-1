
package org.springframework.samples.petclinic.service.rafa;

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
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthenticatedService;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.PresidentService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PresidentServiceTests {

	@Autowired
	protected AuthenticatedService	authenticatedService;

	@Autowired
	protected PresidentService		presidentService;

	@Autowired
	protected AuthoritiesService	authoritiesService;


	@Test //CASO POSITIVO - Buscar por Id
	void shouldFindPresidentById() {
		President president = null;
		president = this.presidentService.findPresidentById(1);
		Assertions.assertTrue(president != null);
	}

	@Test //CASO NEGATIVO - Buscar por Id
	void shouldNotFindPresidentById() {
		President president = null;
		president = this.presidentService.findPresidentById(99);
		Assertions.assertFalse(president != null);
	}

	@Test //CASO POSITIVO - Buscar por Username
	void shouldFindPresidentByUsername() {
		President president = null;
		president = this.presidentService.findPresidentByUsername("rafa");
		Assertions.assertTrue(president != null);
	}

	@Test //CASO NEGATIVO - Buscar por Username
	void shouldNotFindPresidentByUsername() {
		President president = null;
		president = this.presidentService.findPresidentByUsername("pedro");
		Assertions.assertFalse(president != null);
	}

	@Test //CASO POSITIVO - Guardar Presidente y generar Id
	void shouldSavePresidentAndGenerateId() throws DataAccessException, CredentialException {

		User ignacio = this.authenticatedService.findAuthenticatedByUsername("ignacio").getUser();

		President newPresident = this.presidentService.findPresidentByUsername("ignacio");

		Assertions.assertFalse(newPresident != null);

		President president = new President();
		president.setId(999);
		president.setFirstName("First Name");
		president.setLastName("Last Name");
		president.setEmail("ignacio@gmail.com");
		president.setTelephone("655766899");
		president.setDni("12345678A");
		president.setUser(ignacio);

		this.presidentService.savePresident(president);

		newPresident = this.presidentService.findPresidentByUsername("ignacio");

		Assertions.assertTrue(newPresident != null);
		Assertions.assertTrue(newPresident.getId() != null);
	}

	@Test //CASO NEGATIVO - Guardar Presidente si dejamos un campo vacío
	void shouldThrowExceptionIfNameEmpty() throws DataAccessException, CredentialException {

		User ignacio = this.authenticatedService.findAuthenticatedByUsername("ignacio").getUser();

		President president = new President();
		president.setId(999);
		president.setFirstName(""); //Dejamos el nombre en blanco para que falle
		president.setLastName("Last Name");
		president.setEmail("ignacio@gmail.com");
		president.setTelephone("655766899");
		president.setDni("12345678A");
		president.setUser(ignacio);

		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			this.presidentService.savePresident(president);
		});

	}

	@Test //CASO POSITIVO - Borrar Presidente
	void shouldDeletePresident() throws DataAccessException, CredentialException, DuplicatedNameException {

		President president = this.presidentService.findPresidentByUsername("rafa");

		Assertions.assertTrue(president != null);

		this.presidentService.deletePresident(president);

		president = this.presidentService.findPresidentByUsername("rafa");

		Assertions.assertFalse(president != null);
	}

	@Test //CASO NEGATIVO - Borrar Presidente si no existe
	void shouldThrowExceptionIfNotExists() throws DataAccessException, CredentialException {

		President president = this.presidentService.findPresidentByUsername("ignacio");

		Assertions.assertThrows(NullPointerException.class, () -> {
			this.presidentService.deletePresident(president);
		});

	}

	@Test //CASO POSITIVO - Actualizar Presidente
	void shouldUpdatePresident() throws DataAccessException, CredentialException {
		President president = this.presidentService.findPresidentById(1);

		String oldLastName = president.getLastName();
		String newLastName = oldLastName + "X";

		president.setLastName(newLastName);
		this.presidentService.savePresident(president);

		// retrieving new name from database
		president = this.presidentService.findPresidentById(1);
		Assertions.assertTrue(president.getLastName().equals(newLastName));
	}

	@Test //CASO NEGATIVO - Actualizar Presidente sin user asignado
	void shouldNotThrowExceptionWithUserNull() throws DataAccessException, CredentialException {
		President president = this.presidentService.findPresidentById(1);

		president.setUser(null);

		Assertions.assertThrows(NullPointerException.class, () -> {
			this.presidentService.savePresident(president);
		});

	}

}
