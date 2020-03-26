
package org.springframework.samples.petclinic.web.validators;

import java.time.LocalDate;

import org.springframework.samples.petclinic.datatypes.CreditCard;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CreditCardValidator implements Validator {

	@Override
	public boolean supports(final Class<?> clazz) {

		return CreditCard.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		CreditCard cc = (CreditCard) target;

		String ccNumber = cc.getCreditCardNumber();
		String ccDate = cc.getExpirationDate();
		String ccCVV = cc.getCvv();

		//CreditCardNumber Validation
		if (ccNumber.isEmpty() || !ccNumber.matches("\\d+") || ccNumber.length() < 13 || ccNumber.length() > 16 || ccNumber == null) {
			errors.rejectValue("creditCard.creditCardNumber", "code.validator.creditCard.number");
		}
		//ExpiritionDate Validation

		if (ccDate.isEmpty() || ccDate == null || !ccDate.matches("^(1[0-2]|0[1-9]|\\d)\\/(\\d{2})$")) {
			errors.rejectValue("creditCard.expirationDate", "code.validator.creditCard.date");

		} else {
			//Expirated
			String[] exdate = ccDate.split("/");

			LocalDate date = LocalDate.of(2000 + Integer.parseInt(exdate[1]), Integer.parseInt(exdate[0]), 01);
			LocalDate today = LocalDate.now();

			if (date.isBefore(today)) {
				errors.rejectValue("creditCard.expirationDate", "code.validator.creditCard.datePast");
			}
		}

		//CVV Validation
		if (ccCVV.isEmpty() || ccCVV == null || !ccCVV.matches("^\\d{3}$")) {
			errors.rejectValue("creditCard.cvv", "code.validator.creditCard.cvv");

		}

	}

}
