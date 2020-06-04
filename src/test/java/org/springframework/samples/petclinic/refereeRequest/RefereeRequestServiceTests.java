
package org.springframework.samples.petclinic.refereeRequest;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.RefereeRequest;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.RefereeRequestService;
import org.springframework.samples.petclinic.service.RefereeService;
import org.springframework.samples.petclinic.service.exceptions.PendingRequestException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
//@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RefereeRequestServiceTests {

	@Autowired
	protected RefereeRequestService	refereeRequestService;

	@Autowired
	protected RefereeService		refereeService;

	@Autowired
	protected AuthoritiesService	authoritiesService;


	@Test //CASO POSITIVO
	void shouldCount() {
		int count = this.refereeRequestService.count();
		Assertions.assertTrue(count == 1);
	}

	@Test //CASO POSITIVO
	void shouldFindRefereeRequestById() {
		RefereeRequest findById = null;
		findById = this.refereeRequestService.findRefereeRequestById(1);
		Assertions.assertTrue(findById != null);
	}

	@Test //CASO NEGATIVO
	void shouldNotFindRefereeRequestById() {
		RefereeRequest findById = null;
		findById = this.refereeRequestService.findRefereeRequestById(1000);
		Assertions.assertTrue(findById == null);
	}

	@Test //CASO POSITIVO
	void shouldFindRefereeRequestByUsername() {
		RefereeRequest findByUsername = null;
		findByUsername = this.refereeRequestService.findRefereeRequestByUsername("gonzalo");
		Assertions.assertTrue(findByUsername != null);
	}

	@Test //CASO NEGATIVO
	void shouldNotFindRefereeRequestByUsername() {
		RefereeRequest findByUsername = null;
		findByUsername = this.refereeRequestService.findRefereeRequestByUsername("pedroTest");
		Assertions.assertTrue(findByUsername == null);
	}

	@Test //CASO POSITIVO
	void shouldDeleteRefereeRequest() {

		//Cogemos una ya existente y la intentamos borrar
		RefereeRequest tbdeleted = this.refereeRequestService.findRefereeRequestByUsername("gonzalo");
		this.refereeRequestService.deleteRefereeRequest(tbdeleted);

		tbdeleted = this.refereeRequestService.findRefereeRequestByUsername("gonzalo");

		Assertions.assertFalse(tbdeleted != null);
	}

	@Test //CASO NEGATIVO
	void shouldNotDeleteRefereeRequest() {

		RefereeRequest tbdeleted = this.refereeRequestService.findRefereeRequestByUsername("gonzaloTest");

		Assertions.assertThrows(NullPointerException.class, () -> {
			this.refereeRequestService.deleteRefereeRequest(tbdeleted);
		});
	}

	@Test //CASO POSITIVO
	void shouldSaveRefereeRequest() {

		User user = new User();
		user.setEnabled(true);
		user.setUsername("username");
		user.setPassword("username");

		// Creamos uno nuevo manualmente
		RefereeRequest newComp = new RefereeRequest();
		newComp.setId(1000);
		newComp.setTitle("JUnit testing title");
		newComp.setDescription("JUnit testing description");
		newComp.setStatus(RequestStatus.ON_HOLD);
		newComp.setUser(user);

		//Lo guardamos y contamos cuantas peticiones de Competition Admin hay
		this.refereeRequestService.saveRefereeRequest(newComp);
		int count = this.refereeRequestService.count();

		// Debería haber 2, una que existía previamente y la nueva
		Assertions.assertTrue(count == 2);
	}

	@Test //CASO POSITIVO
	void shouldCountRefereeRequestByUsername() throws DataAccessException, PendingRequestException {
		int countByUsername = this.refereeRequestService.countRefereeRequestByUsername("gonzalo0");
		Assertions.assertTrue(countByUsername == 0);
	}

	@Test //CASO REGLA DE NEGOCIO
	void shouldExceptionCountRefereeRequestByUsername() throws DataAccessException, PendingRequestException {
		Assertions.assertThrows(PendingRequestException.class, () -> {
			this.refereeRequestService.countRefereeRequestByUsername("gonzalo");
		});
	}

	@Test //CASO NEGATIVO
	void shouldNotCountRefereeRequestByUsername() throws DataAccessException, PendingRequestException {
		Integer countByUsername = this.refereeRequestService.countRefereeRequestByUsername("gonzaloTest");
		Assertions.assertTrue(countByUsername == 0);
	}

	@Test //CASO POSITIVO
	void shouldFindRefereeRequests() {
		Collection<RefereeRequest> collection = null;
		collection = this.refereeRequestService.findRefereeRequests();
		Assertions.assertTrue(collection.size() == 1);
	}

}
