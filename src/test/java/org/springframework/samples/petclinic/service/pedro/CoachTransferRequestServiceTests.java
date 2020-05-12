
package org.springframework.samples.petclinic.service.pedro;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.CredentialException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.CoachTransferRequest;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.samples.petclinic.service.CoachTransferRequestService;
import org.springframework.samples.petclinic.service.exceptions.AlreadyOneRequestOpenException;
import org.springframework.samples.petclinic.service.exceptions.CoachTransferRequestExistsException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CoachTransferRequestServiceTests {

	@Autowired
	private CoachTransferRequestService coachTransferRequestService;


	@Test // CASO POSITIVO
	void shouldFindAllCoachTransferRequest() {
		Boolean res = false;

		List<CoachTransferRequest> ctrs = new ArrayList<>();

		ctrs.addAll(this.coachTransferRequestService.findAllCoachTransferRequest());

		if (ctrs.size() == 1) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO POSITIVO
	void shouldFindAllReceivedCoachTransferRequestByMyCoachId() {
		Boolean res = false;

		List<CoachTransferRequest> ptrs = new ArrayList<>();

		ptrs.addAll(this.coachTransferRequestService.findAllReceivedCoachTransferRequestByMyCoachId(2));

		if (ptrs.size() == 1) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO NEGATIVO
	void shouldNotFindAllReceivedCoachTransferRequestByMyCoachId() {
		Boolean res = false;

		List<CoachTransferRequest> ptrs = new ArrayList<>();

		ptrs.addAll(this.coachTransferRequestService.findAllReceivedCoachTransferRequestByMyCoachId(1));

		if (ptrs.size() == 0) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO POSITIVO
	void shouldFindAllCoachTransferRequestbyPresident() {
		Boolean res = false;

		List<CoachTransferRequest> ptrs = new ArrayList<>();

		ptrs.addAll(this.coachTransferRequestService.findAllCoachTransferRequestbyPresident("presidente2"));

		if (ptrs.size() == 1) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO NEGATIVO
	void shouldNotFindAllCoachTransferRequestbyPresident() {
		Boolean res = false;

		List<CoachTransferRequest> ptrs = new ArrayList<>();

		ptrs.addAll(this.coachTransferRequestService.findAllCoachTransferRequestbyPresident("NotAPresident"));

		if (ptrs.size() == 0) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO POSITIVO
	void shouldFindAllCoachTransferRequestByMyCoachId() {
		Boolean res = false;

		List<CoachTransferRequest> ptrs = new ArrayList<>();

		ptrs.addAll(this.coachTransferRequestService.findAllCoachTransferRequestByMyCoachId(1));

		if (ptrs.size() == 0) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO NEGATIVO
	void shouldNotFindAllCoachTransferRequestByMyCoachId() {
		Boolean res = false;

		List<CoachTransferRequest> ptrs = new ArrayList<>();

		ptrs.addAll(this.coachTransferRequestService.findAllCoachTransferRequestByMyCoachId(-100));

		if (ptrs.size() == 0) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO POSITIVO
	void shouldFindPlayerTransferRequestByPlayerId() {
		Boolean res = false;

		List<CoachTransferRequest> ptrs = new ArrayList<>();

		ptrs.addAll(this.coachTransferRequestService.findAllCoachTransferRequestByRequestedCoachId(7));

		if (ptrs.size() == 1) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO NEGATIVO
	void shouldNotFindPlayerTransferRequestByPlayerId() {
		Boolean res = false;

		List<CoachTransferRequest> ptrs = new ArrayList<>();

		ptrs.addAll(this.coachTransferRequestService.findAllCoachTransferRequestByRequestedCoachId(-100));

		if (ptrs.size() == 0) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	//

	@Test // CASO POSITIVO
	void shouldFindCoachTransferRequestById() {
		Boolean res = false;

		CoachTransferRequest ptr = this.coachTransferRequestService.findCoachTransferRequestById(0);

		if (ptr != null) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO NEGATIVO
	void shouldNotFindCoachTransferRequestById() {
		Boolean res = false;

		CoachTransferRequest ptr = this.coachTransferRequestService.findCoachTransferRequestById(-100);

		if (ptr == null) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO POSITIVO
	void shouldFindCoachTransferRequestByRequestedCoachIdAndMyCoachId() {
		Boolean res = false;

		CoachTransferRequest ptr = this.coachTransferRequestService.findCoachTransferRequestByRequestedCoachIdAndMyCoachId(1, 2);

		if (ptr == null) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO NEGATIVO
	void shouldNotFindCoachTransferRequestByRequestedCoachIdAndMyCoachId() {
		Boolean res = false;

		CoachTransferRequest ptr = this.coachTransferRequestService.findCoachTransferRequestByRequestedCoachIdAndMyCoachId(-100, -100);

		if (ptr == null) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO POSITIVO
	void shouldFindCoachTransferRequestByMyCoachId() {
		Boolean res = false;

		CoachTransferRequest ptr = this.coachTransferRequestService.findCoachTransferRequestByMyCoachId(2);

		if (ptr != null) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO NEGATIVO
	void shouldNotFindCoachTransferRequestByMyCoachId() {
		Boolean res = false;

		CoachTransferRequest ptr = this.coachTransferRequestService.findCoachTransferRequestByMyCoachId(-100);

		if (ptr != null) {
			res = true;
		}

		Assertions.assertTrue(!res);
	}

	@Test // CASO POSITIVO
	void shouldSaveCoachTransferRequest() throws DataAccessException, CredentialException, CoachTransferRequestExistsException, AlreadyOneRequestOpenException {

		CoachTransferRequest ptr = this.coachTransferRequestService.findCoachTransferRequestById(0);

		ptr.setId(ptr.getId());
		ptr.setStatus(RequestStatus.ACCEPT);

		this.coachTransferRequestService.saveCoachTransferRequest(ptr);

		List<CoachTransferRequest> ptrs = new ArrayList<>();

		ptrs.addAll(this.coachTransferRequestService.findAllCoachTransferRequest());

		if (ptrs.get(0).getStatus() == RequestStatus.ACCEPT) {
			Assertions.assertTrue(true);
		} else {
			Assertions.assertTrue(false);
		}
	}

	@Test // CASO NEGATIVO
	void shouldNotSaveCoachTransferRequest() throws DataAccessException, CredentialException, CoachTransferRequestExistsException {

		CoachTransferRequest ptr = this.coachTransferRequestService.findCoachTransferRequestById(0);

		CoachTransferRequest ptr_mod = new CoachTransferRequest();

		ptr_mod.setId(100);
		ptr_mod.setMyCoach(ptr.getMyCoach());
		ptr_mod.setOffer(ptr.getOffer());
		ptr_mod.setRequestedCoach(ptr.getRequestedCoach());
		ptr_mod.setStatus(ptr.getStatus());

		Assertions.assertThrows(AlreadyOneRequestOpenException.class, () -> {
			this.coachTransferRequestService.saveCoachTransferRequest(ptr_mod);
		});
	}

	@Test // CASO POSITIVO
	void shouldDeleteCoachTransferRequest() {
		CoachTransferRequest ptr = this.coachTransferRequestService.findCoachTransferRequestById(0);

		this.coachTransferRequestService.deleteCoachTransferRequest(ptr);

		List<CoachTransferRequest> ptrs = new ArrayList<>();

		ptrs.addAll(this.coachTransferRequestService.findAllCoachTransferRequest());

		if (ptrs.isEmpty()) {
			Assertions.assertTrue(true);
		} else {
			Assertions.assertTrue(false);
		}

	}

}
