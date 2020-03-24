
package org.springframework.samples.petclinic.web.validators;

import org.springframework.samples.petclinic.model.ContractPlayer;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ContractPlayerValidator implements Validator {

	@Override
	public void validate(final Object obj, final Errors errors) {
		ContractPlayer contractPlayer = (ContractPlayer) obj;

		if (contractPlayer.getSalary() == null) {
			errors.rejectValue("salary", "code.error.validator.required", "required");
		}

		if (contractPlayer.getEndDate() == null) {
			errors.rejectValue("endDate", "code.error.validator.required", "required");
		}
	}

	@Override
	public boolean supports(final Class<?> clazz) {
		return ContractPlayer.class.isAssignableFrom(clazz);
	}

}
