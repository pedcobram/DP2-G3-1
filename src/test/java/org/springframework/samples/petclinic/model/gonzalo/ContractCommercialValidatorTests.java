
package org.springframework.samples.petclinic.model.gonzalo;

import java.util.Date;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.model.ContractCommercial;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class ContractCommercialValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenStartDateNull() {

		Date date = new Date(System.currentTimeMillis() - 1);
		//Testing Contract
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		ContractCommercial cc = new ContractCommercial();

		cc.setClause(100000);
		cc.setMoney(10000);
		cc.setStartDate(null);
		cc.setEndDate(date);
		cc.setPublicity("https://www.imagen.com.mx/assets/img/imagen_share.png");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<ContractCommercial>> constraintViolations = validator.validate(cc);

		Assertions.assertTrue(constraintViolations.size() == 1);
		ConstraintViolation<ContractCommercial> violation = constraintViolations.iterator().next();
		Assertions.assertTrue(violation.getPropertyPath().toString().equals("startDate"));
		Assertions.assertTrue(violation.getMessage().equals("must not be null"));
	}

	@Test
	void shouldNotValidateWhenEndDateNull() {

		Date date = new Date(System.currentTimeMillis() - 1);
		//Testing Contract
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		ContractCommercial cc = new ContractCommercial();

		cc.setClause(100000);
		cc.setMoney(10000);
		cc.setStartDate(date);
		cc.setEndDate(null);
		cc.setPublicity("https://www.imagen.com.mx/assets/img/imagen_share.png");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<ContractCommercial>> constraintViolations = validator.validate(cc);

		Assertions.assertTrue(constraintViolations.size() == 1);
		ConstraintViolation<ContractCommercial> violation = constraintViolations.iterator().next();
		Assertions.assertTrue(violation.getPropertyPath().toString().equals("endDate"));
		Assertions.assertTrue(violation.getMessage().equals("must not be null"));
	}

	@Test
	void shouldNotValidateWhenClauseNull() {

		Date date = new Date(System.currentTimeMillis() - 1);
		//Testing Contract
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		ContractCommercial cc = new ContractCommercial();

		cc.setClause(null);
		cc.setMoney(10000);
		cc.setStartDate(date);
		cc.setEndDate(date);
		cc.setPublicity("https://www.imagen.com.mx/assets/img/imagen_share.png");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<ContractCommercial>> constraintViolations = validator.validate(cc);

		Assertions.assertTrue(constraintViolations.size() == 1);
		ConstraintViolation<ContractCommercial> violation = constraintViolations.iterator().next();
		Assertions.assertTrue(violation.getPropertyPath().toString().equals("clause"));
		Assertions.assertTrue(violation.getMessage().equals("must not be null"));
	}

	@Test
	void shouldNotValidateWhenMoneyNull() {

		Date date = new Date(System.currentTimeMillis() - 1);
		//Testing Contract
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		ContractCommercial cc = new ContractCommercial();

		cc.setClause(1000000);
		cc.setMoney(null);
		cc.setStartDate(date);
		cc.setEndDate(date);
		cc.setPublicity("https://www.imagen.com.mx/assets/img/imagen_share.png");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<ContractCommercial>> constraintViolations = validator.validate(cc);

		Assertions.assertTrue(constraintViolations.size() == 1);
		ConstraintViolation<ContractCommercial> violation = constraintViolations.iterator().next();
		Assertions.assertTrue(violation.getPropertyPath().toString().equals("money"));
		Assertions.assertTrue(violation.getMessage().equals("must not be null"));
	}

	@Test
	void shouldNotValidateWhenPublicityNull() {

		Date date = new Date(System.currentTimeMillis() - 1);
		//Testing Contract
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		ContractCommercial cc = new ContractCommercial();

		cc.setClause(100000);
		cc.setMoney(10000);
		cc.setStartDate(date);
		cc.setEndDate(date);
		cc.setPublicity(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<ContractCommercial>> constraintViolations = validator.validate(cc);

		Assertions.assertTrue(constraintViolations.size() == 1);
		ConstraintViolation<ContractCommercial> violation = constraintViolations.iterator().next();
		Assertions.assertTrue(violation.getPropertyPath().toString().equals("publicity"));
		Assertions.assertTrue(violation.getMessage().equals("must not be null"));
	}

	@Test
	void shouldValidateWell() {

		//Creating a Club for the ContractPlayer
		FootballClub club = new FootballClub();
		club.setName("Chelsea");
		club.setCity("London");
		club.setStadium("Stamford Bridge");
		club.setMoney(1000000);
		Date date = new Date(System.currentTimeMillis() - 1);
		club.setFoundationDate(date);

		//Testing Contract
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		ContractCommercial cc = new ContractCommercial();

		cc.setClause(100000);
		cc.setMoney(10000);
		cc.setStartDate(date);
		cc.setEndDate(date);
		cc.setPublicity("https://www.imagen.com.mx/assets/img/imagen_share.png");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<ContractCommercial>> constraintViolations = validator.validate(cc);

		Assertions.assertTrue(constraintViolations.size() == 0);

	}

}
