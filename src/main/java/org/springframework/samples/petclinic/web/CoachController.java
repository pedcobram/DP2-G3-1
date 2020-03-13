
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Coach;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.service.CoachService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CoachController {

	//	private static final String	VIEWS_COACH_CREATE_OR_UPDATE_FORM	= "coachs/createOrUpdateCoachForm";

	@Autowired
	private final CoachService			coachService;

	@Autowired
	private final FootballClubService	footballClubService;


	@Autowired
	public CoachController(final CoachService coachService, final FootballClubService footballClubService) {
		this.coachService = coachService;
		this.footballClubService = footballClubService;
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

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		if (footballClub == null) {
			return "footballClubs/myClubEmpty";
		}

		if (footballClub.getStatus() == false) {

			Collection<Coach> coachsFA = this.coachService.findAllCoachsFA();
			model.put("coachs", coachsFA);
			model.put("thisClubStatus", footballClub.getStatus());
			model.put("thisClubPresidentUsername", footballClub.getPresident().getUser().getUsername());

			return "coachs/coachList";
		} else {

			Collection<Coach> coachs = this.coachService.findAllCoachs();
			model.put("coachs", coachs);

			return "coachs/coachList";
		}
	}

	@GetMapping("/coachs/{coachId}") //VISTA DETALLADA DE ENTRENADOR
	public ModelAndView showCoach(@PathVariable("coachId") final int coachId) {

		ModelAndView mav = new ModelAndView("coachs/coachDetails");
		mav.addObject(this.coachService.findCoachById(coachId));
		mav.addObject("coachAge", this.coachService.findCoachById(coachId).getAge());

		return mav;
	}

}
