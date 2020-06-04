
package org.springframework.samples.petclinic.playerTransferRequest;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.PlayerTransferRequest;
import org.springframework.samples.petclinic.service.PlayerTransferRequestService;
import org.springframework.samples.petclinic.service.exceptions.MoneyClubException;
import org.springframework.samples.petclinic.service.exceptions.SalaryException;
import org.springframework.samples.petclinic.service.exceptions.TooManyPlayerRequestsException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
//@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PlayerTransferRequestServiceTests {

	@Autowired
	private PlayerTransferRequestService playerTransferRequestService;


	@Test // CASO POSITIVO
	void shouldFindPlayerTransferRequest() {
		Boolean res = false;

		List<PlayerTransferRequest> ptrs = new ArrayList<>();

		ptrs.addAll(this.playerTransferRequestService.findPlayerTransferRequest());

		if (ptrs.size() == 1) {
			res = true;
		}

		Assertions.assertFalse(res);
	}

	@Test // CASO POSITIVO
	void shouldFindPlayerTransferRequestByPresident() {
		Boolean res = false;

		List<PlayerTransferRequest> ptrs = new ArrayList<>();

		ptrs.addAll(this.playerTransferRequestService.findPlayerTransferRequestByPresident("presidente2"));

		if (ptrs.size() == 1) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO NEGATIVO
	void shouldNotFindPlayerTransferRequestByPresident() {
		Boolean res = false;

		List<PlayerTransferRequest> ptrs = new ArrayList<>();

		ptrs.addAll(this.playerTransferRequestService.findPlayerTransferRequestByPresident("NotAPresident"));

		if (ptrs.size() == 0) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO POSITIVO
	void shouldFindPlayerTransferRequestsReceived() {
		Boolean res = false;

		List<PlayerTransferRequest> ptrs = new ArrayList<>();

		ptrs.addAll(this.playerTransferRequestService.findPlayerTransferRequestsReceived(1));

		if (ptrs.size() == 1) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO NEGATIVO
	void shouldNotFindPlayerTransferRequestsReceived() {
		Boolean res = false;

		List<PlayerTransferRequest> ptrs = new ArrayList<>();

		ptrs.addAll(this.playerTransferRequestService.findPlayerTransferRequestsReceived(-100));

		if (ptrs.size() == 0) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO POSITIVO
	void shouldFindPlayerTransferRequestById() {
		Boolean res = false;

		PlayerTransferRequest ptr = this.playerTransferRequestService.findPlayerTransferRequestById(1);

		if (ptr != null) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO NEGATIVO
	void shouldNotFindPlayerTransferRequestById() {
		Boolean res = false;

		PlayerTransferRequest ptr = this.playerTransferRequestService.findPlayerTransferRequestById(-100);

		if (ptr == null) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO POSITIVO
	void shouldFindPlayerTransferRequestByPlayerId() {
		Boolean res = false;

		PlayerTransferRequest ptr = this.playerTransferRequestService.findPlayerTransferRequestByPlayerId(1);

		if (ptr != null) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO NEGATIVO
	void shouldNotFindPlayerTransferRequestByPlayerId() {
		Boolean res = false;

		PlayerTransferRequest ptr = this.playerTransferRequestService.findPlayerTransferRequestByPlayerId(-100);

		if (ptr == null) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO POSITIVO
	void shouldFindOnlyByPlayerId() {
		Boolean res = false;

		PlayerTransferRequest ptr = this.playerTransferRequestService.findOnlyByPlayerId(1);

		if (ptr != null) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO NEGATIVO
	void shouldNotFindOnlyByPlayerId() {
		Boolean res = false;

		PlayerTransferRequest ptr = this.playerTransferRequestService.findOnlyByPlayerId(-100);

		if (ptr == null) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO POSITIVO
	void shouldFindPlayerTransferRequestByPlayerIdAndStatusAccepted() {
		Boolean res = false;

		PlayerTransferRequest ptr = this.playerTransferRequestService.findPlayerTransferRequestByPlayerIdAndStatusAccepted(-100);

		if (ptr == null) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO NEGATIVO
	void shouldNotFindPlayerTransferRequestByPlayerIdAndStatusAccepted() {
		Boolean res = false;

		PlayerTransferRequest ptr = this.playerTransferRequestService.findPlayerTransferRequestByPlayerIdAndStatusAccepted(-100);

		if (ptr == null) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO POSITIVO
	void shouldCountPlayerTransferRequestsByPresidentAndPlayer() {
		Boolean res = false;

		Integer ptr = this.playerTransferRequestService.countPlayerTransferRequestsByPresidentAndPlayer("presidente2", 1);

		if (ptr == 1) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO NEGATIVO
	void shouldNotCountPlayerTransferRequestsByPresidentAndPlayer() {
		Boolean res = false;

		Integer ptr = this.playerTransferRequestService.countPlayerTransferRequestsByPresidentAndPlayer("NotAPresident", -100);

		if (ptr == 0) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test // CASO POSITIVO
	void shouldSavePlayerTransferRequest() throws DataAccessException, MoneyClubException, TooManyPlayerRequestsException, SalaryException {
		PlayerTransferRequest ptr = this.playerTransferRequestService.findPlayerTransferRequestById(1);

		ptr.setContractTime(2);

		this.playerTransferRequestService.updatePlayerTransferRequest(ptr);

		PlayerTransferRequest ptr_updated = this.playerTransferRequestService.findPlayerTransferRequestById(1);

		if (ptr_updated.getContractTime() == 2) {
			Assertions.assertTrue(true);
		} else {
			Assertions.assertTrue(false);
		}
	}

	@Test // CASO POSITIVO
	void shouldDeletePlayerTransferRequest() {
		PlayerTransferRequest ptr = this.playerTransferRequestService.findPlayerTransferRequestById(1);

		this.playerTransferRequestService.deletePlayerTransferRequest(ptr);

		PlayerTransferRequest ptr_deleted = this.playerTransferRequestService.findPlayerTransferRequestById(1);

		if (ptr_deleted == null) {
			Assertions.assertTrue(true);
		} else {
			Assertions.assertTrue(false);
		}

	}

}
