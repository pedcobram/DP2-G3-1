
package org.springframework.samples.petclinic.round;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Competition;
import org.springframework.samples.petclinic.model.CompetitionAdmin;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.Round;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.CompetitionService;
import org.springframework.samples.petclinic.service.MatchService;
import org.springframework.samples.petclinic.service.RoundService;
import org.springframework.samples.petclinic.web.RoundController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = RoundController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class RoundControllerTest {

	@Autowired
	private MockMvc				mockMvc;
	@MockBean
	private RoundService		roundService;
	@MockBean
	private CompetitionService	competitionService;
	@MockBean
	private MatchService		matchService;

	private static final String	VIEWS_ROUND_LIST		= "round/roundList";
	private static final String	VIEWS_ROUND_DETAILS		= "round/roundDetails";
	private static final String	VIEWS_URL_ROUND_LIST	= "/WEB-INF/jsp/round/roundList.jsp";
	private static final String	VIEWS_URL_ROUND_DETAILS	= "/WEB-INF/jsp/round/roundDetails.jsp";

	private static final int	TEST_COMPETITION_ID		= 3;


	@BeforeEach
	void setup() {
		User user = new User();
		user.setUsername("rufus");
		user.setPassword("shinra");
		user.setEnabled(true);

		CompetitionAdmin rufus = new CompetitionAdmin();
		rufus.setId(98);
		rufus.setFirstName("Rufus");
		rufus.setLastName("Shinra");
		rufus.setDni("12345678H");
		rufus.setEmail("rufus@shinra.com");
		rufus.setTelephone("608551023");
		rufus.setUser(user);

		Competition c = new Competition();
		c.setId(RoundControllerTest.TEST_COMPETITION_ID);
		c.setName("Copa del Rey");

		Match m = new Match();
		m.setId(1);
		List<Match> ms = new ArrayList<Match>();
		ms.add(m);

		List<Round> rs = new ArrayList<Round>();
		Round r = new Round();
		r.setId(1);
		r.setCompetition(c);
		r.setName("Cuartos de final");

		BDDMockito.given(this.roundService.findByCompetitionId(RoundControllerTest.TEST_COMPETITION_ID)).willReturn(rs);
		BDDMockito.given(this.competitionService.findCompetitionById(RoundControllerTest.TEST_COMPETITION_ID)).willReturn(c);
		BDDMockito.given(this.roundService.findById(1)).willReturn(r);
		BDDMockito.given(this.matchService.findMatchById(1)).willReturn(m);
	}

	@WithMockUser(username = "rufus", authorities = {
		"CompetitionAdmin"
	})
	@Test //CASO POSITIVO - SHOW ROUNDS
	void testShowsRounds() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/rounds", RoundControllerTest.TEST_COMPETITION_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("competition")).andExpect(MockMvcResultMatchers.model().attributeExists("rounds")).andExpect(MockMvcResultMatchers.view().name(RoundControllerTest.VIEWS_ROUND_LIST))
			.andExpect(MockMvcResultMatchers.forwardedUrl(RoundControllerTest.VIEWS_URL_ROUND_LIST));
		;

	}
	@WithAnonymousUser
	@Test //CASO NEGATIVO -SHOW ROUNDS
	void testNotShowsRounds() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/rounds", RoundControllerTest.TEST_COMPETITION_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
		;

	}
	@WithMockUser(username = "rufus", authorities = {
		"CompetitionAdmin"
	})
	@Test //CASO POSITIVO - SHOW ROUND
	void testShowsRound() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/round/{roundId}", RoundControllerTest.TEST_COMPETITION_ID, 1)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("round")).andExpect(MockMvcResultMatchers.view().name(RoundControllerTest.VIEWS_ROUND_DETAILS)).andExpect(MockMvcResultMatchers.forwardedUrl(RoundControllerTest.VIEWS_URL_ROUND_DETAILS));
		;

	}
	@WithAnonymousUser
	@Test //CASO NEGATIVO - SHOW ROUND
	void testNotShowsRound() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/round/{roundId}", RoundControllerTest.TEST_COMPETITION_ID, 1)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
		;

	}
	@WithMockUser(username = "rufus", authorities = {
		"CompetitionAdmin"
	})
	@Test //CASO POSITIVO - SHOW ROUND
	void testShowsMatch() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/round/{roundId}/match/{matchId}", RoundControllerTest.TEST_COMPETITION_ID, 1, 1)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("match")).andExpect(MockMvcResultMatchers.view().name("competitions/matchDetails")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/matchDetails.jsp"));
		;

	}
	@WithAnonymousUser
	@Test //CASO NEGATIVO - SHOW ROUND
	void testNotShowsMatch() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/round/{roundId}/match/{matchId}", RoundControllerTest.TEST_COMPETITION_ID, 1, 1)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
		;

	}
	@WithMockUser(username = "rufus", authorities = {
		"CompetitionAdmin"
	})
	@Test //CASO POSITIVO - EDITMATCH
	void testEditMatch() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/competitions/{competitionId}/round/{roundId}/match/{matchId}", RoundControllerTest.TEST_COMPETITION_ID, 1, 1).param("matchDate", "2021/05/31 20:30").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.model().attributeExists("match")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("hasError")).andExpect(MockMvcResultMatchers.view().name("competitions/matchDetails"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/matchDetails.jsp"));

	}
	@WithAnonymousUser
	@Test //CASO NEGATIVO - EDIT MATCH
	void testNotEditMatch() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/competitions/{competitionId}/round/{roundId}/match/{matchId}", RoundControllerTest.TEST_COMPETITION_ID, 1, 1).param("matchDate", "2021/05/31").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError());

	}

}
