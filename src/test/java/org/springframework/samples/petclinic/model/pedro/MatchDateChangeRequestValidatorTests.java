
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
import org.springframework.samples.petclinic.model.MatchDateChangeRequest;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class MatchDateChangeRequestValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateTitle() throws ParseException {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		MatchDateChangeRequest mdcr = new MatchDateChangeRequest();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date matchDate = sdf.parse("2021/03/18 20:30");

		mdcr.setTitle(null);
		mdcr.setNew_date(matchDate);
		mdcr.setStatus(RequestStatus.ON_HOLD);
		mdcr.setReason("reason");
		mdcr.setRequest_creator("president1");
		mdcr.setMatch(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<MatchDateChangeRequest>> constraintViolations = validator.validate(mdcr);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<MatchDateChangeRequest> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("title") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be null") == 0);

	}

	@Test
	void shouldNotValidateNewDate() throws ParseException {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		MatchDateChangeRequest mdcr = new MatchDateChangeRequest();

		//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		//		Date matchDate = sdf.parse("2021/03/18 20:30");

		mdcr.setTitle("title");
		mdcr.setNew_date(null);
		mdcr.setStatus(RequestStatus.ON_HOLD);
		mdcr.setReason("reason");
		mdcr.setRequest_creator("president1");
		mdcr.setMatch(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<MatchDateChangeRequest>> constraintViolations = validator.validate(mdcr);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<MatchDateChangeRequest> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("new_date") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be null") == 0);

	}

	@Test
	void shouldNotValidateReason() throws ParseException {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		MatchDateChangeRequest mdcr = new MatchDateChangeRequest();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date matchDate = sdf.parse("2021/03/18 20:30");

		mdcr.setTitle("title");
		mdcr.setNew_date(matchDate);
		mdcr.setStatus(RequestStatus.ON_HOLD);
		mdcr.setReason(null);
		mdcr.setRequest_creator("president1");
		mdcr.setMatch(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<MatchDateChangeRequest>> constraintViolations = validator.validate(mdcr);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<MatchDateChangeRequest> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("reason") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be null") == 0);

	}

	@Test
	void shouldNotValidateRequestCreator() throws ParseException {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		MatchDateChangeRequest mdcr = new MatchDateChangeRequest();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date matchDate = sdf.parse("2021/03/18 20:30");

		mdcr.setTitle("title");
		mdcr.setNew_date(matchDate);
		mdcr.setStatus(RequestStatus.ON_HOLD);
		mdcr.setReason("reason");
		mdcr.setRequest_creator(null);
		mdcr.setMatch(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<MatchDateChangeRequest>> constraintViolations = validator.validate(mdcr);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<MatchDateChangeRequest> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("request_creator") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be null") == 0);

	}

	@Test
	void shouldValidateWell() throws ParseException {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		MatchDateChangeRequest mdcr = new MatchDateChangeRequest();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date matchDate = sdf.parse("2021/03/18 20:30");

		mdcr.setTitle("title");
		mdcr.setNew_date(matchDate);
		mdcr.setStatus(RequestStatus.ON_HOLD);
		mdcr.setReason("reason");
		mdcr.setRequest_creator("president1");
		mdcr.setMatch(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<MatchDateChangeRequest>> constraintViolations = validator.validate(mdcr);

		Assertions.assertTrue(constraintViolations.size() == 0);

	}

}
