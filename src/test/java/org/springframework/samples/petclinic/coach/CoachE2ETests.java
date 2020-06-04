
package org.springframework.samples.petclinic.coach;

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

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
//@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
//@AutoConfigureTestDatabase(replace = Replace.ANY)
public class CoachE2ETests {

	@Autowired
	private MockMvc mockMvc;


	@WithMockUser(username = "presidente1", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - VISTA DE ENTRENADOR
	void testShowCoach() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/coachs/{coachId}", 1)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("coach"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("clubId")).andExpect(MockMvcResultMatchers.model().attributeExists("clubStatus"))
			.andExpect(MockMvcResultMatchers.model().attribute("coach", Matchers.hasProperty("firstName", Matchers.is("Julen")))).andExpect(MockMvcResultMatchers.model().attribute("coach", Matchers.hasProperty("lastName", Matchers.is("Lopetegui"))))
			.andExpect(MockMvcResultMatchers.model().attribute("coach", Matchers.hasProperty("salary", Matchers.is(3000000)))).andExpect(MockMvcResultMatchers.model().attribute("coach", Matchers.hasProperty("clause", Matchers.is(6000000))))
			.andExpect(MockMvcResultMatchers.model().attribute("coachAge", Matchers.is(52))).andExpect(MockMvcResultMatchers.view().name("coachs/coachDetails")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/coachs/coachDetails.jsp"));
	}

	@Test //CASO NEGATIVO - VISTA DE ENTRENADOR sin user
	void testShowCoachError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/coachs/{coachId}", 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "owner8", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - GET - REGISTRAR ENTRENADOR
	void testInitCreationFormSuccess() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/coach/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("coach")).andExpect(MockMvcResultMatchers.model().attributeExists("regs"))
			.andExpect(MockMvcResultMatchers.view().name("coachs/createOrUpdateCoachForm")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/coachs/createOrUpdateCoachForm.jsp"));
	}

	@WithMockUser(username = "rafa", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - GET - REGISTRAR ENTRENADOR con presidente sin club
	void testInitCreationFormWithoutClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/coach/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("coach"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("regs")).andExpect(MockMvcResultMatchers.view().name("footballClubs/myClubEmpty")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/myClubEmpty.jsp"));
	}

	@WithMockUser(username = "presidente1", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - GET - REGISTRAR ENTRENADOR con presidente con club público
	void testInitCreationFormWithClubPublished() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/coach/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("coach"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("regs")).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@WithMockUser(username = "owner8", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - POST - REGISTRAR ENTRENADOR
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/coach/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "Barret").param("lastName", "Wallace").param("salary", "1000000").param("birthDate", "2000/01/01"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/coachs/13"));
	}

	@WithMockUser(username = "owner8", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - POST - REGISTRAR ENTRENADOR
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/coach/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("lastName", "Wallace").param("salary", "1000000").param("birthDate", "2000/01/01"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("coach")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("coachs/createOrUpdateCoachForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/coachs/createOrUpdateCoachForm.jsp"));
	}

	@WithMockUser(username = "presidente1", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - GET - FICHAR ENTRENADOR
	void testInitSigningFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/coach/{coachId}/sign", 10)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("coach"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("clubCoach")).andExpect(MockMvcResultMatchers.model().attribute("freeAgent", Matchers.is(true))).andExpect(MockMvcResultMatchers.model().attribute("iHaveCoach", Matchers.is(true)))
			.andExpect(MockMvcResultMatchers.model().attribute("toPayValue", Matchers.is(6000000 + " €"))).andExpect(MockMvcResultMatchers.model().attribute("myCoachFirstName", Matchers.is("Julen")))
			.andExpect(MockMvcResultMatchers.model().attribute("myCoachLastName", Matchers.is("Lopetegui"))).andExpect(MockMvcResultMatchers.model().attribute("clubName", Matchers.is("Sevilla Fútbol Club")))
			.andExpect(MockMvcResultMatchers.model().attribute("readonly", Matchers.is(true))).andExpect(MockMvcResultMatchers.view().name("coachs/createOrUpdateCoachForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/coachs/createOrUpdateCoachForm.jsp"));
	}

	@WithMockUser(username = "rafa", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - GET - FICHAR ENTRENADOR con presidente sin club
	void testInitSigningFormWithoutClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/coach/{coachId}/sign", 2)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("coach"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("clubCoach")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("freeAgent")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("iHaveCoach"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("toPayValue")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("myCoachFirstName")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("myCoachLastName"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("clubName")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("readonly")).andExpect(MockMvcResultMatchers.view().name("footballClubs/myClubEmpty"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/myClubEmpty.jsp"));
	}

	@WithMockUser(username = "owner8", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - GET - FICHAR ENTRENADOR con club y con presidente con club no publicado 
	void testInitSigningFormWithClubNotPublished() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/coach/{coachId}/sign", 3)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("coach"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("clubCoach")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("freeAgent")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("iHaveCoach"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("toPayValue")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("myCoachFirstName")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("myCoachLastName"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("clubName")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("readonly")).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - GET - FICHAR ENTRENADOR sin user
	void testInitSigningFormWithoutUser() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/coachs/{coachId}/sign", 2)).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "owner8", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - POST - FICHAR  ENTRENADOR
	void testProcessSigningFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/coach/{coachId}/sign", 10).with(SecurityMockMvcRequestPostProcessors.csrf()).param("salary", "1000000")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithMockUser(username = "presidente1", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - POST - FICHAR  ENTRENADOR
	void testProcessSigningFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/coach/{coachId}/sign", 2).with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.model().attributeHasErrors("coach"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("clubCoach")).andExpect(MockMvcResultMatchers.model().attribute("freeAgent", Matchers.is(true))).andExpect(MockMvcResultMatchers.model().attribute("iHaveCoach", Matchers.is(true)))
			.andExpect(MockMvcResultMatchers.model().attribute("toPayValue", Matchers.is(6000000 + " €"))).andExpect(MockMvcResultMatchers.model().attribute("myCoachFirstName", Matchers.is("Julen")))
			.andExpect(MockMvcResultMatchers.model().attribute("myCoachLastName", Matchers.is("Lopetegui"))).andExpect(MockMvcResultMatchers.model().attribute("clubName", Matchers.is("Sevilla Fútbol Club")))
			.andExpect(MockMvcResultMatchers.model().attribute("readonly", Matchers.is(true))).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("coachs/createOrUpdateCoachForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/coachs/createOrUpdateCoachForm.jsp"));
	}

	@WithMockUser(username = "presidente1", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - DESPEDIR ENTRENADOR
	void testFireCoach() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/coach/{coachId}/fire", 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/footballClubs/myClub/presidente1"));
	}

	@WithMockUser(username = "presidente1", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - DESPEDIR ENTRENADOR siendo agente libre
	void testFireCoachFAError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/coach/{coachId}/fire", 10)).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@WithMockUser(username = "presidente1", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - DESPEDIR ENTRENADOR de otro club
	void testFireCoachWrongPresident() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/coach/{coachId}/fire", 3)).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - DESPEDIR ENTRENADOR sin user
	void testFireCoachWithoutUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/coachs/{coachId}/fire", 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

}
