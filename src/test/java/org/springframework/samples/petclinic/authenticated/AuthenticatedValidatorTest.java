
package org.springframework.samples.petclinic.authenticated;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class AuthenticatedValidatorTest {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	@Test
	void shouldValidateAuthenticated() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Authenticated a = new Authenticated();
		a.setDni("25847874E");
		a.setEmail("nacho@gmail.com");
		a.setFirstName("nacho");
		a.setLastName("rdgz");
		a.setTelephone("354897878");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Authenticated>> constraintViolations = validator.validate(a);

		Assertions.assertTrue(constraintViolations.size() == 0);

	}

	@Test
	void shouldNotValidateWhenDniEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Authenticated a = new Authenticated();
		a.setDni("");
		a.setEmail("nacho@gmail.com");
		a.setFirstName("nacho");
		a.setLastName("rdgz");
		a.setTelephone("354897878");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Authenticated>> constraintViolations = validator.validate(a);

		Assertions.assertTrue(constraintViolations.size() == 2);
		ConstraintViolation<Authenticated> violation = constraintViolations.iterator().next();
		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("dni") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be empty") == 0 || violation.getMessage().compareTo("must match \"[0-9]{7,8}[A-Za-z]\"") == 0);

	}
	@Test
	void shouldNotValidateWhenDniNotPattern() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Authenticated a = new Authenticated();
		a.setDni("25847s84E");
		a.setEmail("nacho@gmail.com");
		a.setFirstName("nacho");
		a.setLastName("rdgz");
		a.setTelephone("354897878");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Authenticated>> constraintViolations = validator.validate(a);

		Assertions.assertTrue(constraintViolations.size() == 1);
		ConstraintViolation<Authenticated> violation = constraintViolations.iterator().next();
		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("dni") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must match \"[0-9]{7,8}[A-Za-z]\"") == 0);
	}
	@Test
	void shouldNotValidateWhenEmailEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Authenticated a = new Authenticated();
		a.setDni("25847874E");
		a.setEmail("");
		a.setFirstName("nacho");
		a.setLastName("rdgz");
		a.setTelephone("354897878");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Authenticated>> constraintViolations = validator.validate(a);

		Assertions.assertTrue(constraintViolations.size() == 1);
		ConstraintViolation<Authenticated> violation = constraintViolations.iterator().next();
		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("email") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be empty") == 0);
	}
	void shouldNotValidateWhenFirstNameEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Authenticated a = new Authenticated();
		a.setDni("25847874E");
		a.setEmail("nacho@gmail.com");
		a.setFirstName("");
		a.setLastName("rdgz");
		a.setTelephone("354897878");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Authenticated>> constraintViolations = validator.validate(a);

		Assertions.assertTrue(constraintViolations.size() == 1);
		ConstraintViolation<Authenticated> violation = constraintViolations.iterator().next();
		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("firstName") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be empty") == 0);
	}
	void shouldNotValidateWhenLastNameEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Authenticated a = new Authenticated();
		a.setDni("25847874E");
		a.setEmail("nacho@gmail.com");
		a.setFirstName("nacho");
		a.setLastName("");
		a.setTelephone("354897878");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Authenticated>> constraintViolations = validator.validate(a);

		Assertions.assertTrue(constraintViolations.size() == 1);
		ConstraintViolation<Authenticated> violation = constraintViolations.iterator().next();
		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("firstName") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be empty") == 0);
	}
	@Test
	void shouldNotValidateWhenTelephoneEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Authenticated a = new Authenticated();
		a.setDni("25847874E");
		a.setEmail("nacho@gmail.com");
		a.setFirstName("nacho");
		a.setLastName("rdgz");
		a.setTelephone("");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Authenticated>> constraintViolations = validator.validate(a);

		Assertions.assertTrue(constraintViolations.size() == 2);
		ConstraintViolation<Authenticated> violation = constraintViolations.iterator().next();
		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("telephone") == 0);

	}

}
