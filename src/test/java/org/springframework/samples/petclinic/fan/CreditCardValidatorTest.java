
package org.springframework.samples.petclinic.fan;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.datatypes.CreditCard;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class CreditCardValidatorTest {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldValidate() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		CreditCard cc = new CreditCard();
		cc.setCreditCardNumber("5439150646899040");
		cc.setExpirationDate("11/23");
		cc.setCvv("854");
		Validator validator = this.createValidator();
		Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(cc);

		Assertions.assertTrue(constraintViolations.size() == 0);
		//		ConstraintViolation<Fan> violation = constraintViolations.iterator().next();
		//		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("vip");
		//		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	@Test
	void shouldNotValidateCCNumber() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		CreditCard cc = new CreditCard();
		cc.setCreditCardNumber(null);
		cc.setExpirationDate("11/23");
		cc.setCvv("854");
		Validator validator = this.createValidator();
		Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(cc);

		Assertions.assertTrue(constraintViolations.size() == 1);
		ConstraintViolation<CreditCard> violation = constraintViolations.iterator().next();
		System.out.println(violation.getMessage());
		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("creditCardNumber") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be empty") == 0);

	}
	@Test
	void shouldNotValidateCCDate() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		CreditCard cc = new CreditCard();
		cc.setCreditCardNumber("5439150646899040");
		cc.setExpirationDate("");
		cc.setCvv("854");
		Validator validator = this.createValidator();
		Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(cc);

		Assertions.assertTrue(constraintViolations.size() == 2);
		ConstraintViolation<CreditCard> violation = constraintViolations.iterator().next();
		System.out.println(violation.getMessage());
		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("expirationDate") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be empty") == 0 || violation.getMessage().compareTo("“MM/YY”") == 0);

	}
	@Test
	void shouldNotValidateCCcvv() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		CreditCard cc = new CreditCard();
		cc.setCreditCardNumber("5439150646899040");
		cc.setExpirationDate("11/23");
		cc.setCvv("");
		Validator validator = this.createValidator();
		Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(cc);

		Assertions.assertTrue(constraintViolations.size() == 2);
		ConstraintViolation<CreditCard> violation = constraintViolations.iterator().next();
		System.out.println(violation.getMessage());
		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("cvv") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be empty") == 0 || violation.getMessage().compareTo("“999”") == 0);

	}

}
