
package org.springframework.samples.petclinic.web.e2e.gonzalo;

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
public class RefereeRequestE2ETests {

	@Autowired
	private MockMvc mockMvc;


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
		this.mockMvc.perform(MockMvcRequestBuilders.get("/refereeRequest/list")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
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
		this.mockMvc.perform(MockMvcRequestBuilders.get("/refereeRequest/new")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "gonza", authorities = {
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
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myRefereeRequest/edit")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "gonza", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void processUpdateRefereeForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/myRefereeRequest/edit").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "title").param("description", "description").param("status", "ON_HOLD"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithMockUser(username = "gonza", authorities = {
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
		this.mockMvc.perform(MockMvcRequestBuilders.get("/deleteRefereeRequest")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
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
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myRefereeRequest")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test //CASO POSITIVO
	void acceptRefereeRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/refereeRequest/accept/gonzalo")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontAcceptRefereeRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/refereeRequest/accept/gonzalo")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test //CASO POSITIVO
	void rejectRefereeRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/refereeRequest/reject/gonzalo"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/refereeRequest/list"))
			.andExpect(MockMvcResultMatchers.redirectedUrl("/refereeRequest/list"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontRejectRefereeRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/refereeRequest/reject/gonzalo")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

}
