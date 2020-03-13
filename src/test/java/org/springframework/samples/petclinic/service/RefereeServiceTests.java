
package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.MatchRefereeRequest;
import org.springframework.samples.petclinic.model.MatchRefereeRequests;
import org.springframework.samples.petclinic.model.Referee;
import org.springframework.samples.petclinic.model.Referees;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class RefereeServiceTests {

	@Autowired
	protected RefereeService				refereeService;

	@Autowired
	protected MatchRefereeRequestService	matchRefereeRequestService;

	@Autowired
	protected AuthenticatedService			authenticatedService;


	@Test
	void shouldFindRefereetById() {

		Boolean res = true;

		Referee r = this.refereeService.findRefereeById(1);

		if (r == null) {
			res = false;
		}

		Assertions.assertTrue(res == true);

	}

	@Test
	void shouldFindRefereeByUsername() {

		Boolean res = true;

		Referee r = this.refereeService.findRefereeByUsername("referee1");

		if (r == null) {
			res = false;
		}

		Assertions.assertTrue(res == true);

	}

	@Test
	void shouldFindAllReferees() {

		Boolean res = true;

		Referees rs = new Referees();

		rs.getRefereesList().addAll(this.refereeService.findAllReferees());

		for (Referee r : rs.getRefereesList()) {
			if (r == null) {
				res = false;
				break;
			}
		}

		Assertions.assertTrue(res == true);

	}

	@Test
	void shouldDeleteReferee() {

		Referee ref = this.refereeService.findRefereeByUsername("referee1");

		MatchRefereeRequests mrrs = new MatchRefereeRequests();

		mrrs.getMatchRefereeRequestList().addAll(this.matchRefereeRequestService.findOnHoldMatchRefereeRequests("referee1"));

		for (MatchRefereeRequest mrr : mrrs.getMatchRefereeRequestList()) {
			mrr.setReferee(null);
		}

		int pre_delete = this.refereeService.count();
		Assertions.assertTrue(pre_delete == 2);

		this.refereeService.deleteReferee(ref);

		int post_delete = this.refereeService.count();
		Assertions.assertTrue(post_delete == 1);

	}

	@Test
	void shouldSaveReferee() {

		User user = this.authenticatedService.findAuthenticatedByUsername("rafa").getUser();

		Referee r = new Referee();

		r.setId(100);
		r.setFirstName("Test");
		r.setLastName("Test");
		r.setEmail("Test@gmail.com");
		r.setDni("49032196Z");
		r.setTelephone("693968582");
		r.setUser(user);

		this.refereeService.saveReferee(r);

		int count = this.refereeService.count();

		Assertions.assertTrue(count == 3);

	}

}
