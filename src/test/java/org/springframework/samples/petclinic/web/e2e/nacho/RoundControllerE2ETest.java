
package org.springframework.samples.petclinic.web.e2e.nacho;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
//@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
//@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RoundControllerE2ETest {

	@Autowired
	private MockMvc				mockMvc;

	private static final String	VIEWS_ROUND_LIST		= "round/roundList";
	private static final String	VIEWS_ROUND_DETAILS		= "round/roundDetails";
	private static final String	VIEWS_URL_ROUND_LIST	= "/WEB-INF/jsp/round/roundList.jsp";
	private static final String	VIEWS_URL_ROUND_DETAILS	= "/WEB-INF/jsp/round/roundDetails.jsp";

	private static final int	TEST_COMPETITION_ID		= 3;


	//	@WithMockUser(username = "pedro", authorities = {
	//		"CompetitionAdmin"
	//	})
	//	@Test //CASO POSITIVO - SHOW ROUNDS
	//	void testShowsRounds() throws Exception {
	//		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/rounds", RoundControllerE2ETest.TEST_COMPETITION_ID)).andExpect(MockMvcResultMatchers.status().isOk());
	//		//			.andExpect(MockMvcResultMatchers.model().attributeExists("competition")).andExpect(MockMvcResultMatchers.model().attributeExists("rounds")).andExpect(MockMvcResultMatchers.view().name(RoundControllerE2ETest.VIEWS_ROUND_LIST))
	//		//			.andExpect(MockMvcResultMatchers.forwardedUrl(RoundControllerE2ETest.VIEWS_URL_ROUND_LIST));
	//		;
	//
	//	}
	@WithAnonymousUser
	@Test //CASO NEGATIVO -SHOW ROUNDS
	void testNotShowsRounds() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/rounds", RoundControllerE2ETest.TEST_COMPETITION_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
		;

	}
	//	@WithMockUser(username = "pedro", authorities = {
	//		"CompetitionAdmin"
	//	})
	//	@Test //CASO POSITIVO - SHOW ROUND
	//	void testShowsRound() throws Exception {
	//		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/round/{roundId}", RoundControllerE2ETest.TEST_COMPETITION_ID, 1)).andExpect(MockMvcResultMatchers.status().isOk())
	//			.andExpect(MockMvcResultMatchers.model().attributeExists("round")).andExpect(MockMvcResultMatchers.model().attributeExists("rounds")).andExpect(MockMvcResultMatchers.view().name(RoundControllerE2ETest.VIEWS_ROUND_DETAILS))
	//			.andExpect(MockMvcResultMatchers.forwardedUrl(RoundControllerE2ETest.VIEWS_URL_ROUND_DETAILS));
	//		;
	//
	//	}
	@WithAnonymousUser
	@Test //CASO NEGATIVO - SHOW ROUND
	void testNotShowsRound() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/round/{roundId}", RoundControllerE2ETest.TEST_COMPETITION_ID, 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
		;

	}
	@WithMockUser(username = "pedro", authorities = {
		"CompetitionAdmin"
	})
	@Test //CASO POSITIVO - SHOW ROUND
	void testShowsMatch() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/round/{roundId}/match/{matchId}", RoundControllerE2ETest.TEST_COMPETITION_ID, 1, 1)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("match")).andExpect(MockMvcResultMatchers.view().name("competitions/matchDetails")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/matchDetails.jsp"));
		;

	}
	@WithAnonymousUser
	@Test //CASO NEGATIVO - SHOW ROUND
	void testNotShowsMatch() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/round/{roundId}/match/{matchId}", RoundControllerE2ETest.TEST_COMPETITION_ID, 1, 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
		;

	}

}
