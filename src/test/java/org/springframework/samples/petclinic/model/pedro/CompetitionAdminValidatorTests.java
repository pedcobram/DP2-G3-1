
package org.springframework.samples.petclinic.model.pedro;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.model.CompetitionAdmin;
import org.springframework.samples.petclinic.model.User;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class CompetitionAdminValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateTelephone() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		User u = new User();

		u.setEnabled(true);
		u.setPassword("password");
		u.setUsername("username");

		CompetitionAdmin ca = new CompetitionAdmin();

		ca.setTelephone("");
		ca.setDni("49032196Z");
		ca.setEmail("test@gmail.com");
		ca.setFirstName("First Name");
		ca.setLastName("Last Name");
		ca.setUser(u);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<CompetitionAdmin>> constraintViolations = validator.validate(ca);

		Assertions.assertTrue(constraintViolations.size() == 2);

		ConstraintViolation<CompetitionAdmin> violation = constraintViolations.iterator().next();
		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("telephone") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be empty") == 0);

	}

	@Test
	void shouldNotValidateEmail() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		User u = new User();

		u.setEnabled(true);
		u.setPassword("password");
		u.setUsername("username");

		CompetitionAdmin ca = new CompetitionAdmin();

		ca.setTelephone("123456789");
		ca.setDni("12345678A");
		ca.setEmail("");
		ca.setFirstName("First Name");
		ca.setLastName("Last Name");
		ca.setUser(u);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<CompetitionAdmin>> constraintViolations = validator.validate(ca);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<CompetitionAdmin> violation = constraintViolations.iterator().next();
		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("email") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be empty") == 0);

	}

	@Test
	void shouldNotValidateDNI() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		User u = new User();

		u.setEnabled(true);
		u.setPassword("password");
		u.setUsername("username");

		CompetitionAdmin ca = new CompetitionAdmin();

		ca.setTelephone("123456789");
		ca.setDni("");
		ca.setEmail("test@gmail.com");
		ca.setFirstName("First Name");
		ca.setLastName("Last Name");
		ca.setUser(u);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<CompetitionAdmin>> constraintViolations = validator.validate(ca);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<CompetitionAdmin> violation = constraintViolations.iterator().next();
		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("dni") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be empty") == 0);

	}

	@Test
	void shouldValidate() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		User u = new User();

		u.setEnabled(true);
		u.setPassword("password");
		u.setUsername("username");

		CompetitionAdmin ca = new CompetitionAdmin();

		ca.setTelephone("123456789");
		ca.setDni("12345678A");
		ca.setEmail("test@gmail.com");
		ca.setFirstName("First Name");
		ca.setLastName("Last Name");
		ca.setUser(u);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<CompetitionAdmin>> constraintViolations = validator.validate(ca);

		Assertions.assertTrue(constraintViolations.size() == 0);

	}

}
