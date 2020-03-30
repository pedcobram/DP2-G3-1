
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
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.Enum.FootballPlayerPosition;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class FootballPlayerValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenFirstNameEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		FootballPlayer player = new FootballPlayer();

		player.setFirstName("");
		player.setLastName("Maradona");
		player.setValue(10000000);
		player.setPosition(FootballPlayerPosition.MIDFIELDER);

		Date date = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -16);
		date = cal.getTime();

		player.setBirthDate(date);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<FootballPlayer>> constraintViolations = validator.validate(player);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<FootballPlayer> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("firstName");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	@Test
	void shouldNotValidateWhenLastNameEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		FootballPlayer player = new FootballPlayer();

		player.setFirstName("Diego Armando");
		player.setLastName("");
		player.setValue(10000000);
		player.setPosition(FootballPlayerPosition.MIDFIELDER);

		Date date = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -16);
		date = cal.getTime();

		player.setBirthDate(date);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<FootballPlayer>> constraintViolations = validator.validate(player);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<FootballPlayer> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("lastName");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	@Test
	void shouldNotValidateWhenValueNull() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		FootballPlayer player = new FootballPlayer();

		player.setFirstName("Diego Armando");
		player.setLastName("Maradona");
		player.setValue(null);
		player.setPosition(FootballPlayerPosition.MIDFIELDER);

		Date date = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -16);
		date = cal.getTime();

		player.setBirthDate(date);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<FootballPlayer>> constraintViolations = validator.validate(player);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<FootballPlayer> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("value");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	@Test
	void shouldNotValidateWhenPositionNull() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		FootballPlayer player = new FootballPlayer();

		player.setFirstName("Diego Armando");
		player.setLastName("Maradona");
		player.setValue(10000000);
		player.setPosition(null);

		Date date = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -16);
		date = cal.getTime();

		player.setBirthDate(date);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<FootballPlayer>> constraintViolations = validator.validate(player);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<FootballPlayer> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("position");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	@Test
	void shouldNotValidateWhenBirthDateNull() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		FootballPlayer player = new FootballPlayer();

		player.setFirstName("Diego Armando");
		player.setLastName("Maradona");
		player.setValue(10000000);
		player.setPosition(FootballPlayerPosition.MIDFIELDER);

		Date date = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -16);
		date = cal.getTime();

		player.setBirthDate(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<FootballPlayer>> constraintViolations = validator.validate(player);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<FootballPlayer> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("birthDate");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	@Test
	void shouldValidateWell() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		FootballPlayer player = new FootballPlayer();

		player.setFirstName("Diego Armando");
		player.setLastName("Maradona");
		player.setValue(10000000);
		player.setPosition(FootballPlayerPosition.MIDFIELDER);

		Date date = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -16);
		date = cal.getTime();

		player.setBirthDate(date);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<FootballPlayer>> constraintViolations = validator.validate(player);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(0);
	}

}
