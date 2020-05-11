
package org.springframework.samples.petclinic.service.nacho;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Round;
import org.springframework.samples.petclinic.service.AuthenticatedService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.RoundService;
import org.springframework.samples.petclinic.service.exceptions.StatusException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class RoundServiceTest {

	@Autowired
	protected RoundService			roundService;
	@Autowired
	protected FootballClubService	footballClubService;
	@Autowired
	protected AuthenticatedService	authenticathedService;


	@Test //CASO POSITIVO
	void shouldFindRoundByCompetitionId() {

		List<Round> f = this.roundService.findByCompetitionId(3);

		Assertions.assertTrue(f != null);
	}
	@Test //CASO NEGATIVO
	void shouldNotFindRoundByCompetitionId() {

		List<Round> f = this.roundService.findByCompetitionId(1);

		Assertions.assertTrue(f.isEmpty());
	}

	@Test //CASO POSITIVO
	void shouldfindById() {

		Round f = this.roundService.findById(1);

		Assertions.assertTrue(f != null);
	}
	@Test //CASO NEGATIVO
	void shouldNotfindById() {

		Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.roundService.findById(3);
		});
	}
	@Test //CASO POSITIVO
	void shouldSave() {

		Round f = new Round();

		f.setName("prueba");

		this.roundService.save(f);

		Assertions.assertTrue(!this.roundService.findById(2).equals(null));
	}
	@Test //CASO NEGATIVO
	void shouldNotSave() {

		Round f = new Round();
		f.setName("");
		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			this.roundService.save(f);
		});

	}

	@Test //CASO POSITIVO
	void shouldDelete() throws DataAccessException, StatusException {

		this.roundService.delete(this.roundService.findById(2));
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.roundService.findById(2);
		});

	}
	@Test //CASO NEGATIVO
	void shouldNotDelete() throws DataAccessException {
		Assertions.assertThrows(StatusException.class, () -> {
			this.roundService.delete(this.roundService.findById(1));
		});
	}

	@Test //CASO NEGATIVO
	void shouldNotDelete1() {

		Assertions.assertThrows(NullPointerException.class, () -> {
			this.roundService.delete(null);
		});
	}

	@Test //CASO POSITIVO
	void shouldDeleteAll() throws DataAccessException, StatusException {

		this.roundService.deleteAll(4);

		Assertions.assertTrue(!this.roundService.findById(2).equals(null));

	}
	@Test //CASO NEGATIVO
	void shouldNotDeleteAll() throws DataAccessException {

		Assertions.assertThrows(StatusException.class, () -> {
			this.roundService.deleteAll(3);
		});

	}

}
