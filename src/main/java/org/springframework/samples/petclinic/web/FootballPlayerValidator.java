
package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class FootballPlayerValidator implements Validator {

	private static final String REQUIRED = "required";


	@Override
	public void validate(final Object obj, final Errors errors) {
		FootballPlayer footballPlayer = (FootballPlayer) obj;
		String firstName = footballPlayer.getFirstName();

		String lastName = footballPlayer.getLastName();
		// firstname validation
		if (!StringUtils.hasLength(firstName) || firstName.length() > 50 || firstName.length() < 2) {
			errors.rejectValue("firstName", FootballPlayerValidator.REQUIRED + " and between 2 and 50 characters", FootballPlayerValidator.REQUIRED + " and between 2 and 50 character");
		}

		// lastname validation
		if (!StringUtils.hasLength(lastName) || lastName.length() > 50 || lastName.length() < 2) {
			errors.rejectValue("lastName", FootballPlayerValidator.REQUIRED + " and between 2 and 50 characters", FootballPlayerValidator.REQUIRED + " and between 2 and 50 character");
		}

		// position validation
		if (footballPlayer.isNew() && footballPlayer.getPosition() == null) {
			errors.rejectValue("position", FootballPlayerValidator.REQUIRED, FootballPlayerValidator.REQUIRED);
		}

		// birth date validation
		if (footballPlayer.getBirthDate() == null) {
			errors.rejectValue("birthDate", FootballPlayerValidator.REQUIRED, FootballPlayerValidator.REQUIRED);
		}

	}

	@Override
	public boolean supports(final Class<?> clazz) {
		return FootballPlayer.class.isAssignableFrom(clazz);
	}

}
