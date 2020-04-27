
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Competition;
import org.springframework.samples.petclinic.model.Enum.CompetitionType;
import org.springframework.samples.petclinic.service.CompetitionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CompetitionController {

	private static final String			VIEWS_COMPETITION_CREATE_OR_UPDATE_FORM	= "competitions/createOrUpdateCompetitionForm";

	@Autowired
	private final CompetitionService	competitionService;


	@Autowired
	public CompetitionController(final CompetitionService competitionService) {
		this.competitionService = competitionService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@ModelAttribute("types")
	public List<CompetitionType> populateTypes() {
		List<CompetitionType> types = new ArrayList<>();
		types.add(CompetitionType.LEAGUE);
		types.add(CompetitionType.PLAYOFFS);
		return types;
	}

	@GetMapping("/competitions/{competitionId}") //VISTA DETALLADA DE COMPETICIÓN
	public ModelAndView showCompetition(@PathVariable("competitionId") final int competitionId) {

		ModelAndView mav = new ModelAndView("competitions/competitionDetails");
		mav.addObject(this.competitionService.findCompetitionById(competitionId));

		return mav;
	}

	@GetMapping(value = "/competitions/list") //LISTA DE COMPETICIONES PUBLICADAS
	public String showCompetitionList(final Map<String, Object> model) {

		List<Competition> competitions = new ArrayList<>();
		competitions.addAll(this.competitionService.findAllPublishedCompetitions());

		model.put("competitions", competitions);

		return "competitions/competitionList";
	}

	@GetMapping(value = "/competition/mylist") //LISTA DE MIS COMPETICIONES
	public String showMyCompetitionList(final Map<String, Object> model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		List<Competition> competitions = new ArrayList<>();
		competitions.addAll(this.competitionService.findMyCompetitions(currentPrincipalName));

		model.put("competitions", competitions);

		return "competitions/competitionList";
	}

	@GetMapping(value = "/competition/new") //CREAR COMPETICIÓN - GET
	public String initCreationForm(final Map<String, Object> model) {

		Competition competition = new Competition();
		model.put("competition", competition);
		model.put("isNew", true);
		return CompetitionController.VIEWS_COMPETITION_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/competition/new") //CREAR COMPETICIÓN - POST
	public String processCreationForm(@Valid final Competition competition, final BindingResult result, final Model model) throws DataAccessException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		if (result.hasErrors()) {
			model.addAttribute("competition", competition);
			model.addAttribute("isNew", true);
			return CompetitionController.VIEWS_COMPETITION_CREATE_OR_UPDATE_FORM;
		} else {

			competition.setStatus(false);
			competition.setCreator(currentPrincipalName);
			this.competitionService.saveCompetition(competition);
		}
		return "redirect:/competitions/" + competition.getId();
	}
}
