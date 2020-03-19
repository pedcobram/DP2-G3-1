
package org.springframework.samples.petclinic.service.pedro;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.MatchRefereeRequest;
import org.springframework.samples.petclinic.model.Referee;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.samples.petclinic.service.MatchRefereeRequestService;
import org.springframework.samples.petclinic.service.MatchService;
import org.springframework.samples.petclinic.service.RefereeService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class MatchRefereeRequestServiceTests {

	@Autowired
	protected MatchRefereeRequestService	matchRefereeRequestService;

	@Autowired
	protected MatchService					matchService;

	@Autowired
	protected RefereeService				refereeService;


	@Test
	void shouldCount() {
		int count = this.matchRefereeRequestService.count();
		Assert.assertTrue(count == 2);
	}

	@Test
	void shouldFindAllOnHoldMatchRefereeRequests() {

		List<MatchRefereeRequest> mrrs = new ArrayList<>();
		Boolean res = true;

		mrrs.addAll(this.matchRefereeRequestService.findAllOnHoldMatchRefereeRequests());

		int count = mrrs.size();

		for (MatchRefereeRequest mrr : mrrs) {
			if (mrr.getStatus() != RequestStatus.ON_HOLD) {
				res = false;
				break;
			}
		}

		Assertions.assertTrue(count == 2);
		Assertions.assertTrue(res == true);
	}

	@Test
	void shouldFindOnHoldMatchRefereeRequests() {

		List<MatchRefereeRequest> mrrs = new ArrayList<>();
		Boolean res = true;

		mrrs.addAll(this.matchRefereeRequestService.findOnHoldMatchRefereeRequests("referee1"));

		int count = mrrs.size();

		for (MatchRefereeRequest mrr : mrrs) {
			if (mrr.getStatus() != RequestStatus.ON_HOLD) {
				res = false;
				break;
			}
		}

		Assertions.assertTrue(count == 1);
		Assertions.assertTrue(res == true);

	}

	@Test
	void shouldFindMatchRefereeRequestById() {

		Boolean res = true;

		MatchRefereeRequest mrr = this.matchRefereeRequestService.findMatchRefereeRequestById(1);

		if (mrr == null) {
			res = false;
		}

		Assertions.assertTrue(res);
	}

	@Test
	void shouldFindMatchRefereeRequestByUsernameAndMatchId() {

		Boolean res = true;

		MatchRefereeRequest mrr = this.matchRefereeRequestService.findMatchRefereeRequestByUsernameAndMatchId("referee1", 1);

		if (mrr == null) {
			res = false;
		}

		Assertions.assertTrue(res);

	}

	@Test
	void shouldSaveMatchRefereeRequest() {

		int pre_save = this.matchRefereeRequestService.count();
		Assertions.assertTrue(pre_save == 2);

		MatchRefereeRequest newMRR = new MatchRefereeRequest();

		Match match = this.matchService.findMatchById(1);
		Referee referee = this.refereeService.findRefereeById(1);

		newMRR.setId(100);
		newMRR.setTitle("JUnit test MRR");
		newMRR.setStatus(RequestStatus.ON_HOLD);
		newMRR.setMatch(match);
		newMRR.setReferee(referee);

		this.matchRefereeRequestService.saveMatchRefereeRequest(newMRR);

		int post_save = this.matchRefereeRequestService.count();
		Assertions.assertTrue(post_save == 3);

	}

	@Test
	void shouldDeleteMatchRefereeRequest() {

		MatchRefereeRequest mrr = this.matchRefereeRequestService.findMatchRefereeRequestById(1);

		mrr.setMatch(null);
		mrr.setReferee(null);

		this.matchRefereeRequestService.deleteMatchRefereeRequest(mrr);

		int post_delete = this.matchRefereeRequestService.count();
		Assertions.assertTrue(post_delete == 1);

	}

}
