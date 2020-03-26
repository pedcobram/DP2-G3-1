
package org.springframework.samples.petclinic.web.nacho;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.Fan;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.MatchRecord;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthenticatedService;
import org.springframework.samples.petclinic.service.FanService;
import org.springframework.samples.petclinic.service.MatchRecordService;
import org.springframework.samples.petclinic.web.WelcomeController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = WelcomeController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class WelcomeControllerTest {

	@Autowired
	private WelcomeController		welcomeController;

	@Autowired
	private MockMvc					mockMvc;

	@MockBean
	private MatchRecordService		matchRecordService;

	@MockBean
	private AuthenticatedService	authenticatedService;
	@MockBean
	private FanService				fanService;
	private Fan						f;
	private Authenticated			au;
	private FootballClub			club;
	private Collection<MatchRecord>	m;
	private static final int		TEST_ID	= 1;


	@BeforeEach
	void setup() {
		User user = new User();

		user.setUsername("fan");
		user.setPassword("fan");
		user.setEnabled(true);

		this.au = new Authenticated();

		this.au.setId(WelcomeControllerTest.TEST_ID);
		this.au.setFirstName("Fan");
		this.au.setLastName("Test");
		this.au.setDni("12345678T");
		this.au.setEmail("fans@test.com");
		this.au.setTelephone("657063253");
		this.au.setUser(user);

		User userP = new User();
		user.setUsername("rufus");
		user.setPassword("shinra");
		user.setEnabled(true);

		President rufus = new President();
		rufus.setId(WelcomeControllerTest.TEST_ID);
		rufus.setFirstName("Rufus");
		rufus.setLastName("Shinra");
		rufus.setDni("12345678H");
		rufus.setEmail("rufus@shinra.com");
		rufus.setTelephone("608551023");
		rufus.setUser(userP);

		this.club = new FootballClub();
		Date now = new Date(System.currentTimeMillis() - 1);
		this.club.setCrest("");
		this.club.setId(WelcomeControllerTest.TEST_ID);
		this.club.setCity("Midgar");
		this.club.setFans(0);
		this.club.setFoundationDate(now);
		this.club.setMoney(100000000);
		this.club.setName("Shinra Inc");
		this.club.setPresident(rufus);
		this.club.setStadium("Suburbios Stadium");
		this.club.setStatus(true);

		this.f = new Fan();
		this.f.setId(WelcomeControllerTest.TEST_ID);
		this.f.setUser(this.au);
		this.f.setClub(this.club);
		this.f.setVip(false);
		MatchRecord m1 = new MatchRecord();
		this.m = new ArrayList<MatchRecord>();
		this.m.add(m1);

		BDDMockito.given(this.fanService.existFan(WelcomeControllerTest.TEST_ID)).willReturn(true);
		BDDMockito.given(this.fanService.findByUserId(WelcomeControllerTest.TEST_ID)).willReturn(this.f);
		BDDMockito.given(this.authenticatedService.findAuthenticatedByUsername("fan")).willReturn(this.au);
		BDDMockito.given(this.matchRecordService.findLastMatches(WelcomeControllerTest.TEST_ID)).willReturn(this.m);
	}

	@WithMockUser(username = "fan")
	@Test //CASO POSITIVO - CON FAN
	void testWelcomeFanSuccess() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("isVip")).andExpect(MockMvcResultMatchers.model().attributeExists("isFan"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("club")).andExpect(MockMvcResultMatchers.model().attributeExists("lastMatches")).andExpect(MockMvcResultMatchers.model().attribute("isFan", true))
			.andExpect(MockMvcResultMatchers.model().attribute("isVip", false)).andExpect(MockMvcResultMatchers.model().attribute("club", this.club)).andExpect(MockMvcResultMatchers.model().attribute("lastMatches", this.m))
			.andExpect(MockMvcResultMatchers.view().name("welcome")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/welcome.jsp"));
	}

	@Test //CASO POSITIVO - SIN FAN
	void testWelcomeSuccess() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("isVip")).andExpect(MockMvcResultMatchers.model().attributeExists("isFan"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("club")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("lastMatches")).andExpect(MockMvcResultMatchers.model().attribute("isFan", false))
			.andExpect(MockMvcResultMatchers.model().attribute("isVip", false)).andExpect(MockMvcResultMatchers.view().name("welcome")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/welcome.jsp"));
	}
}
