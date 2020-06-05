
package org.springframework.samples.petclinic.match;

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
public class MatchE2ETests {

	@Autowired
	private MockMvc mockMvc;


	@WithMockUser(username = "pedro", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void showMatchList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/list")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test //CASO NEGATIVO
	@WithAnonymousUser
	void dontShowMatchList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/list")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void showMatch() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test //CASO POSITIVO
	@WithAnonymousUser
	void dontShowMatch() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void initUpdateMatchForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/edit/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test //CASO NEGATIVO
	@WithAnonymousUser
	void dontInitUpdateMatchForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/edit/1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void processUpdateMatchForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/matches/edit/1").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "Title").param("matchDate", "2020/03/20 20:30").param("matchStatus", "TO_BE_PLAYED").param("stadium", "Stadium")
			.param("footballClub1", "Sevilla Fútbol Club").param("footballClub2", "Fútbol Club Barcelona")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO NEGATIVO
	void dontProcessUpdateMatchForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/matches/edit/1").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "Title").param("matchDate", "").param("matchStatus", "TO_BE_PLAYED").param("stadium", "")
			.param("footballClub1", "Sevilla Fútbol Club").param("footballClub2", "Fútbol Club Barcelona")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("exceptions/exception"));
		//.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("match", "matchDate"))
		//.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("match", "stadium"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void showReceivedMatchRequestList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/referee/list")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test //CASO NEGATIVO
	@WithAnonymousUser
	void dontShowReceivedMatchRequestList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/referee/list")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

}
