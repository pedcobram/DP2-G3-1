
package org.springframework.samples.petclinic.service.pedro;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.MatchRequest;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.MatchRequestService;
import org.springframework.samples.petclinic.service.exceptions.DateException;
import org.springframework.samples.petclinic.service.exceptions.IllegalDateException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MatchRequestServiceTests {

	@Autowired
	protected MatchRequestService	matchRequestService;

	@Autowired
	protected FootballClubService	footballClubService;


	@Test //CASO POSITIVO
	void shouldFindAllMatchRequests() {

		List<MatchRequest> mrs = new ArrayList<>();

		mrs.addAll(this.matchRequestService.findAllMatchRequests());

		int count = mrs.size();

		Assertions.assertTrue(count == 4);
	}

	@Test //CASO POSITIVO
	void shouldFindAllMatchRequestsReceived() {

		List<MatchRequest> mrs = new ArrayList<>();

		mrs.addAll(this.matchRequestService.findAllMatchRequestsReceived("Sevilla Fútbol Club"));

		int count = mrs.size();

		Assertions.assertTrue(count == 2);
	}

	@Test //CASO NEGATIVO
	void shouldNotFindAllMatchRequestsReceived() {

		List<MatchRequest> mrs = new ArrayList<>();

		mrs.addAll(this.matchRequestService.findAllMatchRequestsReceived("Sevillaa Fútboll Clubb"));

		int count = mrs.size();

		Assertions.assertTrue(count == 0);
	}

	@Test //CASO POSITIVO
	void shouldFindAllMatchRequestsSent() {

		List<MatchRequest> mrs = new ArrayList<>();

		mrs.addAll(this.matchRequestService.findAllMatchRequestsSent("Sevilla Fútbol Club"));

		int count = mrs.size();

		Assertions.assertTrue(count == 2);
	}

	@Test //CASO NEGATIVO
	void shouldNotFindAllMatchRequestsSent() {

		List<MatchRequest> mrs = new ArrayList<>();

		mrs.addAll(this.matchRequestService.findAllMatchRequestsSent("Sevillaa Fútboll Clubb"));

		int count = mrs.size();

		Assertions.assertTrue(count == 0);
	}

	@Test //CASO POSITIVO
	void shouldFindMatchRequestById() {

		Boolean res = true;

		MatchRequest mr = this.matchRequestService.findMatchRequestById(1);

		if (mr == null) {
			res = false;
		}

		Assertions.assertTrue(res);
	}

	@Test //CASO NEGATIVO
	void shouldNotFindMatchRequestById() {

		Boolean res = true;

		MatchRequest mr = this.matchRequestService.findMatchRequestById(100);

		if (mr == null) {
			res = false;
		}

		Assertions.assertFalse(res);
	}

	@Test //CASO POSITIVO
	void shouldSaveMatchRequest() throws DataAccessException, IllegalDateException, DateException {

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

	@Test //CASO NEGATIVO
	void shouldNotSaveMatchRequest() {

		MatchRequest newMR = new MatchRequest();

		Calendar d = Calendar.getInstance();
		d.set(2025, 02, 02, 20, 20);
		Date date = d.getTime();

		FootballClub fc1 = this.footballClubService.findFootballClubById(1);
		FootballClub fc2 = this.footballClubService.findFootballClubById(2);

		newMR.setId(100);
		newMR.setTitle("");
		newMR.setStatus(RequestStatus.ON_HOLD);
		newMR.setStadium("Stadium");
		newMR.setFootballClub1(fc1);
		newMR.setFootballClub2(fc2);
		newMR.setMatchDate(date);

		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			this.matchRequestService.saveMatchRequest(newMR);
		});
	}

	@Test //CASO REGLA DE NEGOCIO
	void shouldIllegalDateExceptionSaveMatchRequest() {

		MatchRequest newMR = new MatchRequest();

		LocalDateTime now = LocalDateTime.now();
		Date now_date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

		FootballClub fc1 = this.footballClubService.findFootballClubById(1);
		FootballClub fc2 = this.footballClubService.findFootballClubById(2);

		newMR.setId(100);
		newMR.setTitle("JUnit test");
		newMR.setStatus(RequestStatus.ON_HOLD);
		newMR.setStadium("Stadium");
		newMR.setFootballClub1(fc1);
		newMR.setFootballClub2(fc2);
		newMR.setMatchDate(now_date);

		Assertions.assertThrows(IllegalDateException.class, () -> {
			this.matchRequestService.saveMatchRequest(newMR);
		});
	}

	@Test //CASO POSITIVO
	void shouldDeleteMatchRequest() {

		MatchRequest mr = this.matchRequestService.findMatchRequestById(2);

		mr.setReferee(null);
		mr.setFootballClub1(null);
		mr.setFootballClub2(null);

		this.matchRequestService.deleteMatchRequest(mr);

		int post_count = this.matchRequestService.count();

		Assertions.assertTrue(post_count == 3);
	}

	@Test //CASO NEGATIVO
	void shouldNotDeleteMatchRequest() {

		MatchRequest mr = this.matchRequestService.findMatchRequestById(200);

		Assertions.assertThrows(NullPointerException.class, () -> {
			this.matchRequestService.deleteMatchRequest(mr);
		});
	}

}
