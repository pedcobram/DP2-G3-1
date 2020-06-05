
package org.springframework.samples.petclinic.web;

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
import org.springframework.samples.petclinic.service.exceptions.DateException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.samples.petclinic.service.exceptions.MoneyClubException;
import org.springframework.samples.petclinic.service.exceptions.NumberOfPlayersAndCoachException;
import org.springframework.samples.petclinic.service.exceptions.SalaryException;
import org.springframework.samples.petclinic.service.exceptions.StatusException;
import org.springframework.samples.petclinic.service.exceptions.StatusRegisteringException;
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

	private static final String			url_error_18Years					= "code.error.validator.18Years";
	private static final String			url_error_salaryMinAndMaxCoach		= "code.error.validator.salaryMinAndMaxCoach";
	private static final String			url_error_salary					= "code.error.validator.salary";
	private static final String			url_error_justOneCoach				= "code.error.validator.justOneCoach";
	private static final String			url_error_signTransaction			= "code.error.validator.signTransaction";
	private static final String			url_error_signWithoutCoach			= "code.error.validator.signWithoutCoach";

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

	@GetMapping("/coachs/{coachId}") //VISTA DETALLADA DE ENTRENADOR
	public ModelAndView showCoach(@PathVariable("coachId") final int coachId) {

		ModelAndView mav = new ModelAndView("coachs/coachDetails");
		mav.addObject(this.coachService.findCoachById(coachId));
		mav.addObject("coachAge", this.coachService.findCoachById(coachId).getAge());

		//Le paso al modelo si el id del club del user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		FootballClub myClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		if (myClub != null) { //Mostrar Botón para fichar o no
			mav.addObject("clubId", myClub.getId());
			mav.addObject("clubStatus", myClub.getStatus());
		} else {
			mav.addObject("iCantSign", true);

		}

		return mav;
	}

	@GetMapping(value = "/coach/new") //REGISTRAR ENTRENADOR - GET
	public String initCreationForm(final Map<String, Object> model) throws CredentialException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		FootballClub myClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		if (myClub == null) { //SEGURIDAD si no tenemos equipo no se puede registrar
			return "footballClubs/myClubEmpty";
		}

		if (myClub.getStatus()) { //SEGURIDAD si tenemos equipo publico no se puede registrar
			throw new CredentialException();
		}

		Coach coach = new Coach();
		model.put("coach", coach);
		model.put("regs", true);
		return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/coach/new") //REGISTRAR ENTRENADOR - POST
	public String processCreationForm(@Valid final Coach coach, final BindingResult result, final Model model)
		throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, MoneyClubException, SalaryException, StatusRegisteringException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		FootballClub myClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);
		model.addAttribute("regs", true);
		if (result.hasErrors()) {
			return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
		} else {
			try {
				coach.setClause(coach.getSalary() * 3);
				this.coachService.saveCoach(coach, myClub);
			} catch (DuplicatedNameException ex) {
				result.rejectValue("firstName", "duplicate", "already exists");
				result.rejectValue("lastName", "duplicate", "already exists");
				return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
			} catch (DateException ex) {
				result.rejectValue("birthDate", CoachController.url_error_18Years, "18 years");
				return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
			} catch (SalaryException ex) {
				result.rejectValue("salary", CoachController.url_error_salaryMinAndMaxCoach, "wrong money!");
				return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
			} catch (MoneyClubException ex) {
				result.rejectValue("salary", CoachController.url_error_salary, "Not enough money!");
				return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
			} catch (NumberOfPlayersAndCoachException ex) {
				result.rejectValue("firstName", CoachController.url_error_justOneCoach, "just one coach");
				return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
			} catch (StatusException ex) {
				return "redirect:exceptions/forbidden";
			} catch (StatusRegisteringException ex) {
				return "redirect:exceptions/forbidden";
			}
			return "redirect:/coachs/" + coach.getId();
		}
	}

	@GetMapping(value = "/coach/{coachId}/sign")  //FICHAR ENTRENADOR - GET
	public String initSignCoachForm(@PathVariable("coachId") final int coachId, final Model model) throws CredentialException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		FootballClub myClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		if (myClub == null) { //SEGURIDAD si no tenemos equipo no se puede fichar
			return "footballClubs/myClubEmpty";
		}

		Coach myCoach = this.coachService.findCoachByClubId(myClub.getId());
		Coach coach = this.coachService.findCoachById(coachId);

		if (!myClub.getStatus() && coach.getClub() != null) { //SEGURIDAD
			throw new CredentialException();
		}

		if (coach.getClub() == null) {  //Añadiendo variables al modelo
			model.addAttribute("freeAgent", true);
		} else {
			model.addAttribute("freeAgent", false);
			model.addAttribute("clubCoach", coach.getClub().getName());
			model.addAttribute("toPayValue", coach.getClause() + " €");
		}

		if (myCoach != null) {  //Añadiendo variables al modelo
			model.addAttribute("iHaveCoach", true);
			model.addAttribute("toPayValue", myCoach.getClause() + " €");
			model.addAttribute("myCoachFirstName", myCoach.getFirstName());
			model.addAttribute("myCoachLastName", myCoach.getLastName());
		}

		model.addAttribute(coach);
		model.addAttribute("clubName", myClub.getName());
		model.addAttribute("readonly", true);
		return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;

	}

	@PostMapping(value = "/coach/{coachId}/sign") //FICHAR ENTRENADOR - POST
	public String processUpdateFootballClubForm(@Valid final Coach coach, final BindingResult result, @PathVariable("coachId") final int coachId, final Model model)
		throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, MoneyClubException, CredentialException, StatusRegisteringException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		FootballClub myClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);
		Coach coach1 = this.coachService.findCoachById(coachId);
		Coach myCoach = this.coachService.findCoachByClubId(myClub.getId());

		if (coach.getClub() == null) {  //Añadiendo variables al modelo
			model.addAttribute("freeAgent", true);
		} else {
			model.addAttribute("freeAgent", false);
			model.addAttribute("clubCoach", coach.getClub().getName());
			model.addAttribute("toPayValue", coach.getClause() + " €");
		}

		if (myCoach != null) {  //Añadiendo variables al modelo
			model.addAttribute("iHaveCoach", true);
			model.addAttribute("toPayValue", myCoach.getClause() + " €");
			model.addAttribute("myCoachFirstName", myCoach.getFirstName());
			model.addAttribute("myCoachLastName", myCoach.getLastName());
		}

		model.addAttribute(coach);
		model.addAttribute("clubName", myClub.getName());
		model.addAttribute("readonly", true);

		if (result.hasErrors()) {
			model.addAttribute(coach);
			model.addAttribute("readonly", true);
			return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;

		} else {

			/** ENTRENADOR QUE QUEREMOS FICHAR **/
			Coach coachToUpdate = this.coachService.findCoachById(coachId);

			//Copiamos los datos del coach actual(vista del modelo) al equipo a actualizar excepto el club
			BeanUtils.copyProperties(coach, coachToUpdate, "id", "club");

			/** Entrenador de MI EQUIPO **/
			Coach clubCoach = this.coachService.findCoachByClubId(myClub.getId());

			if (clubCoach != null) { //SI TENGO ENTRENADOR

				if (coachToUpdate.getClub() != null) { //Y al que quiero fichar tiene otro club
					try {
						Integer clausulaApagar = coach1.getClause();
						coachToUpdate.setClause(coachToUpdate.getSalary() * 3);
						this.coachService.signCoach(coachToUpdate, myClub, clausulaApagar);
					} catch (SalaryException ex) {
						result.rejectValue("salary", CoachController.url_error_salaryMinAndMaxCoach, "wrong money!");
						return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
					} catch (MoneyClubException e) {
						result.rejectValue("salary", CoachController.url_error_signTransaction, "Not enough money!");
						return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
					}

				} else { //Si al que quiero fichar no tiene club			
					try {
						Integer clausulaApagar = clubCoach.getClause();
						coachToUpdate.setClause(coachToUpdate.getSalary() * 3);
						this.coachService.signCoach(coachToUpdate, myClub, clausulaApagar);
					} catch (SalaryException ex) {
						result.rejectValue("salary", CoachController.url_error_salaryMinAndMaxCoach, "wrong money!");
						return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
					} catch (MoneyClubException e) {
						result.rejectValue("salary", CoachController.url_error_signTransaction, "Not enough money!");
						return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
					}
				}

			} else { //SI NO TENGO ENTRENADOR			
				try {
					coachToUpdate.setClause(coachToUpdate.getSalary() * 3);
					this.coachService.saveCoach(coachToUpdate, myClub);
				} catch (DuplicatedNameException e) {
					result.rejectValue("firstName", "duplicate", "already exists");
					result.rejectValue("lastName", "duplicate", "already exists");
					return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
				} catch (DateException ex) {
					result.rejectValue("birthDate", CoachController.url_error_18Years, "18 years");
					return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
				} catch (NumberOfPlayersAndCoachException e) {
					result.rejectValue("firstName", CoachController.url_error_justOneCoach, "just one coach");
					return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
				} catch (SalaryException ex) {
					result.rejectValue("salary", CoachController.url_error_salaryMinAndMaxCoach, "wrong money!");
					return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
				} catch (MoneyClubException e) {
					result.rejectValue("salary", CoachController.url_error_salary, "Not enough money!");
					return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
				} catch (StatusException ex) {
					result.rejectValue("salary", CoachController.url_error_signWithoutCoach, "No tienes entrenador, no puedes fichar otro");
					return CoachController.VIEWS_COACH_CREATE_OR_UPDATE_FORM;
				}
			}

			//Si todo sale bien vamos a la vista de mi club
			return "redirect:/footballClubs/myClub/" + currentPrincipalName;
		}
	}

	@RequestMapping(value = "/coach/{coachId}/fire") //DESPEDIR ENTRENADOR
	public String processDeleteForm(@PathVariable("coachId") final int coachId, final Model model) throws CredentialException, DataAccessException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		Coach coach = this.coachService.findCoachById(coachId);

		if (coach.getClub() == null) { //SEGURIDAD
			throw new CredentialException("Forbidden Access");
		}

		if (!coach.getClub().getPresident().getUser().getUsername().equals(currentPrincipalName)) { //SEGURIDAD
			throw new CredentialException("Forbidden Access");
		}

		try {

			this.coachService.fireCoach(coach);
		} catch (MoneyClubException e) {
			model.addAttribute("salaryError", true);
			model.addAttribute(coach);
			return "/coachs/coachDetails";
		}

		return "redirect:/footballClubs/myClub/" + currentPrincipalName;
	}

}
