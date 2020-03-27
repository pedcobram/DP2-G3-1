
package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = MatchRequestController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class MatchRequestControllerTests {

	@Autowired
	private MockMvc					mockMvc;

	@MockBean
	private MatchRequestController	matchRequestController;

	@MockBean
	private FootballClubController	footballClubController;

	@MockBean
	private MatchController			matchController;


	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void showSentMatchRequestList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRequests/sent/president1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontShowSentMatchRequestList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRequests/sent/president1")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void showReceivedMatchRequestList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRequests/received/president1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontShowReceivedMatchRequestList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRequests/received/president1")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void initCreateMatchRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRequests/president1/new")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontInitCreateMatchRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("matchRequests/president1/new")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void createMatchRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/matchRequests/president1/new")
			.with(SecurityMockMvcRequestPostProcessors.csrf())
			.param("title", "Title")
			.param("matchDate", "2020/04/25 20:30")
			.param("stadium", "Stadium")
			.param("status", "ON_HOLD"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().hasNoErrors());
	}
	
	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO NEGATIVO
	void dontCreateMatchRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/matchRequests/president1/new")
			.with(SecurityMockMvcRequestPostProcessors.csrf())
			.param("title", "")
			.param("matchDate", "")
			.param("stadium", "")
			.param("status", ""))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().hasErrors())
		.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("matchRequest", "title"))
		.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("matchRequest", "matchDate"))
		.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("matchRequest", "stadium"))
		.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("matchRequest", "status"));
	}
	
	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void processDeleteMatchRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRequests/delete/1/president1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontProcessDeleteMatchRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRequests/delete/1/president1")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	//
	
	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void processAcceptMatchRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRequests/accept/1/president1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontProcessAcceptMatchRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRequests/accept/1/president1")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	//
	
	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void processRejectMatchRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRequests/reject/1/president1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontProcessRejectMatchRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRequests/reject/1/president1")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

}
