
package org.springframework.samples.petclinic.web.e2e.pedro;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
//@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
//@AutoConfigureTestDatabase(replace = Replace.ANY)
public class PlayerTransferRequestE2ETests {

	@Autowired
	private MockMvc				mockMvc;

	private static final int	TEST_PRESIDENT_ID				= 1;
	private static final int	TEST_FOOTBALL_CLUB_ID			= 1;
	private static final int	TEST_FOOTBALL_PLAYER_ID			= 1;
	private static final int	TEST_CONTRACT_ID				= 1;
	private static final int	TEST_PLAYER_TRANSFER_REQUEST_ID	= 1;


	@WithMockUser(username = "presidente1", authorities = "president")
	@Test //CASO POSITIVO
	void testShowFootballPlayerList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/request/{playerId}", PlayerTransferRequestE2ETests.TEST_FOOTBALL_PLAYER_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("playerTransferRequest")).andExpect(MockMvcResultMatchers.view().name("playerTransferRequest/createOrUpdatePlayerTransferRequestForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/playerTransferRequest/createOrUpdatePlayerTransferRequestForm.jsp"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontShowFootballPlayerList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/request/{playerId}", PlayerTransferRequestE2ETests.TEST_FOOTBALL_PLAYER_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(username = "presidente1", authorities = "president")
	@Test //CASO POSITIVO
	void testProcessCreationForm() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/transfers/players/request/{playerId}", PlayerTransferRequestE2ETests.TEST_FOOTBALL_PLAYER_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("offer", "15000000").param("contractTime", "2"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/transfers/players")).andExpect(MockMvcResultMatchers.redirectedUrl("/transfers/players"));
	}

	@WithMockUser(username = "presidente1", authorities = "president")
	@Test //CASO NEGATIVO 
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/transfers/players/request/{playerId}", PlayerTransferRequestE2ETests.TEST_FOOTBALL_PLAYER_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("offer", "").param("contractTime", ""))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors()).andExpect(MockMvcResultMatchers.model().attributeExists("playerTransferRequest"))
			.andExpect(MockMvcResultMatchers.view().name("playerTransferRequest/createOrUpdatePlayerTransferRequestForm")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/playerTransferRequest/createOrUpdatePlayerTransferRequestForm.jsp"));
	}

	//

	@WithMockUser(username = "presidente1", authorities = "president")
	@Test //CASO POSITIVO
	void testShowPlayerRequestsSentList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/requests/sent")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("playerTransferRequests"))
			.andExpect(MockMvcResultMatchers.view().name("playerTransferRequest/playerTransferRequestSentList")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/playerTransferRequest/playerTransferRequestSentList.jsp"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontShowPlayerRequestsSentList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/requests/sent")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	//

	@WithMockUser(username = "presidente1", authorities = "president")
	@Test //CASO POSITIVO
	void testDeleteTransferPlayerRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/requests/sent/delete/{requestId}", PlayerTransferRequestE2ETests.TEST_PLAYER_TRANSFER_REQUEST_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/transfers/players/requests/sent")).andExpect(MockMvcResultMatchers.redirectedUrl("/transfers/players/requests/sent"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontDeleteTransferPlayerRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/requests/sent/delete/{requestId}", PlayerTransferRequestE2ETests.TEST_PLAYER_TRANSFER_REQUEST_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	//

	@WithMockUser(username = "presidente1", authorities = "president")
	@Test //CASO POSITIVO
	void testViewTransferPlayerReceivedList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/requests/received")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("playerTransferRequests"))
			.andExpect(MockMvcResultMatchers.view().name("playerTransferRequest/playerTransferRequestReceivedList")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/playerTransferRequest/playerTransferRequestReceivedList.jsp"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontViewTransferPlayerReceivedList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/requests/received")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	//

	@WithMockUser(username = "presidente1", authorities = "president")
	@Test //CASO POSITIVO
	void testRejectTransferPlayerRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/requests/received/reject/{playerId}", PlayerTransferRequestE2ETests.TEST_FOOTBALL_PLAYER_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/transfers/players/requests/received")).andExpect(MockMvcResultMatchers.redirectedUrl("/transfers/players/requests/received"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontRejectTransferPlayerRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/requests/received/reject/{playerId}", PlayerTransferRequestE2ETests.TEST_FOOTBALL_PLAYER_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	//

	@WithMockUser(username = "presidente1", authorities = "president")
	@Test //CASO POSITIVO
	void testAcceptTransferPlayerRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/requests/received/accept/{playerId}", PlayerTransferRequestE2ETests.TEST_FOOTBALL_PLAYER_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/transfers/players/requests/received")).andExpect(MockMvcResultMatchers.redirectedUrl("/transfers/players/requests/received"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontAcceptTransferPlayerRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/requests/received/accept/{playerId}", PlayerTransferRequestE2ETests.TEST_FOOTBALL_PLAYER_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

}
