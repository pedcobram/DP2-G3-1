
package org.springframework.samples.petclinic.service.pedro;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.FootballPlayerMatchStatistic;
import org.springframework.samples.petclinic.model.FootballPlayerMatchStatistics;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.MatchRecord;
import org.springframework.samples.petclinic.model.Enum.MatchRecordStatus;
import org.springframework.samples.petclinic.service.FootballPlayerMatchStatisticService;
import org.springframework.samples.petclinic.service.FootballPlayerService;
import org.springframework.samples.petclinic.service.MatchService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
//@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FootballPlayerMatchStatisticTests {

	@Autowired
	protected FootballPlayerMatchStatisticService	footballPlayerMatchStatisticService;

	@Autowired
	protected MatchService							matchService;

	@Autowired
	protected FootballPlayerService					footballPlayerService;


	@Test //CASO POSITIVO
	void shouldFindAllFootballPlayerMatchStatistics() {

		FootballPlayerMatchStatistics fpss = new FootballPlayerMatchStatistics();

		fpss.getFootballPlayerStatisticsList().addAll(this.footballPlayerMatchStatisticService.findAllFootballPlayerMatchStatistics());

		int count = fpss.getFootballPlayerStatisticsList().size();

		Assertions.assertTrue(count == 5);
	}

	@Test //CASO POSITIVO
	void shouldFindAllFootballPlayerMatchStatisticsBySeason() {

		FootballPlayerMatchStatistics fpss = new FootballPlayerMatchStatistics();

		fpss.getFootballPlayerStatisticsList().addAll(this.footballPlayerMatchStatisticService.findAllFootballPlayerMatchStatisticsBySeason("2019", "2020"));

		int count = fpss.getFootballPlayerStatisticsList().size();

		Assertions.assertTrue(count == 5);
	}

	@Test //CASO POSITIVO
	void shouldFindFootballPlayerMatchStatisticByMatchId() {

		FootballPlayerMatchStatistics fpss = new FootballPlayerMatchStatistics();

		fpss.getFootballPlayerStatisticsList().addAll(this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticByMatchRecordId(1));

		int count = fpss.getFootballPlayerStatisticsList().size();

		Assertions.assertTrue(count == 1);
	}

	@Test //CASO POSITIVO
	void shouldFindFootballPlayerMatchStatisticById() {

		Boolean res = true;

		FootballPlayerMatchStatistic fps = this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticById(1);

		if (fps == null) {
			res = false;
		}

		Assertions.assertTrue(res);
	}

	@Test //CASO NEGATIVO
	void shouldNotFindFootballPlayerMatchStatisticById() {

		Boolean res = true;

		FootballPlayerMatchStatistic fps = this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticById(1000);

		if (fps == null) {
			res = false;
		}

		Assertions.assertFalse(res);
	}

	@Test //CASO POSITIVO
	void shouldFindFootballPlayerMatchStatisticByPlayerId() {

		Boolean res = true;

		Collection<FootballPlayerMatchStatistic> fps = this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticByPlayerId(1);

		if (fps == null) {
			res = false;
		}

		Assertions.assertTrue(res);
	}

	@Test //CASO NEGATIVO
	void shouldNotFindFootballPlayerMatchStatisticByPlayerId() {

		Boolean res = true;

		Collection<FootballPlayerMatchStatistic> fps = this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticByPlayerId(1000);

		if (fps.isEmpty()) {
			res = false;
		}

		Assertions.assertFalse(res);
	}

	@Test //CASO POSITIVO
	void shouldFindFootballPlayerMatchStatisticByPlayerIdAndMatchRecordId() {

		Boolean res = true;

		FootballPlayerMatchStatistic fps = this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticByPlayerIdAndMatchRecordId(1, 1);

		if (fps == null) {
			res = false;
		}

		Assertions.assertTrue(res);

	}

	@Test //CASO NEGATIVO
	void shouldNotFindFootballPlayerMatchStatisticByPlayerIdAndMatchRecordId() {

		Boolean res = true;

		FootballPlayerMatchStatistic fps = this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticByPlayerIdAndMatchRecordId(100, 100);

		if (fps == null) {
			res = false;
		}

		Assertions.assertFalse(res);

	}

	@Test //CASO POSITIVO
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

	@Test //CASO NEGATIVO
	void shouldNotSaveFootballPlayerStatistic() {

		Match m = this.matchService.findMatchById(1);
		FootballPlayer fp = this.footballPlayerService.findFootballPlayerById(1);

		MatchRecord mr = new MatchRecord();

		mr.setId(100);
		mr.setMatch(m);
		mr.setResult("result");
		mr.setSeason_end("0000");
		mr.setSeason_start("0000");
		mr.setStatus(MatchRecordStatus.NOT_PUBLISHED);
		mr.setTitle("");

		FootballPlayerMatchStatistic fpms = new FootballPlayerMatchStatistic();

		fpms.setAssists(0);
		fpms.setGoals(0);
		fpms.setId(99);
		fpms.setMatchRecord(mr);
		fpms.setPlayer(fp);
		fpms.setReceived_goals(0);
		fpms.setRed_cards(0);
		fpms.setSeason_end("0000");
		fpms.setSeason_start("0000");
		fpms.setYellow_cards(0);

		// FIX POR GONZALO
		// He supuesto que al estar el titulo vacio buscabas que ocurriera un fallo
		// provocado por la mala elaboracion del MatchRecord "mr" -> title vacio (fallo)
		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			this.footballPlayerMatchStatisticService.saveFootballPlayerStatistic(fpms);
		});

	}

	@Test //CASO POSITIVO
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

	@Test //CASO NEGATIVO
	void shouldNotDeleteFootballPlayerStatistic() {

		FootballPlayerMatchStatistic fpms = this.footballPlayerMatchStatisticService.findFootballPlayerMatchStatisticById(100);

		Assertions.assertThrows(DataAccessException.class, () -> {
			this.footballPlayerMatchStatisticService.deleteFootballPlayerStatistic(fpms);
		});
	}

}
