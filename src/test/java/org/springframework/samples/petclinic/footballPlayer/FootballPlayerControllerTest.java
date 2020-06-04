
package org.springframework.samples.petclinic.footballPlayer;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Enum.FootballPlayerPosition;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.FootballPlayerService;
import org.springframework.samples.petclinic.web.FootballPlayerController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = FootballPlayerController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class FootballPlayerControllerTest {
	
	private static final int TEST_FOOTBALL_PLAYER_ID = 99;
	private static final int TEST_FOOTBALL_CLUB_ID = 97;

	@Autowired
	private FootballPlayerController	footballPlayerController;

	@Autowired
	private MockMvc						mockMvc;

	@MockBean
	private FootballPlayerService		footballPlayerService;

	@MockBean
	private FootballClubService			footballClubService;
	
	private FootballClub newTeam;
	

	@BeforeEach
	void setup() {

		User user = new User();
		user.setUsername("rufus");
		user.setPassword("shinra");
		user.setEnabled(true);

		President rufus = new President();
		rufus.setId(98);
		rufus.setFirstName("Rufus");
		rufus.setLastName("Shinra");
		rufus.setDni("12345678H");
		rufus.setEmail("rufus@shinra.com");
		rufus.setTelephone("608551023");
		rufus.setUser(user);

		newTeam = new FootballClub();
		Date now = new Date(System.currentTimeMillis() - 1);
		newTeam = new FootballClub();
		newTeam.setCity("Midgar");
		newTeam.setFans(0);
		newTeam.setFoundationDate(now);
		newTeam.setMoney(100000000);
		newTeam.setName("Shinra Inc");
		newTeam.setPresident(rufus);
		newTeam.setStadium("Suburbios Stadium");
		newTeam.setStatus(false);
		newTeam.setId(TEST_FOOTBALL_CLUB_ID);
		
		Date date = new GregorianCalendar(2000, Calendar.FEBRUARY, 11).getTime();
			
		FootballPlayer oliver = new FootballPlayer();
		oliver.setBirthDate(date);
		oliver.setClub(newTeam);
		oliver.setFirstName("Oliver");
		oliver.setLastName("Atom");
		oliver.setPosition(FootballPlayerPosition.STRIKER);
		oliver.setValue(6000000);
		oliver.setId(TEST_FOOTBALL_PLAYER_ID);
		
		FootballPlayer mark = new FootballPlayer();
		mark.setBirthDate(now);
		mark.setClub(null);
		mark.setFirstName("Mark");
		mark.setLastName("Lenders");
		mark.setPosition(FootballPlayerPosition.STRIKER);
		mark.setValue(6000000);
		mark.setId(TEST_FOOTBALL_PLAYER_ID + 1);
		
		BDDMockito.given(this.footballPlayerService.findFootballPlayerById(TEST_FOOTBALL_PLAYER_ID)).willReturn(oliver);
		BDDMockito.given(this.footballPlayerService.findAllFootballPlayers()).willReturn(Lists.newArrayList(oliver, mark));
		BDDMockito.given(this.footballPlayerService.findAllFootballPlayersFA()).willReturn(Lists.newArrayList(mark));
		BDDMockito.given(this.footballPlayerService.findAllClubFootballPlayers(TEST_FOOTBALL_CLUB_ID)).willReturn(Lists.newArrayList(oliver));
		BDDMockito.given(this.footballClubService.findFootballClubByPresident("rufus")).willReturn(newTeam);
		
	}

	@WithMockUser(username = "rufus")
	
	@Test //CASO POSITIVO - LISTA DE JUGADORES
	void testShowFootballPlayerList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/footballPlayers"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("footballPlayers"))
		.andExpect(MockMvcResultMatchers.model().attribute("footballPlayers", Matchers.hasSize(2)))
		.andExpect(MockMvcResultMatchers.view().name("footballPlayers/footballPlayerList"))
		.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballPlayers/footballPlayerList.jsp"));
	}
	
	@Test //CASO NEGATIVO - LISTA DE JUGADORES sin user
	void testShowFootballPlayerListError() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/footballPlayers"))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}	
	
	@WithMockUser(username = "rufus")
	
	@Test //CASO POSITIVO - LISTA DE JUGADORES FA
	void testShowFootballPlayerFAList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/footballPlayers/freeAgent"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("footballPlayers"))
		.andExpect(MockMvcResultMatchers.model().attribute("footballPlayers", Matchers.hasSize(1)))
		.andExpect(MockMvcResultMatchers.view().name("footballPlayers/footballPlayerList"))
		.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballPlayers/footballPlayerList.jsp"));
	}
	
	@Test //CASO NEGATIVO - LISTA DE JUGADORES FA sin user
	void testShowFootballPlayerFAListError() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/footballPlayers/freeAgent"))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}	
	
	@WithMockUser(username = "rufus")
	
	@Test //CASO POSITIVO - LISTA DE JUGADORES DE UN EQUIPO
	void testShowFootballPlayerClubList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list/{footballClubId}/footballPlayers", TEST_FOOTBALL_CLUB_ID))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("footballPlayers"))
		.andExpect(MockMvcResultMatchers.model().attribute("footballPlayers", Matchers.hasSize(1)))
		.andExpect(MockMvcResultMatchers.view().name("footballPlayers/footballPlayerList"))
		.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballPlayers/footballPlayerList.jsp"));
	}
	
	@Test //CASO NEGATIVO - LISTA DE JUGADORES DE UN EQUIPO sin user
	void testShowFootballPlayerClubListError() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list/{footballClubId}/footballPlayers", TEST_FOOTBALL_CLUB_ID))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}	
	
	@WithMockUser(username = "rufus")
	
	@Test //CASO POSITIVO - LISTA DE JUGADORES DE MI EQUIPO
	void testShowFootballPlayerMyClubList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/footballPlayers"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("footballPlayers"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("thisClubPresidentUsername"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("thisClubStatus"))
		.andExpect(MockMvcResultMatchers.model().attribute("footballPlayers", Matchers.hasSize(1)))
		.andExpect(MockMvcResultMatchers.view().name("footballPlayers/footballPlayerList"))
		.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballPlayers/footballPlayerList.jsp"));
	}
	
	@WithMockUser(username = "presidentWithoutClub")
	
	@Test //CASO POSITIVO - LISTA DE JUGADORES DE MI EQUIPO con el presidente sin equipo
	void testShowFootballPlayerMyClubListWithoutClub() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/footballPlayers"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("footballPlayers"))
		.andExpect(MockMvcResultMatchers.view().name("footballClubs/myClubEmpty"))
		.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/myClubEmpty.jsp"));
	}
	
	@Test //CASO NEGATIVO - LISTA DE JUGADORES DE UN EQUIPO sin user
	void testShowFootballPlayerMyClubListError() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/footballPlayers"))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}	
	
	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - VER JUGADOR DETALLADAMENTE POR ID
	void testShowFootballPlayer() throws Exception {
		
		Date date = new GregorianCalendar(2000, Calendar.FEBRUARY, 11).getTime();
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballPlayers/{footballPlayerId}", TEST_FOOTBALL_PLAYER_ID))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("footballPlayer"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("footballPlayerAge"))
				.andExpect(MockMvcResultMatchers.model().attribute("footballPlayer", Matchers.hasProperty("firstName", Matchers.is("Oliver"))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballPlayer", Matchers.hasProperty("lastName", Matchers.is("Atom"))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballPlayer", Matchers.hasProperty("position", Matchers.is(FootballPlayerPosition.STRIKER))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballPlayer", Matchers.hasProperty("value", Matchers.is(6000000))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballPlayer", Matchers.hasProperty("birthDate", Matchers.is(date))))
				.andExpect(MockMvcResultMatchers.model().attribute("footballPlayer", Matchers.hasProperty("club", Matchers.is(this.newTeam))))
				.andExpect(MockMvcResultMatchers.view().name("footballPlayers/footballPlayerDetails"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballPlayers/footballPlayerDetails.jsp"));
	}
	
	@Test //CASO NEGATIVO - VER JUGADOR DETALLADAMENTE POR ID sin user
	void testShowFootballPlayerError() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/footballPlayers/{footballPlayerId}", TEST_FOOTBALL_PLAYER_ID))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}	
	
	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - GET - REGISTRAR JUGADOR
	void testInitCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballPlayer/new"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("footballPlayer"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("positions"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("salary"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("clause"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("startDate"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("endDate"))
				.andExpect(MockMvcResultMatchers.view().name("footballPlayers/createOrUpdateFootballPlayerForm"))	
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballPlayers/createOrUpdateFootballPlayerForm.jsp"));
	}
	
	@WithMockUser(username = "rufus")

	@Test //CASO NEGATIVO - GET - REGISTRAR JUGADOR con club público
	void testInitCreationFormWithClubPublished() throws Exception {
		
		newTeam.setStatus(true);
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballPlayer/new"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}
	
	
	@Test //CASO NEGATIVO - GET - REGISTRAR JUGADOR sin user
	void testInitCreationFormSuccessError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballPlayer/new"))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - POST - REGISTRAR JUGADOR
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/footballPlayer/new")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("firstName", "Tom")
				.param("lastName", "Baker")
				.param("position", "MIDFIELDER")
				.param("birthDate", "1997/01/01"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/footballClubs/myClub/footballPlayers"));
	}
	
	@WithMockUser(username = "rufus")

	@Test //CASO NEGATIVO - POST - REGISTRAR JUGADOR sin todos los parámetros necesarios
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/footballPlayer/new")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("lastName", "Baker")
				.param("position", "MIDFIELDER")
				.param("birthDate", "1997/01/01"))
				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("footballPlayer"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("footballPlayers/createOrUpdateFootballPlayerForm"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballPlayers/createOrUpdateFootballPlayerForm.jsp"));	
	}
}
