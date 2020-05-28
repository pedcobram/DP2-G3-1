
package org.springframework.samples.petclinic.web.gonzalo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.web.RefereeRequestController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = RefereeRequestController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class RefereeRequestControllerTests {

	@Autowired
	private MockMvc						mockMvc;

	@MockBean
	private RefereeRequestController	refereeRequestController;


	@WithMockUser(username = "gonza", authorities = {
		"admin"
	})
	@Test //CASO POSITIVO
	void showRefereeRequestListTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/refereeRequest/list")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontShowRefereeRequestListTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/refereeRequest/list")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "gonza", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void initRefereeCreationFormTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/refereeRequest/new")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontInitRefereeCreationFormTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/refereeRequest/new")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "gonza", password = "gonza", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void processCreationFormTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/refereeRequest/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "").param("description", "description").param("status", "ON_HOLD"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("refereeRequest")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("refereeRequest", "title"));
	}

	@WithMockUser(username = "gonza", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void initUpdateRefereeForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myRefereeRequest/edit")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontInitUpdateRefereeForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myRefereeRequest/edit")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "gonza", password = "gonza", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void processUpdateRefereeForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/myRefereeRequest/edit").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "title").param("description", "description").param("status", "ON_HOLD"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithMockUser(username = "gonza", password = "gonza", authorities = {
		"authenticated"
	})
	@Test //CASO NEGATIVO
	void dontProcessUpdateRefereeForm() throws Exception {

		String title = "";

		this.mockMvc.perform(MockMvcRequestBuilders.post("/myRefereeRequest/edit").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", title).param("description", "description").param("status", "ON_HOLD"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("refereeRequest")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("refereeRequest", "title"));
	}

	@WithMockUser(username = "gonza", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void deleteRefereeRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/deleteRefereeRequest")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontDeleteRefereeRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/deleteRefereeRequest")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "gonza", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void showRefereeRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myRefereeRequest")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontShowRefereeRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myRefereeRequest")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "gonza", authorities = {
		"admin"
	})
	@Test //CASO POSITIVO
	void acceptRefereeRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/refereeRequest/accept/gonzalo")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontAcceptRefereeRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/refereeRequest/accept/gonzalo")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "gonza", authorities = {
		"admin"
	})
	@Test //CASO POSITIVO
	void rejectRefereeRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/refereeRequest/reject/{username}", "gonzalo")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontRejectRefereeRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/refereeRequest/reject/gonzalo")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

}
