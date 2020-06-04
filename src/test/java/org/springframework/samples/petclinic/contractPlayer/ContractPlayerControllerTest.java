
package org.springframework.samples.petclinic.contractPlayer;

import java.text.SimpleDateFormat;
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
import org.springframework.samples.petclinic.model.ContractPlayer;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Enum.FootballPlayerPosition;
import org.springframework.samples.petclinic.service.ContractPlayerService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.FootballPlayerService;
import org.springframework.samples.petclinic.service.PlayerTransferRequestService;
import org.springframework.samples.petclinic.web.ContractPlayerController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = ContractPlayerController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class ContractPlayerControllerTest {

	private static final int				TEST_FOOTBALL_CLUB_ID	= 1;
	private static final int				TEST_FOOTBALL_PLAYER_ID	= 1;
	private static final int				TEST_CONTRACT_PLAYER_ID	= 1;

	@Autowired
	private ContractPlayerController		contractPlayerController;

	@Autowired
	private MockMvc							mockMvc;

	@MockBean
	private FootballPlayerService			footballPlayerService;

	@MockBean
	private FootballClubService				footballClubService;

	@MockBean
	private PlayerTransferRequestService	playerTransferRequestService;

	@MockBean
	private ContractPlayerService			contractPlayerService;

	private FootballClub					shinraInc;

	private FootballPlayer					zackFair;

	private FootballPlayer					genesisRhapsodos;


	@BeforeEach
	void setup() {

		Date birthDatePlayer = new GregorianCalendar(2000, Calendar.FEBRUARY, 11).getTime();
		Date now = new Date(System.currentTimeMillis() - 1);
		Date startContractDate = new GregorianCalendar(2019, Calendar.JUNE, 30).getTime();
		Date endContractDate = new GregorianCalendar(2024, Calendar.JUNE, 30).getTime();

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

		this.shinraInc = new FootballClub();
		this.shinraInc.setName("Shinra Inc");
		this.shinraInc.setCity("Midgar");
		this.shinraInc.setStadium("Suburbios Stadium");
		this.shinraInc.setMoney(100000000);
		this.shinraInc.setFans(0);
		this.shinraInc.setFoundationDate(now);
		this.shinraInc.setStatus(true);
		this.shinraInc.setPresident(rufus);
		this.shinraInc.setId(ContractPlayerControllerTest.TEST_FOOTBALL_CLUB_ID);

		//Jugadores
		this.zackFair = new FootballPlayer();
		this.zackFair.setBirthDate(birthDatePlayer);
		this.zackFair.setClub(this.shinraInc);
		this.zackFair.setFirstName("Zack");
		this.zackFair.setLastName("Fair");
		this.zackFair.setPosition(FootballPlayerPosition.STRIKER);
		this.zackFair.setValue(6000000);
		this.zackFair.setId(ContractPlayerControllerTest.TEST_FOOTBALL_PLAYER_ID);

		FootballPlayer cloudStrife = new FootballPlayer();
		cloudStrife.setBirthDate(birthDatePlayer);
		cloudStrife.setClub(this.shinraInc);
		cloudStrife.setFirstName("Cloud");
		cloudStrife.setLastName("Strife");
		cloudStrife.setPosition(FootballPlayerPosition.STRIKER);
		cloudStrife.setValue(3000000);
		cloudStrife.setId(ContractPlayerControllerTest.TEST_FOOTBALL_PLAYER_ID + 1);

		FootballPlayer angealHewley = new FootballPlayer();
		angealHewley.setBirthDate(now);
		angealHewley.setClub(this.shinraInc);
		angealHewley.setFirstName("Angeal");
		angealHewley.setLastName("Hewley");
		angealHewley.setPosition(FootballPlayerPosition.DEFENDER);
		angealHewley.setValue(9000000);
		angealHewley.setId(ContractPlayerControllerTest.TEST_FOOTBALL_PLAYER_ID + 2);

		//Jugador FA
		this.genesisRhapsodos = new FootballPlayer();
		this.genesisRhapsodos.setBirthDate(birthDatePlayer);
		this.genesisRhapsodos.setClub(null);
		this.genesisRhapsodos.setFirstName("Genesis");
		this.genesisRhapsodos.setLastName("Rhapsodos");
		this.genesisRhapsodos.setPosition(FootballPlayerPosition.MIDFIELDER);
		this.genesisRhapsodos.setValue(6000000);
		this.genesisRhapsodos.setId(ContractPlayerControllerTest.TEST_FOOTBALL_PLAYER_ID + 3);

		//Contratos
		ContractPlayer zackContract = new ContractPlayer();
		zackContract.setSalary(2000000);
		zackContract.setClause(3000000);
		zackContract.setClub(this.shinraInc);
		zackContract.setEndDate(endContractDate);
		zackContract.setStartDate(startContractDate);
		zackContract.setPlayer(this.zackFair);
		zackContract.setId(ContractPlayerControllerTest.TEST_CONTRACT_PLAYER_ID);

		ContractPlayer cloudContract = new ContractPlayer();
		cloudContract.setSalary(1000000);
		cloudContract.setClause(1500000);
		cloudContract.setClub(this.shinraInc);
		cloudContract.setEndDate(endContractDate);
		cloudContract.setStartDate(startContractDate);
		cloudContract.setPlayer(cloudStrife);
		cloudContract.setId(ContractPlayerControllerTest.TEST_CONTRACT_PLAYER_ID + 1);

		ContractPlayer angealContract = new ContractPlayer();
		angealContract.setSalary(3000000);
		angealContract.setClause(4500000);
		angealContract.setClub(this.shinraInc);
		angealContract.setEndDate(endContractDate);
		angealContract.setStartDate(startContractDate);
		angealContract.setPlayer(angealHewley);
		angealContract.setId(ContractPlayerControllerTest.TEST_CONTRACT_PLAYER_ID + 2);

		//Entrenador con mi Club
		Coach sephirot = new Coach();
		sephirot.setBirthDate(now);
		sephirot.setClause(6000000);
		sephirot.setClub(this.shinraInc);
		sephirot.setFirstName("Sephirot");
		sephirot.setLastName("Jenova");
		sephirot.setSalary(4000000);
		sephirot.setId(1);

		BDDMockito.given(this.footballClubService.findFootballClubByPresident("rufus")).willReturn(this.shinraInc);
		BDDMockito.given(this.contractPlayerService.findAllPlayerContractsByClubId(ContractPlayerControllerTest.TEST_FOOTBALL_CLUB_ID)).willReturn(Lists.newArrayList(zackContract, cloudContract, angealContract));
		BDDMockito.given(this.contractPlayerService.findContractPlayerByPlayerId(ContractPlayerControllerTest.TEST_FOOTBALL_PLAYER_ID)).willReturn(zackContract);
		BDDMockito.given(this.footballPlayerService.findFootballPlayerById(ContractPlayerControllerTest.TEST_FOOTBALL_PLAYER_ID)).willReturn(this.zackFair);
		BDDMockito.given(this.footballPlayerService.findFootballPlayerById(ContractPlayerControllerTest.TEST_FOOTBALL_PLAYER_ID + 3)).willReturn(this.genesisRhapsodos);
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - LISTA DE CONTRATOS DE JUGADORES DE MI CLUB
	void testShowMyContractList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("contractPlayers"))
			.andExpect(MockMvcResultMatchers.model().attribute("contractPlayers", Matchers.hasSize(3))).andExpect(MockMvcResultMatchers.view().name("contracts/contractPlayerList"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/contracts/contractPlayerList.jsp"));
	}

	@WithMockUser(username = "presidentWithoutClub")

	@Test //CASO NEGATIVO - LISTA DE CONTRATOS DE JUGADORES DE MI CLUB con presidente sin club
	void testShowMyContractListWithClubNotPublished() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("contractPlayers"))
			.andExpect(MockMvcResultMatchers.view().name("footballClubs/myClubEmpty")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/myClubEmpty.jsp"));
	}

	@Test //CASO NEGATIVO - LISTA DE CONTRATOS DE JUGADORES DE MI CLUB sin user
	void testShowMyContractErrorListWithoutUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/list")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - VISTA DE CONTRATO DETALLADA
	void testShowContract() throws Exception {

		Date startContractDate = new GregorianCalendar(2019, Calendar.JUNE, 30).getTime();
		Date endContractDate = new GregorianCalendar(2024, Calendar.JUNE, 30).getTime();

		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/{footballPlayerId}", ContractPlayerControllerTest.TEST_FOOTBALL_PLAYER_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("contractPlayer")).andExpect(MockMvcResultMatchers.model().attribute("contractPlayer", Matchers.hasProperty("salary", Matchers.is(2000000))))
			.andExpect(MockMvcResultMatchers.model().attribute("contractPlayer", Matchers.hasProperty("clause", Matchers.is(3000000))))
			.andExpect(MockMvcResultMatchers.model().attribute("contractPlayer", Matchers.hasProperty("club", Matchers.is(this.shinraInc))))
			.andExpect(MockMvcResultMatchers.model().attribute("contractPlayer", Matchers.hasProperty("endDate", Matchers.is(endContractDate))))
			.andExpect(MockMvcResultMatchers.model().attribute("contractPlayer", Matchers.hasProperty("startDate", Matchers.is(startContractDate))))
			.andExpect(MockMvcResultMatchers.model().attribute("contractPlayer", Matchers.hasProperty("player", Matchers.is(this.zackFair)))).andExpect(MockMvcResultMatchers.view().name("contracts/contractPlayerDetails"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/contracts/contractPlayerDetails.jsp"));
	}

	@WithMockUser(username = "rufus2")

	@Test //CASO NEGATIVO - VISTA DE CONTRATO DETALLADA sin ser el presidente del club del contrato
	void testShowContractWithWrongClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/{footballPlayerId}", ContractPlayerControllerTest.TEST_FOOTBALL_PLAYER_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("contractPlayer")).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - VISTA DE CONTRATO DETALLADA sin user
	void testShowContractError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/{footballPlayerId}", ContractPlayerControllerTest.TEST_FOOTBALL_PLAYER_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - GET - FICHAR AGENTE LIBRE
	void testInitCreationFormSuccess() throws Exception {

		Date moment = new Date(System.currentTimeMillis() - 1);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String date = simpleDateFormat.format(moment);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/{footballPlayerId}/new", ContractPlayerControllerTest.TEST_FOOTBALL_PLAYER_ID + 3)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("contractPlayer")).andExpect(MockMvcResultMatchers.model().attribute("startDate", Matchers.is(date))).andExpect(MockMvcResultMatchers.model().attribute("clausula", Matchers.is(3000000)))
			.andExpect(MockMvcResultMatchers.model().attribute("salario", Matchers.is(600000))).andExpect(MockMvcResultMatchers.model().attribute("valor", Matchers.is(6000000)))
			.andExpect(MockMvcResultMatchers.model().attribute("playerName", Matchers.is("GENESIS RHAPSODOS"))).andExpect(MockMvcResultMatchers.model().attribute("clubName", Matchers.is("SHINRA INC")))
			.andExpect(MockMvcResultMatchers.view().name("contracts/createOrUpdateContractPlayerForm")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/contracts/createOrUpdateContractPlayerForm.jsp"));
	}

	@WithMockUser(username = "presidentWithoutClub")

	@Test //CASO POSITIVO - GET - FICHAR AGENTE LIBRE con presidente sin club
	void testInitCreationFormWithoutClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/{footballPlayerId}/new", ContractPlayerControllerTest.TEST_FOOTBALL_PLAYER_ID + 3)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("contractPlayer")).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - GET - FICHAR AGENTE LIBRE sin user
	void testInitCreationFormError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/{footballPlayerId}/new", ContractPlayerControllerTest.TEST_FOOTBALL_PLAYER_ID + 3)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")

	@Test //CASO NEGATIVO - POST - FICHAR AGENTE LIBRE
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc
			.perform(
				MockMvcRequestBuilders.post("/contractPlayer/{footballPlayerId}/new", ContractPlayerControllerTest.TEST_FOOTBALL_PLAYER_ID + 3).with(SecurityMockMvcRequestPostProcessors.csrf()).param("salary", "1000000").param("endDate", "2024/01/01"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/contractPlayer/4"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO NEGATIVO - POST - FICHAR AGENTE LIBRE sin todos los parámetros
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/contractPlayer/{footballPlayerId}/new", ContractPlayerControllerTest.TEST_FOOTBALL_PLAYER_ID + 3).with(SecurityMockMvcRequestPostProcessors.csrf()).param("endDate", "2024/01/01"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("contractPlayer")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("contracts/createOrUpdateContractPlayerForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/contracts/createOrUpdateContractPlayerForm.jsp"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - DESPEDIR JUGADOR
	void testFirePlayer() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/{footballPlayerId}/delete", ContractPlayerControllerTest.TEST_FOOTBALL_PLAYER_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/footballClubs/myClub/rufus"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO NEGATIVO - DESPEDIR JUGADOR que es agente libre
	void testFireFAPlayerError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/{footballPlayerId}/delete", ContractPlayerControllerTest.TEST_FOOTBALL_PLAYER_ID + 3)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@WithMockUser(username = "rufus2")

	@Test //CASO NEGATIVO - DESPEDIR JUGADOR con otro user que no es el dueño del contrato
	void testFirePlayerWrongPresident() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/{footballPlayerId}/delete", ContractPlayerControllerTest.TEST_FOOTBALL_PLAYER_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - DESPEDIR JUGADOR sin user
	void testDeleteFootballClubError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractPlayer/{footballPlayerId}/delete", ContractPlayerControllerTest.TEST_FOOTBALL_PLAYER_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());

	}

}
