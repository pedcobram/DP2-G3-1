
package org.springframework.samples.petclinic.model.rafa;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.model.President;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class PresidentValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenFirstNameEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		President president = new President();

		president.setFirstName("");
		president.setLastName("Ruíz de Lopera");
		president.setTelephone("600500400");
		president.setEmail("betisLibre@lopera.com");
		president.setDni("12345678A");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<President>> constraintViolations = validator.validate(president);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<President> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("firstName");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	@Test
	void shouldNotValidateWhenLastNameEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		President president = new President();

		president.setFirstName("Manuel");
		president.setLastName("");
		president.setTelephone("600500400");
		president.setEmail("betisLibre@lopera.com");
		president.setDni("12345678A");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<President>> constraintViolations = validator.validate(president);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<President> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("lastName");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	@Test
	void shouldNotValidateWhenEmailEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		President president = new President();

		president.setFirstName("Manuel");
		president.setLastName("Ruíz de Lopera");
		president.setTelephone("600500400");
		president.setEmail("");
		president.setDni("12345678A");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<President>> constraintViolations = validator.validate(president);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<President> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("email");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	@Test
	void shouldNotValidateWhenTelephoneEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		President president = new President();

		president.setFirstName("Manuel");
		president.setLastName("Ruíz de Lopera");
		president.setTelephone("");
		president.setEmail("betisLibre@lopera.com");
		president.setDni("12345678A");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<President>> constraintViolations = validator.validate(president);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(2);
		ConstraintViolation<President> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("telephone");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	@Test
	void shouldNotValidateWhenTelephoneNotInRange() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		President president = new President();

		president.setFirstName("Manuel");
		president.setLastName("Ruíz de Lopera");
		president.setTelephone("11111111111");
		president.setEmail("betisLibre@lopera.com");
		president.setDni("12345678A");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<President>> constraintViolations = validator.validate(president);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<President> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("telephone");
		Assertions.assertThat(violation.getMessage()).isEqualTo("numeric value out of bounds (<10 digits>.<0 digits> expected)");
	}

	@Test
	void shouldNotValidateWhenDNIEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		President president = new President();

		president.setFirstName("Manuel");
		president.setLastName("Ruíz de Lopera");
		president.setTelephone("600500400");
		president.setEmail("betisLibre@lopera.com");
		president.setDni("");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<President>> constraintViolations = validator.validate(president);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(2);
		ConstraintViolation<President> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("dni");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	@Test
	void shouldNotValidateWhenDNIBadPattern() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		President president = new President();

		president.setFirstName("Manuel");
		president.setLastName("Ruíz de Lopera");
		president.setTelephone("600500400");
		president.setEmail("betisLibre@lopera.com");
		president.setDni("asdsada");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<President>> constraintViolations = validator.validate(president);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<President> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("dni");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must match \"[0-9]{7,8}[A-Za-z]\"");
	}

	@Test
	void shouldValidateWell() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		President president = new President();

		president.setFirstName("Manuel");
		president.setLastName("Ruíz de Lopera");
		president.setTelephone("600500400");
		president.setEmail("betisLibre@lopera.com");
		president.setDni("12345678A");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<President>> constraintViolations = validator.validate(president);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(0);

	}

}
