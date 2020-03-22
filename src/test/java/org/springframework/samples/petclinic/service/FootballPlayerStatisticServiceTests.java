
package org.springframework.samples.petclinic.service;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.FootballPlayerStatistic;
import org.springframework.samples.petclinic.model.FootballPlayerStatistics;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class FootballPlayerStatisticServiceTests {

	@Autowired
	protected FootballPlayerStatisticService	footballPlayerStatisticService;

	@Autowired
	protected FootballPlayerService				footballPlayerService;

	@Autowired
	protected MatchService						matchService;


	@Test //CASO POSITIVO
	void shouldFindAllFootballPlayerStatistics() {

		FootballPlayerStatistics fpss = new FootballPlayerStatistics();

		fpss.getFootballPlayerStatisticsList().addAll(this.footballPlayerStatisticService.findAllFootballPlayerStatistics());

		int count = fpss.getFootballPlayerStatisticsList().size();

		Assertions.assertTrue(count == 64);
	}

	@Test //CASO POSITIVO
	void shouldFindAllFootballPlayerStatisticsBySeason() {

		FootballPlayerStatistics fpss = new FootballPlayerStatistics();

		fpss.getFootballPlayerStatisticsList().addAll(this.footballPlayerStatisticService.findAllFootballPlayerStatisticsBySeason("2019", "2020"));

		Integer count = fpss.getFootballPlayerStatisticsList().size();

		Assertions.assertTrue(count != null);
	}

	@Test //CASO NEGATIVO
	void shouldNotFindAllFootballPlayerStatisticsBySeason() {

		FootballPlayerStatistics fpss = new FootballPlayerStatistics();

		fpss.getFootballPlayerStatisticsList().addAll(this.footballPlayerStatisticService.findAllFootballPlayerStatisticsBySeason("", ""));

		Integer count = fpss.getFootballPlayerStatisticsList().size();

		Assertions.assertTrue(count == 0);
	}

	@Test //CASO POSITIVO
	void shouldFindFootballPlayerStatisticById() {

		Boolean res = true;

		FootballPlayerStatistic fps = this.footballPlayerStatisticService.findFootballPlayerStatisticById(1);

		if (fps == null) {
			res = false;
		}

		Assertions.assertTrue(res);
	}

	@Test //CASO NEGATIVO
	void shouldNotFindFootballPlayerStatisticById() {

		Boolean res = true;

		FootballPlayerStatistic fps = this.footballPlayerStatisticService.findFootballPlayerStatisticById(100);

		if (fps == null) {
			res = false;
		}

		Assertions.assertFalse(res);
	}

	@Test //CASO POSITIVO
	void shouldFindFootballPlayerStatisticByPlayerId() {

		Boolean res = true;

		FootballPlayerStatistic fps = this.footballPlayerStatisticService.findFootballPlayerStatisticByPlayerId(1);

		if (fps == null) {
			res = false;
		}

		Assertions.assertTrue(res);
	}

	@Test //CASO NEGATIVO
	void shouldNotFindFootballPlayerStatisticByPlayerId() {

		Boolean res = true;

		FootballPlayerStatistic fps = this.footballPlayerStatisticService.findFootballPlayerStatisticByPlayerId(100);

		if (fps == null) {
			res = false;
		}

		Assertions.assertFalse(res);
	}

	@Test //CASO POSITIVO
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

		Integer count = this.footballPlayerStatisticService.count();

		Assertions.assertTrue(count == 65);
	}

	@Test //CASO NEGATIVO
	void shouldNotSaveFootballPlayerStatistic() {

		FootballPlayer fp = this.footballPlayerService.findFootballPlayerById(1);

		FootballPlayerStatistic fpms = new FootballPlayerStatistic();

		fpms.setAssists(null);
		fpms.setGoals(0);
		fpms.setId(100);
		fpms.setPlayer(fp);
		fpms.setReceived_goals(0);
		fpms.setRed_cards(0);
		fpms.setSeason_end("0000");
		fpms.setSeason_start("0000");
		fpms.setYellow_cards(0);

		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			this.footballPlayerStatisticService.saveFootballPlayerStatistic(fpms);
		});
	}

	@Test //CASO POSITIVO
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

	@Test //CASO NEGATIVO
	void shouldNotDeleteFootballPlayerStatistic() {

		FootballPlayerStatistic fpms = this.footballPlayerStatisticService.findFootballPlayerStatisticById(100);

		Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> {
			this.footballPlayerStatisticService.deleteFootballPlayerStatistic(fpms);
		});
	}

}
