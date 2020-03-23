
package org.springframework.samples.petclinic.model.pedro;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.model.CompAdminRequest;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class CompAdminRequestValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenTitleEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		User u = new User();

		u.setEnabled(true);
		u.setPassword("password");
		u.setUsername("username");

		CompAdminRequest car = new CompAdminRequest();

		car.setTitle("");
		car.setDescription("Description");
		car.setStatus(RequestStatus.ON_HOLD);
		car.setUser(u);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<CompAdminRequest>> constraintViolations = validator.validate(car);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<CompAdminRequest> violation = constraintViolations.iterator().next();
		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("title") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be empty") == 0);

	}

	@Test
	void shouldNotValidateWhenDescriptionEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		User u = new User();

		u.setEnabled(true);
		u.setPassword("password");
		u.setUsername("username");

		CompAdminRequest car = new CompAdminRequest();

		car.setTitle("Title");
		car.setDescription("");
		car.setStatus(RequestStatus.ON_HOLD);
		car.setUser(u);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<CompAdminRequest>> constraintViolations = validator.validate(car);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<CompAdminRequest> violation = constraintViolations.iterator().next();
		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("description") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be empty") == 0);

	}

	@Test
	void shouldValidateWithoutErrors() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		User u = new User();

		u.setEnabled(true);
		u.setPassword("password");
		u.setUsername("username");

		CompAdminRequest car = new CompAdminRequest();

		car.setTitle("Title");
		car.setDescription("Description");
		car.setStatus(RequestStatus.ON_HOLD);
		car.setUser(u);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<CompAdminRequest>> constraintViolations = validator.validate(car);

		Assertions.assertTrue(constraintViolations.size() == 0);

	}

}
