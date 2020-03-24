
package org.springframework.samples.petclinic.service.nacho;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.samples.petclinic.datatypes.CreditCard;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.Fan;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.service.AuthenticatedService;
import org.springframework.samples.petclinic.service.FanService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedFanUserException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class FanServiceTest {

	@Autowired
	protected FanService			fanService;
	@Autowired
	protected FootballClubService	footballClubService;
	@Autowired
	protected AuthenticatedService	authenticathedService;


	@Test //CASO POSITIVO
	void shouldExistFan() {

		Boolean b = this.fanService.existFan(3);

		Assertions.assertTrue(b);
	}
	@Test //CASO NEGATIVO
	void shouldNotExistFan() {

		Boolean b = this.fanService.existFan(1);

		Assertions.assertFalse(b);
	}

	@Test //CASO POSITIVO
	void shouldSaveFanNoVip() throws DataAccessException, DuplicatedFanUserException, DuplicatedNameException {

		Fan f = new Fan();

		FootballClub c = this.footballClubService.findFootballClubById(1);
		Authenticated au = this.authenticathedService.findAuthenticatedById(1);

		f.setClub(c);
		f.setUser(au);
		f.setVip(false);

		this.fanService.saveFan(f);

		boolean b = this.fanService.existFan(f.getUser().getId());

		Assertions.assertTrue(b);
	}
	@Test //CASO POSITIVO
	void shouldSaveFanVip() throws DataAccessException, DuplicatedFanUserException, DuplicatedNameException {

		Fan f = new Fan();

		FootballClub c = this.footballClubService.findFootballClubById(1);
		Authenticated au = this.authenticathedService.findAuthenticatedById(1);
		CreditCard cc = new CreditCard();
		cc.setCvv("325");
		cc.setCreditCardNumber("5789456123547854");
		cc.setExpirationDate("12/23");

		f.setClub(c);
		f.setUser(au);
		f.setVip(true);
		f.setCreditCard(cc);

		this.fanService.saveFan(f);

		boolean b = this.fanService.existFan(f.getUser().getId());

		Assertions.assertTrue(b);
	}
	@Test //CASO NEGATIVO
	void shouldNotSaveFan() throws DataAccessException, DuplicatedFanUserException, DuplicatedNameException {

		Fan f = new Fan();

		Assertions.assertThrows(NullPointerException.class, () -> {
			this.fanService.saveFan(f);
		});
	}
	@Test //CASO NEGATIVO
	void shouldNotSaveFanDuplicated() throws DataAccessException, DuplicatedNameException {

		Fan f = new Fan();

		FootballClub c = this.footballClubService.findFootballClubById(2);
		Authenticated au = this.authenticathedService.findAuthenticatedById(3);

		f.setClub(c);
		f.setUser(au);
		f.setVip(false);

		Assertions.assertThrows(DuplicatedFanUserException.class, () -> {
			this.fanService.saveFan(f);
		});
	}
	@Test //CASO POSITIVO
	void shouldFindFanByUserId() {

		Fan f = this.fanService.findByUserId(3);

		Assertions.assertTrue(f != null);
	}
	@Test //CASO NEGATIVO
	void shouldNotFindFanByUserId() {

		Fan f = this.fanService.findByUserId(2);

		Assertions.assertTrue(f == null);
	}

	@Test //CASO POSITIVO
	void shouldDeleteFan() throws DataAccessException, DuplicatedNameException {

		Fan f = this.fanService.findByUserId(3);
		this.fanService.delete(f);

		boolean b = this.fanService.existFan(f.getUser().getId());
		Assertions.assertFalse(b);
	}
	@Test //CASO NEGATIVO
	void shouldNotDeleteFan() throws DataAccessException, DuplicatedNameException {

		Fan f = this.fanService.findByUserId(6);

		Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> {
			this.fanService.delete(f);
		});
	}

}
