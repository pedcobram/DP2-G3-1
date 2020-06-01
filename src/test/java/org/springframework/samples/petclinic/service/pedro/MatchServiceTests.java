
package org.springframework.samples.petclinic.service.pedro;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.Referee;
import org.springframework.samples.petclinic.model.Enum.MatchStatus;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.MatchRefereeRequestService;
import org.springframework.samples.petclinic.service.MatchRequestService;
import org.springframework.samples.petclinic.service.MatchService;
import org.springframework.samples.petclinic.service.RefereeService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class MatchServiceTests {

	@Autowired
	protected MatchService					matchService;

	@Autowired
	protected RefereeService				refereeService;

	@Autowired
	protected FootballClubService			footballClubService;

	@Autowired
	protected MatchRequestService			matchRequestService;

	@Autowired
	protected MatchRefereeRequestService	matchRefereeRequestService;


	@Test //CASO POSITIVO
	void shouldFindAllMatchRequests() {

		List<Match> ms = new ArrayList<>();

		ms.addAll(this.matchService.findAllMatchRequests());

		int count = ms.size();

		Assertions.assertTrue(count == 9);
	}

	@Test //CASO POSITIVO
	void shouldFindAllMatchesByReferee() {

		List<Match> ms = new ArrayList<>();

		ms.addAll(this.matchService.findAllMatchesByReferee("referee1"));

		int count = ms.size();

		Assertions.assertTrue(count == 4);
	}

	@Test //CASO NEGATIVO
	void shouldNotFindAllMatchesByReferee() {

		List<Match> ms = new ArrayList<>();

		ms.addAll(this.matchService.findAllMatchesByReferee("referee100"));

		int count = ms.size();

		Assertions.assertTrue(count == 0);
	}

	@Test //CASO POSITIVO
	void shouldFindMatchById() {

		Boolean res = true;

		Match m = this.matchService.findMatchById(10);

		if (m == null) {
			res = false;
		}

		Assertions.assertTrue(res);
	}

	@Test //CASO NEGATIVO
	void shouldNotFindMatchById() {

		Boolean res = true;

		Match m = this.matchService.findMatchById(100);

		if (m == null) {
			res = false;
		}

		Assertions.assertFalse(res);
	}

	@Test //CASO POSITIVO
	void shouldSaveMatch() {

		Match m = new Match();

		Calendar d = Calendar.getInstance();
		d.set(2025, 02, 02, 20, 20);
		Date date = d.getTime();

		FootballClub fc1 = this.footballClubService.findFootballClubById(1);
		FootballClub fc2 = this.footballClubService.findFootballClubById(2);

		Referee ref = this.refereeService.findRefereeById(1);

		m.setId(100);
		m.setTitle("JUnit test");
		m.setMatchDate(date);
		m.setMatchStatus(MatchStatus.FINISHED);
		m.setStadium("Stadium");
		m.setFootballClub1(fc1);
		m.setFootballClub2(fc2);
		m.setReferee(ref);

		this.matchService.saveMatch(m);

		int count = this.matchService.count();

		Assertions.assertTrue(count == 10);
	}

	@Test //CASO NEGATIVO
	void shouldNotSaveMatch() {

		Match m = new Match();

		FootballClub fc1 = this.footballClubService.findFootballClubById(1);
		FootballClub fc2 = this.footballClubService.findFootballClubById(2);

		Referee ref = this.refereeService.findRefereeById(1);

		m.setId(100);
		m.setTitle("JUnit test");
		m.setMatchDate(null);
		m.setMatchStatus(MatchStatus.FINISHED);
		m.setStadium("Stadium");
		m.setFootballClub1(fc1);
		m.setFootballClub2(fc2);
		m.setReferee(ref);

		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			this.matchService.saveMatch(m);
		});
	}

	@Test //CASO POSITIVO
	void shouldDeteleMatch() {

		Match m = new Match();

		Calendar d = Calendar.getInstance();
		d.set(2025, 02, 02, 20, 20);
		Date matchDate = d.getTime();

		m.setTitle("Title");
		m.setMatchDate(matchDate);
		m.setMatchStatus(MatchStatus.TO_BE_PLAYED);
		m.setStadium("Stadium");
		m.setCreator("Creator");

		this.matchService.saveMatch(m);

		int pre_delete = this.matchService.count();

		Assertions.assertTrue(pre_delete == 10);

		this.matchService.deleteMatch(m);

		int post_delete = this.matchService.count();

		Assertions.assertTrue(post_delete == 9);
	}

	@Test //CASO NEGATIVO
	void shouldNotDeteleMatch() {

		Match m = this.matchService.findMatchById(100);

		Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> {
			this.matchService.deleteMatch(m);
		});
	}

}
