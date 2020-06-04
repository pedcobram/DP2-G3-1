
package org.springframework.samples.petclinic.referee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.web.AuthenticatedController;
import org.springframework.samples.petclinic.web.RefereeController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = RefereeController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class RefereeControllerTests {

	@Autowired
	private MockMvc					mockMvc;

	@MockBean
	private RefereeController		refereeController;

	@MockBean
	private AuthenticatedController	authenticatedController;


	@WithMockUser(username = "pedro", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void createReferee() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/createReferee")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontCreateReferee() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/createReferee")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "pedro", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void deleteReferee() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/referee/delete")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontDeleteReferee() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/referee/delete")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "pedro", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void initUpdateRefereeForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myRefereeProfile/edit")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontInitUpdateRefereeForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myRefereeProfile/edit")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "pedro", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void processUpdateRefereeForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/myRefereeProfile/edit").with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "First Name").param("lastName", "Last Name").param("telephone", "111111111")
			.param("email", "test@gmail.com").param("dni", "11111111A")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().hasNoErrors());
	}

	@WithMockUser(username = "pedro", authorities = {
		"authenticated"
	})
	@Test //CASO NEGATIVO
	void dontProcessUpdateRefereeForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/myRefereeProfile/edit").with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "").param("lastName", "").param("telephone", "").param("email", "").param("dni", ""))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().hasErrors()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("referee", "firstName"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("referee", "lastName")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("referee", "telephone"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("referee", "email")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("referee", "dni"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void showRefereeProfile() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myRefereeProfile")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontShowRefereeProfile() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myRefereeProfile")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

}
