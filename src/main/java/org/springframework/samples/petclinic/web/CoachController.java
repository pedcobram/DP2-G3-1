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

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Coach;
import org.springframework.samples.petclinic.service.CoachService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CoachController {

	private static final String	VIEWS_COACH_CREATE_OR_UPDATE_FORM	= "coachs/createOrUpdateCoachForm";

	@Autowired
	private final CoachService	coachService;


	@Autowired
	public CoachController(final CoachService coachService) {
		this.coachService = coachService;
	}

	//	@InitBinder("coach")
	//	public void initCoachBinder(final WebDataBinder dataBinder) {
	//		dataBinder.setValidator(new CoachValidator());
	//	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/coachs") //LISTA DE ENTRENADORES
	public String showCoachList(final Map<String, Object> model) {

		Collection<Coach> coachs = this.coachService.findAllCoachs();
		model.put("coachs", coachs);

		return "coachs/coachList";
	}

	@GetMapping(value = "/coachs/freeAgent") //LISTE DE ENTRENADORES FA
	public String showCoachFAList(final Map<String, Object> model) {

		Collection<Coach> coachsFA = this.coachService.findAllCoachsFA();
		model.put("coachsFA", coachsFA);

		return "coachs/coachFAList";
	}

	@GetMapping("/coachs/{coachId}") //VISTA DETALLADA DE ENTRENADOR
	public ModelAndView showCoach(@PathVariable("coachId") final int coachId) {

		ModelAndView mav = new ModelAndView("coachs/CoachDetails");
		mav.addObject(this.coachService.findCoachById(coachId));

		return mav;
	}

}
