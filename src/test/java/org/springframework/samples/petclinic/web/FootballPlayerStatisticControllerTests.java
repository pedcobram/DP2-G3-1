package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = FootballPlayerStatisticController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class FootballPlayerStatisticControllerTests {
	
	@Autowired
	private MockMvc						mockMvc;
	
	@MockBean
	private FootballPlayerStatisticController	footballPlayerStatisticController;
	
	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})
	@Test //CASO POSITIVO
	void viewFootballPlayerStatistic() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballPlayerStatistic/detail/1"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test //CASO NEGATIVO
	void dontViewFootballPlayerStatistic() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballPlayerStatistic/detail/1"))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
}
