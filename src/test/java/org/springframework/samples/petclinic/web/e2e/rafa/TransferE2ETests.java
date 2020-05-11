
package org.springframework.samples.petclinic.web.e2e.rafa;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class TransferE2ETests {

	@Autowired
	private MockMvc mockMvc;


	@WithMockUser(username = "presidente1", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - PANEL DE FICHAJES
	void testShowTransferPanel() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/panel")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("status"))
			.andExpect(MockMvcResultMatchers.view().name("transfers/transferPanel")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/transfers/transferPanel.jsp"));
	}

	@WithMockUser(username = "rafa", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - PANEL DE FICHAJES sin club
	void testShowTransferPanelWithoutClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/panel")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("status"))
			.andExpect(MockMvcResultMatchers.view().name("footballClubs/myClubEmpty")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/myClubEmpty.jsp"));
	}

	@Test //CASO NEGATIVO - PANEL DE FICHAJES sin user
	void testShowTransferPanelError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/panel")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "presidente1", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - LISTA DE ENTRENADORES
	void testShowCoachList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("coachs"))
			.andExpect(MockMvcResultMatchers.model().attribute("coachs", Matchers.hasSize(8))).andExpect(MockMvcResultMatchers.view().name("transfers/coachList")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/transfers/coachList.jsp"));
	}

	@WithMockUser(username = "owner8", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - LISTA DE ENTRENADORES con presidente con club sin publicar
	void testShowCoachListWithClubNotPublished() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("coachs"))
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@WithMockUser(username = "rafa", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - LISTA DE ENTRENADORES con presidente sin club
	void testShowCoachListWithoutClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("coachs"))
			.andExpect(MockMvcResultMatchers.view().name("footballClubs/myClubEmpty")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/myClubEmpty.jsp"));
	}

	@Test //CASO NEGATIVO - LISTA DE ENTRENADORES sin user
	void testShowCoachErrorListWithoutUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coachs")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "owner8", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - LISTA DE ENTRENADORES FA
	void testShowFACoachList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/free-agents")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("coachs"))
			.andExpect(MockMvcResultMatchers.model().attribute("coachs", Matchers.hasSize(3))).andExpect(MockMvcResultMatchers.view().name("coachs/coachList")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/coachs/coachList.jsp"));
	}

	@WithMockUser(username = "rafa", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - LISTA DE ENTRENADORES FA con presidente sin club
	void testShowCoachFAListWithoutClub() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/free-agents")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("coachs"))
			.andExpect(MockMvcResultMatchers.view().name("footballClubs/myClubEmpty")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/myClubEmpty.jsp"));
	}

	@Test //CASO NEGATIVO - LISTA DE ENTRENADORES FA sin user
	void testShowCoachFAErrorListWithoutUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coachs/free-agents")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "presidente1", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - LISTA DE JUGADORES
	void testShowPlayerList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("footballPlayers"))
			.andExpect(MockMvcResultMatchers.model().attribute("footballPlayers", Matchers.hasSize(56))).andExpect(MockMvcResultMatchers.view().name("transfers/footballPlayerList"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/transfers/footballPlayerList.jsp"));
	}

	@WithMockUser(username = "owner8", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - LISTA DE JUGADORES con presidente con club sin publicar
	void testShowPlayerListWithClubNotPublished() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("footballPlayers"))
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@WithMockUser(username = "rafa", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - LISTA DE JUGADORES con presidente sin club
	void testShowPlayerListWithoutClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("footballPlayers"))
			.andExpect(MockMvcResultMatchers.view().name("footballClubs/myClubEmpty")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/myClubEmpty.jsp"));
	}

	@Test //CASO NEGATIVO - LISTA DE JUGADORES sin user
	void testShowPlayerErrorListWithoutUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "owner8", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - LISTA DE JUGADORES FA
	void testShowPlayerFAList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/free-agents")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("footballPlayers"))
			.andExpect(MockMvcResultMatchers.model().attribute("footballPlayers", Matchers.hasSize(5))).andExpect(MockMvcResultMatchers.view().name("transfers/footballPlayerList"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/transfers/footballPlayerList.jsp"));
	}

	@WithMockUser(username = "rafa", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - LISTA DE JUGADORES FA con presidente sin club
	void testShowPlayerFAListWithoutClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/free-agents")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("footballPlayers"))
			.andExpect(MockMvcResultMatchers.view().name("footballClubs/myClubEmpty")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/myClubEmpty.jsp"));
	}

	@Test //CASO NEGATIVO - LISTA DE JUGADORES FA sin user
	void testShowPlayerFAErrorListWithoutUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/free-agents")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

}
