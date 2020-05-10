
package org.springframework.samples.petclinic.model.nacho;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.model.Round;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class RoundValidatorTest {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	@Test
	void shouldValidate() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Round r = new Round();
		r.nameRounds(16);
		Validator validator = this.createValidator();
		Set<ConstraintViolation<Round>> constraintViolations = validator.validate(r);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(0);
		//		ConstraintViolation<Fan> violation = constraintViolations.iterator().next();
		//		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("vip");
		//		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be null");
	}
	@Test
	void shouldNotValidateName() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Round r = new Round();
		r.setName("");
		Validator validator = this.createValidator();
		Set<ConstraintViolation<Round>> constraintViolations = validator.validate(r);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Round> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
		Assertions.assertThat(violation.getMessage()).isEqualTo("size must be between 3 and 50");
	}

}
