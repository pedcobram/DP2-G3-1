
package org.springframework.samples.petclinic.web.pedro;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.web.AuthenticatedController;
import org.springframework.samples.petclinic.web.CompetitionAdminController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = CompetitionAdminController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class CompetitionAdminControllerTests {

	@Autowired
	private MockMvc						mockMvc;

	@MockBean
	private AuthenticatedController		authenticatedController;

	@MockBean
	private CompetitionAdminController	competitionAdminController;


	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})
	@Test //CASO POSITIVO
	void deleteCompetitionAdmin() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitionAdmin/delete")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontDeleteCompetitionAdmin() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitionAdmin/delete")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})
	@Test //CASO POSITIVO
	void initUpdatePresidentForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myCompetitionAdminProfile/edit")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontInitUpdatePresidentForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myCompetitionAdminProfile/edit")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})
	@Test //CASO POSITIVO
	void processUpdateCompetitionAdminForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/myCompetitionAdminProfile/edit").with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "First Name").param("lastName", "Last Name").param("telephone", "111111111")
			.param("email", "test@gmail.com").param("dni", "11111111A")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontProcessUpdateCompetitionAdminForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/myCompetitionAdminProfile/edit").with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "First Name").param("lastName", "Last Name").param("telephone", "111111111")
			.param("email", "test@gmail.com").param("dni", "11111111A")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})
	@Test //CASO NEGATIVO
	void ProcessWithErrorUpdateCompetitionAdminForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/myCompetitionAdminProfile/edit").with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "").param("lastName", "").param("telephone", "").param("email", "").param("dni", ""))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("competitionAdmin")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("competitionAdmin", "firstName"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("competitionAdmin", "lastName")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("competitionAdmin", "telephone"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("competitionAdmin", "email")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("competitionAdmin", "dni"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})
	@Test //CASO POSITIVO
	void showCompetitionAdminProfile() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myCompetitionAdminProfile")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontShowCompetitionAdminProfile() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myCompetitionAdminProfile")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

}
