
package org.springframework.samples.petclinic.web.rafa;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.web.FootballClubController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = FootballClubController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class FootballClubControllerTest {

	@Autowired
	private FootballClubController	footballClubController;

	@Autowired
	private MockMvc					mockMvc;

	@MockBean
	private FootballClubService		footballClubService;

	private FootballClub			footballClub;


	@BeforeEach
	void setup() {

		User user = new User();
		user.setUsername("rufus");
		user.setPassword("shinra");
		user.setEnabled(true);

		President rufus = new President();
		rufus.setId(99);
		rufus.setFirstName("Rufus");
		rufus.setLastName("Shinra");
		rufus.setDni("12345678H");
		rufus.setEmail("rufus@shinra.com");
		rufus.setTelephone("608551023");
		rufus.setUser(user);

		Date now = new Date(System.currentTimeMillis() - 1);

		this.footballClub = new FootballClub();
		this.footballClub.setCity("Midgar");
		this.footballClub.setFans(0);
		this.footballClub.setFoundationDate(now);
		this.footballClub.setMoney(100000000);
		this.footballClub.setName("Shinra Inc");
		this.footballClub.setPresident(rufus);
		this.footballClub.setStadium("Suburbios Stadium");
		this.footballClub.setStatus(false);
		this.footballClub.setId(99);
		BDDMockito.given(this.footballClubService.findFootballClubByPresident("rufus")).willReturn(this.footballClub);

	}

	@WithMockUser(username = "rafa", authorities = {
		"president"
	})
	
	@Test //CASO POSITIVO - GET - CREAR CLUB
	void testInitCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/new"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("footballClubs/createOrUpdateFootballClubForm"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("footballClub"));
	}
	
	@Test //CASO NEGATIVO - GET - CREAR CLUB
	void testInitCreationFormSuccessError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/new"))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	
	@WithMockUser(username = "rafa", authorities = {
		"president"
	})	
	
    @Test //CASO POSITIVO - POST - CREAR CLUB
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/footballClubs/myClub/new")
							.with(SecurityMockMvcRequestPostProcessors.csrf())
							.param("name", "Shinra Inc")
							.param("city", "Midgar")
							.param("crest", "https://www.example.com")
							.param("stadium", "Suburbios Stadium")
							.param("foundationDate", "1997/01/01"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/footballClubs/myClub/rafa"));
	}
	
	@WithMockUser(username = "rafa", authorities = {
		"president"
	})	
	
    @Test //CASO NEGATIVO - POST - CREAR CLUB
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/footballClubs/myClub/new")
							.with(SecurityMockMvcRequestPostProcessors.csrf())
							.param("name", "Shinra Inc")
							.param("crest", "https://www.example.com")
							.param("stadium", "Suburbios Stadium")
							.param("foundationDate", "1997/01/01"))
				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("footballClub"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("footballClubs/createOrUpdateFootballClubForm"));
	}
	
	@WithMockUser(username = "rufus", authorities = {
		"president"
	})
	
	@Test
	void testInitUpdateForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/rufus/edit"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("footballClub"))
				.andExpect(MockMvcResultMatchers.view().name("footballClubs/createOrUpdateFootballClubForm"));
	}
}
