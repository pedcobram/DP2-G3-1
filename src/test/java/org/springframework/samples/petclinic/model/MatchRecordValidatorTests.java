
package org.springframework.samples.petclinic.model;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.model.Enum.MatchRecordStatus;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class MatchRecordValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateTitle() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		MatchRecord mr = new MatchRecord();

		mr.setTitle("");
		mr.setResult("result");
		mr.setStatus(MatchRecordStatus.NOT_PUBLISHED);
		mr.setSeason_end("2000");
		mr.setSeason_start("2000");
		mr.setMatch(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<MatchRecord>> constraintViolations = validator.validate(mr);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<MatchRecord> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("title") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be blank") == 0);

	}

	@Test
	void shouldNotValidateStatus() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		MatchRecord mr = new MatchRecord();

		mr.setTitle("title");
		mr.setResult("result");
		mr.setStatus(null);
		mr.setSeason_end("2000");
		mr.setSeason_start("2000");
		mr.setMatch(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<MatchRecord>> constraintViolations = validator.validate(mr);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<MatchRecord> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("status") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be null") == 0);

	}

	@Test
	void shouldNotValidateSeasonStart() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		MatchRecord mr = new MatchRecord();

		mr.setTitle("title");
		mr.setResult("result");
		mr.setStatus(MatchRecordStatus.NOT_PUBLISHED);
		mr.setSeason_end("2020");
		mr.setSeason_start("1");
		mr.setMatch(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<MatchRecord>> constraintViolations = validator.validate(mr);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<MatchRecord> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("season_start") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("Must be a four digit number") == 0);

	}

	@Test
	void shouldNotValidateSeasonEnd() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		MatchRecord mr = new MatchRecord();

		mr.setTitle("title");
		mr.setResult("result");
		mr.setStatus(MatchRecordStatus.NOT_PUBLISHED);
		mr.setSeason_end("1");
		mr.setSeason_start("2020");
		mr.setMatch(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<MatchRecord>> constraintViolations = validator.validate(mr);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<MatchRecord> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("season_end") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("Must be a four digit number") == 0);

	}

	@Test
	void shouldValidateMatchRecord() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		MatchRecord mr = new MatchRecord();

		mr.setTitle("title");
		mr.setResult("result");
		mr.setStatus(MatchRecordStatus.NOT_PUBLISHED);
		mr.setSeason_end("2019");
		mr.setSeason_start("2020");
		mr.setMatch(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<MatchRecord>> constraintViolations = validator.validate(mr);

		Assertions.assertTrue(constraintViolations.size() == 0);

	}

}
