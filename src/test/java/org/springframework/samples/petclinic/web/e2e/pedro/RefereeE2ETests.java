
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
public class RefereeE2ETests {

	@Autowired
	private MockMvc mockMvc;


	//.andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));

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
		this.mockMvc.perform(MockMvcRequestBuilders.get("/createReferee")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
		;
	}

	@WithMockUser(username = "pedro", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void deleteReferee() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/referee/delete")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontDeleteReferee() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/referee/delete")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void initUpdateRefereeForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myRefereeProfile/edit")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontInitUpdateRefereeForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myRefereeProfile/edit")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
		;
	}

	@WithMockUser(username = "pedro", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void processUpdateRefereeForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/myRefereeProfile/edit").with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "First Name").param("lastName", "Last Name").param("telephone", "111111111")
			.param("email", "test@gmail.com").param("dni", "11111111A")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().hasNoErrors());
	}

	@WithMockUser(username = "pedro", authorities = {
		"referee"
	})
	@Test //CASO NEGATIVO
	void dontProcessUpdateRefereeForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/myRefereeProfile/edit").with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "").param("lastName", "").param("telephone", "").param("email", "").param("dni", ""))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().hasErrors()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("referee", "firstName"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("referee", "lastName")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("referee", "telephone"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("referee", "email")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("referee", "dni"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void showRefereeProfile() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myRefereeProfile")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontShowRefereeProfile() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myRefereeProfile")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
		;
	}

}
