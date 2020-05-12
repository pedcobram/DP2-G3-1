
package org.springframework.samples.petclinic.web.e2e.rafa;

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
public class PresidentE2ETests {

	@Autowired
	private MockMvc mockMvc;


	@WithMockUser(username = "rafa", authorities = {
		"president"
	})

	@Test // CASO POSITIVO - VER PRESIDENTE DETALLADAMENTE
	void testShowPresident() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/presidents/{presidentUsername}", "rafa")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithMockUser(username = "rufus", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - VER PRESIDENTE DETALLADAMENTE con otro user
	void testShowPresidentErrorWithAnotherUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/presidents/{presidentUsername}", "rafa")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - VER PRESIDENTE DETALLADAMENTE
	void testShowPresidentError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/presidents/{presidentUsername}", "rafa")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));

	}

	@WithMockUser(username = "manuel")

	@Test //CASO POSITIVO - LA CREACIÓN DE UN PRESIDENTE NO TIENE GET
	void testCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/presidents/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "Rufus").param("lastName", "Shinra").param("email", "rufus@shinra.com").param("dni", "12345678H")
			.param("telephone", "600766899")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("/presidents/manuel"));
	}

	@Test //CASO NEGATIVO - CREAR PRESIDENTE sin user conectado
	void testCreationFormSuccessError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/presidents/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "Rufus").param("lastName", "Shinra").param("email", "rufus@shinra.com").param("dni", "12345678H")
			.param("telephone", "600766899")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "rafa", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - GET - EDITAR PRESIDENTE
	void testInitUpdatePresidentForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/presidents/{presidentUsername}/edit", "rafa")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("president"))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("id", Matchers.is(10)))).andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("lastName", Matchers.is("Liébana Fuentes"))))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("firstName", Matchers.is("Rafael")))).andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("dni", Matchers.is("11111111A"))))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("email", Matchers.is("rafliefue@alum.us.es"))))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("telephone", Matchers.is("600111222")))).andExpect(MockMvcResultMatchers.view().name("presidents/createOrUpdatePresidentForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/presidents/createOrUpdatePresidentForm.jsp"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - GET - EDITAR PRESIDENTE con otro user
	void testInitUpdatePresidentFormWithAnotherUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/presidents/{presidentUsername}/edit", "rafa")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - GET - EDITAR PRESIDENTE sin user
	void testInitUpdatePresidentFormError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/presidents/{presidentUsername}/edit", "rafa")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "rafa", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - POST - EDITAR PRESIDENTE
	void testProcessUpdateFormSuccess() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/presidents/{presidentUsername}/edit", "rafa").with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "Rafael").param("lastName", "Liébana Fuentes").param("dni", "11111111A")
				.param("email", "rafliefue@alum.us.es").param("telephone", "600111222").param("user.username", "rafa").param("user.password", "rafa"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/presidents/rafa")).andExpect(MockMvcResultMatchers.redirectedUrl("/presidents/rafa"));
	}

	@WithMockUser(username = "rafa", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - POST - EDITAR PRESIDENTE quitando el parámetro del nombre
	void testProcessUpdateFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/presidents/{presidentUsername}/edit", "rafa").with(SecurityMockMvcRequestPostProcessors.csrf()).param("lastName", "Liébana Fuentes").param("dni", "11111111A").param("email", "rafliefue@alum.us.es")
				.param("telephone", "600111222").param("user.username", "rafa").param("user.password", "rafa"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("president")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("presidents/createOrUpdatePresidentForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/presidents/createOrUpdatePresidentForm.jsp"));
	}

	@WithMockUser(username = "rufus", authorities = {
		"president"
	})

	@Test //CASO NEGATIVO - POST - EDITAR PRESIDENTE con otro user
	void testProcessUpdatePresidentFormWithAnotherUser() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/presidents/{presidentUsername}/edit", "rafa").with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "RufusEdit").param("lastName", "ShinraEdit").param("dni", "88888888H")
				.param("email", "rufusEdit@shinra.com").param("telephone", "888888888").param("user.username", "rafa").param("user.password", "rafa"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@WithMockUser(username = "rafa", authorities = {
		"president"
	})

	@Test //CASO POSITIVO - BORRAR PRESIDENTE
	void testDeletePresident() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/presidents/delete")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/myProfile/rafa"));
	}

	@Test //CASO NEGATIVO - BORRAR PRESIDENTE
	void testDeletePresidentError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/presidents/delete")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

}
