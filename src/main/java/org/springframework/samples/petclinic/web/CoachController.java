
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.security.auth.login.CredentialException;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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
import org.springframework.web.bind.annotation.RequestMapping;
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

		//Le paso al modelo si el id del club del user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		FootballClub myClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		mav.addObject("clubId", myClub.getId());

		return mav;
	}

	@GetMapping(value = "/coachs/new") //REGISTRAR ENTRENADOR - GET
	public String initCreationForm(final Map<String, Object> model) {

		Coach coach = new Coach();
		model.put("coach", coach);

		return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/coachs/new") //REGISTRAR ENTRENADOR - POST
	public String processCreationForm(@Valid final Coach coach, final BindingResult result, final Model model) throws DataAccessException, DuplicatedNameException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		FootballClub thisClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);
		Coach thereIsACoach = this.coachService.findCoachByClubId(thisClub.getId());

		if (thereIsACoach != null) { //Validación Solo se puede tener un Coach
			result.rejectValue("firstName", "code.error.validator.justOneCoach", "just one coach");
			return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
		}

		if (result.hasErrors()) {
			return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
		} else {
			try {
				coach.setClub(thisClub);
				coach.setClause(coach.getSalary() * 3);
				this.coachService.saveCoach(coach);
			} catch (DuplicatedNameException ex) {
				result.rejectValue("firstName", "duplicate", "already exists");
				return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
			}
			return "redirect:/coachs/" + coach.getId();
		}
	}

	//Fichar Coach
	@GetMapping(value = "/coachs/{coachId}/sign")
	public String initSignCoachForm(@PathVariable("coachId") final int coachId, final Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		FootballClub myClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);
		Coach myCoach = this.coachService.findCoachByClubId(myClub.getId());
		Coach coach = this.coachService.findCoachById(coachId);

		if (coach.getClub() == null) {
			model.addAttribute("freeAgent", true);
		} else {
			model.addAttribute("freeAgent", false);
			model.addAttribute("clubCoach", coach.getClub().getName());
			model.addAttribute("toPayValue", coach.getClause() + " €");
		}

		if (myCoach != null) {
			model.addAttribute("myCoachFirstName", myCoach.getFirstName());
			model.addAttribute("myCoachLastName", myCoach.getLastName());
		}

		model.addAttribute(coach);
		model.addAttribute("clubName", myClub.getName());
		model.addAttribute("readonly", true);
		return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;

	}

	//Editar Club - Post
	@PostMapping(value = "/coachs/{coachId}/sign")
	public String processUpdateFootballClubForm(@Valid final Coach coach, final BindingResult result, @PathVariable("coachId") final int coachId, final Model model) throws DataAccessException, DuplicatedNameException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		FootballClub myClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);
		Coach coach1 = this.coachService.findCoachById(coachId);

		if (coach1.getClub() == null) {
			model.addAttribute("freeAgent", true);
		} else {
			model.addAttribute("freeAgent", false);
			model.addAttribute("clubCoach", coach1.getClub().getName());
			model.addAttribute("toPayValue", coach1.getClause() + " €");
		}
		model.addAttribute("clubName", myClub.getName());

		if (result.hasErrors()) {
			model.addAttribute(coach);
			model.addAttribute("readonly", true);
			return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;

		} else {

			/** ENTRENADOR QUE QUEREMOS FICHAR **/
			Coach coachToUpdate = this.coachService.findCoachById(coachId);

			//Copiamos los datos del coach actual(vista del modelo) al equipo a actualizar excepto el club
			BeanUtils.copyProperties(coach, coachToUpdate, "id", "club");

			try {

				/** Entrenador de MI EQUIPO **/
				Coach clubCoach = this.coachService.findCoachByClubId(myClub.getId());

				if (clubCoach != null) { //Si tengo entrenador
					if (coachToUpdate.getClub() != null) { //Y al que quiero fichar tiene otro club
						clubCoach.setClub(coachToUpdate.getClub()); //Mi entrenador se va al suyo
					} else { //Si al que quiero fichar no tiene club
						clubCoach.setClub(null); //Mi entrenador pasa a agente libre
						clubCoach.setSalary(0);
						clubCoach.setClause(0);
					}
				}

				coachToUpdate.setClub(myClub);
				coachToUpdate.setClause(coachToUpdate.getSalary() * 3);
				this.coachService.saveCoach(coachToUpdate);

			} catch (DuplicatedNameException ex) {

				result.rejectValue("firstName", "duplicate", "already exists");
				return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
			}

			//Si todo sale bien vamos a la vista de mi club
			return "redirect:/myfootballClub/" + currentPrincipalName;
		}
	}

	//DESPEDIR ENTRENADOR
	@RequestMapping(value = "/coachs/{coachId}/fire")
	public String processDeleteForm(@PathVariable("coachId") final int coachId) throws CredentialException, DataAccessException, DuplicatedNameException {

		//Obtenemos el username del usuario actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Coach coach = this.coachService.findCoachById(coachId);

		//Seguridad: Solo el dueño del club puede despedirlo
		if (!coach.getClub().getPresident().getUser().getUsername().equals(currentPrincipalName)) {
			throw new CredentialException("Forbidden Access");
		}

		coach.setClub(null);

		this.coachService.saveCoach(coach);

		//Habría que que restarle a los fondos del club la cláusula de rescisión ya que lo estamos despidiendo. Pero da fallo

		return "redirect:/myfootballClub/" + currentPrincipalName;
	}

}
