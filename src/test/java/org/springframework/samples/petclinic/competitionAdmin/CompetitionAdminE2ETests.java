
package org.springframework.samples.petclinic.competitionAdmin;

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
public class CompetitionAdminE2ETests {

	@Autowired
	private MockMvc mockMvc;


	//	@WithMockUser(username = "pedro", authorities = {
	//		"competitionAdmin"
	//	})
	//	@Test //CASO POSITIVO
	//	void deleteCompetitionAdmin() throws Exception {
	//		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitionAdmin/delete")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.redirectedUrl("/myProfile/pedro"));
	//		;
	//	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontDeleteCompetitionAdmin() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitionAdmin/delete")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})
	@Test //CASO POSITIVO
	void initUpdatePresidentForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myCompetitionAdminProfile/edit")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontInitUpdatePresidentForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myCompetitionAdminProfile/edit")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})
	@Test //CASO POSITIVO
	void processUpdateCompetitionAdminForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/myCompetitionAdminProfile/edit").with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "First Name").param("lastName", "Last Name").param("telephone", "111111111")
			.param("email", "test@gmail.com").param("dni", "11111111A")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontProcessUpdateCompetitionAdminForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/myCompetitionAdminProfile/edit").with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "First Name").param("lastName", "Last Name").param("telephone", "111111111")
			.param("email", "test@gmail.com").param("dni", "11111111A")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})
	@Test //CASO NEGATIVO
	void ProcessWithErrorUpdateCompetitionAdminForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/myCompetitionAdminProfile/edit").with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "").param("lastName", "").param("telephone", "").param("email", "").param("dni", ""))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("competitionAdmin")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("competitionAdmin", "firstName"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("competitionAdmin", "lastName")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("competitionAdmin", "telephone"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("competitionAdmin", "email")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("competitionAdmin", "dni"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})
	@Test //CASO POSITIVO
	void showCompetitionAdminProfile() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myCompetitionAdminProfile")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontShowCompetitionAdminProfile() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/myCompetitionAdminProfile")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

}
