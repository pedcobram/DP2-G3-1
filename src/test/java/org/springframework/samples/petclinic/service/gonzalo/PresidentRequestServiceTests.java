
package org.springframework.samples.petclinic.service.gonzalo;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.PresidentRequest;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.PresidentRequestService;
import org.springframework.samples.petclinic.service.PresidentService;
import org.springframework.samples.petclinic.service.exceptions.PendingRequestException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
//@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PresidentRequestServiceTests {

	@Autowired
	protected PresidentRequestService	presidentRequestService;

	@Autowired
	protected PresidentService			presidentService;

	@Autowired
	protected AuthoritiesService		authoritiesService;


	@Test //CASO POSITIVO
	void shouldCount() {
		int count = this.presidentRequestService.count();
		Assertions.assertTrue(count == 1);
	}

	@Test //CASO POSITIVO
	void shouldFindPresidentRequestById() {
		PresidentRequest findById = null;
		findById = this.presidentRequestService.findPresidentRequestById(1);
		Assertions.assertTrue(findById != null);
	}

	@Test //CASO NEGATIVO
	void shouldNotFindPresidentRequestById() {
		PresidentRequest findById = null;
		findById = this.presidentRequestService.findPresidentRequestById(1000);
		Assertions.assertTrue(findById == null);
	}

	@Test //CASO POSITIVO
	void shouldFindPresidentRequestByUsername() {
		PresidentRequest findByUsername = null;
		findByUsername = this.presidentRequestService.findPresidentRequestByUsername("gonzalo");
		Assertions.assertTrue(findByUsername != null);
	}

	@Test //CASO NEGATIVO
	void shouldNotFindPresidentRequestByUsername() {
		PresidentRequest findByUsername = null;
		findByUsername = this.presidentRequestService.findPresidentRequestByUsername("pedroTest");
		Assertions.assertTrue(findByUsername == null);
	}

	@Test //CASO POSITIVO
	void shouldDeletePresidentRequest() {

		//Cogemos una ya existente y la intentamos borrar
		PresidentRequest tbdeleted = this.presidentRequestService.findPresidentRequestByUsername("gonzalo");
		this.presidentRequestService.deletePresidentRequest(tbdeleted);

		tbdeleted = this.presidentRequestService.findPresidentRequestByUsername("gonzalo");

		Assertions.assertFalse(tbdeleted != null);
	}

	@Test //CASO NEGATIVO
	void shouldNotDeletePresidentRequest() {

		PresidentRequest tbdeleted = this.presidentRequestService.findPresidentRequestByUsername("gonzaloTest");

		Assertions.assertThrows(NullPointerException.class, () -> {
			this.presidentRequestService.deletePresidentRequest(tbdeleted);
		});
	}

	@Test //CASO POSITIVO
	void shouldSavePresidentRequest() {

		User user = new User();
		user.setEnabled(true);
		user.setUsername("username");
		user.setPassword("username");

		// Creamos uno nuevo manualmente
		PresidentRequest newComp = new PresidentRequest();
		newComp.setId(1000);
		newComp.setTitle("JUnit testing title");
		newComp.setDescription("JUnit testing description");
		newComp.setStatus(RequestStatus.ON_HOLD);
		newComp.setUser(user);

		//Lo guardamos y contamos cuantas peticiones de Competition Admin hay
		this.presidentRequestService.savePresidentRequest(newComp);
		int count = this.presidentRequestService.count();

		// Debería haber 2, una que existía previamente y la nueva
		Assertions.assertTrue(count == 2);
	}

	@Test //CASO POSITIVO
	void shouldCountPresidentRequestByUsername() throws DataAccessException, PendingRequestException {
		int countByUsername = this.presidentRequestService.countPresidentRequestByUsername("gonzalo0");
		Assertions.assertTrue(countByUsername == 0);
	}

	@Test //CASO REGLA DE NEGOCIO
	void shouldExceptionCountPresidentRequestByUsername() throws DataAccessException, PendingRequestException {
		Assertions.assertThrows(PendingRequestException.class, () -> {
			this.presidentRequestService.countPresidentRequestByUsername("gonzalo");
		});
	}

	@Test //CASO NEGATIVO
	void shouldNotCountPresidentRequestByUsername() throws DataAccessException, PendingRequestException {
		Integer countByUsername = this.presidentRequestService.countPresidentRequestByUsername("gonzaloTest");
		Assertions.assertTrue(countByUsername == 0);
	}

	@Test //CASO POSITIVO
	void shouldFindPresidentRequests() {
		Collection<PresidentRequest> collection = null;
		collection = this.presidentRequestService.findPresidentRequests();
		Assertions.assertTrue(collection.size() == 1);
	}

}
