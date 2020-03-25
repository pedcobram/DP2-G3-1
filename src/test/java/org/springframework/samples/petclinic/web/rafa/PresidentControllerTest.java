
package org.springframework.samples.petclinic.web.rafa;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthenticatedService;
import org.springframework.samples.petclinic.service.PresidentService;
import org.springframework.samples.petclinic.web.PresidentController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = PresidentController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class PresidentControllerTest {

	@Autowired
	private PresidentController		presidentController;

	@Autowired
	private MockMvc					mockMvc;

	@MockBean
	private PresidentService		presidentService;

	@MockBean
	private AuthenticatedService	authenticatedService;

	private President				rufus;


	@BeforeEach
	void setup() {

		User user = new User();
		user.setUsername("rufus");
		user.setPassword("shinra");
		user.setEnabled(true);

		this.rufus = new President();
		this.rufus.setId(99);
		this.rufus.setFirstName("Rufus");
		this.rufus.setLastName("Shinra");
		this.rufus.setDni("12345678H");
		this.rufus.setEmail("rufus@shinra.com");
		this.rufus.setTelephone("608551023");
		this.rufus.setUser(user);
		BDDMockito.given(this.presidentService.findPresidentByUsername("rufus")).willReturn(this.rufus);
	}

	@WithMockUser(username = "ignacio", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO - LA CREACIÓN DE UN PRESIDENTE NO TIENE GET
	void testCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/presidents/new")
			.param("firstName", "Rufus").param("lastName", "Shinra")
			.with(SecurityMockMvcRequestPostProcessors.csrf())
			.param("email", "rufus@shinra.com").param("dni", "12345678H").param("telephone", "600766899"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	/**
	 * @WithMockUser(username = "rafa", authorities = {
	 *                        "authenticated"
	 *                        })
	 * 
	 * @Test //LA CREACIÓN DE UN PRESIDENTE NO TIENE GET
	 *       void testCreationFormHasErrors() throws Exception {
	 *       this.mockMvc.perform(
	 *       post("/presidents/new").param("firstName", "Rufus").param("lastName", "Shinra").with(csrf())
	 *       .param("dni", "12345678H"))
	 *       .andExpect(status().isOk())
	 *       .andExpect(model().attributeHasErrors("president"))
	 *       .andExpect(model().attributeHasFieldErrors("president", "email"))
	 *       .andExpect(model().attributeHasFieldErrors("president", "telephone"));
	 *       }
	 * 
	 **/
	@WithMockUser(username = "rafa", authorities = {
		"president"
	})

	@Test
	void testShowPresident() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/presidents/{presidentUsername}", "rafa")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("lastName", Matchers.is("Liébana Fuentes"))))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("firstName", Matchers.is("Rafael")))).andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("dni", Matchers.is("11111111A"))))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("email", Matchers.is("rafliefue@alum.us.es"))))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("telephone", Matchers.is("600111222")))).andExpect(MockMvcResultMatchers.view().name("presidents/presidentDetails"));
	}

	@WithMockUser(username = "rafa", authorities = {
		"president"
	})
	@Test
	void testInitUpdatePresidentForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/presidents/{presidentUsername}/edit", "rafa")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("president"))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("lastName", Matchers.is("Liébana Fuentes"))))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("firstName", Matchers.is("Rafael")))).andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("dni", Matchers.is("11111111A"))))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("email", Matchers.is("rafliefue@alum.us.es"))))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("telephone", Matchers.is("600111222")))).andExpect(MockMvcResultMatchers.view().name("presidents/createOrUpdatePresidentForm"));
	}
}
