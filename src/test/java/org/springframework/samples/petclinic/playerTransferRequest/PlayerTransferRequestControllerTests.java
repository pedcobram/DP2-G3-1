
package org.springframework.samples.petclinic.playerTransferRequest;

import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.ContractPlayer;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.PlayerTransferRequest;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Enum.FootballPlayerPosition;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.samples.petclinic.service.ContractPlayerService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.FootballPlayerService;
import org.springframework.samples.petclinic.service.PlayerTransferRequestService;
import org.springframework.samples.petclinic.web.PlayerTransferRequestController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = PlayerTransferRequestController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class PlayerTransferRequestControllerTests {

	@Autowired
	private MockMvc							mockMvc;

	@MockBean
	private FootballClubService				footballClubService;

	@MockBean
	private FootballPlayerService			footballPlayerService;

	@MockBean
	private PlayerTransferRequestService	playerTransferRequestService;

	@MockBean
	private ContractPlayerService			contractPlayerService;

	private static final int				TEST_PRESIDENT_ID				= 97;
	private static final int				TEST_FOOTBALL_CLUB_ID			= 98;
	private static final int				TEST_FOOTBALL_PLAYER_ID			= 99;
	private static final int				TEST_CONTRACT_ID				= 100;
	private static final int				TEST_PLAYER_TRANSFER_REQUEST_ID	= 101;


	@BeforeEach
	void setup() {

		// User

		User user = new User();

		user.setEnabled(true);
		user.setUsername("president");
		user.setPassword("president");

		// President

		President president = new President();

		president.setId(PlayerTransferRequestControllerTests.TEST_PRESIDENT_ID);
		president.setDni("11111111A");
		president.setFirstName("President1");
		president.setLastName("President1");
		president.setEmail("test@gmail.com");
		president.setTelephone("111111111");
		president.setUser(user);

		// FootballClub

		Calendar foundationDate = Calendar.getInstance();

		FootballClub fc = new FootballClub();

		fc.setId(PlayerTransferRequestControllerTests.TEST_FOOTBALL_CLUB_ID);
		fc.setCity("City");
		fc.setCrest("Crest");
		fc.setFans(44000);
		fc.setFoundationDate(foundationDate.getTime());
		fc.setMoney(150000000);
		fc.setName("Club name");
		fc.setStadium("Stadium name");
		fc.setStatus(true);
		fc.setPresident(president);

		// FootballPlayer

		Calendar birthDate = Calendar.getInstance();
		FootballPlayer footballPlayer = new FootballPlayer();

		footballPlayer.setId(PlayerTransferRequestControllerTests.TEST_FOOTBALL_PLAYER_ID);
		footballPlayer.setFirstName("playername");
		footballPlayer.setLastName("playerlastname");
		footballPlayer.setClub(fc);
		footballPlayer.setBirthDate(birthDate.getTime());
		footballPlayer.setPosition(FootballPlayerPosition.STRIKER);
		footballPlayer.setValue(150000000);

		// Contract

		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		endDate.add(Calendar.YEAR, 2);

		ContractPlayer contract = new ContractPlayer();

		contract.setId(PlayerTransferRequestControllerTests.TEST_CONTRACT_ID);
		contract.setClause(15000000 * 6);
		contract.setClub(fc);
		contract.setStartDate(startDate.getTime());
		contract.setEndDate(endDate.getTime());
		contract.setSalary(15000000);
		contract.setPlayer(footballPlayer);

		// Player Transfer Request

		PlayerTransferRequest playerTransferRequest = new PlayerTransferRequest();

		playerTransferRequest.setId(PlayerTransferRequestControllerTests.TEST_PLAYER_TRANSFER_REQUEST_ID);
		playerTransferRequest.setOffer(2000000L);
		playerTransferRequest.setStatus(RequestStatus.ON_HOLD);
		playerTransferRequest.setContractTime(1);
		playerTransferRequest.setClub(fc);
		playerTransferRequest.setContract(contract);
		playerTransferRequest.setFootballPlayer(footballPlayer);

		BDDMockito.given(this.playerTransferRequestService.findPlayerTransferRequestById(PlayerTransferRequestControllerTests.TEST_PLAYER_TRANSFER_REQUEST_ID)).willReturn(playerTransferRequest);
		BDDMockito.given(this.footballPlayerService.findFootballPlayerById(PlayerTransferRequestControllerTests.TEST_FOOTBALL_PLAYER_ID)).willReturn(footballPlayer);
		BDDMockito.given(this.footballClubService.findFootballClubByPresident("president")).willReturn(fc);
		BDDMockito.given(this.contractPlayerService.findContractPlayerByPlayerId(PlayerTransferRequestControllerTests.TEST_FOOTBALL_PLAYER_ID)).willReturn(contract);
		BDDMockito.given(this.playerTransferRequestService.countPlayerTransferRequestsByPresidentAndPlayer("president", PlayerTransferRequestControllerTests.TEST_PRESIDENT_ID)).willReturn(0);
		BDDMockito.given(this.playerTransferRequestService.findPlayerTransferRequestByPlayerId(PlayerTransferRequestControllerTests.TEST_FOOTBALL_PLAYER_ID)).willReturn(playerTransferRequest);
	}

	@WithMockUser(username = "president")
	@Test //CASO POSITIVO
	void testShowFootballPlayerList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/request/{playerId}", PlayerTransferRequestControllerTests.TEST_FOOTBALL_PLAYER_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("playerTransferRequest")).andExpect(MockMvcResultMatchers.view().name("playerTransferRequest/createOrUpdatePlayerTransferRequestForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/playerTransferRequest/createOrUpdatePlayerTransferRequestForm.jsp"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontShowFootballPlayerList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/request/{playerId}", PlayerTransferRequestControllerTests.TEST_FOOTBALL_PLAYER_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "president")
	@Test //CASO POSITIVO
	void testProcessCreationForm() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/transfers/players/request/{playerId}", PlayerTransferRequestControllerTests.TEST_FOOTBALL_PLAYER_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("offer", "15000000").param("contractTime", "2"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/transfers/players")).andExpect(MockMvcResultMatchers.redirectedUrl("/transfers/players"));
	}

	@WithMockUser(username = "president")
	@Test //CASO NEGATIVO 
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/transfers/players/request/{playerId}", PlayerTransferRequestControllerTests.TEST_FOOTBALL_PLAYER_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("offer", "").param("contractTime", ""))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors()).andExpect(MockMvcResultMatchers.model().attributeExists("playerTransferRequest"))
			.andExpect(MockMvcResultMatchers.view().name("playerTransferRequest/createOrUpdatePlayerTransferRequestForm")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/playerTransferRequest/createOrUpdatePlayerTransferRequestForm.jsp"));
	}

	//

	@WithMockUser(username = "president")
	@Test //CASO POSITIVO
	void testShowPlayerRequestsSentList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/requests/sent")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("playerTransferRequests"))
			.andExpect(MockMvcResultMatchers.view().name("playerTransferRequest/playerTransferRequestSentList")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/playerTransferRequest/playerTransferRequestSentList.jsp"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontShowPlayerRequestsSentList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/requests/sent")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	//

	@WithMockUser(username = "president")
	@Test //CASO POSITIVO
	void testDeleteTransferPlayerRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/requests/sent/delete/{requestId}", PlayerTransferRequestControllerTests.TEST_PLAYER_TRANSFER_REQUEST_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/transfers/players/requests/sent")).andExpect(MockMvcResultMatchers.redirectedUrl("/transfers/players/requests/sent"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontDeleteTransferPlayerRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/requests/sent/delete/{requestId}", PlayerTransferRequestControllerTests.TEST_PLAYER_TRANSFER_REQUEST_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	//

	@WithMockUser(username = "president")
	@Test //CASO POSITIVO
	void testViewTransferPlayerReceivedList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/requests/received")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("playerTransferRequests"))
			.andExpect(MockMvcResultMatchers.view().name("playerTransferRequest/playerTransferRequestReceivedList")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/playerTransferRequest/playerTransferRequestReceivedList.jsp"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontViewTransferPlayerReceivedList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/requests/received")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	//

	@WithMockUser(username = "president")
	@Test //CASO POSITIVO
	void testRejectTransferPlayerRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/requests/received/reject/{playerId}", PlayerTransferRequestControllerTests.TEST_FOOTBALL_PLAYER_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/transfers/players/requests/received")).andExpect(MockMvcResultMatchers.redirectedUrl("/transfers/players/requests/received"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontRejectTransferPlayerRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/requests/received/reject/{playerId}", PlayerTransferRequestControllerTests.TEST_FOOTBALL_PLAYER_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	//

	@WithMockUser(username = "president")
	@Test //CASO POSITIVO
	void testAcceptTransferPlayerRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/requests/received/accept/{playerId}", PlayerTransferRequestControllerTests.TEST_FOOTBALL_PLAYER_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/transfers/players/requests/received")).andExpect(MockMvcResultMatchers.redirectedUrl("/transfers/players/requests/received"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontAcceptTransferPlayerRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/requests/received/accept/{playerId}", PlayerTransferRequestControllerTests.TEST_FOOTBALL_PLAYER_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

}
