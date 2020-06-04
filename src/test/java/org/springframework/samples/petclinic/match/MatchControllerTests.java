
package org.springframework.samples.petclinic.match;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.Enum.MatchStatus;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.MatchService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.web.MatchController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = MatchController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class MatchControllerTests {

	@Autowired
	private MockMvc				mockMvc;

	@SuppressWarnings("unused")
	@Autowired
	private MatchController		matchController;

	@MockBean
	private MatchService		matchService;

	@MockBean
	private FootballClubService	footballClubService;

	@MockBean
	private UserService			userService;


	@BeforeEach
	void setup() {

		Calendar cal = new GregorianCalendar();
		cal.set(2020, 03, 24, 20, 30);
		Date date = cal.getTime();

		Match match = new Match();

		match.setId(100);
		match.setTitle("Title");
		match.setStadium("Stadium");
		match.setMatchStatus(MatchStatus.TO_BE_PLAYED);
		match.setMatchDate(date);
		match.setCreator("Me");
		//match.setReferee(referee);
		//match.setMatchRecord();
		//match.setFootballClub2(footballClub2);
		//match.setFootballClub1(footballClub1);
		BDDMockito.given(this.matchService.findMatchById(100)).willReturn(match);

	}

	@WithMockUser(username = "pedro", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void showMatchList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/list")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test //CASO NEGATIVO
	void dontShowMatchList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/list")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "pedro", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void showMatch() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test //CASO POSITIVO
	void dontShowMatch() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/1")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void initUpdateMatchForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/edit/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test //CASO NEGATIVO
	void dontInitUpdateMatchForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/edit/1")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void processUpdateMatchForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/matches/edit/1").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "Title").param("matchDate", "2020/03/20 20:30").param("matchStatus", "TO_BE_PLAYED").param("stadium", "Stadium")
			.param("footballClub1", "Sevilla Fútbol Club").param("footballClub2", "Fútbol Club Barcelona")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO NEGATIVO
	void dontProcessUpdateMatchForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/matches/edit/1").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "Title").param("matchDate", "").param("matchStatus", "TO_BE_PLAYED").param("stadium", "")
			.param("footballClub1", "Sevilla Fútbol Club").param("footballClub2", "Fútbol Club Barcelona")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("exceptions/exception"));
		//.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("match", "matchDate"))
		//.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("match", "stadium"));
	}

	@WithMockUser(username = "pedro", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void showReceivedMatchRequestList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/referee/list")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test //CASO POSITIVO
	void dontShowReceivedMatchRequestList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/referee/list")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

}
