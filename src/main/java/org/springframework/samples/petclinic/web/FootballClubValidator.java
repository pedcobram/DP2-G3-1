/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class FootballClubValidator implements Validator {

	private static final String REQUIRED = "required";


	@Override
	public void validate(final Object obj, final Errors errors) {
		FootballClub footballClub = (FootballClub) obj;

		if (footballClub.getFoundationDate() == null) {
			errors.rejectValue("foundationDate", FootballClubValidator.REQUIRED, FootballClubValidator.REQUIRED);
		}
	}

	@Override
	public boolean supports(final Class<?> clazz) {
		return FootballClub.class.isAssignableFrom(clazz);
	}

}
