
package org.springframework.samples.petclinic.web;

import java.util.Calendar;
import java.util.Date;

import org.springframework.samples.petclinic.model.ContractPlayer;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ContractPlayerValidator implements Validator {

	@Override
	public void validate(final Object obj, final Errors errors) {
		ContractPlayer contractPlayer = (ContractPlayer) obj;

		Date now = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.YEAR, +1);
		now = cal.getTime();

		if (contractPlayer.getSalary() == null) {
			errors.rejectValue("salary", "code.error.validator.required", "required");
		}

		if (contractPlayer.getEndDate() == null || contractPlayer.getEndDate().before(now)) {
			errors.rejectValue("endDate", "code.error.validator.requiredAnd1YearContract", "required");
		}
	}

	@Override
	public boolean supports(final Class<?> clazz) {
		return ContractPlayer.class.isAssignableFrom(clazz);
	}

}
