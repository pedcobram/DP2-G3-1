
package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = CompAdminRequestController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class CompAdminRequestControllerTest {

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

	@Test //CASO NEGATIVO
	void dontInitCompAdminCreationFormTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitionAdminRequest/new")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	//	@WithMockUser(username = "pedro", password = "pedro", authorities = {
	//		"authenticated"
	//	})
	//	@Test //CASO POSITIVO
	//	void processCreationFormTest() throws Exception {
	//		this.mockMvc.perform(MockMvcRequestBuilders.post("/competitionAdminRequest/new").param("title", "title").param("description", "description").param("status", "0")).andExpect(MockMvcResultMatchers.status().isOk());
	//	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})
	@Test //CASO POSITIVO
	void initCompAdminCreatioaanFormTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myCompetitionAdminRequest/pedro")).andExpect(MockMvcResultMatchers.status().isOk());
	}

}
