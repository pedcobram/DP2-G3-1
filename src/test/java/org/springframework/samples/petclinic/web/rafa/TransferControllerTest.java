
package org.springframework.samples.petclinic.web.rafa;

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
import org.springframework.samples.petclinic.model.Coach;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Enum.FootballPlayerPosition;
import org.springframework.samples.petclinic.service.CoachService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.FootballPlayerService;
import org.springframework.samples.petclinic.web.TransferController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = TransferController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class TransferControllerTest {

	private static final int		TEST_FOOTBALL_PLAYER_ID	= 99;
	private static final int		TEST_FOOTBALL_CLUB_ID	= 97;

	@Autowired
	private TransferController		transferController;

	@Autowired
	private MockMvc					mockMvc;

	@MockBean
	private FootballPlayerService	footballPlayerService;

	@MockBean
	private FootballClubService		footballClubService;

	@MockBean
	private CoachService			coachService;

	private FootballClub			newTeam;


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

		FootballClub toho = new FootballClub();

		this.newTeam = new FootballClub();
		Date now = new Date(System.currentTimeMillis() - 1);
		this.newTeam = new FootballClub();
		this.newTeam.setCity("Midgar");
		this.newTeam.setFans(0);
		this.newTeam.setFoundationDate(now);
		this.newTeam.setMoney(100000000);
		this.newTeam.setName("Shinra Inc");
		this.newTeam.setPresident(rufus);
		this.newTeam.setStadium("Suburbios Stadium");
		this.newTeam.setStatus(true);
		this.newTeam.setId(TransferControllerTest.TEST_FOOTBALL_CLUB_ID);

		Date date = new GregorianCalendar(2000, Calendar.FEBRUARY, 11).getTime();

		//Jugador con mi Club
		FootballPlayer oliver = new FootballPlayer();
		oliver.setBirthDate(date);
		oliver.setClub(this.newTeam);
		oliver.setFirstName("Oliver");
		oliver.setLastName("Atom");
		oliver.setPosition(FootballPlayerPosition.STRIKER);
		oliver.setValue(6000000);
		oliver.setId(TransferControllerTest.TEST_FOOTBALL_PLAYER_ID);

		//Jugador con Club
		FootballPlayer julian = new FootballPlayer();
		julian.setBirthDate(date);
		julian.setClub(toho);
		julian.setFirstName("Julian");
		julian.setLastName("Ross");
		julian.setPosition(FootballPlayerPosition.STRIKER);
		julian.setValue(6000000);
		julian.setId(TransferControllerTest.TEST_FOOTBALL_PLAYER_ID + 2);

		//Jugador FA
		FootballPlayer mark = new FootballPlayer();
		mark.setBirthDate(now);
		mark.setClub(null);
		mark.setFirstName("Mark");
		mark.setLastName("Lenders");
		mark.setPosition(FootballPlayerPosition.STRIKER);
		mark.setValue(6000000);
		mark.setId(TransferControllerTest.TEST_FOOTBALL_PLAYER_ID + 1);

		//Entrenador con mi Club
		Coach sephirot = new Coach();
		sephirot.setBirthDate(now);
		sephirot.setClause(6000000);
		sephirot.setClub(this.newTeam);
		sephirot.setFirstName("Sephirot");
		sephirot.setLastName("Jenova");
		sephirot.setSalary(4000000);
		sephirot.setId(98);

		//Entrenador con Club
		Coach angeal = new Coach();
		angeal.setBirthDate(now);
		angeal.setClause(6000000);
		angeal.setClub(toho);
		angeal.setFirstName("Sephirot");
		angeal.setLastName("Jenova");
		angeal.setSalary(4000000);
		angeal.setId(92);

		//ENtrenador FA
		Coach genesis = new Coach();
		genesis.setBirthDate(now);
		genesis.setClause(6000000);
		genesis.setClub(null);
		genesis.setFirstName("Genesos");
		genesis.setLastName("Loveless");
		genesis.setSalary(4000000);
		genesis.setId(96);

		BDDMockito.given(this.footballPlayerService.findAllFootballPlayers()).willReturn(Lists.newArrayList(oliver, mark, julian));
		BDDMockito.given(this.footballPlayerService.findAllFootballPlayersFA()).willReturn(Lists.newArrayList(mark));
		BDDMockito.given(this.footballPlayerService.findAllClubFootballPlayers(TransferControllerTest.TEST_FOOTBALL_CLUB_ID)).willReturn(Lists.newArrayList(oliver));
		BDDMockito.given(this.footballClubService.findFootballClubByPresident("rufus")).willReturn(this.newTeam);
		BDDMockito.given(this.coachService.findAllCoachs()).willReturn(Lists.newArrayList(sephirot, genesis, angeal));
		BDDMockito.given(this.coachService.findAllCoachsFA()).willReturn(Lists.newArrayList(genesis));
		BDDMockito.given(this.coachService.findCoachByClubId(TransferControllerTest.TEST_FOOTBALL_CLUB_ID)).willReturn(sephirot);

	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - PANEL DE FICHAJES
	void testShowTransferPanel() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/panel"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("status"))
			.andExpect(MockMvcResultMatchers.view().name("transfers/transferPanel"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/transfers/transferPanel.jsp"));
	}

	@WithMockUser(username = "rufus2")

	@Test //CASO NEGATIVO - PANEL DE FICHAJES sin club
	void testShowTransferPanelWithoutClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/panel"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("status"))
			.andExpect(MockMvcResultMatchers.view().name("footballClubs/myClubEmpty"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/myClubEmpty.jsp"));
	}

	@Test //CASO NEGATIVO - PANEL DE FICHAJES sin user
	void testShowTransferPanelError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/panel"))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - LISTA DE ENTRENADORES
	void testShowCoachList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("coachs"))
			.andExpect(MockMvcResultMatchers.model().attribute("coachs", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers.view().name("transfers/coachList"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/transfers/coachList.jsp"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO NEGATIVO - LISTA DE ENTRENADORES con presidente con club sin publicar
	void testShowCoachListWithClubNotPublished() throws Exception {

		this.newTeam.setStatus(false);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("coachs"))
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@WithMockUser(username = "rufus2")

	@Test //CASO NEGATIVO - LISTA DE ENTRENADORES con presidente sin club
	void testShowCoachListWithoutClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("coachs"))
			.andExpect(MockMvcResultMatchers.view().name("footballClubs/myClubEmpty"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/myClubEmpty.jsp"));
	}

	@Test //CASO NEGATIVO - LISTA DE ENTRENADORES sin user
	void testShowCoachErrorListWithoutUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coachs")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - LISTA DE ENTRENADORES FA
	void testShowFACoachList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/free-agents"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("coachs"))
			.andExpect(MockMvcResultMatchers.model().attribute("coachs", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers.view().name("coachs/coachList"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/coachs/coachList.jsp"));
	}

	@WithMockUser(username = "rufus2")

	@Test //CASO NEGATIVO - LISTA DE ENTRENADORES FA con presidente sin club
	void testShowCoachFAListWithoutClub() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/free-agents"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("coachs"))
			.andExpect(MockMvcResultMatchers.view().name("footballClubs/myClubEmpty"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/myClubEmpty.jsp"));
	}

	@Test //CASO NEGATIVO - LISTA DE ENTRENADORES FA sin user
	void testShowCoachFAErrorListWithoutUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coachs/free-agents"))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - LISTA DE JUGADORES
	void testShowPlayerList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("footballPlayers"))
			.andExpect(MockMvcResultMatchers.model().attribute("footballPlayers", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers.view().name("transfers/footballPlayerList"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/transfers/footballPlayerList.jsp"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO NEGATIVO - LISTA DE JUGADORES con presidente con club sin publicar
	void testShowPlayerListWithClubNotPublished() throws Exception {

		this.newTeam.setStatus(false);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("footballPlayers"))
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@WithMockUser(username = "rufus2")

	@Test //CASO NEGATIVO - LISTA DE JUGADORES con presidente sin club
	void testShowPlayerListWithoutClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("footballPlayers"))
			.andExpect(MockMvcResultMatchers.view().name("footballClubs/myClubEmpty"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/myClubEmpty.jsp"));
	}

	@Test //CASO NEGATIVO - LISTA DE JUGADORES sin user
	void testShowPlayerErrorListWithoutUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players"))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - LISTA DE JUGADORES FA
	void testShowPlayerFAList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/free-agents"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("footballPlayers"))
			.andExpect(MockMvcResultMatchers.model().attribute("footballPlayers", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers.view().name("transfers/footballPlayerList"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/transfers/footballPlayerList.jsp"));
	}

	@WithMockUser(username = "rufus2")

	@Test //CASO NEGATIVO - LISTA DE JUGADORES FA con presidente sin club
	void testShowPlayerFAListWithoutClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/free-agents"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("footballPlayers"))
			.andExpect(MockMvcResultMatchers.view().name("footballClubs/myClubEmpty"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/myClubEmpty.jsp"));
	}

	@Test //CASO NEGATIVO - LISTA DE JUGADORES FA sin user
	void testShowPlayerFAErrorListWithoutUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/free-agents"))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

}
