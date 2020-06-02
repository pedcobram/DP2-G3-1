
package org.springframework.samples.petclinic.service.pedro;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.MatchDateChangeRequest;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.samples.petclinic.service.MatchDateChangeRequestService;
import org.springframework.samples.petclinic.service.MatchService;
import org.springframework.samples.petclinic.service.exceptions.AlreadyOneRequestOpenException;
import org.springframework.samples.petclinic.service.exceptions.IllegalDateException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MatchDateChangeRequestServiceTests {

	@Autowired
	protected MatchDateChangeRequestService	matchDateChangeRequestService;

	@Autowired
	protected MatchService					matchService;


	@Test //CASO POSITIVO
	void shouldFindMatchDateChangeRequestById() {

		Boolean res = true;

		MatchDateChangeRequest mdcr = this.matchDateChangeRequestService.findMatchDateChangeRequestById(2);

		if (mdcr == null) {
			res = false;
		}

		Assertions.assertTrue(res);
	}

	@Test //CASO NEGATIVO
	void shouldNotFindMatchDateChangeRequestById() {

		Boolean res = true;

		MatchDateChangeRequest mdcr = this.matchDateChangeRequestService.findMatchDateChangeRequestById(-100);

		if (mdcr == null) {
			res = false;
		}

		Assertions.assertFalse(res);
	}

	@Test //CASO POSITIVO
	void shouldFindAllMatchDateChangeRequests() {

		Collection<MatchDateChangeRequest> mdcrs = new ArrayList<>();

		mdcrs.addAll(this.matchDateChangeRequestService.findAllMatchDateChangeRequests("presidente2"));

		int count = mdcrs.size();

		Assertions.assertTrue(count == 1);
	}

	@Test //CASO NEGATIVO
	void shouldNotFindAllMatchDateChangeRequests() {

		Collection<MatchDateChangeRequest> mdcrs = new ArrayList<>();

		mdcrs.addAll(this.matchDateChangeRequestService.findAllMatchDateChangeRequests("presidente100"));

		int count = mdcrs.size();

		Assertions.assertTrue(count == 0);
	}

	@WithMockUser(username = "presidente1", roles = "president")
	@Test //CASO POSITIVO
	void shouldSaveMatchDateChangeRequest() throws ParseException, IllegalDateException, AlreadyOneRequestOpenException {

		MatchDateChangeRequest mdcr = new MatchDateChangeRequest();

		Match m = this.matchService.findMatchById(2);

		Calendar d = Calendar.getInstance();
		d.set(2025, 02, 02, 20, 20);
		Date date = d.getTime();

		mdcr.setTitle("title");
		mdcr.setNew_date(date);
		mdcr.setStatus(RequestStatus.ON_HOLD);
		mdcr.setReason("reason");
		mdcr.setRequest_creator("presidente1");
		mdcr.setMatch(m);

		this.matchDateChangeRequestService.saveMatchDateChangeRequest(mdcr);

	}

	@WithMockUser(username = "presidente1", roles = "president")
	@Test //CASO POSITIVO
	void shouldNotSaveMatchDateChangeRequestRN1() throws ParseException, IllegalDateException, AlreadyOneRequestOpenException {

		MatchDateChangeRequest mdcr = new MatchDateChangeRequest();

		Match m = this.matchService.findMatchById(2);

		Calendar d = Calendar.getInstance();
		d.set(2015, 02, 02, 20, 20);
		Date date = d.getTime();

		mdcr.setTitle("title");
		mdcr.setNew_date(date);
		mdcr.setStatus(RequestStatus.ON_HOLD);
		mdcr.setReason("reason");
		mdcr.setRequest_creator("presidente1");
		mdcr.setMatch(m);

		Assertions.assertThrows(IllegalDateException.class, () -> {
			this.matchDateChangeRequestService.saveMatchDateChangeRequest(mdcr);
		});

	}

	//	@WithMockUser(username = "presidente1", roles = "president")
	//	@Test //CASO POSITIVO
	//	void shouldNotSaveMatchDateChangeRequestRN2() throws ParseException, IllegalDateException, AlreadyOneRequestOpenException {
	//
	//		MatchDateChangeRequest mdcr = new MatchDateChangeRequest();
	//
	//		Match m = this.matchService.findMatchById(1);
	//
	//		Calendar d = Calendar.getInstance();
	//		d.set(2025, 02, 02, 20, 20);
	//		Date date = d.getTime();
	//
	//		mdcr.setTitle("title");
	//		mdcr.setNew_date(date);
	//		mdcr.setStatus(RequestStatus.ON_HOLD);
	//		mdcr.setReason("reason");
	//		mdcr.setRequest_creator("presidente1");
	//		mdcr.setMatch(m);
	//
	//		Assertions.assertThrows(AlreadyOneRequestOpenException.class, () -> {
	//			this.matchDateChangeRequestService.saveMatchDateChangeRequest(mdcr);
	//		});
	//
	//	}

}
