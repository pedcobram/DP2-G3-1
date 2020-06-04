
package org.springframework.samples.petclinic.footballClub;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
//@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
//@AutoConfigureTestDatabase(replace = Replace.ANY)
@Transactional
public class FootballClubE2ETests {

	@Autowired
	private MockMvc mockMvc;


	//	@WithMockUser(username = "presidente1", authorities = {
	//		"president"
	//	})
	//
	//	@Test //CASO POSITIVO - VER EQUIPO DETALLADAMENTE POR ID
	//	void testShowFootballClub() throws Exception {
	//
	//		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list/{footballClubId}", 1)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("footballClub"))
	//			.andExpect(MockMvcResultMatchers.model().attributeExists("coach")).andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("city", Matchers.is("Seville"))))
	//			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("stadium", Matchers.is("Ramón Sánchez-Pizjuan"))))
	//			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("money", Matchers.is(150000000)))).andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("fans", Matchers.is(44000))))
	//			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("status", Matchers.is(true)))).andExpect(MockMvcResultMatchers.model().attribute("coach", Matchers.hasProperty("firstName", Matchers.is("Julen"))))
	//			.andExpect(MockMvcResultMatchers.model().attribute("coach", Matchers.hasProperty("lastName", Matchers.is("Lopetegui")))).andExpect(MockMvcResultMatchers.view().name("footballClubs/footballClubDetails"))
	//			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/footballClubDetails.jsp"));
	//	}

	@WithMockUser(username = "rafa", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - GET - CREAR CLUB
	void testInitCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/new")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithMockUser(username = "presidente1", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - GET - CREAR CLUB teniendo ya club
	void testInitCreationFormWithClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - GET - CREAR CLUB
	void testInitCreationFormSuccessError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/new")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "rafa", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - POST - CREAR CLUB
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/footballClubs/myClub/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Shinra Inc").param("city", "Midgar").param("crest", "https://www.example.com")
			.param("stadium", "Suburbios Stadium").param("foundationDate", "1997/01/01")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithMockUser(username = "rafa", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - POST - CREAR CLUB 
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/footballClubs/myClub/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Shinra Inc").param("crest", "https://www.example.com").param("stadium", "Suburbios Stadium")
				.param("foundationDate", "1997/01/01"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("footballClub")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("footballClubs/createOrUpdateFootballClubForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/createOrUpdateFootballClubForm.jsp"));
	}

	@WithMockUser(username = "owner8", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - GET - EDITAR CLUB
	void testInitUpdateForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/{principalUsername}/edit", "owner8")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("footballClub"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("isEditing")).andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("name", Matchers.is("Besaid Aurochs"))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("city", Matchers.is("Besaid Island"))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("stadium", Matchers.is("Besaid Island Stadium"))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("fans", Matchers.is(40)))).andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("status", Matchers.is(false))))
			.andExpect(MockMvcResultMatchers.view().name("footballClubs/createOrUpdateFootballClubForm")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/createOrUpdateFootballClubForm.jsp"));
	}

	@WithMockUser(username = "owner8", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - GET - EDITAR CLUB con otro user
	void testInitUpdateFormWhitAnotherUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/{principalUsername}/edit", "rufus")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - GET - EDITAR CLUB
	void testInitUpdateFormError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/{principalUsername}/edit", "rufus")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "owner8", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - POST - EDITAR CLUB
	void testProcessUpdateFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/footballClubs/myClub/{principalUsername}/edit", "owner8").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Besaid Aurochs").param("city", "Besaid Island")
			.param("crest", "https://www.example.com").param("stadium", "Besaid Island Stadium").param("foundationDate", "1997/01/01")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithMockUser(username = "owner8", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - POST - EDITAR CLUB
	void testProcessUpdateFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/footballClubs/myClub/{principalUsername}/edit", "owner8").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "ShinraEdit Inc").param("crest", "https://www.example.com")
				.param("stadium", "Suburbios Stadium").param("foundationDate", "1997/01/01"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("footballClub")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("footballClubs/createOrUpdateFootballClubForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/createOrUpdateFootballClubForm.jsp"));
	}

	@WithMockUser(username = "presidente1", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - LISTA DE EQUIPOS
	void testShowFootballClubList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("footballClubs"))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClubs", Matchers.hasSize(9))).andExpect(MockMvcResultMatchers.view().name("footballClubs/footballClubList"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/footballClubList.jsp"));
	}

	@Test //CASO NEGATIVO - LISTA DE EQUIPOS
	void testShowFootballClubListError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	//	@WithMockUser(username = "presidente1", authorities = {
	//		"president"
	//	})
	//
	//	@Test //CASO POSITIVO - VER EQUIPO DETALLADAMENTE POR USERNAME
	//	void testShowFootballClubByUsername() throws Exception {
	//		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/{principalUsername}", "presidente1")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("footballClub"))
	//			.andExpect(MockMvcResultMatchers.model().attributeExists("coach")).andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("name", Matchers.is("Sevilla Fútbol Club"))))
	//			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("city", Matchers.is("Seville"))))
	//			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("stadium", Matchers.is("Ramón Sánchez-Pizjuan"))))
	//			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("money", Matchers.is(150000000)))).andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("fans", Matchers.is(44000))))
	//			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("status", Matchers.is(true)))).andExpect(MockMvcResultMatchers.model().attribute("coach", Matchers.hasProperty("firstName", Matchers.is("Julen"))))
	//			.andExpect(MockMvcResultMatchers.model().attribute("coach", Matchers.hasProperty("lastName", Matchers.is("Lopetegui")))).andExpect(MockMvcResultMatchers.view().name("footballClubs/myClubDetails"))
	//			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/myClubDetails.jsp"));
	//	}

	@WithMockUser(username = "rafa2", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - VER EQUIPO DETALLADAMENTE POR USERNAME sin club
	void testShowFootballClubByUsernameWithoutClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/{principalUsername}", "rafa2")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("footballClub"))
			.andExpect(MockMvcResultMatchers.view().name("footballClubs/myClubEmpty")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/myClubEmpty.jsp"));
	}

	@Test //CASO NEGATIVO - VER EQUIPO DETALLADAMENTE POR USERNAME
	void testShowFootballClubByUsernameError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list/{principalUsername}", "rufus")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));

	}

	@Test //CASO NEGATIVO - VER EQUIPO DETALLADAMENTE POR ID
	void testShowFootballClubError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list/{footballClubId}", 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "presidente1", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - BORRAR EQUIPO
	void testDeleteFootballClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/{principalUsername}/delete", "presidente1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithMockUser(username = "presidente2", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - BORRAR EQUIPO con otro user
	void testDeleteFootballClubWrongPresident() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/{principalUsername}/delete", "presidente1")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - BORRAR EQUIPO sin user
	void testDeleteFootballClubError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/{principalUsername}/delete", "presidente1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));

	}

}
