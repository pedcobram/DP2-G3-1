
package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PresidentServiceTests {

	@Autowired
	protected AuthenticatedService	authenticatedService;

	@Autowired
	protected PresidentService		presidentService;

	@Autowired
	protected AuthoritiesService	authoritiesService;


	@Test
	void shouldFindPresidentById() {
		President president = null;
		president = this.presidentService.findPresidentById(1); //Caso Positivo
		Assertions.assertTrue(president != null);
		president = this.presidentService.findPresidentById(99); //Caso Negativo
		Assertions.assertFalse(president != null);
	}

	@Test
	void shouldFindPresidentByUsername() {
		President president = null;
		president = this.presidentService.findPresidentByUsername("rafa"); //Caso Positivo
		Assertions.assertTrue(president != null);
		president = this.presidentService.findPresidentByUsername("pedro"); //Caso Negativo
		Assertions.assertFalse(president != null);
	}

	@Test
	void shouldSavePresident() {

		User ignacio = this.authenticatedService.findAuthenticatedByUsername("ignacio").getUser();

		President newPresident = this.presidentService.findPresidentByUsername("ignacio");

		Assertions.assertFalse(newPresident != null);  //Caso Negativo

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

		Assertions.assertTrue(newPresident != null); //Caso Positivo
	}

	@Test
	void shouldDeletePresident() {

		President president = this.presidentService.findPresidentByUsername("rafa");

		Assertions.assertTrue(president != null); //Caso Positivo

		this.presidentService.deletePresident(president);

		president = this.presidentService.findPresidentByUsername("rafa");

		Assertions.assertFalse(president != null); //Caso Negativo

	}

	@Test
	void shouldUpdatePresident() {
		President president = this.presidentService.findPresidentById(1);

		String oldLastName = president.getLastName();
		String newLastName = oldLastName + "X";

		president.setLastName(newLastName);
		this.presidentService.savePresident(president);

		// retrieving new name from database
		president = this.presidentService.findPresidentById(1);
		Assertions.assertTrue(president.getLastName().equals(newLastName));
	}

}
