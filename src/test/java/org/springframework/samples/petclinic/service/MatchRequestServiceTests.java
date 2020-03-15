
package org.springframework.samples.petclinic.service;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.MatchRequest;
import org.springframework.samples.petclinic.model.MatchRequests;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class MatchRequestServiceTests {

	@Autowired
	protected MatchRequestService	matchRequestService;

	@Autowired
	protected FootballClubService	footballClubService;


	@Test
	void shouldFindAllMatchRequests() {

		MatchRequests mrs = new MatchRequests();

		mrs.getMatchRequestList().addAll(this.matchRequestService.findAllMatchRequests());

		int count = mrs.getMatchRequestList().size();

		Assertions.assertTrue(count == 4);
	}

	@Test
	void shouldFindAllMatchRequestsReceived() {

		MatchRequests mrs = new MatchRequests();

		mrs.getMatchRequestList().addAll(this.matchRequestService.findAllMatchRequestsReceived("Sevilla Fútbol Club"));

		int count = mrs.getMatchRequestList().size();

		Assertions.assertTrue(count == 2);
	}

	@Test
	void shouldFindAllMatchRequestsSent() {

		MatchRequests mrs = new MatchRequests();

		mrs.getMatchRequestList().addAll(this.matchRequestService.findAllMatchRequestsSent("Sevilla Fútbol Club"));

		int count = mrs.getMatchRequestList().size();

		Assertions.assertTrue(count == 2);
	}

	@Test
	void shouldFindMatchRequestById() {

		Boolean res = true;

		MatchRequest mr = this.matchRequestService.findMatchRequestById(1);

		if (mr == null) {
			res = false;
		}

		Assertions.assertTrue(res == true);
	}

	@Test
	void shouldSaveMatchRequest() {

		MatchRequest newMR = new MatchRequest();

		Calendar d = Calendar.getInstance();
		d.set(2025, 02, 02, 20, 20);
		Date date = d.getTime();

		FootballClub fc1 = this.footballClubService.findFootballClubById(1);
		FootballClub fc2 = this.footballClubService.findFootballClubById(2);

		newMR.setId(100);
		newMR.setTitle("JUnit test");
		newMR.setStatus(RequestStatus.ON_HOLD);
		newMR.setStadium("Stadium");
		newMR.setFootballClub1(fc1);
		newMR.setFootballClub2(fc2);
		newMR.setMatchDate(date);

		this.matchRequestService.saveMatchRequest(newMR);

		int count = this.matchRequestService.count();

		Assertions.assertTrue(count == 5);
	}

	@Test
	void shouldDeleteMatchRequest() {

		MatchRequest mr = this.matchRequestService.findMatchRequestById(2);

		mr.setReferee(null);
		mr.setFootballClub1(null);
		mr.setFootballClub2(null);

		this.matchRequestService.deleteMatchRequest(mr);

		int post_count = this.matchRequestService.count();

		Assertions.assertTrue(post_count == 3);
	}

}
