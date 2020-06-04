
package org.springframework.samples.petclinic.president;

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
		this.rufus.setId(1);
		this.rufus.setFirstName("Rufus");
		this.rufus.setLastName("Shinra");
		this.rufus.setDni("12345678H");
		this.rufus.setEmail("rufus@shinra.com");
		this.rufus.setTelephone("608551023");
		this.rufus.setUser(user);
		BDDMockito.given(this.presidentService.findPresidentByUsername("rufus")).willReturn(this.rufus);
	}

	@WithMockUser(username = "rufus")
	
	@Test //CASO POSITIVO - LA CREACIÓN DE UN PRESIDENTE NO TIENE GET
	void testCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/presidents/new")
			.with(SecurityMockMvcRequestPostProcessors.csrf())
			.param("firstName", "Rufus")
			.param("lastName", "Shinra")
			.param("email", "rufus@shinra.com")
			.param("dni", "12345678H")
			.param("telephone", "600766899"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test //CASO NEGATIVO - CREAR PRESIDENTE sin user conectado
	void testCreationFormSuccessError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/presidents/new")
			.with(SecurityMockMvcRequestPostProcessors.csrf())
			.param("firstName", "Rufus")
			.param("lastName", "Shinra")
			.param("email", "rufus@shinra.com")
			.param("dni", "12345678H")
			.param("telephone", "600766899"))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@WithMockUser(username = "rufus")
	
	@Test //CASO POSITIVO - GET - EDITAR PRESIDENTE
	void testInitUpdatePresidentForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/presidents/{presidentUsername}/edit", "rufus"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("president"))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("id", Matchers.is(1))))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("lastName", Matchers.is("Shinra"))))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("firstName", Matchers.is("Rufus"))))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("dni", Matchers.is("12345678H"))))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("email", Matchers.is("rufus@shinra.com"))))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("telephone", Matchers.is("608551023"))))
			.andExpect(MockMvcResultMatchers.view().name("presidents/createOrUpdatePresidentForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/presidents/createOrUpdatePresidentForm.jsp"));
	}
	
	@WithMockUser(username = "rufus2")
	
	@Test //CASO NEGATIVO - GET - EDITAR PRESIDENTE con otro user
	void testInitUpdatePresidentFormWithAnotherUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/presidents/{presidentUsername}/edit", "rufus"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}
	
	@Test //CASO NEGATIVO - GET - EDITAR PRESIDENTE sin user
	void testInitUpdatePresidentFormError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/presidents/{presidentUsername}/edit", "rufus"))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	 
	@WithMockUser(username = "rufus")
	
	 @Test //CASO POSITIVO - POST - EDITAR PRESIDENTE
		void testProcessUpdateFormSuccess() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders.post("/presidents/{presidentUsername}/edit", "rufus")
								.with(SecurityMockMvcRequestPostProcessors.csrf())
								.param("firstName", "RufusEdit")
								.param("lastName", "ShinraEdit")
								.param("dni", "88888888H")
								.param("email", "rufusEdit@shinra.com")
								.param("telephone", "888888888")
								.param("user.username", "rufus")
								.param("user.password", "shinraEdit"))
					.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
					.andExpect(MockMvcResultMatchers.view().name("redirect:/presidents/rufus"));
		}
	
	@WithMockUser(username = "rufus")	
	
	@Test //CASO NEGATIVO - POST - EDITAR PRESIDENTE quitando el parámetro del nombre
	void testProcessUpdateFormHasErrors() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/presidents/{presidentUsername}/edit", "rufus")
							.with(SecurityMockMvcRequestPostProcessors.csrf())
							.param("lastName", "ShinraEdit")
							.param("dni", "88888888H")
							.param("email", "rufusEdit@shinra.com")
							.param("telephone", "888888888")
							.param("user.username", "rufus")
							.param("user.password", "shinraEdit"))
				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("president"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("presidents/createOrUpdatePresidentForm"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/presidents/createOrUpdatePresidentForm.jsp"));
		}
	
	@WithMockUser(username = "rufus2")
	
	@Test //CASO NEGATIVO - POST - EDITAR PRESIDENTE con otro user
	void testProcessUpdatePresidentFormWithAnotherUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/presidents/{presidentUsername}/edit", "rufus")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("firstName", "RufusEdit")
				.param("lastName", "ShinraEdit")
				.param("dni", "88888888H")
				.param("email", "rufusEdit@shinra.com")
				.param("telephone", "888888888")
				.param("user.username", "rufus")
				.param("user.password", "shinraEdit"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}
	
	
	@WithMockUser(username = "rufus")
	
	@Test //CASO POSITIVO - BORRAR PRESIDENTE
	void testDeletePresident() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/presidents/delete"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/myProfile/rufus"));			
	}
	
	@Test //CASO NEGATIVO - BORRAR PRESIDENTE
	void testDeletePresidentError() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/presidents/delete"))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());			
	}
		
	@WithMockUser(username = "rufus")

	@Test // CASO POSITIVO - VER PRESIDENTE DETALLADAMENTE
	void testShowPresident() throws Exception {
	
		this.mockMvc.perform(MockMvcRequestBuilders.get("/presidents/{presidentUsername}", "rufus"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("id", Matchers.is(1))))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("lastName", Matchers.is("Shinra"))))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("firstName", Matchers.is("Rufus"))))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("dni", Matchers.is("12345678H"))))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("email", Matchers.is("rufus@shinra.com"))))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("telephone", Matchers.is("608551023"))))
			.andExpect(MockMvcResultMatchers.view().name("presidents/presidentDetails"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/presidents/presidentDetails.jsp"));
	}
	
	@WithMockUser(username = "rufus2")
	
	@Test //CASO NEGATIVO - VER PRESIDENTE DETALLADAMENTE con otro user
	void  testShowPresidentErrorWithAnotherUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/presidents/{presidentUsername}", "rufus"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}
	
	@Test //CASO NEGATIVO - VER PRESIDENTE DETALLADAMENTE
	void testShowPresidentError() throws Exception {
			 mockMvc.perform(MockMvcRequestBuilders.get("/presidents/{presidentUsername}", "rufus"))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError());
				
	}

	
}
