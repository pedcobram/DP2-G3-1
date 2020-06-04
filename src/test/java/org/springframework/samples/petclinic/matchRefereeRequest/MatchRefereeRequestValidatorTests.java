
package org.springframework.samples.petclinic.matchRefereeRequest;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.model.MatchRefereeRequest;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class MatchRefereeRequestValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateTitle() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		MatchRefereeRequest mrr = new MatchRefereeRequest();

		mrr.setTitle("");
		mrr.setStatus(RequestStatus.ON_HOLD);
		mrr.setMatch(null);
		mrr.setReferee(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<MatchRefereeRequest>> constraintViolations = validator.validate(mrr);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<MatchRefereeRequest> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("title") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be empty") == 0);
	}

	@Test
	void shouldNotValidateStatus() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		MatchRefereeRequest mrr = new MatchRefereeRequest();

		mrr.setTitle("title");
		mrr.setStatus(null);
		mrr.setMatch(null);
		mrr.setReferee(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<MatchRefereeRequest>> constraintViolations = validator.validate(mrr);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<MatchRefereeRequest> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("status") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be null") == 0);
	}

}
