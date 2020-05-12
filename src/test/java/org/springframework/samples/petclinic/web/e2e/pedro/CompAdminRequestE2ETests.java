
package org.springframework.samples.petclinic.web.e2e.pedro;

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
public class CompAdminRequestE2ETests {

	@Autowired
	private MockMvc mockMvc;


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
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitionAdminRequest/list")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
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
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitionAdminRequest/new")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "pedro", authorities = {
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
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myCompetitionAdminRequest/edit")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void processUpdateCompetitionAdminForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/myCompetitionAdminRequest/edit").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "title").param("description", "description").param("status", "ON_HOLD"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithMockUser(username = "pedro", authorities = {
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
		this.mockMvc.perform(MockMvcRequestBuilders.get("/deleteCompAdminRequest")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
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
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myCompetitionAdminRequest")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test //CASO POSITIVO
	void acceptCompetitionAdminRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitionAdminRequest/accept/gonzalo")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontAcceptCompetitionAdminRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitionAdminRequest/accept/gonzalo")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test //CASO POSITIVO
	void rejectCompetitionAdminRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitionAdminRequest/reject/{usuario}", "gonzalo")).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/competitionAdminRequest/list"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontRejectCompetitionAdminRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitionAdminRequest/reject/gonzalo")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

}
