
package org.springframework.samples.petclinic.web.validators;

import org.springframework.samples.petclinic.model.Coach;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CoachValidator implements Validator {

	private static final String REQUIRED = "required";


	@Override
	public void validate(final Object obj, final Errors errors) {
		Coach coach = (Coach) obj;
		String firstName = coach.getFirstName();
		String lastName = coach.getLastName();

		// First Name Validation
		if (!StringUtils.hasLength(firstName) || firstName.length() > 50 || firstName.length() < 2) {
			errors.rejectValue("firstName", "code.error.validator.requiredAndLength250", CoachValidator.REQUIRED + " and between 2 and 50 character");
		}

		// Last Name Validation
		if (!StringUtils.hasLength(lastName) || lastName.length() > 50 || lastName.length() < 2) {
			errors.rejectValue("lastName", "code.error.validator.requiredAndLength250", CoachValidator.REQUIRED + " and between 2 and 50 character");
		}

		// Salary Validation
		if (coach.getSalary() == null) {
			errors.rejectValue("salary", "code.error.validator.required", CoachValidator.REQUIRED);
		}

		// Birth Date Validation
		if (coach.getBirthDate() == null) {
			errors.rejectValue("birthDate", "code.error.validator.required", CoachValidator.REQUIRED);
		}

	}

	@Override
	public boolean supports(final Class<?> clazz) {
		return Coach.class.isAssignableFrom(clazz);
	}

}
