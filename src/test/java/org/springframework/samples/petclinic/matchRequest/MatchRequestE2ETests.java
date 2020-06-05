
package org.springframework.samples.petclinic.matchRequest;

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
public class MatchRequestE2ETests {

	@Autowired
	private MockMvc mockMvc;


	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void showSentMatchRequestList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRequests/sent")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontShowSentMatchRequestList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRequests/sent")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void showReceivedMatchRequestList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRequests/received")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontShowReceivedMatchRequestList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRequests/received")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
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
		this.mockMvc.perform(MockMvcRequestBuilders.get("matchRequests/president1/new")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void createMatchRequest() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/matchRequests/president1/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "Title").param("matchDate", "2020/04/25 20:30").param("stadium", "Stadium").param("status", "ON_HOLD"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().hasNoErrors());
	}

	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO NEGATIVO
	void dontCreateMatchRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/matchRequests/president2/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "").param("matchDate", "").param("stadium", "").param("status", ""))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("exceptions/exception"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void processDeleteMatchRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRequests/delete/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontProcessDeleteMatchRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRequests/delete/1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	//

	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void processAcceptMatchRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRequests/accept/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontProcessAcceptMatchRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRequests/accept/1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	//

	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void processRejectMatchRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRequests/reject/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontProcessRejectMatchRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matchRequests/reject/1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

}
