
package org.springframework.samples.petclinic.model.rafa;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.model.Calendary;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class CalendaryValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldValidateWell() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Calendary ca = new Calendary();

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Calendary>> constraintViolations = validator.validate(ca);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(0);

	}

}
