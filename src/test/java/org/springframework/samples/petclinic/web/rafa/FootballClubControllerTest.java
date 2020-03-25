
package org.springframework.samples.petclinic.web.rafa;

import java.util.Date;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
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
	
	private static final int TEST_FOOTBALL_CLUB_ID = 1;

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
		this.footballClub.setId(TEST_FOOTBALL_CLUB_ID);
		BDDMockito.given(this.footballClubService.findFootballClubByPresident("rufus")).willReturn(this.footballClub);
		BDDMockito.given(this.footballClubService.findFootballClubById(TEST_FOOTBALL_CLUB_ID)).willReturn(this.footballClub);

	}

	@WithMockUser(username = "rufus")
	
	@Test //CASO POSITIVO - GET - CREAR CLUB
	void testInitCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/new"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("footballClubs/createOrUpdateFootballClubForm"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("footballClub"))
		.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/createOrUpdateFootballClubForm.jsp"));
	}
	
	@Test //CASO NEGATIVO - GET - CREAR CLUB
	void testInitCreationFormSuccessError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/new"))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	
	@WithMockUser(username = "rufus")	
	
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
				.andExpect(MockMvcResultMatchers.view().name("redirect:/footballClubs/myClub/rufus"));
	}
	
	@WithMockUser(username = "rufus")	
	
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
				.andExpect(MockMvcResultMatchers.view().name("footballClubs/createOrUpdateFootballClubForm"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/createOrUpdateFootballClubForm.jsp"));
	}
	
	@WithMockUser(username = "rufus")
	
	@Test //CASO POSITIVO - GET - EDITAR CLUB
	void testInitUpdateForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/{principalUsername}/edit", "rufus"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("footballClub"))
				.andExpect(MockMvcResultMatchers.model().attribute("footballClub", hasProperty("name", is("Shinra Inc"))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballClub", hasProperty("president", is(this.footballClub.getPresident()))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballClub", hasProperty("foundationDate", is(this.footballClub.getFoundationDate()))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballClub", hasProperty("city", is("Midgar"))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballClub", hasProperty("stadium", is("Suburbios Stadium"))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballClub", hasProperty("money", is(100000000))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballClub", hasProperty("fans", is(0))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballClub", hasProperty("status", is(false))))
				.andExpect(MockMvcResultMatchers.view().name("footballClubs/createOrUpdateFootballClubForm"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/createOrUpdateFootballClubForm.jsp"));
	}
	
	@Test //CASO NEGATIVO - GET - EDITAR CLUB
	void testInitUpdateFormError() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/{principalUsername}/edit", "rufus"))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@WithMockUser(username = "rufus")	
	
    @Test //CASO POSITIVO - POST - EDITAR CLUB
	void testProcessUpdateFormSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/footballClubs/myClub/{principalUsername}/edit", "rufus")
							.with(SecurityMockMvcRequestPostProcessors.csrf())
							.param("name", "Shinra Inc")
							.param("city", "Midgar")
							.param("crest", "https://www.example.com")
							.param("stadium", "Suburbios Stadium")
							.param("foundationDate", "1997/01/01"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/footballClubs/myClub/rufus"));
	}
	
	@WithMockUser(username = "rufus")	
	
	@Test //CASO NEGATIVO - POST - EDITAR CLUB
	void testProcessUpdateFormHasErrors() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/footballClubs/myClub/{principalUsername}/edit", "rufus")
							.with(SecurityMockMvcRequestPostProcessors.csrf())
							.param("name", "Shinra Inc")
							.param("crest", "https://www.example.com")
							.param("stadium", "Suburbios Stadium")
							.param("foundationDate", "1997/01/01"))
				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("footballClub"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("footballClubs/createOrUpdateFootballClubForm"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/createOrUpdateFootballClubForm.jsp"));
	}
	
	@WithMockUser(username = "rufus")
	
	@Test //CASO POSITIVO - LISTA DE EQUIPOS
	void testShowFootballClubListHtml() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("footballClubs"))
		.andExpect(MockMvcResultMatchers.view().name("footballClubs/footballClubList"))
		.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/footballClubList.jsp"));
	}
	
	@Test //CASO NEGATIVO - LISTA DE EQUIPOS
	void testShowFootballClubListHtmlError() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list"))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}	
		
	@WithMockUser(username = "rufus")
	
	@Test //CASO POSITIVO - BORRAR EQUIPO
	void testDeleteFootballClub() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/{principalUsername}/delete", "rufus"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/footballClubs/myClub/rufus"))	;			
	}
	
	@Test //CASO NEGATIVO - BORRAR EQUIPO
	void testDeleteFootballClubError() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/{principalUsername}/delete", "rufus"))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
				
	}
	
	@WithMockUser(username = "rufus")
	
	@Test //CASO POSITIVO - VER EQUIPO DETALLADAMENTE POR USERNAME
	void testShowFootballClubByUsername() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list/{principalUsername}", "rufus"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("footballClub", hasProperty("name", is("Shinra Inc"))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballClub", hasProperty("president", is(this.footballClub.getPresident()))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballClub", hasProperty("foundationDate", is(this.footballClub.getFoundationDate()))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballClub", hasProperty("city", is("Midgar"))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballClub", hasProperty("stadium", is("Suburbios Stadium"))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballClub", hasProperty("money", is(100000000))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballClub", hasProperty("fans", is(0))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballClub", hasProperty("status", is(false))))
				.andExpect(MockMvcResultMatchers.view().name("footballClubs/footballClubDetails"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/myClubDetails.jsp"));
	}
	
	@Test //CASO NEGATIVO - VER EQUIPO DETALLADAMENTE POR USERNAME
	void testShowFootballClubByUsernameError() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list/{principalUsername}", "rufus"))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
				
	}
	
	@WithMockUser(username = "rufus")
	
	@Test //CASO POSITIVO - VER EQUIPO DETALLADAMENTE POR ID
	void testShowFootballClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list/{footballClubId}", TEST_FOOTBALL_CLUB_ID))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("name", Matchers.is("Shinra Inc"))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("president", Matchers.is(this.footballClub.getPresident()))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("foundationDate", Matchers.is(this.footballClub.getFoundationDate()))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("city", Matchers.is("Midgar"))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("stadium", Matchers.is("Suburbios Stadium"))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("money", Matchers.is(100000000))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("fans", Matchers.is(0))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("status", Matchers.is(false))))
				.andExpect(MockMvcResultMatchers.view().name("footballClubs/footballClubDetails"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/footballClubDetails.jsp"));
	}
	
	@Test //CASO NEGATIVO - VER EQUIPO DETALLADAMENTE POR ID
	void testShowFootballClubError() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list/{footballClubId}", TEST_FOOTBALL_CLUB_ID))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
}