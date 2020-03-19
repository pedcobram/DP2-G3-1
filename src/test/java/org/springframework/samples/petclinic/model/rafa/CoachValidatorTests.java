
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
import org.springframework.samples.petclinic.model.Coach;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class CoachValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenFirstNameEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Coach coach = new Coach();

		coach.setFirstName("");
		coach.setLastName("Caparrós");
		coach.setSalary(10000000);
		coach.setClause(10000000);

		Date date = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -18);
		date = cal.getTime();

		coach.setBirthDate(date);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Coach>> constraintViolations = validator.validate(coach);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Coach> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("firstName");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	@Test
	void shouldNotValidateWhenLastNameEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Coach coach = new Coach();

		coach.setFirstName("Joaquin");
		coach.setLastName("");
		coach.setSalary(10000000);
		coach.setClause(10000000);

		Date date = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -18);
		date = cal.getTime();

		coach.setBirthDate(date);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Coach>> constraintViolations = validator.validate(coach);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Coach> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("lastName");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	@Test
	void shouldNotValidateWhenBirthDateNull() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Coach coach = new Coach();

		coach.setFirstName("Joaquin");
		coach.setLastName("Caparrós");
		coach.setSalary(10000000);
		coach.setClause(10000000);

		Date date = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -18);
		date = cal.getTime();

		coach.setBirthDate(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Coach>> constraintViolations = validator.validate(coach);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Coach> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("birthDate");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	@Test
	void shouldValidateWell() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Coach coach = new Coach();

		coach.setFirstName("Joaquin");
		coach.setLastName("Caparrós");
		coach.setSalary(10000000);
		coach.setClause(10000000);

		Date date = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -18);
		date = cal.getTime();

		coach.setBirthDate(date);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Coach>> constraintViolations = validator.validate(coach);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(0);
	}

}
