
package org.springframework.samples.petclinic.web.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class FootballClubValidator implements Validator {

	private static final String	REQUIRED				= "required";
	private static final String	URL_VALIDATOR_LENGTH	= "code.error.validator.requiredAndLength350";


	@Override
	public void validate(final Object obj, final Errors errors) {
		FootballClub footballClub = (FootballClub) obj;

		String Name = footballClub.getName();
		String City = footballClub.getCity();
		String Stadium = footballClub.getStadium();

		// Name Validation
		if (!StringUtils.hasLength(Name) || Name.length() > 50 || Name.length() < 3) {
			errors.rejectValue("name", FootballClubValidator.URL_VALIDATOR_LENGTH);
		}

		// City not Empty Validation
		if (!StringUtils.hasLength(City) || City.length() > 50 || City.length() < 3) {
			errors.rejectValue("city", FootballClubValidator.URL_VALIDATOR_LENGTH);
		}

		// Stadium not Empty Validation
		if (!StringUtils.hasLength(Stadium) || Stadium.length() > 50 || Stadium.length() < 3) {
			errors.rejectValue("stadium", FootballClubValidator.URL_VALIDATOR_LENGTH);
		}

		// Foundation Date Validation
		if (footballClub.getFoundationDate() == null) {
			errors.rejectValue("foundationDate", "code.error.validator.required");
		}

		Pattern p1 = Pattern.compile("(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
		Matcher m1 = p1.matcher(footballClub.getCrest());

		// URL Validation
		if (StringUtils.hasLength(footballClub.getCrest()) && !m1.matches()) {
			errors.rejectValue("crest", "code.error.validator.url", FootballClubValidator.REQUIRED);
		}

	}

	@Override
	public boolean supports(final Class<?> clazz) {
		return FootballClub.class.isAssignableFrom(clazz);
	}

}
