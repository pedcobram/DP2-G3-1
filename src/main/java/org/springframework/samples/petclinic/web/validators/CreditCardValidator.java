
package org.springframework.samples.petclinic.web.validators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		if (ccDate.isEmpty() || ccDate == null || ccDate.matches("^(1[0-2]|0[1-9]|\\d)\\/(\\d{2})$")) {
			errors.rejectValue("creditCard.expirationDate", "code.validator.creditCard.date");

		}
		//Expirated
		String[] exdate = ccDate.split("/");
		Date date;
		try {
			date = new SimpleDateFormat("dd/MM/yy").parse("01/" + exdate[0] + "/" + exdate[1]);
			Date today = new Date();
			if (date.after(today)) {
				errors.rejectValue("creditCard.expirationDate", "code.validator.creditCard.datePast");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//CVV Validation
		if (ccCVV.isEmpty() || ccCVV == null || ccCVV.matches("^\\d{3}$")) {
			errors.rejectValue("creditCard.cvv", "code.validator.creditCard.cvv");

		}

	}

}
