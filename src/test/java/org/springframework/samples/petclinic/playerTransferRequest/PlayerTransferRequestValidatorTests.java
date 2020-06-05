
package org.springframework.samples.petclinic.playerTransferRequest;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.model.ContractPlayer;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.PlayerTransferRequest;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Enum.FootballPlayerPosition;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class PlayerTransferRequestValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	private President createPresident() {

		User user = new User();

		user.setEnabled(true);
		user.setUsername("president");
		user.setPassword("president");

		President president = new President();

		president.setId(1);
		president.setDni("11111111A");
		president.setFirstName("President1");
		president.setLastName("President1");
		president.setEmail("test@gmail.com");
		president.setTelephone("111111111");
		president.setUser(user);

		return president;
	}

	private FootballClub createFootballClub() {

		Calendar foundationDate = Calendar.getInstance();
		President president = this.createPresident();

		FootballClub fc = new FootballClub();

		fc.setId(1);
		fc.setCity("City");
		fc.setCrest("Crest");
		fc.setFans(44000);
		fc.setFoundationDate(foundationDate.getTime());
		fc.setMoney(150000000);
		fc.setName("Club name");
		fc.setStadium("Stadium name");
		fc.setStatus(true);
		fc.setPresident(president);

		return fc;
	}

	private FootballPlayer createFootballPlayer() {

		Calendar birthDate = Calendar.getInstance();
		FootballPlayer footballPlayer = new FootballPlayer();
		FootballClub club = this.createFootballClub();

		footballPlayer.setId(1);
		footballPlayer.setFirstName("playername");
		footballPlayer.setLastName("playerlastname");
		footballPlayer.setClub(club);
		footballPlayer.setBirthDate(birthDate.getTime());
		footballPlayer.setPosition(FootballPlayerPosition.STRIKER);
		footballPlayer.setValue(150000000);

		return footballPlayer;
	}

	private ContractPlayer createContractPlayer() {

		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		endDate.add(Calendar.YEAR, 2);

		FootballClub footballClub = this.createFootballClub();
		FootballPlayer player = this.createFootballPlayer();

		ContractPlayer contract = new ContractPlayer();

		contract.setId(1);
		contract.setClause(15000000 * 6);
		contract.setClub(footballClub);
		contract.setStartDate(startDate.getTime());
		contract.setEndDate(endDate.getTime());
		contract.setSalary(15000000);
		contract.setPlayer(player);

		return contract;
	}

	@Test
	void shouldValidateWell() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		FootballClub club = this.createFootballClub();
		ContractPlayer contract = this.createContractPlayer();
		FootballPlayer player = this.createFootballPlayer();

		PlayerTransferRequest ptr = new PlayerTransferRequest();

		ptr.setId(1);
		ptr.setOffer(2000000L);
		ptr.setStatus(RequestStatus.ON_HOLD);
		ptr.setContractTime(1);
		ptr.setClub(club);
		ptr.setContract(contract);
		ptr.setFootballPlayer(player);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<PlayerTransferRequest>> constraintViolations = validator.validate(ptr);

		Assertions.assertTrue(constraintViolations.size() == 0);
	}

	@Test
	void shouldNotValidateOffer() throws ParseException {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		FootballClub club = this.createFootballClub();
		ContractPlayer contract = this.createContractPlayer();
		FootballPlayer player = this.createFootballPlayer();

		PlayerTransferRequest ptr = new PlayerTransferRequest();

		ptr.setId(1);
		ptr.setOffer(null);
		ptr.setStatus(RequestStatus.ON_HOLD);
		ptr.setContractTime(1);
		ptr.setClub(club);
		ptr.setContract(contract);
		ptr.setFootballPlayer(player);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<PlayerTransferRequest>> constraintViolations = validator.validate(ptr);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<PlayerTransferRequest> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("offer") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be null") == 0);
	}

	@Test
	void shouldNotValidateMinimumContractTime() throws ParseException {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		FootballClub club = this.createFootballClub();
		ContractPlayer contract = this.createContractPlayer();
		FootballPlayer player = this.createFootballPlayer();

		PlayerTransferRequest ptr = new PlayerTransferRequest();

		ptr.setId(1);
		ptr.setOffer(2000000L);
		ptr.setStatus(RequestStatus.ON_HOLD);
		ptr.setContractTime(0);
		ptr.setClub(club);
		ptr.setContract(contract);
		ptr.setFootballPlayer(player);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<PlayerTransferRequest>> constraintViolations = validator.validate(ptr);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<PlayerTransferRequest> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("contractTime") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must be greater than or equal to 1") == 0);
	}

	@Test
	void shouldNotValidateMaximumContractTime() throws ParseException {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		FootballClub club = this.createFootballClub();
		ContractPlayer contract = this.createContractPlayer();
		FootballPlayer player = this.createFootballPlayer();

		PlayerTransferRequest ptr = new PlayerTransferRequest();

		ptr.setId(1);
		ptr.setOffer(2000000L);
		ptr.setStatus(RequestStatus.ON_HOLD);
		ptr.setContractTime(6);
		ptr.setClub(club);
		ptr.setContract(contract);
		ptr.setFootballPlayer(player);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<PlayerTransferRequest>> constraintViolations = validator.validate(ptr);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<PlayerTransferRequest> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("contractTime") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must be less than or equal to 5") == 0);
	}

	@Test
	void shouldNotValidateNullContractTime() throws ParseException {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		FootballClub club = this.createFootballClub();
		ContractPlayer contract = this.createContractPlayer();
		FootballPlayer player = this.createFootballPlayer();

		PlayerTransferRequest ptr = new PlayerTransferRequest();

		ptr.setId(1);
		ptr.setOffer(2000000L);
		ptr.setStatus(RequestStatus.ON_HOLD);
		ptr.setContractTime(null);
		ptr.setClub(club);
		ptr.setContract(contract);
		ptr.setFootballPlayer(player);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<PlayerTransferRequest>> constraintViolations = validator.validate(ptr);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<PlayerTransferRequest> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("contractTime") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be null") == 0);
	}

}
