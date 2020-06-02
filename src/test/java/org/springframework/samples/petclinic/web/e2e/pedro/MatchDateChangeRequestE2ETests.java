
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
public class MatchDateChangeRequestE2ETests {

	@Autowired
	private MockMvc				mockMvc;

	private static final int	TEST_MATCH_ID					= 1;
	private static final int	TEST_MATCH_DATE_CHANGE_REQUEST	= 1;


	//	@WithMockUser(username = "presidente1", authorities = "president")
	//	@Test //CASO POSITIVO
	//	void testRequestMatchDateChangeRequest() throws Exception {
	//		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/edit/date/{matchId}", MatchDateChangeRequestE2ETests.TEST_MATCH_ID)).andExpect(MockMvcResultMatchers.status().isOk())
	//			.andExpect(MockMvcResultMatchers.model().attributeExists("matchDateChangeRequest")).andExpect(MockMvcResultMatchers.view().name("matchDateChangeRequest/createOrUpdateMatchDateChangeRequestForm"))
	//			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/matchDateChangeRequest/createOrUpdateMatchDateChangeRequestForm.jsp"));
	//	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontRequestMatchDateChangeRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/edit/date/{matchId}", MatchDateChangeRequestE2ETests.TEST_MATCH_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(username = "presidente1", authorities = "president")
	@Test //CASO POSITIVO
	void testProcessCreationForm() throws Exception {
		this.mockMvc
			.perform(
				MockMvcRequestBuilders.post("/matches/edit/date/{matchId}", 10).with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "title").param("new_date", "2055/05/11 20:30").param("reason", "qqq").param("request_creator", "president1"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/matches/list")).andExpect(MockMvcResultMatchers.redirectedUrl("/matches/list"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontProcessCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/matches/edit/date/{matchId}", MatchDateChangeRequestE2ETests.TEST_MATCH_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "title").param("new_date", "2015/05/11 20:30")
			.param("reason", "qqq").param("request_creator", "president1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(username = "presidente1", authorities = "president")
	@Test //CASO POSITIVO
	void testShowMatchDateChangeRequestList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/date-request/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("matchDateChangeRequest"))
			.andExpect(MockMvcResultMatchers.view().name("matchDateChangeRequest/matchDateChangeRequestList")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/matchDateChangeRequest/matchDateChangeRequestList.jsp"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontShowMatchDateChangeRequestList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/date-request/list")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	//	@WithMockUser(username = "presidente1", authorities = "president")
	//	@Test //CASO POSITIVO
	//	void testDeleteMatchDateChangeRequest() throws Exception {
	//		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/date-request/delete/{matchId}", 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/matches/date-request/list"))
	//			.andExpect(MockMvcResultMatchers.redirectedUrl("/matches/date-request/list"));
	//	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontDeleteMatchDateChangeRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/date-request/delete/{matchId}", MatchDateChangeRequestE2ETests.TEST_MATCH_DATE_CHANGE_REQUEST)).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}
}
