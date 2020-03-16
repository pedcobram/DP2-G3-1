
package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.FootballPlayerMatchStatistic;
import org.springframework.samples.petclinic.model.FootballPlayerMatchStatistics;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.MatchRecord;
import org.springframework.samples.petclinic.model.Enum.MatchRecordStatus;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class FootballPlayerMatchStatisticTests {

	@Autowired
	protected FootballPlayerMatchStatisticService	footballPlayerMatchStatisticService;

	@Autowired
	protected MatchService							matchService;

	@Autowired
	protected FootballPlayerService					footballPlayerService;


	@Test
	void shouldFindAllFootballPlayerMatchStatistics() {

		FootballPlayerMatchStatistics fpss = new FootballPlayerMatchStatistics();

		fpss.getFootballPlayerStatisticsList().addAll(this.footballPlayerMatchStatisticService.findAllFootballPlayerMatchStatistics());

		int count = fpss.getFootballPlayerStatisticsList().size();

		Assertions.assertTrue(count == 0);
	}

	@Test
	void shouldFindAllFootballPlayerMatchStatisticsBySeason() {

		FootballPlayerMatchStatistics fpss = new FootballPlayerMatchStatistics();

		fpss.getFootballPlayerStatisticsList().addAll(this.footballPlayerMatchStatisticService.findAllFootballPlayerMatchStatisticsBySeason("2019", "2020"));

		int count = fpss.getFootballPlayerStatisticsList().size();

		Assertions.assertTrue(count == 0);
	}

	@Test
	void shouldFindFootballPlayerMatchStatisticByMatchId() {

		FootballPlayerMatchStatistics fpss = new FootballPlayerMatchStatistics();

		fpss.getFootballPlayerStatisticsList().addAll(this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticByMatchId(1));

		int count = fpss.getFootballPlayerStatisticsList().size();

		Assertions.assertTrue(count == 0);

	}

	@Test
	void shouldFindFootballPlayerMatchStatisticById() {

		Boolean res = false;

		FootballPlayerMatchStatistic fps = this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticById(1);

		if (fps == null) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test
	void shouldFindFootballPlayerMatchStatisticByPlayerId() {

		Boolean res = false;

		FootballPlayerMatchStatistic fps = this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticByPlayerId(1);

		if (fps == null) {
			res = true;
		}

		Assertions.assertTrue(res);
	}

	@Test
	void shouldFindFootballPlayerMatchStatisticByPlayerIdAndMatchRecordId() {

		Boolean res = false;

		FootballPlayerMatchStatistic fps = this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticByPlayerIdAndMatchRecordId(1, 1);

		if (fps == null) {
			res = true;
		}

		Assertions.assertTrue(res);

	}

	@Test
	void shouldSaveFootballPlayerStatistic() {

		Match m = this.matchService.findMatchById(1);
		FootballPlayer fp = this.footballPlayerService.findFootballPlayerById(1);

		MatchRecord mr = new MatchRecord();

		mr.setId(100);
		mr.setMatch(m);
		mr.setResult("Test");
		mr.setSeason_end("0000");
		mr.setSeason_start("0000");
		mr.setStatus(MatchRecordStatus.NOT_PUBLISHED);
		mr.setTitle("Test");

		FootballPlayerMatchStatistic fpms = new FootballPlayerMatchStatistic();

		fpms.setAssists(0);
		fpms.setGoals(0);
		fpms.setId(100);
		fpms.setMatchRecord(mr);
		fpms.setPlayer(fp);
		fpms.setReceived_goals(0);
		fpms.setRed_cards(0);
		fpms.setSeason_end("0000");
		fpms.setSeason_start("0000");
		fpms.setYellow_cards(0);

		this.footballPlayerMatchStatisticService.saveFootballPlayerStatistic(fpms);

	}

	@Test
	void shouldDeleteFootballPlayerStatistic() {

		Match m = this.matchService.findMatchById(1);
		FootballPlayer fp = this.footballPlayerService.findFootballPlayerById(1);

		MatchRecord mr = new MatchRecord();

		mr.setId(100);
		mr.setMatch(m);
		mr.setResult("Test");
		mr.setSeason_end("0000");
		mr.setSeason_start("0000");
		mr.setStatus(MatchRecordStatus.NOT_PUBLISHED);
		mr.setTitle("Test");

		FootballPlayerMatchStatistic fpms = new FootballPlayerMatchStatistic();

		fpms.setAssists(0);
		fpms.setGoals(0);
		fpms.setId(100);
		fpms.setMatchRecord(mr);
		fpms.setPlayer(fp);
		fpms.setReceived_goals(0);
		fpms.setRed_cards(0);
		fpms.setSeason_end("0000");
		fpms.setSeason_start("0000");
		fpms.setYellow_cards(0);

		this.footballPlayerMatchStatisticService.saveFootballPlayerStatistic(fpms);

		this.footballPlayerMatchStatisticService.deleteFootballPlayerStatistic(fpms);

		FootballPlayerMatchStatistic fpmss = this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticById(100);

		Assertions.assertTrue(fpmss == null);

	}

}
