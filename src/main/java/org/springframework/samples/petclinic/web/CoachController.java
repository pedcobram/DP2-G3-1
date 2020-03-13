
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Coach;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.service.CoachService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.samples.petclinic.web.validators.CoachValidator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CoachController {

	private static final String			VIEWS_COACH_CREATE_OR_UPDATE_FORM	= "coachs/createOrUpdateCoachForm";

	@Autowired
	private final CoachService			coachService;

	@Autowired
	private final FootballClubService	footballClubService;


	@Autowired
	public CoachController(final CoachService coachService, final FootballClubService footballClubService) {
		this.coachService = coachService;
		this.footballClubService = footballClubService;
	}

	@InitBinder("coach")
	public void initCoachBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new CoachValidator());
	}

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

	@GetMapping(value = "/coachs/new") //REGISTRAR ENTRENADOR - GET
	public String initCreationForm(final Map<String, Object> model) {

		Coach coach = new Coach();
		model.put("coach", coach);
		coach.setClause(0);

		return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
	}

	//Crear Jugador - Post
	@PostMapping(value = "/coachs/new")
	public String processCreationForm(@Valid final Coach coach, final BindingResult result, final Model model) throws DataAccessException, DuplicatedNameException {

		//Obtenemos el username del usuario actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		//Obtenemos el Club del user actual
		FootballClub thisClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		//Validación si el club ya tiene un entrenador
		Coach thereIsACoach = this.coachService.findCoachByClubId(thisClub.getId());

		if (thereIsACoach != null) {
			result.rejectValue("firstName", "code.error.validator.justOneCoach", "just one coach");
			return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
		}

		//Si hay errores seguimos en la vista de creación
		if (result.hasErrors()) {
			return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
		} else {
			try {

				coach.setClub(thisClub);

				coach.setClause(coach.getSalary() * 5);

				this.coachService.saveCoach(coach);

				//Si capturamos excepción de nombre duplicado seguimos en la vista de creación
			} catch (DuplicatedNameException ex) {
				//Mostramos el mensaje de error en el atributo name
				result.rejectValue("firstName", "duplicate", "already exists");
				return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
			}
			//Si todo sale bien vamos a la vista de mi club
			return "redirect:/myfootballClub/" + currentPrincipalName;
		}
	}

}
