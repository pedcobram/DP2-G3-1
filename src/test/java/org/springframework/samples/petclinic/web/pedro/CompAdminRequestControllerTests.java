
package org.springframework.samples.petclinic.web.pedro;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.web.CompAdminRequestController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = CompAdminRequestController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class CompAdminRequestControllerTests {

	@Autowired
	private MockMvc						mockMvc;

	@MockBean
	private CompAdminRequestController	compAdminRequestController;


	@WithMockUser(username = "pedro", authorities = {
		"admin"
	})
	@Test //CASO POSITIVO
	void showCompetitionAdminRequestListTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitionAdminRequest/list")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontShowCompetitionAdminRequestListTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitionAdminRequest/list")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "pedro", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void initCompAdminCreationFormTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitionAdminRequest/new")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontInitCompAdminCreationFormTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitionAdminRequest/new")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "pedro", password = "pedro", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void processCreationFormTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/competitionAdminRequest/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "").param("description", "description").param("status", "ON_HOLD"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("compAdminRequest")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("compAdminRequest", "title"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void initUpdateCompetitionAdminForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myCompetitionAdminRequest/edit")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontInitUpdateCompetitionAdminForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myCompetitionAdminRequest/edit")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "pedro", password = "pedro", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void processUpdateCompetitionAdminForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/myCompetitionAdminRequest/edit").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "title").param("description", "description").param("status", "ON_HOLD"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithMockUser(username = "pedro", password = "pedro", authorities = {
		"authenticated"
	})
	@Test //CASO NEGATIVO
	void dontProcessUpdateCompetitionAdminForm() throws Exception {

		String title = "";

		this.mockMvc.perform(MockMvcRequestBuilders.post("/myCompetitionAdminRequest/edit").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", title).param("description", "description").param("status", "ON_HOLD"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("compAdminRequest")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("compAdminRequest", "title"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void deleteCompAdminRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/deleteCompAdminRequest")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontDeleteCompAdminRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/deleteCompAdminRequest")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})
	@Test //CASO POSITIVO
	void showCompAdminRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myCompetitionAdminRequest")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontShowCompAdminRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myCompetitionAdminRequest")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "pedro", authorities = {
		"admin"
	})
	@Test //CASO POSITIVO
	void acceptCompetitionAdminRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitionAdminRequest/accept/gonzalo")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontAcceptCompetitionAdminRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitionAdminRequest/accept/gonzalo")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "pedro", authorities = {
		"admin"
	})
	@Test //CASO POSITIVO
	void rejectCompetitionAdminRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitionAdminRequest/reject/{username}", "gonzalo")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontRejectCompetitionAdminRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitionAdminRequest/reject/gonzalo")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

}
