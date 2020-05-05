
package org.springframework.samples.petclinic.model.pedro;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.model.Coach;
import org.springframework.samples.petclinic.model.CoachTransferRequest;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class CoachTransferRequestValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	private President createPresident(final String presidentName, final String phone) {

		User user = new User();

		user.setEnabled(true);
		user.setUsername("president");
		user.setPassword("president");

		President president = new President();

		president.setId(1);
		president.setDni("11111111A");
		president.setFirstName(presidentName);
		president.setLastName(presidentName);
		president.setEmail(presidentName + "@gmail.com");
		president.setTelephone(phone);
		president.setUser(user);

		return president;
	}

	private FootballClub createFootballClub(final Integer clubId, final President president) {

		Calendar foundationDate = Calendar.getInstance();

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

	private Coach createCoach(final String coachName, final FootballClub club) {

		Calendar birthDate = Calendar.getInstance();

		User user = new User();

		user.setEnabled(true);
		user.setUsername(coachName);
		user.setPassword(coachName);

		Coach c = new Coach();

		c.setId(1);
		c.setFirstName(coachName);
		c.setLastName(coachName);
		c.setBirthDate(birthDate.getTime());
		c.setClause(100000);
		c.setClub(club);

		return c;
	}

	@Test
	void shouldValidateWell() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		President presi1 = this.createPresident("President1", "111111111");
		FootballClub club1 = this.createFootballClub(0, presi1);
		Coach myCoach = this.createCoach("Coach1", club1);

		President presi2 = this.createPresident("President2", "222222222");
		FootballClub club2 = this.createFootballClub(0, presi2);
		Coach requestedCoach = this.createCoach("Coach2", club2);

		CoachTransferRequest ctr = new CoachTransferRequest();

		ctr.setId(1);
		ctr.setOffer(2000000L);
		ctr.setStatus(RequestStatus.ON_HOLD);
		ctr.setMyCoach(myCoach);
		ctr.setRequestedCoach(requestedCoach);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<CoachTransferRequest>> constraintViolations = validator.validate(ctr);

		Assertions.assertTrue(constraintViolations.size() == 0);
	}

	@Test
	void shouldNotValidateOffer() throws ParseException {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		President presi1 = this.createPresident("President1", "111111111");
		FootballClub club1 = this.createFootballClub(0, presi1);
		Coach myCoach = this.createCoach("Coach1", club1);

		President presi2 = this.createPresident("President2", "222222222");
		FootballClub club2 = this.createFootballClub(0, presi2);
		Coach requestedCoach = this.createCoach("Coach2", club2);

		CoachTransferRequest ctr = new CoachTransferRequest();

		ctr.setId(1);
		ctr.setOffer(null);
		ctr.setStatus(RequestStatus.ON_HOLD);
		ctr.setMyCoach(myCoach);
		ctr.setRequestedCoach(requestedCoach);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<CoachTransferRequest>> constraintViolations = validator.validate(ctr);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<CoachTransferRequest> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("offer") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be null") == 0);
	}

	@Test
	void shouldNotValidateMinimumOffer() throws ParseException {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		President presi1 = this.createPresident("President1", "111111111");
		FootballClub club1 = this.createFootballClub(0, presi1);
		Coach myCoach = this.createCoach("Coach1", club1);

		President presi2 = this.createPresident("President2", "222222222");
		FootballClub club2 = this.createFootballClub(0, presi2);
		Coach requestedCoach = this.createCoach("Coach2", club2);

		CoachTransferRequest ctr = new CoachTransferRequest();

		ctr.setId(1);
		ctr.setOffer(10L);
		ctr.setStatus(RequestStatus.ON_HOLD);
		ctr.setMyCoach(myCoach);
		ctr.setRequestedCoach(requestedCoach);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<CoachTransferRequest>> constraintViolations = validator.validate(ctr);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<CoachTransferRequest> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("offer") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must be greater than or equal to 1000000") == 0);
	}

	@Test
	void shouldNotValidateMaximumOffer() throws ParseException {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		President presi1 = this.createPresident("President1", "111111111");
		FootballClub club1 = this.createFootballClub(0, presi1);
		Coach myCoach = this.createCoach("Coach1", club1);

		President presi2 = this.createPresident("President2", "222222222");
		FootballClub club2 = this.createFootballClub(0, presi2);
		Coach requestedCoach = this.createCoach("Coach2", club2);

		CoachTransferRequest ctr = new CoachTransferRequest();

		ctr.setId(1);
		ctr.setOffer(50000000L);
		ctr.setStatus(RequestStatus.ON_HOLD);
		ctr.setMyCoach(myCoach);
		ctr.setRequestedCoach(requestedCoach);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<CoachTransferRequest>> constraintViolations = validator.validate(ctr);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<CoachTransferRequest> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("offer") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must be less than or equal to 25000000") == 0);
	}

}
