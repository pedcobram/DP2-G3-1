
package org.springframework.samples.petclinic.web.pedro;

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
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.MatchRecord;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.model.Referee;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Enum.MatchRecordStatus;
import org.springframework.samples.petclinic.model.Enum.MatchStatus;
import org.springframework.samples.petclinic.service.MatchRecordService;
import org.springframework.samples.petclinic.web.FootballPlayerStatisticController;
import org.springframework.samples.petclinic.web.MatchController;
import org.springframework.samples.petclinic.web.MatchRecordController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = MatchRecordController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class MatchRecordControllerTests {

	private static final int					TEST_MATCH_RECORD_ID	= 1;

	@Autowired
	private MockMvc								mockMvc;

	@MockBean
	private MatchRecordController				matchRecordController;

	@MockBean
	private MatchController						matchController;

	@MockBean
	private MatchRecordService					matchRecordService;

	@MockBean
	private FootballPlayerStatisticController	footballPlayerStatisticController;

	private MatchRecord							matchRecord;


	@BeforeEach()
	void setup() {

		User user2 = new User();
		user2.setUsername("rufus2");
		user2.setPassword("shinra2");
		user2.setEnabled(true);

		President rufus2 = new President();
		rufus2.setId(91);
		rufus2.setFirstName("Rufus2");
		rufus2.setLastName("Shinra2");
		rufus2.setDni("12345478H");
		rufus2.setEmail("rufus2@shinra.com");
		rufus2.setTelephone("608551023");
		rufus2.setUser(user2);

		FootballClub fc1 = new FootballClub();

		Calendar a = new GregorianCalendar();
		a.set(1997, 02, 02);
		Date now = a.getTime();

		fc1.setId(100);
		fc1.setCity("Midgar2");
		fc1.setFans(0);
		fc1.setFoundationDate(now);
		fc1.setMoney(100000000);
		fc1.setName("Shinra2 Inc");
		fc1.setPresident(rufus2);
		fc1.setStadium("Suburbios2 Stadium");
		fc1.setStatus(true);

		FootballClub fc2 = new FootballClub();
		fc1.setId(100);
		fc1.setCity("Midgar2");
		fc1.setFans(0);
		fc1.setFoundationDate(now);
		fc1.setMoney(100000000);
		fc1.setName("Shinra2 Inc");
		fc1.setPresident(rufus2);
		fc1.setStadium("Suburbios2 Stadium");
		fc1.setStatus(true);

		User user1 = new User();
		user1.setUsername("rufus1");
		user1.setPassword("shinra1");
		user1.setEnabled(true);

		Referee rufus1 = new Referee();
		rufus1.setId(91);
		rufus1.setFirstName("Rufus1");
		rufus1.setLastName("Shinra1");
		rufus1.setDni("12345478H");
		rufus1.setEmail("rufus2@shinra.com");
		rufus1.setTelephone("608551023");
		rufus1.setUser(user1);

		User user3 = new User();
		user3.setUsername("rufus3");
		user3.setPassword("shinra3");
		user3.setEnabled(true);

		Referee rufus3 = new Referee();
		rufus3.setId(91);
		rufus3.setFirstName("Rufus3");
		rufus3.setLastName("Shinra3");
		rufus3.setDni("12345478H");
		rufus3.setEmail("rufus2@shinra.com");
		rufus3.setTelephone("608551023");
		rufus3.setUser(user3);

		Match match = new Match();

		Calendar b = new GregorianCalendar();
		b.set(2025, 02, 02, 20, 30);
		Date date = a.getTime();

		match.setId(100);
		match.setTitle("Title");
		match.setStadium("Stadium 1");
		match.setMatchStatus(MatchStatus.TO_BE_PLAYED);
		match.setMatchDate(date);
		match.setReferee(rufus3);
		match.setFootballClub1(fc1);
		match.setFootballClub2(fc2);
		match.setCreator("Creator username");

		this.matchRecord = new MatchRecord();
		this.matchRecord.setId(MatchRecordControllerTests.TEST_MATCH_RECORD_ID);
		this.matchRecord.setTitle("Title");
		this.matchRecord.setSeason_end("2020");
		this.matchRecord.setSeason_start("2019");
		this.matchRecord.setStatus(MatchRecordStatus.NOT_PUBLISHED);
		this.matchRecord.setResult("Test result");
		this.matchRecord.setMatch(match);

		BDDMockito.given(this.matchRecordService.findMatchRecordById(100)).willReturn(this.matchRecord);

	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void initCreateMatchRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/1/new")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontInitCreateMatchRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/1/new")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void processCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/matches/matchRecord/1/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "Title").param("status", "NOT_PUBLISHED").param("season_start", "2019").param("season_end", "2020")
			.param("result", "Result")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().hasNoErrors());
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO NEGATIVO
	void dontProcessCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/matches/matchRecord/1/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "").param("status", "").param("season_start", "a").param("season_end", "b").param("result", "Result"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().hasErrors()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("matchRecord", "season_start"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("matchRecord", "season_end")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("matchRecord", "title"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("matchRecord", "status"));
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void initUpdateMatchRecordForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/1/edit")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontInitUpdateMatchRecordForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/1/edit")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void processUpdateMatchRecordForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/matches/matchRecord/1/edit").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "Title").param("status", "NOT_PUBLISHED").param("season_start", "2019").param("season_end", "2020")
			.param("result", "Result")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().hasNoErrors());
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO NEGATIVO
	void dontUpdateMatchRecordForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/matches/matchRecord/1/edit").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "").param("status", "").param("season_start", "a").param("season_end", "b").param("result", "Result"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().hasErrors()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("matchRecord", "season_start"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("matchRecord", "season_end")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("matchRecord", "title"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("matchRecord", "status"));

	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void viewMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/1/view")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO POSITIVO
	void dontViewMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/1/view")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void addGoalMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/goal/add/1/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontAddGoalMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/goal/add/1/1")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void substractGoalMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/goal/substract/1/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontSubstractGoalMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/goal/substract/1/1")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void addAssistMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/assist/add/1/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontAddAssistMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/assist/add/1/1")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void substractAssistMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/assist/substract/1/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO POSITIVO
	void dontSubstractAssistMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/assist/substract/1/1")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void addRedCardMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/redCard/add/1/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontAddRedCardMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/redCard/add/1/1")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void substractRedCardMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/redCard/substract/1/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontSubstractRedCardMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/redCard/substract/1/1")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void addYellowCardMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/yellowCard/add/1/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontAddYellowCardMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/yellowCard/add/1/1")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void substractYellowCardMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/yellowCard/substract/1/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontSubstractYellowCardMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/yellowCard/substract/1/1")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void addReceivedGoalMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/receivedGoals/add/1/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontAddReceivedGoalMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/receivedGoals/add/1/1")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void substractReceivedGoalMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/receivedGoals/substract/1/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontSubstractReceivedGoalMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/receivedGoals/substract/1/1")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

}
