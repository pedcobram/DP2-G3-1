
package org.springframework.samples.petclinic.web.rafa;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.service.PresidentService;
import org.springframework.samples.petclinic.web.PresidentController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = PresidentController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class PresidentControllerTest {

	@Autowired
	private PresidentController	presidentController;

	@Autowired
	private MockMvc				mockMvc;

	@MockBean
	private PresidentService	presidentService;


	@WithMockUser(username = "rafa", authorities = {
		"authenticated"
	})
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/presidents/new")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testInitUpdatePresidentForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/presidents/{presidentUsername}/edit", "rafa")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("president"))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("lastName", Matchers.is("Liébana Fuentes"))))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("firstName", Matchers.is("Rafael")))).andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("dni", Matchers.is("11111111A"))))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("email", Matchers.is("rafliefue@alum.us.es"))))
			.andExpect(MockMvcResultMatchers.model().attribute("president", Matchers.hasProperty("telephone", Matchers.is("600111222")))).andExpect(MockMvcResultMatchers.view().name("presidents/createOrUpdatePresidentForm"));
	}
}
