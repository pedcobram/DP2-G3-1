
package org.springframework.samples.petclinic.matchRefereeRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
//@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
//@AutoConfigureTestDatabase(replace = Replace.ANY)
public class MatchRefereeRequestE2ETests {

	@Autowired
	private MockMvc mockMvc;


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
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/refereeRequest/refereeList/1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
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
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/refereeRequest/new/1/1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
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
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRefereeRequest/list")).andExpect(MockMvcResultMatchers.status().is4xxClientError()).andExpect(MockMvcResultMatchers.forwardedUrl("/exception/forbidden"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontShowMatchRefereeRequestList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRefereeRequest/list")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	//

	@WithMockUser(username = "pedro", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void acceptMatchRefereeRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRefereeRequest/list/accept/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontAcceptMatchRefereeRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRefereeRequest/list/accept/1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	//

	@WithMockUser(username = "pedro", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void rejectCompetitionAdminRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRefereeRequest/list/reject/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontRejectCompetitionAdminRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRefereeRequest/list/reject/1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}
}
