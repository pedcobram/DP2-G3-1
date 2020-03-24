
package org.springframework.samples.petclinic.model.nacho;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.model.Fan;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class FanValidatorTest {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	@Test
	void shouldNotValidateWhenVIPNull() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Fan f = new Fan();

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Fan>> constraintViolations = validator.validate(f);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(5);
		ConstraintViolation<Fan> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("vip");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

}
