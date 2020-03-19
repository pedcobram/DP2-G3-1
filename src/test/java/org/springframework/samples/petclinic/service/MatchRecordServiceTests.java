
package org.springframework.samples.petclinic.service;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.MatchRecord;
import org.springframework.samples.petclinic.model.Enum.MatchRecordStatus;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class MatchRecordServiceTests {

	@Autowired
	protected MatchRecordService	matchRecordService;

	@Autowired
	protected MatchService			matchService;


	@Test //CASO POSITIVO
	void shouldFindMatchRecordById() {

		Boolean res = true;

		MatchRecord mr = this.matchRecordService.findMatchRecordById(1);

		if (mr == null) {
			res = false;
		}

		Assertions.assertTrue(res);
	}

	@Test //CASO NEGATIVO
	void shouldNotFindMatchRecordById() {

		Boolean res = true;

		MatchRecord mr = this.matchRecordService.findMatchRecordById(100);

		if (mr == null) {
			res = false;
		}

		Assertions.assertFalse(res);
	}

	@Test //CASO POSITIVO
	void shouldFindMatchRecordByMatchId() {

		Boolean res = true;

		MatchRecord mr = this.matchRecordService.findMatchRecordByMatchId(1);

		if (mr == null) {
			res = false;
		}

		Assertions.assertTrue(res);
	}

	@Test //CASO NEGATIVO
	void shouldNotFindMatchRecordByMatchId() {

		Boolean res = true;

		MatchRecord mr = this.matchRecordService.findMatchRecordByMatchId(100);

		if (mr == null) {
			res = false;
		}

		Assertions.assertFalse(res);
	}

	@Test //CASO POSITIVO
	void shouldSaveMatchRecord() {

		Match m = this.matchService.findMatchById(1);

		MatchRecord mr = new MatchRecord();

		mr.setId(100);
		mr.setMatch(m);
		mr.setResult("Test");
		mr.setSeason_end("0000");
		mr.setSeason_start("0000");
		mr.setStatus(MatchRecordStatus.NOT_PUBLISHED);
		mr.setTitle("Test");

		this.matchRecordService.saveMatchRecord(mr);
	}

	@Test //CASO NEGATIVO
	void shouldNotSaveMatchRecord() {

		Match m = this.matchService.findMatchById(1);

		MatchRecord mr = new MatchRecord();

		mr.setId(100);
		mr.setMatch(m);
		mr.setResult("Test");
		mr.setSeason_end("0000");
		mr.setSeason_start("0000");
		mr.setStatus(MatchRecordStatus.NOT_PUBLISHED);
		mr.setTitle("");

		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			this.matchRecordService.saveMatchRecord(mr);
		});
	}

	@Test //CASO POSITIVO
	void shouldDeleteMatchRecord() {

		Match m = this.matchService.findMatchById(1);

		MatchRecord mr = new MatchRecord();

		mr.setId(100);
		mr.setMatch(m);
		mr.setResult("Test");
		mr.setSeason_end("0000");
		mr.setSeason_start("0000");
		mr.setStatus(MatchRecordStatus.NOT_PUBLISHED);
		mr.setTitle("Test");

		this.matchRecordService.saveMatchRecord(mr);

		this.matchRecordService.deleteMatchRecord(mr);

		MatchRecord mr1 = this.matchRecordService.findMatchRecordById(100);

		Assertions.assertTrue(mr1 == null);
	}

	@Test //CASO NEGATIVO
	void shouldNotDeleteMatchRecord() {
		MatchRecord mr = this.matchRecordService.findMatchRecordById(100);

		Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> {
			this.matchRecordService.deleteMatchRecord(mr);
		});
	}

}
