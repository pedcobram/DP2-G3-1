
package org.springframework.samples.petclinic.web.e2e.pedro;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class CoachTransferRequestE2ETests {
	
	//SI SE EJECUTA SIN DIRTIES CONTEXT DA ERROR EN DOS TESTS QUE INDIVIDUALMENTE SON CORRECTOS

	@Autowired
	private MockMvc				mockMvc;

	private static final int	TEST_MY_COACH_ID					= 2;
	private static final int	TEST_REQUESTED_COACH_ID				= 1;
	private static final int	TEST_MY_FOOTBALL_CLUB_ID			= 2;
	private static final int	TEST_REQUESTED_FOOTBALL_CLUB_ID		= 1;
	private static final int	TEST_MY_PRESIDENT_ID				= 2;
	private static final int	TEST_REQUESTED_PRESIDENT_ID			= 1;
	private static final int	TEST_MY_COACH_TRANSFER_REQUEST_ID	= 0;


	//

	@WithMockUser(username = "presidente1", authorities = "president")

	@Test //CASO POSITIVO
	void testShowRequestTransferCoach() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/request/{coachId}", CoachTransferRequestE2ETests.TEST_REQUESTED_COACH_ID))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("coachTransferRequest"))
			.andExpect(MockMvcResultMatchers.view().name("coachTransferRequest/createOrUpdateCoachTransferRequestForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/coachTransferRequest/createOrUpdateCoachTransferRequestForm.jsp"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontShowRequestTransferCoach() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/request/{coachId}", CoachTransferRequestE2ETests.TEST_REQUESTED_COACH_ID))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	//

	@WithMockUser(username = "presidente1", authorities = "president")
	@Test //CASO POSITIVO
	void testProcessCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/transfers/coaches/request/{coachId}", CoachTransferRequestE2ETests.TEST_REQUESTED_COACH_ID)
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("offer", "1000000"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/transfers/coaches"))
			.andExpect(MockMvcResultMatchers.redirectedUrl("/transfers/coaches"));
	}

	@WithMockUser(username = "presidente1", authorities = "president")
	@Test //CASO NEGATIVO
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/transfers/coaches/request/{coachId}", CoachTransferRequestE2ETests.TEST_REQUESTED_COACH_ID)
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("offer", ""))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors())
			.andExpect(MockMvcResultMatchers.model().attributeExists("coachTransferRequest"))
			.andExpect(MockMvcResultMatchers.view().name("coachTransferRequest/createOrUpdateCoachTransferRequestForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/coachTransferRequest/createOrUpdateCoachTransferRequestForm.jsp"));
	}

	//

	@WithMockUser(username = "presidente1", authorities = "president")
	@Test //CASO POSITIVO
	void testShowPlayerRequestsSentList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/requests/sent"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("coachTransferRequests"))
			.andExpect(MockMvcResultMatchers.view().name("coachTransferRequest/coachTransferRequestSentList"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/coachTransferRequest/coachTransferRequestSentList.jsp"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontShowPlayerRequestsSentList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/requests/sent"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	//

	@WithMockUser(username = "presidente2", authorities = "president")
	@Test //CASO POSITIVO
	void testDeleteTransferPlayerRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/requests/sent/delete/{requestId}", CoachTransferRequestE2ETests.TEST_MY_COACH_TRANSFER_REQUEST_ID))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/transfers/coaches/requests/sent"))
			.andExpect(MockMvcResultMatchers.redirectedUrl("/transfers/coaches/requests/sent"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontDeleteTransferPlayerRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/requests/sent/delete/{requestId}", CoachTransferRequestE2ETests.TEST_MY_COACH_TRANSFER_REQUEST_ID))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	//

	@WithMockUser(username = "presidente1", authorities = "president")
	@Test //CASO POSITIVO
	void testViewTransferPlayerReceivedList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/requests/received"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("coachTransferRequests"))
			.andExpect(MockMvcResultMatchers.view().name("coachTransferRequest/coachTransferRequestReceivedList"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/coachTransferRequest/coachTransferRequestReceivedList.jsp"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontViewTransferPlayerReceivedList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/requests/received"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	//

	@WithMockUser(username = "presidente1", authorities = "president")
	@Test //CASO POSITIVO
	void testRejectTransferPlayerRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/requests/received/reject/{coachRequestId}", CoachTransferRequestE2ETests.TEST_MY_COACH_TRANSFER_REQUEST_ID))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/transfers/coaches/requests/received"))
			.andExpect(MockMvcResultMatchers.redirectedUrl("/transfers/coaches/requests/received"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontRejectTransferPlayerRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/requests/received/reject/{coachId}", CoachTransferRequestE2ETests.TEST_MY_COACH_TRANSFER_REQUEST_ID))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	//

	@WithMockUser(username = "presidente1", authorities = "president")
	@Test //CASO POSITIVO
	void testAcceptTransferPlayerRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/requests/received/accept/{playerId}", CoachTransferRequestE2ETests.TEST_MY_COACH_TRANSFER_REQUEST_ID))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/transfers/coaches/requests/received"))
			.andExpect(MockMvcResultMatchers.redirectedUrl("/transfers/coaches/requests/received"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontAcceptTransferPlayerRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/requests/received/accept/{playerId}", CoachTransferRequestE2ETests.TEST_MY_COACH_TRANSFER_REQUEST_ID))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

}
