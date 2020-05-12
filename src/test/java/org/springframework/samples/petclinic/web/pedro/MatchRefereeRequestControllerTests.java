
package org.springframework.samples.petclinic.web.pedro;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.web.FootballPlayerController;
import org.springframework.samples.petclinic.web.MatchController;
import org.springframework.samples.petclinic.web.MatchRecordController;
import org.springframework.samples.petclinic.web.MatchRefereeRequestController;
import org.springframework.samples.petclinic.web.RefereeController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = MatchRefereeRequestController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class MatchRefereeRequestControllerTests {

	@Autowired
	private MockMvc							mockMvc;

	@MockBean
	private MatchRefereeRequestController	matchRefereeRequestController;

	@MockBean
	private RefereeController				refereeController;

	@MockBean
	private MatchController					matchController;

	@MockBean
	private MatchRecordController			matchRecordController;

	@MockBean
	private FootballPlayerController		footballPlayerController;


	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void initMatchRefereeRequestList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/refereeRequest/refereeList/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontInitMatchRefereeRequestList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/refereeRequest/refereeList/1")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void initMatchRefereeRequestForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/refereeRequest/new/1/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontInitMatchRefereeRequestForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/refereeRequest/new/1/1")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void createMatchRefereeRequestForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/matches/refereeRequest/new/1/1").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "Title").param("status", "ON_HOLD")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().hasNoErrors());
	}

	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO NEGATIVO
	void dontCreateMatchRefereeRequestForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/matches/refereeRequest/new/1/1").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "").param("status", "")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().hasErrors()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("matchRefereeRequest", "title"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("matchRefereeRequest", "status"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void showMatchRefereeRequestList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRefereeRequest/list")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontShowMatchRefereeRequestList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRefereeRequest/list")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	//

	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void acceptMatchRefereeRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRefereeRequest/list/accept/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontAcceptMatchRefereeRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRefereeRequest/list/accept/1")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	//

	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void rejectCompetitionAdminRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRefereeRequest/list/reject/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontRejectCompetitionAdminRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRefereeRequest/list/reject/1")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
}
