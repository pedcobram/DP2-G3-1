
package org.springframework.samples.petclinic.match;

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
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.Enum.MatchStatus;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class MatchValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateDate() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Match m = new Match();

		m.setCreator("creator");
		m.setFootballClub1(null);
		m.setFootballClub2(null);
		m.setMatchDate(null);
		m.setMatchRecord(null);
		m.setMatchStatus(MatchStatus.TO_BE_PLAYED);
		m.setReferee(null);
		m.setStadium("Stadium");
		m.setTitle("title");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Match>> constraintViolations = validator.validate(m);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<Match> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("matchDate") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be null") == 0);
	}

	@Test
	void shouldNotValidateStadium() throws ParseException {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Match m = new Match();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date d = sdf.parse("2020/03/18 20:30");

		m.setCreator("creator");
		m.setFootballClub1(null);
		m.setFootballClub2(null);
		m.setMatchDate(d);
		m.setMatchRecord(null);
		m.setMatchStatus(MatchStatus.TO_BE_PLAYED);
		m.setReferee(null);
		m.setStadium(null);
		m.setTitle("title");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Match>> constraintViolations = validator.validate(m);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<Match> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("stadium") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be empty") == 0);
	}

	@Test
	void shouldValidateMatch() throws ParseException {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Match m = new Match();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date d = sdf.parse("2020/03/18 20:30");

		m.setTitle("title");
		m.setStadium("Stadium");
		m.setMatchDate(d);
		m.setMatchStatus(MatchStatus.TO_BE_PLAYED);
		m.setCreator("creator");
		m.setFootballClub1(null);
		m.setFootballClub2(null);
		m.setMatchRecord(null);
		m.setReferee(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Match>> constraintViolations = validator.validate(m);

		Assertions.assertTrue(constraintViolations.size() == 0);

	}

}
