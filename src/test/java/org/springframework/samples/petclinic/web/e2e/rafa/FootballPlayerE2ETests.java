
package org.springframework.samples.petclinic.web.e2e.rafa;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Enum.FootballPlayerPosition;
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
public class FootballPlayerE2ETests {

	@Autowired
	private MockMvc mockMvc;


	@WithMockUser(username = "rafa", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - LISTA DE JUGADORES
	void testShowFootballPlayerList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballPlayers")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("footballPlayers"))
			.andExpect(MockMvcResultMatchers.model().attribute("footballPlayers", Matchers.hasSize(68))).andExpect(MockMvcResultMatchers.view().name("footballPlayers/footballPlayerList"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballPlayers/footballPlayerList.jsp"));
	}

	@Test //CASO NEGATIVO - LISTA DE JUGADORES sin user
	void testShowFootballPlayerListError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballPlayers")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "rafa", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - LISTA DE JUGADORES FA
	void testShowFootballPlayerFAList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballPlayers/freeAgent")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("footballPlayers"))
			//.andExpect(MockMvcResultMatchers.model().attribute("footballPlayers", Matchers.hasSize(5)))
			.andExpect(MockMvcResultMatchers.view().name("footballPlayers/footballPlayerList")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballPlayers/footballPlayerList.jsp"));
	}

	@Test //CASO NEGATIVO - LISTA DE JUGADORES FA sin user
	void testShowFootballPlayerFAListError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballPlayers/freeAgent")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "rafa", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - LISTA DE JUGADORES DE UN EQUIPO
	void testShowFootballPlayerClubList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list/{footballClubId}/footballPlayers", 1)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("footballPlayers"))
			//.andExpect(MockMvcResultMatchers.model().attribute("footballPlayers", Matchers.hasSize(7)))
			.andExpect(MockMvcResultMatchers.view().name("footballPlayers/footballPlayerList")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballPlayers/footballPlayerList.jsp"));
	}

	@Test //CASO NEGATIVO - LISTA DE JUGADORES DE UN EQUIPO sin user
	void testShowFootballPlayerClubListError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list/{footballClubId}/footballPlayers", 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "presidente1", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - LISTA DE JUGADORES DE MI EQUIPO
	void testShowFootballPlayerMyClubList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/footballPlayers")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("footballPlayers"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("thisClubPresidentUsername")).andExpect(MockMvcResultMatchers.model().attributeExists("thisClubStatus"))
			//.andExpect(MockMvcResultMatchers.model().attribute("footballPlayers", Matchers.hasSize(8)))
			.andExpect(MockMvcResultMatchers.view().name("footballPlayers/footballPlayerList")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballPlayers/footballPlayerList.jsp"));
	}

	@WithMockUser(username = "rafa", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - LISTA DE JUGADORES DE MI EQUIPO con el presidente sin equipo
	void testShowFootballPlayerMyClubListWithoutClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/footballPlayers")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("footballPlayers"))
			.andExpect(MockMvcResultMatchers.view().name("footballClubs/myClubEmpty")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/myClubEmpty.jsp"));
	}

	@Test //CASO NEGATIVO - LISTA DE JUGADORES DE UN EQUIPO sin user
	void testShowFootballPlayerMyClubListError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/footballPlayers")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "rafa", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - VER JUGADOR DETALLADAMENTE POR ID
	void testShowFootballPlayer() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballPlayers/{footballPlayerId}", 1)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("footballPlayer"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("footballPlayerAge")).andExpect(MockMvcResultMatchers.model().attribute("footballPlayer", Matchers.hasProperty("firstName", Matchers.is("Tomas"))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballPlayer", Matchers.hasProperty("lastName", Matchers.is("Vaclik"))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballPlayer", Matchers.hasProperty("position", Matchers.is(FootballPlayerPosition.GOALKEEPER))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballPlayer", Matchers.hasProperty("value", Matchers.is(18000000)))).andExpect(MockMvcResultMatchers.view().name("footballPlayers/footballPlayerDetails"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballPlayers/footballPlayerDetails.jsp"));
	}

	@Test //CASO NEGATIVO - VER JUGADOR DETALLADAMENTE POR ID sin user
	void testShowFootballPlayerError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballPlayers/{footballPlayerId}", 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "owner8", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - GET - REGISTRAR JUGADOR
	void testInitCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballPlayer/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("footballPlayer"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("positions")).andExpect(MockMvcResultMatchers.model().attributeExists("salary")).andExpect(MockMvcResultMatchers.model().attributeExists("clause"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("startDate")).andExpect(MockMvcResultMatchers.model().attributeExists("endDate")).andExpect(MockMvcResultMatchers.view().name("footballPlayers/createOrUpdateFootballPlayerForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballPlayers/createOrUpdateFootballPlayerForm.jsp"));
	}

	@WithMockUser(username = "presidente1", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - GET - REGISTRAR JUGADOR con club público
	void testInitCreationFormWithClubPublished() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballPlayer/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - GET - REGISTRAR JUGADOR sin user
	void testInitCreationFormSuccessError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballPlayer/new")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "owner8", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - POST - REGISTRAR JUGADOR
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/footballPlayer/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "Tom").param("lastName", "Baker").param("position", "MIDFIELDER").param("birthDate", "1997/01/01"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/footballClubs/myClub/footballPlayers"));
	}

	@WithMockUser(username = "owner8", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - POST - REGISTRAR JUGADOR sin todos los parámetros necesarios
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/footballPlayer/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("lastName", "Baker").param("position", "MIDFIELDER").param("birthDate", "1997/01/01"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("footballPlayer")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("footballPlayers/createOrUpdateFootballPlayerForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballPlayers/createOrUpdateFootballPlayerForm.jsp"));
	}

}
