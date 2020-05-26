
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
public class PresidentRequestE2ETests {

	@Autowired
	private MockMvc mockMvc;


	@WithMockUser(username = "gonza", authorities = {
		"admin"
	})
	@Test //CASO POSITIVO
	void showPresidentRequestListTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/presidentRequest/list")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontShowPresidentRequestListTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/presidentRequest/list")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "gonza", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void initPresidentCreationFormTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/presidentRequest/new")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontInitPresidentCreationFormTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/presidentRequest/new")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "gonza", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void processCreationFormTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/presidentRequest/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "").param("description", "description").param("status", "ON_HOLD"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("presidentRequest")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("presidentRequest", "title"));
	}

	@WithMockUser(username = "gonza", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void initUpdatePresidentForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myPresidentRequest/edit")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontInitUpdatePresidentForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myPresidentRequest/edit")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "gonza", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void processUpdatePresidentForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/myPresidentRequest/edit").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "title").param("description", "description").param("status", "ON_HOLD"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithMockUser(username = "gonza", authorities = {
		"authenticated"
	})
	@Test //CASO NEGATIVO
	void dontProcessUpdatePresidentForm() throws Exception {

		String title = "";

		this.mockMvc.perform(MockMvcRequestBuilders.post("/myPresidentRequest/edit").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", title).param("description", "description").param("status", "ON_HOLD"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("presidentRequest")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("presidentRequest", "title"));
	}

	@WithMockUser(username = "gonza", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void deletePresidentRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/deletePresidentRequest")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontDeletePresidentRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/deletePresidentRequest")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "gonza", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void showPresidentRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myPresidentRequest")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontShowPresidentRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myPresidentRequest")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test //CASO POSITIVO
	void acceptPresidentRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/presidentRequest/accept/gonzalo")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontAcceptPresidentRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/presidentRequest/accept/gonzalo")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test //CASO POSITIVO
	void rejectPresidentRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/presidentRequest/reject/gonzalo")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontRejectPresidentRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/presidentRequest/reject/gonzalo")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

}
