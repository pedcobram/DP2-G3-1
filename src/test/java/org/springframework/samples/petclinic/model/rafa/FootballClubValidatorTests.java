
package org.springframework.samples.petclinic.model.rafa;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.President;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class FootballClubValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenNameEmpty() {

		President president = new President();

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		FootballClub club = new FootballClub();

		club.setStatus(false);
		club.setFans(0);
		club.setPresident(president);
		club.setName("");
		club.setCity("London");
		club.setStadium("Stamford Bridge");
		club.setMoney(1000000);

		Date date = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -1);
		date = cal.getTime();

		club.setFoundationDate(date);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<FootballClub>> constraintViolations = validator.validate(club);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<FootballClub> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	@Test
	void shouldNotValidateWhenStadiumEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		FootballClub club = new FootballClub();

		President president = new President();
		club.setStatus(false);
		club.setFans(0);
		club.setPresident(president);
		club.setName("Chelsea");
		club.setCity("London");
		club.setStadium("");
		club.setMoney(1000000);

		Date date = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -1);
		date = cal.getTime();

		club.setFoundationDate(date);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<FootballClub>> constraintViolations = validator.validate(club);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<FootballClub> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("stadium");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	@Test
	void shouldNotValidateWhenCityEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		FootballClub club = new FootballClub();

		President president = new President();
		club.setStatus(false);
		club.setFans(0);
		club.setPresident(president);
		club.setName("Chelsea");
		club.setCity("");
		club.setStadium("Stamford Bridge");
		club.setMoney(1000000);

		Date date = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -1);
		date = cal.getTime();

		club.setFoundationDate(date);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<FootballClub>> constraintViolations = validator.validate(club);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<FootballClub> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("city");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	@Test
	void shouldNotValidateWhenMoneyNull() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		FootballClub club = new FootballClub();

		President president = new President();
		club.setStatus(false);
		club.setFans(0);
		club.setPresident(president);
		club.setName("Chelsea");
		club.setCity("London");
		club.setStadium("Stamford Bridge");
		club.setMoney(null);

		Date date = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -1);
		date = cal.getTime();

		club.setFoundationDate(date);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<FootballClub>> constraintViolations = validator.validate(club);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<FootballClub> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("money");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	@Test
	void shouldNotValidateWhenFoundationDateNull() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		FootballClub club = new FootballClub();

		President president = new President();
		club.setStatus(false);
		club.setFans(0);
		club.setPresident(president);
		club.setName("Chelsea");
		club.setCity("London");
		club.setStadium("Stamford Bridge");
		club.setMoney(1000000);

		Date date = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -1);
		date = cal.getTime();

		club.setFoundationDate(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<FootballClub>> constraintViolations = validator.validate(club);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<FootballClub> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("foundationDate");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	@Test
	void shouldNotValidateWhenCrestNotURL() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		FootballClub club = new FootballClub();

		President president = new President();
		club.setStatus(false);
		club.setFans(0);
		club.setPresident(president);
		club.setName("Chelsea");
		club.setCity("London");
		club.setStadium("Stamford Bridge");
		club.setMoney(1000000);

		Date date = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -1);
		date = cal.getTime();

		club.setFoundationDate(date);
		club.setCrest("asd");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<FootballClub>> constraintViolations = validator.validate(club);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<FootballClub> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("crest");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must be a valid URL");
	}

	@Test
	void shouldValidateWell() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		FootballClub club = new FootballClub();

		President president = new President();
		club.setStatus(false);
		club.setFans(0);
		club.setPresident(president);
		club.setName("Chelsea");
		club.setCity("London");
		club.setStadium("Stamford Bridge");
		club.setMoney(1000000);

		Date date = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -1);
		date = cal.getTime();

		club.setFoundationDate(date);
		club.setCrest("https://www.example.com");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<FootballClub>> constraintViolations = validator.validate(club);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(0);
	}

}
