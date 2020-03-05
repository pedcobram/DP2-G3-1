
package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.CompetitionAdmin;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CompetitionAdminService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CompetitionAdminController {

	private static final String				VIEWS_COMPETITION_ADMIN_CREATE_OR_UPDATE_FORM	= "competitionAdmins/createOrUpdateCompetitionAdminForm";

	private final CompetitionAdminService	competitionAdminService;


	@Autowired
	public CompetitionAdminController(final CompetitionAdminService competitionAdminService, final UserService userService, final AuthoritiesService authoritiesService) {
		this.competitionAdminService = competitionAdminService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/competitionAdmins/new")
	public String initCreationForm(final Map<String, Object> model) {
		CompetitionAdmin competitionAdmin = new CompetitionAdmin();
		model.put("competitionAdmin", competitionAdmin);
		return CompetitionAdminController.VIEWS_COMPETITION_ADMIN_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/competitionAdmins/new")
	public String processCreationForm(@Valid final CompetitionAdmin competitionAdmin, final BindingResult result) {
		if (result.hasErrors()) {
			return CompetitionAdminController.VIEWS_COMPETITION_ADMIN_CREATE_OR_UPDATE_FORM;
		} else {
			//creating Competition Admin, user and authorities
			this.competitionAdminService.saveCompetitionAdmin(competitionAdmin);

			return "redirect:/";
		}
	}

}
