
package org.springframework.samples.petclinic.model.pedro;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.model.MatchRequest;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class MatchRequestValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateTitle() throws ParseException {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		MatchRequest mr = new MatchRequest();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date matchDate = sdf.parse("2020/03/18 20:30");

		mr.setTitle("");
		mr.setCreator("creator");
		mr.setFootballClub1(null);
		mr.setFootballClub2(null);
		mr.setMatchDate(matchDate);
		mr.setReferee(null);
		mr.setStadium("stadium");
		mr.setStatus(RequestStatus.ON_HOLD);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<MatchRequest>> constraintViolations = validator.validate(mr);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<MatchRequest> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("title") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be blank") == 0);
	}

	@Test
	void shouldNotValidateMatchDate() throws ParseException {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		MatchRequest mr = new MatchRequest();

		mr.setTitle("title");
		mr.setCreator("creator");
		mr.setFootballClub1(null);
		mr.setFootballClub2(null);
		mr.setMatchDate(null);
		mr.setReferee(null);
		mr.setStadium("stadium");
		mr.setStatus(RequestStatus.ON_HOLD);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<MatchRequest>> constraintViolations = validator.validate(mr);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<MatchRequest> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("matchDate") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be null") == 0);
	}

	@Test
	void shouldNotValidateStatus() throws ParseException {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		MatchRequest mr = new MatchRequest();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date matchDate = sdf.parse("2020/03/18 20:30");

		mr.setTitle("title");
		mr.setCreator("creator");
		mr.setStatus(null);
		mr.setFootballClub1(null);
		mr.setFootballClub2(null);
		mr.setMatchDate(matchDate);
		mr.setReferee(null);
		mr.setStadium("stadium");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<MatchRequest>> constraintViolations = validator.validate(mr);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<MatchRequest> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("status") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be null") == 0);
	}

	@Test
	void shouldNotValidateStadium() throws ParseException {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		MatchRequest mr = new MatchRequest();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date matchDate = sdf.parse("2020/03/18 20:30");

		mr.setTitle("title");
		mr.setCreator("creator");
		mr.setFootballClub1(null);
		mr.setFootballClub2(null);
		mr.setMatchDate(matchDate);
		mr.setReferee(null);
		mr.setStadium("");
		mr.setStatus(RequestStatus.ON_HOLD);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<MatchRequest>> constraintViolations = validator.validate(mr);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<MatchRequest> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("stadium") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be empty") == 0);
	}

}
