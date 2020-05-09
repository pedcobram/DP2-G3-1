
package org.springframework.samples.petclinic.model.rafa;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.model.Jornada;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class JornadaValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenNameNull() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Jornada jornada = new Jornada();

		jornada.setName(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Jornada>> constraintViolations = validator.validate(jornada);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Jornada> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be null");

	}

	@Test
	void shouldValidateWell() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Jornada jornada = new Jornada();

		jornada.setName("Jornada 1");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Jornada>> constraintViolations = validator.validate(jornada);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(0);
	}

}
