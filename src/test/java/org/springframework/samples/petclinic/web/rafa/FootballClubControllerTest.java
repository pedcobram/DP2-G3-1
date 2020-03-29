
package org.springframework.samples.petclinic.web.rafa;

import java.util.Date;

import org.assertj.core.util.Lists;
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
import org.springframework.samples.petclinic.model.Coach;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.ContractCommercialService;
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

	private static final int			TEST_FOOTBALL_CLUB_ID	= 1;

	@Autowired
	private FootballClubController		footballClubController;

	@Autowired
	private MockMvc						mockMvc;

	@MockBean
	private FootballClubService			footballClubService;

	@MockBean
	private ContractCommercialService	contractCommercialService;

	private FootballClub				club;


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

		User user2 = new User();
		user2.setUsername("rufus2");
		user2.setPassword("shinra2");
		user2.setEnabled(true);

		President rufus2 = new President();
		rufus2.setId(91);
		rufus2.setFirstName("Rufus2");
		rufus2.setLastName("Shinra2");
		rufus2.setDni("12345478H");
		rufus2.setEmail("rufus2@shinra.com");
		rufus2.setTelephone("608551023");
		rufus2.setUser(user2);

		Date now = new Date(System.currentTimeMillis() - 1);

		this.club = new FootballClub();
		this.club.setCity("Midgar");
		this.club.setFans(0);
		this.club.setFoundationDate(now);
		this.club.setMoney(100000000);
		this.club.setName("Shinra Inc");
		this.club.setPresident(rufus);
		this.club.setStadium("Suburbios Stadium");
		this.club.setStatus(false);
		this.club.setId(FootballClubControllerTest.TEST_FOOTBALL_CLUB_ID);

		FootballClub anotherOne = new FootballClub();

		anotherOne.setCity("Midgar2");
		anotherOne.setFans(0);
		anotherOne.setFoundationDate(now);
		anotherOne.setMoney(100000000);
		anotherOne.setName("Shinra2 Inc");
		anotherOne.setPresident(rufus2);
		anotherOne.setStadium("Suburbios2 Stadium");
		anotherOne.setStatus(true);
		this.club.setId(FootballClubControllerTest.TEST_FOOTBALL_CLUB_ID);

		Coach sephirot = new Coach();
		sephirot.setBirthDate(now);
		sephirot.setClause(6000000);
		sephirot.setClub(this.club);
		sephirot.setFirstName("Sephirot");
		sephirot.setLastName("Jenova");
		sephirot.setSalary(4000000);
		sephirot.setId(98);

		BDDMockito.given(this.footballClubService.findFootballClubByPresident("rufus")).willReturn(this.club);
		BDDMockito.given(this.footballClubService.findCoachByClubId(FootballClubControllerTest.TEST_FOOTBALL_CLUB_ID)).willReturn(sephirot);
		BDDMockito.given(this.footballClubService.findFootballClubById(FootballClubControllerTest.TEST_FOOTBALL_CLUB_ID)).willReturn(this.club);
		BDDMockito.given(this.footballClubService.findFootballClubs()).willReturn(Lists.newArrayList(anotherOne));

	}

	@WithMockUser(username = "rufus2")

	@Test //CASO POSITIVO - GET - CREAR CLUB
	void testInitCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/new"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("footballClub"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("isNew"))
			.andExpect(MockMvcResultMatchers.view().name("footballClubs/createOrUpdateFootballClubForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/createOrUpdateFootballClubForm.jsp"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO NEGATIVO - GET - CREAR CLUB teniendo ya club
	void testInitCreationFormWithClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/new"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - GET - CREAR CLUB
	void testInitCreationFormSuccessError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/new"))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus2")

	@Test //CASO POSITIVO - POST - CREAR CLUB
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/footballClubs/myClub/new")
			.with(SecurityMockMvcRequestPostProcessors.csrf())
			.param("name", "Shinra Inc")
			.param("city", "Midgar")
			.param("crest", "https://www.example.com")
			.param("stadium", "Suburbios Stadium")
			.param("foundationDate", "1997/01/01"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/footballClubs/myClub/rufus2"));
	}

	@WithMockUser(username = "rufus2")

	@Test //CASO NEGATIVO - POST - CREAR CLUB 
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/footballClubs/myClub/new")
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
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/{principalUsername}/edit", "rufus"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("footballClub"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("isEditing"))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("name", Matchers.is("Shinra Inc"))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("president", Matchers.is(this.club.getPresident()))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("foundationDate", Matchers.is(this.club.getFoundationDate()))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("city", Matchers.is("Midgar"))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("stadium", Matchers.is("Suburbios Stadium"))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("money", Matchers.is(100000000)))).andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("fans", Matchers.is(0))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("status", Matchers.is(false)))).andExpect(MockMvcResultMatchers.view().name("footballClubs/createOrUpdateFootballClubForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/createOrUpdateFootballClubForm.jsp"));
	}
	
	@WithMockUser(username = "rufus2")

	@Test //CASO POSITIVO - GET - EDITAR CLUB con otro user
	void testInitUpdateFormWhitAnotherUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/{principalUsername}/edit", "rufus"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}


	@Test //CASO NEGATIVO - GET - EDITAR CLUB
	void testInitUpdateFormError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/{principalUsername}/edit", "rufus")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - POST - EDITAR CLUB
	void testProcessUpdateFormSuccess() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/footballClubs/myClub/{principalUsername}/edit", "rufus").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "ShinraEdit Inc").param("city", "MidgarEdit")
				.param("crest", "https://www.example.com").param("stadium", "Suburbios Stadium").param("foundationDate", "1997/01/01"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/footballClubs/myClub/rufus"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO NEGATIVO - POST - EDITAR CLUB
	void testProcessUpdateFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/footballClubs/myClub/{principalUsername}/edit", "rufus").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "ShinraEdit Inc").param("crest", "https://www.example.com")
				.param("stadium", "Suburbios Stadium").param("foundationDate", "1997/01/01"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("footballClub")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("footballClubs/createOrUpdateFootballClubForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/createOrUpdateFootballClubForm.jsp"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - LISTA DE EQUIPOS
	void testShowFootballClubListHtml() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("footballClubs"))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClubs", Matchers.hasSize(1))).andExpect(MockMvcResultMatchers.view().name("footballClubs/footballClubList"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/footballClubList.jsp"));
	}

	@Test //CASO NEGATIVO - LISTA DE EQUIPOS
	void testShowFootballClubListHtmlError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - VER EQUIPO DETALLADAMENTE POR USERNAME
	void testShowFootballClubByUsername() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/{principalUsername}", "rufus")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("footballClub"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("coach")).andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("name", Matchers.is("Shinra Inc"))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("president", Matchers.is(this.club.getPresident()))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("foundationDate", Matchers.is(this.club.getFoundationDate()))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("city", Matchers.is("Midgar"))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("stadium", Matchers.is("Suburbios Stadium"))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("money", Matchers.is(100000000)))).andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("fans", Matchers.is(0))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("status", Matchers.is(false)))).andExpect(MockMvcResultMatchers.model().attribute("coach", Matchers.hasProperty("firstName", Matchers.is("Sephirot"))))
			.andExpect(MockMvcResultMatchers.model().attribute("coach", Matchers.hasProperty("lastName", Matchers.is("Jenova")))).andExpect(MockMvcResultMatchers.view().name("footballClubs/myClubDetails"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/myClubDetails.jsp"));
	}

	@WithMockUser(username = "presidentWithoutClub")

	@Test //CASO POSITIVO - VER EQUIPO DETALLADAMENTE POR USERNAME sin club
	void testShowFootballClubByUsernameWithoutClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/{principalUsername}", "presidentWithoutClub")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("footballClub"))
			.andExpect(MockMvcResultMatchers.view().name("footballClubs/myClubEmpty")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/myClubEmpty.jsp"));
	}

	@Test //CASO NEGATIVO - VER EQUIPO DETALLADAMENTE POR USERNAME
	void testShowFootballClubByUsernameError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list/{principalUsername}", "rufus")).andExpect(MockMvcResultMatchers.status().is4xxClientError());

	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - VER EQUIPO DETALLADAMENTE POR ID
	void testShowFootballClub() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list/{footballClubId}", FootballClubControllerTest.TEST_FOOTBALL_CLUB_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("footballClub")).andExpect(MockMvcResultMatchers.model().attributeExists("coach"))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("name", Matchers.is("Shinra Inc"))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("president", Matchers.is(this.club.getPresident()))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("foundationDate", Matchers.is(this.club.getFoundationDate()))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("city", Matchers.is("Midgar"))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("stadium", Matchers.is("Suburbios Stadium"))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("money", Matchers.is(100000000)))).andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("fans", Matchers.is(0))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("status", Matchers.is(false)))).andExpect(MockMvcResultMatchers.model().attribute("coach", Matchers.hasProperty("firstName", Matchers.is("Sephirot"))))
			.andExpect(MockMvcResultMatchers.model().attribute("coach", Matchers.hasProperty("lastName", Matchers.is("Jenova")))).andExpect(MockMvcResultMatchers.view().name("footballClubs/footballClubDetails"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/footballClubDetails.jsp"));
	}

	@Test //CASO NEGATIVO - VER EQUIPO DETALLADAMENTE POR ID
	void testShowFootballClubError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list/{footballClubId}", FootballClubControllerTest.TEST_FOOTBALL_CLUB_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - BORRAR EQUIPO
	void testDeleteFootballClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/{principalUsername}/delete", "rufus")).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/footballClubs/myClub/rufus"));
	}

	@WithMockUser(username = "rufus2")

	@Test //CASO POSITIVO - BORRAR EQUIPO con otro user
	void testDeleteFootballClubWrongPresident() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/{principalUsername}/delete", "rufus")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - BORRAR EQUIPO sin user
	void testDeleteFootballClubError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/{principalUsername}/delete", "rufus")).andExpect(MockMvcResultMatchers.status().is4xxClientError());

	}

}
