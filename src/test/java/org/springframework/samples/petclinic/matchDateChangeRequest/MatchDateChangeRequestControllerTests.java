
package org.springframework.samples.petclinic.matchDateChangeRequest;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.assertj.core.util.Lists;
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
import org.springframework.samples.petclinic.model.MatchDateChangeRequest;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.model.Referee;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Enum.MatchStatus;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.samples.petclinic.service.MatchDateChangeRequestService;
import org.springframework.samples.petclinic.service.MatchService;
import org.springframework.samples.petclinic.web.MatchDateChangeRequestController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = MatchDateChangeRequestController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class MatchDateChangeRequestControllerTests {

	@Autowired
	private MockMvc							mockMvc;

	@MockBean
	private MatchDateChangeRequestService	matchDateChangeRequestService;

	@MockBean
	private MatchService					matchService;

	private static final int				TEST_MATCH_ID					= 100;
	private static final int				TEST_PRESIDENT1_ID				= 101;
	private static final int				TEST_PRESIDENT2_ID				= 101;
	private static final int				TEST_MATCH_DATE_CHANGE_REQUEST	= 102;


	@BeforeEach
	void setup() {

		// User

		User u = new User();

		u.setEnabled(true);
		u.setPassword("password");
		u.setUsername("username");

		// Referee

		Referee referee = new Referee();

		referee.setTelephone("");
		referee.setDni("49032196Z");
		referee.setEmail("test@gmail.com");
		referee.setFirstName("First Name");
		referee.setLastName("Last Name");
		referee.setUser(u);

		// User 1

		User user = new User();

		user.setEnabled(true);
		user.setUsername("president1");
		user.setPassword("president1");

		// President 1

		President president1 = new President();

		president1.setId(MatchDateChangeRequestControllerTests.TEST_PRESIDENT1_ID);
		president1.setDni("11111111A");
		president1.setFirstName("President1");
		president1.setLastName("President1");
		president1.setEmail("test@gmail.com");
		president1.setTelephone("111111111");
		president1.setUser(user);

		// Club 1

		FootballClub club1 = new FootballClub();

		club1.setStatus(false);
		club1.setFans(0);
		club1.setPresident(president1);
		club1.setName("Chelsea");
		club1.setCity("London");
		club1.setStadium("Stamford Bridge");
		club1.setMoney(1000000);

		Date date1 = new Date(System.currentTimeMillis() - 1);
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		cal1.add(Calendar.YEAR, -1);
		date1 = cal1.getTime();

		club1.setFoundationDate(date1);
		club1.setCrest("https://www.example.com");

		// User 2

		User user2 = new User();

		user2.setEnabled(true);
		user2.setUsername("president2");
		user2.setPassword("president2");

		// President 2

		President president2 = new President();

		president2.setId(MatchDateChangeRequestControllerTests.TEST_PRESIDENT2_ID);
		president2.setDni("11111111A");
		president2.setFirstName("President2");
		president2.setLastName("President2");
		president2.setEmail("test@gmail.com");
		president2.setTelephone("111111111");
		president2.setUser(user);

		// Club 2

		FootballClub club2 = new FootballClub();

		club2.setStatus(false);
		club2.setFans(0);
		club2.setPresident(president2);
		club2.setName("Chelsea");
		club2.setCity("London");
		club2.setStadium("Stamford Bridge");
		club2.setMoney(1000000);

		Date date2 = new Date(System.currentTimeMillis() - 1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		cal2.add(Calendar.YEAR, -1);
		date2 = cal2.getTime();

		club2.setFoundationDate(date2);
		club2.setCrest("https://www.example.com");

		// Match

		Match match = new Match();

		Calendar cal = new GregorianCalendar();
		cal.set(2020, 03, 24, 20, 30);
		Date date = cal.getTime();

		match.setId(MatchDateChangeRequestControllerTests.TEST_MATCH_ID);
		match.setTitle("Title");
		match.setStadium("Stadium");
		match.setMatchStatus(MatchStatus.TO_BE_PLAYED);
		match.setMatchDate(date);
		match.setCreator("Me");
		match.setReferee(referee);
		match.setMatchRecord(null);
		match.setFootballClub2(club1);
		match.setFootballClub1(club2);

		// Match Date Change Request

		MatchDateChangeRequest mdcr = new MatchDateChangeRequest();

		Calendar cal3 = new GregorianCalendar();
		cal3.set(2020, 03, 24, 20, 30);
		Date date3 = cal.getTime();

		mdcr.setId(MatchDateChangeRequestControllerTests.TEST_MATCH_DATE_CHANGE_REQUEST);
		mdcr.setMatch(match);
		mdcr.setNew_date(date3);
		mdcr.setReason("reason");
		mdcr.setRequest_creator("president1");
		mdcr.setStatus(RequestStatus.ON_HOLD);
		mdcr.setTitle("title");

		BDDMockito.given(this.matchService.findMatchById(MatchDateChangeRequestControllerTests.TEST_MATCH_ID)).willReturn(match);
		BDDMockito.given(this.matchDateChangeRequestService.findAllMatchDateChangeRequests(mdcr.getRequest_creator())).willReturn(Lists.newArrayList(mdcr));
		BDDMockito.given(this.matchDateChangeRequestService.findMatchDateChangeRequestById(MatchDateChangeRequestControllerTests.TEST_MATCH_DATE_CHANGE_REQUEST)).willReturn(mdcr);
	}

	@WithMockUser(username = "president1")
	@Test //CASO POSITIVO
	void testRequestMatchDateChangeRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/edit/date/{matchId}", MatchDateChangeRequestControllerTests.TEST_MATCH_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("matchDateChangeRequest")).andExpect(MockMvcResultMatchers.view().name("matchDateChangeRequest/createOrUpdateMatchDateChangeRequestForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/matchDateChangeRequest/createOrUpdateMatchDateChangeRequestForm.jsp"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontRequestMatchDateChangeRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/edit/date/{matchId}", MatchDateChangeRequestControllerTests.TEST_MATCH_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "president")
	@Test //CASO POSITIVO
	void testProcessCreationForm() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/matches/edit/date/{matchId}", MatchDateChangeRequestControllerTests.TEST_MATCH_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "title").param("new_date", "2055/05/11 20:30")
				.param("reason", "qqq").param("request_creator", "president1"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/matches/list")).andExpect(MockMvcResultMatchers.redirectedUrl("/matches/list"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontProcessCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/matches/edit/date/{matchId}", MatchDateChangeRequestControllerTests.TEST_MATCH_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "title").param("new_date", "2015/05/11 20:30")
			.param("reason", "qqq").param("request_creator", "president1")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "president1")
	@Test //CASO POSITIVO
	void testShowMatchDateChangeRequestList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/date-request/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("matchDateChangeRequest"))
			.andExpect(MockMvcResultMatchers.view().name("matchDateChangeRequest/matchDateChangeRequestList")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/matchDateChangeRequest/matchDateChangeRequestList.jsp"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontShowMatchDateChangeRequestList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/date-request/list")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@WithMockUser(username = "president1")
	@Test //CASO POSITIVO
	void testDeleteMatchDateChangeRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/date-request/delete/{matchId}", MatchDateChangeRequestControllerTests.TEST_MATCH_DATE_CHANGE_REQUEST))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/matches/date-request/list"))
			.andExpect(MockMvcResultMatchers.redirectedUrl("/matches/date-request/list"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontDeleteMatchDateChangeRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/date-request/delete/{matchId}", MatchDateChangeRequestControllerTests.TEST_MATCH_DATE_CHANGE_REQUEST))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
}
