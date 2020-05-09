
package org.springframework.samples.petclinic.model.rafa;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.model.Competition;
import org.springframework.samples.petclinic.model.Enum.CompetitionType;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class CompetitionValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenNameEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Competition comp = new Competition();

		comp.setName("");
		comp.setDescription("Description");
		comp.setReward(5000000);
		comp.setType(CompetitionType.LEAGUE);
		comp.setCreator("Klose");
		comp.setStatus(false);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Competition>> constraintViolations = validator.validate(comp);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Competition> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	@Test
	void shouldNotValidateWhenDesctriptionEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Competition comp = new Competition();

		comp.setName("Bundesliga");
		comp.setDescription("");
		comp.setReward(5000000);
		comp.setType(CompetitionType.LEAGUE);
		comp.setCreator("Klose");
		comp.setStatus(false);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Competition>> constraintViolations = validator.validate(comp);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Competition> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("description");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	@Test
	void shouldNotValidateWhenRewardNull() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Competition comp = new Competition();

		comp.setName("Bundesliga");
		comp.setDescription("Description");
		comp.setReward(null);
		comp.setType(CompetitionType.LEAGUE);
		comp.setCreator("Klose");
		comp.setStatus(false);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Competition>> constraintViolations = validator.validate(comp);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Competition> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("reward");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	@Test
	void shouldNotValidateWhenCreatorNull() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Competition comp = new Competition();

		comp.setName("Bundesliga");
		comp.setDescription("Description");
		comp.setReward(5000000);
		comp.setType(CompetitionType.LEAGUE);
		comp.setCreator(null);
		comp.setStatus(false);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Competition>> constraintViolations = validator.validate(comp);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Competition> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("creator");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	@Test
	void shouldNotValidateWhenTypeNull() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Competition comp = new Competition();

		comp.setName("Bundesliga");
		comp.setDescription("Description");
		comp.setReward(5000000);
		comp.setType(null);
		comp.setCreator("Klose");
		comp.setStatus(false);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Competition>> constraintViolations = validator.validate(comp);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Competition> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("type");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	@Test
	void shouldValidateWell() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Competition comp = new Competition();

		comp.setName("Bundesliga");
		comp.setDescription("Description");
		comp.setReward(5000000);
		comp.setType(CompetitionType.LEAGUE);
		comp.setCreator("Klose");
		comp.setStatus(false);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Competition>> constraintViolations = validator.validate(comp);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(0);
	}

}
