
package org.springframework.samples.petclinic.web.e2e.rafa;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
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
public class ContractPlayerE2ETests {

	@Autowired
	private MockMvc mockMvc;


	@WithMockUser(username = "presidente1", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - LISTA DE CONTRATOS DE JUGADORES DE MI CLUB
	void testShowMyContractList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("contractPlayers"))
			.andExpect(MockMvcResultMatchers.model().attribute("contractPlayers", Matchers.hasSize(7))).andExpect(MockMvcResultMatchers.view().name("contracts/contractPlayerList"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/contracts/contractPlayerList.jsp"));
	}

	@WithMockUser(username = "rafa", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - LISTA DE CONTRATOS DE JUGADORES DE MI CLUB con presidente sin club
	void testShowMyContractListWithClubNotPublished() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("contractPlayers"))
			.andExpect(MockMvcResultMatchers.view().name("footballClubs/myClubEmpty")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/myClubEmpty.jsp"));
	}

	@Test //CASO NEGATIVO - LISTA DE CONTRATOS DE JUGADORES DE MI CLUB sin user
	void testShowMyContractErrorListWithoutUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/list")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "presidente1", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - VISTA DE CONTRATO DETALLADA
	void testShowContract() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/{footballPlayerId}", 1)).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithMockUser(username = "presidente2", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - VISTA DE CONTRATO DETALLADA sin ser el presidente del club del contrato
	void testShowContractWithWrongClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/{footballPlayerId}", 1)).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test //CASO NEGATIVO - VISTA DE CONTRATO DETALLADA sin user
	void testShowContractError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/{footballPlayerId}", 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "presidente1", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - GET - FICHAR AGENTE LIBRE
	void testInitCreationFormSuccess() throws Exception {

		Date moment = new Date(System.currentTimeMillis() - 1);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String date = simpleDateFormat.format(moment);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/{footballPlayerId}/new", 4)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("contractPlayer"))
			.andExpect(MockMvcResultMatchers.model().attribute("startDate", Matchers.is(date))).andExpect(MockMvcResultMatchers.model().attribute("clausula", Matchers.is(12500000)))
			.andExpect(MockMvcResultMatchers.model().attribute("salario", Matchers.is(2500000))).andExpect(MockMvcResultMatchers.model().attribute("valor", Matchers.is(25000000)))
			.andExpect(MockMvcResultMatchers.model().attribute("playerName", Matchers.is("LUCAS OCAMPOS"))).andExpect(MockMvcResultMatchers.model().attribute("clubName", Matchers.is("SEVILLA FÚTBOL CLUB")))
			.andExpect(MockMvcResultMatchers.view().name("contracts/createOrUpdateContractPlayerForm")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/contracts/createOrUpdateContractPlayerForm.jsp"));
	}

	@WithMockUser(username = "rafa", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - GET - FICHAR AGENTE LIBRE con presidente sin club
	void testInitCreationFormWithoutClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/{footballPlayerId}/new", 4)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("contractPlayer"))
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - GET - FICHAR AGENTE LIBRE sin user
	void testInitCreationFormError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/{footballPlayerId}/new", 4)).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "presidente1", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - POST - FICHAR AGENTE LIBRE
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/contractPlayer/{footballPlayerId}/new", 64).with(SecurityMockMvcRequestPostProcessors.csrf()).param("salary", "1000000").param("endDate", "2024/01/01"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/contractPlayer/64"));
	}

	@WithMockUser(username = "presidente1", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - POST - FICHAR AGENTE LIBRE sin todos los parámetros
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/contractPlayer/{footballPlayerId}/new", 4).with(SecurityMockMvcRequestPostProcessors.csrf()).param("endDate", "2024/01/01"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("contractPlayer")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("contracts/createOrUpdateContractPlayerForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/contracts/createOrUpdateContractPlayerForm.jsp"));
	}

	@WithMockUser(username = "presidente1", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - DESPEDIR JUGADOR
	void testFirePlayer() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/{footballPlayerId}/delete", 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/footballClubs/myClub/presidente1"));
	}

	@WithMockUser(username = "presidente1", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - DESPEDIR JUGADOR que es agente libre
	void testFireFAPlayerError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/{footballPlayerId}/delete", 65)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@WithMockUser(username = "presidente2", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - DESPEDIR JUGADOR con otro user que no es el dueño del contrato
	void testFirePlayerWrongPresident() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/{footballPlayerId}/delete", 1)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - DESPEDIR JUGADOR sin user
	void testDeleteFootballClubError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/{footballPlayerId}/delete", 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));

	}

}
