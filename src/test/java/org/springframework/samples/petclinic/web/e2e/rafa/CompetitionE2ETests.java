
package org.springframework.samples.petclinic.web.e2e.rafa;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Enum.CompetitionType;
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
public class CompetitionE2ETests {

	@Autowired
	private MockMvc mockMvc;


	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})

	@Test //CASO POSITIVO - VISTA DETALLADA DE COMPETICIÓN
	void testShowCompetition() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}", 1)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("competition"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("size")).andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("name", Matchers.is("Premier League"))))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("description", Matchers.is("Torneo donde los equipos participantes jugarán todos contra todos."))))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("type", Matchers.is(CompetitionType.LEAGUE))))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("reward", Matchers.is(10000000)))).andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("creator", Matchers.is("pedro"))))
			.andExpect(MockMvcResultMatchers.view().name("competitions/competitionDetails")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/competitionDetails.jsp"));
	}

	@WithMockUser(username = "pedro2", authorities = {
		"competitionAdmin"
	})

	@Test //CASO NEGATIVO - VISTA DE COMPETICIÓN si no se es el creador y no está publicado
	void testShowCompetitionViewError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}", 1)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("competition"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("size")).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - VISTA DE COMPETICIÓN sin user
	void testShowCompetitionError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}", 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})

	@Test //CASO POSITIVO - VISTA DETALLADA DE CALENDARIO
	void testShowCalendary() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/calendary", 2))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("calendary"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("jornadas"))
			.andExpect(MockMvcResultMatchers.view().name("competitions/calendaryDetails"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/calendaryDetails.jsp"));
	}

	@Test //CASO NEGATIVO - VISTA DE CALENDARIO sin user
	void testShowCalendaryError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/calendary", 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})

	@Test //CASO POSITIVO - VISTA DETALLADA DE JORNADA
	void testShowJornada() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/calendary/jornada/{jornadaId}", 1, 1)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("jornada"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("partidos")).andExpect(MockMvcResultMatchers.model().attribute("partidos", Matchers.hasSize(1))).andExpect(MockMvcResultMatchers.view().name("competitions/jornadasDetails"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/jornadasDetails.jsp"));
	}

	@Test //CASO NEGATIVO - VISTA DE JORNADA sin User
	void testShowJornadaError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/calendary/jornada/{jornadaId}", 1, 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})

	@Test //CASO POSITIVO - VISTA DETALLADA DE PARTIDO
	void testShowMatch() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/calendary/jornada/{jornadaId}/match/{matchId}", 1, 1, 1)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("match")).andExpect(MockMvcResultMatchers.model().attribute("match", Matchers.hasProperty("title", Matchers.is("Match title 1"))))
			.andExpect(MockMvcResultMatchers.model().attribute("match", Matchers.hasProperty("stadium", Matchers.is("Ramón Sánchez-Pizjuan")))).andExpect(MockMvcResultMatchers.view().name("competitions/matchDetails"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/matchDetails.jsp"));
	}

	@Test //CASO NEGATIVO - VISTA DE PARTIDO sin User
	void testShowMatchError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/calendary/jornada/{jornadaId}/match/{matchId}", 1, 1, 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})

	@Test //CASO POSITIVO - LISTA DE COMPETICIONES PUBLICADAS
	void testShowCompetitionsPublishedList() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("competitions"))
			.andExpect(MockMvcResultMatchers.model().attribute("competitions", Matchers.hasSize(1))).andExpect(MockMvcResultMatchers.view().name("competitions/competitionList"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/competitionList.jsp"));
	}

	@Test //CASO NEGATIVO - VISTA DE COMPETICIONES PUBLICADAS sin User
	void testShowCompetitionsPublishedError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/list")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})

	@Test //CASO POSITIVO - LISTA DE MIS COMPETICIONES
	void testShowMyCompetitionsList() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/mylist")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("competitions"))
			.andExpect(MockMvcResultMatchers.model().attribute("competitions", Matchers.hasSize(2))).andExpect(MockMvcResultMatchers.view().name("competitions/competitionList"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/competitionList.jsp"));
	}

	@Test //CASO NEGATIVO - VISTA DE MIS COMPETICIONES sin User
	void testShowMyCompetitionsError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/mylist")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})

	@Test //CASO POSITIVO - GET - CREAR COMPETICIÓN
	void testInitCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("competition"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("isNew")).andExpect(MockMvcResultMatchers.view().name("competitions/createOrUpdateCompetitionForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/createOrUpdateCompetitionForm.jsp"));
	}

	@Test //CASO NEGATIVO - GET - CREAR COMPETICIÓN sin user
	void testInitCreationFormError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/new"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})

	@Test //CASO POSITIVO - POST - CREAR COMPETICIÓN
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/competition/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "The League").param("description", "Description").param("type", "LEAGUE").param("status", "false")
			.param("creator", "rufus").param("reward", "50000000")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/competitions/3"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})

	@Test //CASO NEGATIVO - POST - CREAR COMPETICIÓN
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc
			.perform(
				MockMvcRequestBuilders.post("/competition/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("description", "Description").param("type", "LEAGUE").param("status", "false").param("creator", "rufus").param("reward", "50000000"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("competition")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("competitions/createOrUpdateCompetitionForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/createOrUpdateCompetitionForm.jsp"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})

	@Test //CASO POSITIVO - GET - EDITAR COMPETICIÓN
	void testInitUpdateForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/edit", 1)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("competition"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("isEditing")).andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("name", Matchers.is("Premier League"))))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("description", Matchers.is("Torneo donde los equipos participantes jugarán todos contra todos."))))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("creator", Matchers.is("pedro")))).andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("reward", Matchers.is(10000000))))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("status", Matchers.is(false))))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("type", Matchers.is(CompetitionType.LEAGUE))))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("status", Matchers.is(false)))).andExpect(MockMvcResultMatchers.view().name("competitions/createOrUpdateCompetitionForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/createOrUpdateCompetitionForm.jsp"));
	}

	@WithMockUser(username = "pedro2", authorities = {
		"competitionAdmin"
	})

	@Test //CASO NEGATIVO - GET - EDITAR COMPETICIÓN con otro user
	void testInitUpdateFormWhitAnotherUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/edit", 1)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})

	@Test //CASO NEGATIVO - GET - EDITAR COMPETICIÓN si ya está publicada
	void testInitUpdateFormWithPublishedComp() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/edit", 2)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - GET - EDITAR COMPETICIÓN sin user
	void testInitUpdateFormError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/edit", 1))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})

	@Test //CASO POSITIVO - POST - EDITAR COMPETICIÓN
	void testProcessUpdateFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/competition/{competitionId}/edit", 1).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "The League2").param("description", "Description2").param("type", "LEAGUE")
			.param("status", "false").param("creator", "rufus").param("reward", "50000002")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/competitions/1"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})

	@Test //CASO NEGATIVO - POST - EDITAR COMPETICIÓN
	void testProcessUpdateFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/competition/{competitionId}/edit", 1).with(SecurityMockMvcRequestPostProcessors.csrf()).param("description", "Description2").param("type", "LEAGUE").param("status", "false").param("creator", "rufus")
				.param("reward", "50000002"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("competition")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("competitions/createOrUpdateCompetitionForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/createOrUpdateCompetitionForm.jsp"));
	}

	@WithMockUser(username = "pedro2", authorities = {
		"competitionAdmin"
	})

	@Test //CASO NEGATIVO - POST - EDITAR COMPETICIÓN con otro user
	void testProcessUpdateFormWithOtherUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/competition/{competitionId}/edit", 1).with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})

	@Test //CASO POSITIVO - GET - AÑADIR EQUIPOS
	void testInitAddForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/addClubs", 1)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("competition"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("clubsName")).andExpect(MockMvcResultMatchers.model().attributeExists("isAdd")).andExpect(MockMvcResultMatchers.model().attributeExists("size"))
			.andExpect(MockMvcResultMatchers.model().attribute("clubsName", Matchers.hasSize(5))).andExpect(MockMvcResultMatchers.view().name("competitions/listClubs"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/listClubs.jsp"));
	}

	@WithMockUser(username = "pedro2", authorities = {
		"competitionAdmin"
	})

	@Test //CASO NEGATIVO - GET - AÑADIR EQUIPOS con otro user
	void testProcessAddFormWithOtherUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/competition/{competitionId}/addClubs", 1).with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - GET - AÑADIR EQUIPOS sin user
	void testInitAddFormError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/addClubs", 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})

	@Test //CASO POSITIVO - POST - AÑADIR EQUIPOS
	void testProcessAddFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/competition/{competitionId}/addClubs", 1).with(SecurityMockMvcRequestPostProcessors.csrf()).param("clubs", "Club2")).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/competitions/1"));
	}

	@WithMockUser(username = "pedro2", authorities = {
		"competitionAdmin"
	})

	@Test //CASO NEGATIVO - POST - AÑADIR EQUIPOS con otro Usuario
	void testProcessAddFormWithErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/competition/{competitionId}/addClubs", 1).with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})

	@Test //CASO POSITIVO - GET - VER EQUIPOS
	void testInitShowForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/clubs", 1)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("competition"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("clubsName")).andExpect(MockMvcResultMatchers.model().attributeExists("isAdd")).andExpect(MockMvcResultMatchers.model().attributeExists("size"))
			.andExpect(MockMvcResultMatchers.model().attribute("clubsName", Matchers.hasSize(4))).andExpect(MockMvcResultMatchers.view().name("competitions/listClubs"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/listClubs.jsp"));
	}

	@WithMockUser(username = "pedro2", authorities = {
		"competitionAdmin"
	})

	@Test //CASO NEGATIVO - GET - VER EQUIPOS con otro user
	void testProcessShowFormWithOtherUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/clubs", 1).with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - GET - VER EQUIPOS sin user
	void testInitShowFormError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/clubs", 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})

	@Test //CASO POSITIVO - POST - BORRAR EQUIPOS
	void testProcessRemoveFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/competitions/{competitionId}/clubs", 1).with(SecurityMockMvcRequestPostProcessors.csrf()).param("clubs", "Club2")).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/competitions/1"));
	}

	@WithMockUser(username = "pedro2", authorities = {
		"competitionAdmin"
	})

	@Test //CASO NEGATIVO - POST - BORRAR EQUIPOS con otro Usuario
	void testProcessRemoveFormWithErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/competitions/{competitionId}/clubs", 1).with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})

	@Test //CASO POSITIVO - PUBLICAR COMPETICIÓN
	void testPublishCompetition() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/publish", 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/competitions/1"));
	}

	@WithMockUser(username = "pedro2", authorities = {
		"competitionAdmin"
	})

	@Test //CASO NEGATIVO - PUBLICAR COMPETICIÓN con otro usuario
	void testPublishCompetitionWithOtherUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/publish", 1)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})

	@Test //CASO NEGATIVO - PUBLICAR COMPETICIÓN si ya esta publicada
	void testPublishCompetitionWithAlreadyPublished() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/publish", 2)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - GET - PUBLICAR COMPETICIÓN  sin user
	void testPublishCompetitionError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/publish", 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})

	@Test //CASO POSITIVO - BORRAR COMPETICIÓN
	void testDeleteCompetition() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/delete", 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/"));
	}

	@WithMockUser(username = "pedro2", authorities = {
		"competitionAdmin"
	})

	@Test //CASO NEGATIVO - BORRAR COMPETICIÓN con otro usuario
	void testDeleteCompetitionWithOtherUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/delete", 1)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - GET - BORRAR COMPETICIÓN  sin user
	void testDeleteCompetitionError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/delete", 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

}
