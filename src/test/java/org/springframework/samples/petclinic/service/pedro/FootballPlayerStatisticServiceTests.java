
package org.springframework.samples.petclinic.service.pedro;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.FootballPlayerStatistic;
import org.springframework.samples.petclinic.model.FootballPlayerStatistics;
import org.springframework.samples.petclinic.service.FootballPlayerService;
import org.springframework.samples.petclinic.service.FootballPlayerStatisticService;
import org.springframework.samples.petclinic.service.MatchService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class FootballPlayerStatisticServiceTests {

	@Autowired
	protected FootballPlayerStatisticService	footballPlayerStatisticService;

	@Autowired
	protected FootballPlayerService				footballPlayerService;

	@Autowired
	protected MatchService						matchService;


	@Test
	void shouldFindAllFootballPlayerStatistics() {

		FootballPlayerStatistics fpss = new FootballPlayerStatistics();

		fpss.getFootballPlayerStatisticsList().addAll(this.footballPlayerStatisticService.findAllFootballPlayerStatistics());

		int count = fpss.getFootballPlayerStatisticsList().size();

		Assertions.assertTrue(count == 64);
	}

	@Test
	void shouldFindAllFootballPlayerStatisticsBySeason() {

		FootballPlayerStatistics fpss = new FootballPlayerStatistics();

		fpss.getFootballPlayerStatisticsList().addAll(this.footballPlayerStatisticService.findAllFootballPlayerStatisticsBySeason("2019", "2020"));

		Integer count = fpss.getFootballPlayerStatisticsList().size();

		Assertions.assertTrue(count != null);
	}

	@Test
	void shouldFindFootballPlayerStatisticById() {

		Boolean res = true;

		FootballPlayerStatistic fps = this.footballPlayerStatisticService.findFootballPlayerStatisticById(1);

		if (fps == null) {
			res = false;
		}

		Assertions.assertTrue(res);
	}

	@Test
	void shouldFindFootballPlayerStatisticByPlayerId() {

		Boolean res = true;

		FootballPlayerStatistic fps = this.footballPlayerStatisticService.findFootballPlayerStatisticByPlayerId(1);

		if (fps == null) {
			res = false;
		}

		Assertions.assertTrue(res);

	}

	@Test
	void shouldSaveFootballPlayerStatistic() {

		FootballPlayer fp = this.footballPlayerService.findFootballPlayerById(1);

		FootballPlayerStatistic fpms = new FootballPlayerStatistic();

		fpms.setAssists(0);
		fpms.setGoals(0);
		fpms.setId(100);
		fpms.setPlayer(fp);
		fpms.setReceived_goals(0);
		fpms.setRed_cards(0);
		fpms.setSeason_end("0000");
		fpms.setSeason_start("0000");
		fpms.setYellow_cards(0);

		this.footballPlayerStatisticService.saveFootballPlayerStatistic(fpms);

	}

	@Test
	void shouldDeleteFootballPlayerStatistic() {

		FootballPlayer fp = this.footballPlayerService.findFootballPlayerById(1);

		FootballPlayerStatistic fpms = new FootballPlayerStatistic();

		fpms.setAssists(0);
		fpms.setGoals(0);
		fpms.setId(100);
		fpms.setPlayer(fp);
		fpms.setReceived_goals(0);
		fpms.setRed_cards(0);
		fpms.setSeason_end("0000");
		fpms.setSeason_start("0000");
		fpms.setYellow_cards(0);

		this.footballPlayerStatisticService.saveFootballPlayerStatistic(fpms);

		this.footballPlayerStatisticService.deleteFootballPlayerStatistic(fpms);

		FootballPlayerStatistic fps = this.footballPlayerStatisticService.findFootballPlayerStatisticById(100);

		Assertions.assertTrue(fps == null);

	}

}
