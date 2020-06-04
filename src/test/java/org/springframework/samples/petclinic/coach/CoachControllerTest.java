
package org.springframework.samples.petclinic.coach;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Coach;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.CoachService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.web.CoachController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = CoachController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class CoachControllerTest {

	private static final int	TEST_FOOTBALL_CLUB_ID	= 1;
	private static final int	TEST_COACH_ID			= 1;

	@Autowired
	private CoachController		coachController;

	@Autowired
	private MockMvc				mockMvc;

	@MockBean
	private CoachService		coachService;

	@MockBean
	private FootballClubService	footballClubService;

	private FootballClub		shinraInc;

	private Coach				sephirot;


	@BeforeEach
	void setup() {

		Date birthDate = new GregorianCalendar(2000, Calendar.FEBRUARY, 11).getTime();
		Date now = new Date(System.currentTimeMillis() - 1);

		User user = new User();
		user.setUsername("rufus");
		user.setPassword("shinra");
		user.setEnabled(true);

		User user2 = new User();
		user2.setUsername("rufus2");
		user2.setPassword("shinra2");
		user2.setEnabled(true);

		President rufus = new President();
		rufus.setId(98);
		rufus.setFirstName("Rufus");
		rufus.setLastName("Shinra");
		rufus.setDni("12345678H");
		rufus.setEmail("rufus@shinra.com");
		rufus.setTelephone("608551023");
		rufus.setUser(user);

		President rufus2 = new President();
		rufus2.setUser(user2);

		this.shinraInc = new FootballClub();
		this.shinraInc.setName("Shinra Inc");
		this.shinraInc.setCity("Midgar");
		this.shinraInc.setStadium("Suburbios Stadium");
		this.shinraInc.setMoney(100000000);
		this.shinraInc.setFans(0);
		this.shinraInc.setFoundationDate(now);
		this.shinraInc.setStatus(true);
		this.shinraInc.setPresident(rufus);
		this.shinraInc.setId(CoachControllerTest.TEST_FOOTBALL_CLUB_ID);

		FootballClub anotherClub = new FootballClub();

		anotherClub.setName("Shinra2 Inc");
		anotherClub.setCity("Midgar2");
		anotherClub.setStadium("Suburbios2 Stadium");
		anotherClub.setMoney(100000000);
		anotherClub.setFans(0);
		anotherClub.setFoundationDate(now);
		anotherClub.setStatus(true);
		anotherClub.setPresident(rufus2);
		anotherClub.setId(CoachControllerTest.TEST_FOOTBALL_CLUB_ID + 1);

		//Entrenador con mi Club
		this.sephirot = new Coach();
		this.sephirot.setBirthDate(birthDate);
		this.sephirot.setClause(6000000);
		this.sephirot.setClub(this.shinraInc);
		this.sephirot.setFirstName("Sephirot");
		this.sephirot.setLastName("Jenova");
		this.sephirot.setSalary(4000000);
		this.sephirot.setId(CoachControllerTest.TEST_COACH_ID);

		Coach anotherCoach = new Coach();
		anotherCoach.setBirthDate(birthDate);
		anotherCoach.setClause(3000000);
		anotherCoach.setClub(null);
		anotherCoach.setFirstName("Another");
		anotherCoach.setLastName("Coach");
		anotherCoach.setSalary(4000000);
		anotherCoach.setId(CoachControllerTest.TEST_COACH_ID + 1);

		Coach anotherCoachWithClub = new Coach();
		anotherCoachWithClub.setBirthDate(birthDate);
		anotherCoachWithClub.setClause(3000000);
		anotherCoachWithClub.setClub(anotherClub);
		anotherCoachWithClub.setFirstName("Another");
		anotherCoachWithClub.setLastName("Coach");
		anotherCoachWithClub.setSalary(4000000);
		anotherCoachWithClub.setId(CoachControllerTest.TEST_COACH_ID + 2);

		BDDMockito.given(this.footballClubService.findFootballClubByPresident("rufus")).willReturn(this.shinraInc);
		BDDMockito.given(this.coachService.findCoachByClubId(CoachControllerTest.TEST_FOOTBALL_CLUB_ID)).willReturn(this.sephirot);
		BDDMockito.given(this.coachService.findCoachById(CoachControllerTest.TEST_COACH_ID)).willReturn(this.sephirot);
		BDDMockito.given(this.coachService.findCoachById(CoachControllerTest.TEST_COACH_ID + 1)).willReturn(anotherCoach);
		BDDMockito.given(this.coachService.findCoachById(CoachControllerTest.TEST_COACH_ID + 2)).willReturn(anotherCoachWithClub);

	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - VISTA DE ENTRENADOR
	void testShowCoach() throws Exception {

		Date birthDate = new GregorianCalendar(2000, Calendar.FEBRUARY, 11).getTime();

		this.mockMvc.perform(MockMvcRequestBuilders.get("/coachs/{coachId}", CoachControllerTest.TEST_COACH_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("coach"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("clubId")).andExpect(MockMvcResultMatchers.model().attributeExists("clubStatus"))
			.andExpect(MockMvcResultMatchers.model().attribute("coach", Matchers.hasProperty("firstName", Matchers.is("Sephirot")))).andExpect(MockMvcResultMatchers.model().attribute("coach", Matchers.hasProperty("lastName", Matchers.is("Jenova"))))
			.andExpect(MockMvcResultMatchers.model().attribute("coach", Matchers.hasProperty("salary", Matchers.is(4000000)))).andExpect(MockMvcResultMatchers.model().attribute("coach", Matchers.hasProperty("clause", Matchers.is(6000000))))
			.andExpect(MockMvcResultMatchers.model().attribute("coach", Matchers.hasProperty("club", Matchers.is(this.shinraInc)))).andExpect(MockMvcResultMatchers.model().attribute("coach", Matchers.hasProperty("birthDate", Matchers.is(birthDate))))
			.andExpect(MockMvcResultMatchers.model().attribute("coachAge", Matchers.is(20))).andExpect(MockMvcResultMatchers.view().name("coachs/coachDetails")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/coachs/coachDetails.jsp"));
	}

	@Test //CASO NEGATIVO - VISTA DE ENTRENADOR sin user
	void testShowCoachError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/coachs/{coachId}", CoachControllerTest.TEST_COACH_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - GET - REGISTRAR ENTRENADOR
	void testInitCreationFormSuccess() throws Exception {

		this.shinraInc.setStatus(false);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/coach/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("coach")).andExpect(MockMvcResultMatchers.model().attributeExists("regs"))
			.andExpect(MockMvcResultMatchers.view().name("coachs/createOrUpdateCoachForm")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/coachs/createOrUpdateCoachForm.jsp"));
	}

	@WithMockUser(username = "presidentWithoutClub")

	@Test //CASO NEGATIVO - GET - REGISTRAR ENTRENADOR con presidente sin club
	void testInitCreationFormWithoutClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/coach/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("coach"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("regs")).andExpect(MockMvcResultMatchers.view().name("footballClubs/myClubEmpty")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/myClubEmpty.jsp"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO NEGATIVO - GET - REGISTRAR ENTRENADOR con presidente con club público
	void testInitCreationFormWithClubPublished() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/coach/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("coach"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("regs")).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO NEGATIVO - POST - REGISTRAR ENTRENADOR
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/coach/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "Barret").param("lastName", "Wallace").param("salary", "1000000").param("birthDate", "2000/01/01"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/coachs/null"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO NEGATIVO - POST - REGISTRAR ENTRENADOR
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/coach/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("lastName", "Wallace").param("salary", "1000000").param("birthDate", "2000/01/01"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("coach")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("coachs/createOrUpdateCoachForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/coachs/createOrUpdateCoachForm.jsp"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - GET - FICHAR ENTRENADOR
	void testInitSigningFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/coach/{coachId}/sign", CoachControllerTest.TEST_COACH_ID + 1)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("coach"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("clubCoach")).andExpect(MockMvcResultMatchers.model().attribute("freeAgent", Matchers.is(true))).andExpect(MockMvcResultMatchers.model().attribute("iHaveCoach", Matchers.is(true)))
			.andExpect(MockMvcResultMatchers.model().attribute("toPayValue", Matchers.is(6000000 + " €"))).andExpect(MockMvcResultMatchers.model().attribute("myCoachFirstName", Matchers.is("Sephirot")))
			.andExpect(MockMvcResultMatchers.model().attribute("myCoachLastName", Matchers.is("Jenova"))).andExpect(MockMvcResultMatchers.model().attribute("clubName", Matchers.is("Shinra Inc")))
			.andExpect(MockMvcResultMatchers.model().attribute("readonly", Matchers.is(true))).andExpect(MockMvcResultMatchers.view().name("coachs/createOrUpdateCoachForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/coachs/createOrUpdateCoachForm.jsp"));
	}

	@WithMockUser(username = "presidentWithoutClub")

	@Test //CASO NEGATIVO - GET - FICHAR ENTRENADOR con presidente sin club
	void testInitSigningFormWithoutClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/coach/{coachId}/sign", CoachControllerTest.TEST_COACH_ID + 1)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("coach"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("clubCoach")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("freeAgent")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("iHaveCoach"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("toPayValue")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("myCoachFirstName")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("myCoachLastName"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("clubName")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("readonly")).andExpect(MockMvcResultMatchers.view().name("footballClubs/myClubEmpty"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/myClubEmpty.jsp"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO NEGATIVO - GET - FICHAR ENTRENADOR con club y con presidente con club no publicado 
	void testInitSigningFormWithClubNotPublished() throws Exception {

		this.shinraInc.setStatus(false);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/coach/{coachId}/sign", CoachControllerTest.TEST_COACH_ID + 2)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("coach"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("clubCoach")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("freeAgent")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("iHaveCoach"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("toPayValue")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("myCoachFirstName")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("myCoachLastName"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("clubName")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("readonly")).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - GET - FICHAR ENTRENADOR sin user
	void testInitSigningFormWithoutUser() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/coachs/{coachId}/sign", CoachControllerTest.TEST_COACH_ID + 1)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")

	@Test //CASO NEGATIVO - POST - FICHAR  ENTRENADOR
	void testProcessSigningFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/coach/{coachId}/sign", CoachControllerTest.TEST_COACH_ID + 1).with(SecurityMockMvcRequestPostProcessors.csrf()).param("salary", "1000000").param("firstName", "Another").param("lastName", "Coach")
			.param("birthDate", "2000/02/11")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/footballClubs/myClub/rufus"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO NEGATIVO - POST - FICHAR  ENTRENADOR
	void testProcessSigningFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/coach/{coachId}/sign", CoachControllerTest.TEST_COACH_ID + 1).with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "Another").param("lastName", "Coach").param("birthDate", "2000/02/11"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("coach")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("clubCoach")).andExpect(MockMvcResultMatchers.model().attribute("freeAgent", Matchers.is(true)))
			.andExpect(MockMvcResultMatchers.model().attribute("iHaveCoach", Matchers.is(true))).andExpect(MockMvcResultMatchers.model().attribute("toPayValue", Matchers.is(6000000 + " €")))
			.andExpect(MockMvcResultMatchers.model().attribute("myCoachFirstName", Matchers.is("Sephirot"))).andExpect(MockMvcResultMatchers.model().attribute("myCoachLastName", Matchers.is("Jenova")))
			.andExpect(MockMvcResultMatchers.model().attribute("clubName", Matchers.is("Shinra Inc"))).andExpect(MockMvcResultMatchers.model().attribute("readonly", Matchers.is(true))).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("coachs/createOrUpdateCoachForm")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/coachs/createOrUpdateCoachForm.jsp"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - DESPEDIR ENTRENADOR
	void testFireCoach() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/coach/{coachId}/fire", CoachControllerTest.TEST_COACH_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/footballClubs/myClub/rufus"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO NEGATIVO - DESPEDIR ENTRENADOR siendo agente libre
	void testFireCoachFAError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/coach/{coachId}/fire", CoachControllerTest.TEST_COACH_ID + 1)).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO NEGATIVO - DESPEDIR ENTRENADOR de otro club
	void testFireCoachWrongPresident() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/coach/{coachId}/fire", CoachControllerTest.TEST_COACH_ID + 2)).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - DESPEDIR ENTRENADOR sin user
	void testFireCoachWithoutUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/coachs/{coachId}/fire", CoachControllerTest.TEST_COACH_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

}
