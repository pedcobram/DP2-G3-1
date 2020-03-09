
package org.springframework.samples.petclinic.web;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class FootballClubValidator implements Validator {

	private static final String REQUIRED = "required";


	@Override
	public void validate(final Object obj, final Errors errors) {
		FootballClub footballClub = (FootballClub) obj;

		String Name = footballClub.getName();
		String City = footballClub.getCity();
		String Stadium = footballClub.getStadium();
		Date now = new Date(System.currentTimeMillis() - 1);

		// Name Validation
		if (!StringUtils.hasLength(Name) || Name.length() > 50 || Name.length() < 3) {
			errors.rejectValue("name", "code.error.validator.requiredAndLength", "required" + " and between 3 and 50 character");
		}

		// City not Empty Validation
		if (!StringUtils.hasLength(City) || City.length() > 50 || City.length() < 3) {
			errors.rejectValue("city", "code.error.validator.requiredAndLength", "required" + " and between 3 and 50 character");
		}

		// Stadium not Empty Validation
		if (!StringUtils.hasLength(Stadium) || Stadium.length() > 50 || Stadium.length() < 3) {
			errors.rejectValue("stadium", "code.error.validator.requiredAndLength", "required" + " and between 3 and 50 character");
		}

		// Foundation Date Validation
		if (footballClub.getFoundationDate() == null || footballClub.getFoundationDate().after(now)) {
			errors.rejectValue("foundationDate", "code.error.validator.requiredAndPast", FootballClubValidator.REQUIRED);
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
