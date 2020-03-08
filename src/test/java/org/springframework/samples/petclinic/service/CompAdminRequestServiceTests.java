
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.CompAdminRequest;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Enum.CompAdminRequestStatus;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CompAdminRequestServiceTests {

	@Autowired
	protected CompAdminRequestService	compAdminRequestService;

	@Autowired
	protected CompetitionAdminService	competitionAdminService;


	@Test
	void shouldCount() {
		// Contamos cuantas peticiones hay en BD, debe ser solo una
		int count = this.compAdminRequestService.count();
		Assertions.assertThat(count == 1);
	}

	@Test
	void shouldFindCompAdminRequestById() {
		// Creamos un CompAdminRequest null y
		// llamamos al repo para ver si existe uno como ese id, si no devolvería null
		CompAdminRequest findById = null;
		findById = this.compAdminRequestService.findCompAdminRequestById(1);
		Assertions.assertThat(findById != null);

	}

	@Test
	void shouldFindCompAdminRequestByUsername() {
		// Creamos un CompAdminRequest null y
		// llamamos al repo para ver si existe uno como ese nombre, si no devolvería null
		CompAdminRequest findByUsername = null;
		findByUsername = this.compAdminRequestService.findCompAdminRequestByUsername("pedro");
		Assertions.assertThat(findByUsername != null);

	}

	@Test
	void shouldDeleteCompAdminRequest() {

		//Cogemos una ya existente y la intentamos borrar
		CompAdminRequest tbdeleted = this.compAdminRequestService.findCompAdminRequestById(1);
		tbdeleted.setUser(null); //Si no da error
		this.compAdminRequestService.deleteCompAdminRequest(tbdeleted);

		// Contamos las peticiones existentes, que deben ser 0 una vez eliminada
		int count = this.compAdminRequestService.count();

		Assertions.assertThat(count == 0);
	}

	@Test
	void shouldSaveCompAdminRequest() {

		// Cogemos un usuario que no sea Competition Admin
		User rafa = this.competitionAdminService.findAuthenticatedByUsername("rafa").getUser();

		// Creamos uno nuevo manualmente
		CompAdminRequest newComp = new CompAdminRequest();
		newComp.setId(1000);
		newComp.setTitle("JUnit testing title");
		newComp.setDescription("JUnit testing description");
		newComp.setStatus(CompAdminRequestStatus.ON_HOLD);
		newComp.setUser(rafa);

		//Lo guardamos y contamos cuantas peticiones de Competition Admin hay
		this.compAdminRequestService.saveCompAdminRequest(newComp);
		int count = this.compAdminRequestService.count();

		// Debería haber 2, una que existía previamente y la nueva
		Assertions.assertThat(count == 2);
	}

	@Test
	void shouldCountCompAdminRequestByUsername() {

		int countByUsername = this.compAdminRequestService.countCompAdminRequestByUsername("pedro");
		Assertions.assertThat(countByUsername == 1);
	}

	@Test
	void shouldFindCompAdminRequests() {
		Collection<CompAdminRequest> collection = null;
		collection = this.compAdminRequestService.findCompAdminRequests();
		Assertions.assertThat(collection.size() == 1);
	}

}
