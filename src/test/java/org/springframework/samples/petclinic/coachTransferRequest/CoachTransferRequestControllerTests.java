
package org.springframework.samples.petclinic.coachTransferRequest;

import java.util.Calendar;

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
import org.springframework.samples.petclinic.model.Coach;
import org.springframework.samples.petclinic.model.CoachTransferRequest;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;
import org.springframework.samples.petclinic.service.CoachService;
import org.springframework.samples.petclinic.service.CoachTransferRequestService;
import org.springframework.samples.petclinic.service.ContractPlayerService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.web.CoachTransferRequestController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = CoachTransferRequestController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class CoachTransferRequestControllerTests {

	@Autowired
	private MockMvc						mockMvc;

	@MockBean
	private FootballClubService			footballClubService;

	@MockBean
	private CoachService				coachService;

	@MockBean
	private CoachTransferRequestService	coachTransferRequestService;

	@MockBean
	private ContractPlayerService		contractPlayerService;

	private static final int			TEST_MY_COACH_ID					= 97;
	private static final int			TEST_REQUESTED_COACH_ID				= 98;
	private static final int			TEST_MY_FOOTBALL_CLUB_ID			= 99;
	private static final int			TEST_REQUESTED_FOOTBALL_CLUB_ID		= 100;
	private static final int			TEST_MY_PRESIDENT_ID				= 101;
	private static final int			TEST_REQUESTED_PRESIDENT_ID			= 102;
	private static final int			TEST_MY_COACH_TRANSFER_REQUEST_ID	= 103;


	private President createPresident(final Integer presidentId, final String username, final String presidentName, final String phone) {

		User user = new User();

		user.setEnabled(true);
		user.setUsername(username);
		user.setPassword(username);

		President president = new President();

		president.setId(presidentId);
		president.setDni("11111111A");
		president.setFirstName(presidentName);
		president.setLastName(presidentName);
		president.setEmail(presidentName + "@gmail.com");
		president.setTelephone(phone);
		president.setUser(user);

		return president;
	}

	private FootballClub createFootballClub(final Integer clubId, final President president) {

		Calendar foundationDate = Calendar.getInstance();

		FootballClub fc = new FootballClub();

		fc.setId(clubId);
		fc.setCity("City");
		fc.setCrest("Crest");
		fc.setFans(44000);
		fc.setFoundationDate(foundationDate.getTime());
		fc.setMoney(150000000);
		fc.setName("Club name");
		fc.setStadium("Stadium name");
		fc.setStatus(true);
		fc.setPresident(president);

		return fc;
	}

	private Coach createCoach(final Integer coachId, final String coachName, final FootballClub club) {

		Calendar birthDate = Calendar.getInstance();

		User user = new User();

		user.setEnabled(true);
		user.setUsername(coachName);
		user.setPassword(coachName);

		Coach c = new Coach();

		c.setId(coachId);
		c.setFirstName(coachName);
		c.setLastName(coachName);
		c.setBirthDate(birthDate.getTime());
		c.setClause(100000);
		c.setClub(club);

		return c;
	}

	private CoachTransferRequest createCoachTransferRequest(final Integer ctrId, final RequestStatus rs, final Coach myCoach, final Coach requestedCoach) {

		CoachTransferRequest ctr = new CoachTransferRequest();

		ctr.setId(ctrId);
		ctr.setOffer(1000000L);
		ctr.setStatus(rs);
		ctr.setMyCoach(myCoach);
		ctr.setRequestedCoach(requestedCoach);

		return ctr;

	}

	@BeforeEach
	void setup() {

		President presi1 = this.createPresident(CoachTransferRequestControllerTests.TEST_MY_PRESIDENT_ID, "president1", "President1", "111111111");
		FootballClub club1 = this.createFootballClub(CoachTransferRequestControllerTests.TEST_MY_FOOTBALL_CLUB_ID, presi1);
		Coach myCoach = this.createCoach(CoachTransferRequestControllerTests.TEST_MY_COACH_ID, "Coach1", club1);

		President presi2 = this.createPresident(CoachTransferRequestControllerTests.TEST_REQUESTED_PRESIDENT_ID, "president2", "President2", "222222222");
		FootballClub club2 = this.createFootballClub(CoachTransferRequestControllerTests.TEST_REQUESTED_FOOTBALL_CLUB_ID, presi2);
		Coach requestedCoach = this.createCoach(CoachTransferRequestControllerTests.TEST_REQUESTED_COACH_ID, "Coach2", club2);

		CoachTransferRequest ctr = this.createCoachTransferRequest(CoachTransferRequestControllerTests.TEST_MY_COACH_TRANSFER_REQUEST_ID, RequestStatus.ON_HOLD, myCoach, requestedCoach);

		BDDMockito.given(this.coachService.findCoachById(CoachTransferRequestControllerTests.TEST_MY_COACH_ID)).willReturn(myCoach);
		BDDMockito.given(this.footballClubService.findFootballClubByPresident("president1")).willReturn(club1);
		BDDMockito.given(this.coachService.findCoachById(CoachTransferRequestControllerTests.TEST_REQUESTED_COACH_ID)).willReturn(requestedCoach);
		BDDMockito.given(this.footballClubService.findFootballClubByPresident("president2")).willReturn(club2);
		BDDMockito.given(this.coachTransferRequestService.findAllCoachTransferRequestbyPresident("president1")).willReturn(Lists.newArrayList(ctr));
		BDDMockito.given(this.coachTransferRequestService.findCoachTransferRequestById(CoachTransferRequestControllerTests.TEST_MY_COACH_TRANSFER_REQUEST_ID)).willReturn(ctr);
		BDDMockito.given(this.coachTransferRequestService.findAllCoachTransferRequestByRequestedCoachId(CoachTransferRequestControllerTests.TEST_MY_COACH_TRANSFER_REQUEST_ID)).willReturn(Lists.newArrayList());
		BDDMockito.given(this.coachService.findCoachByClubId(CoachTransferRequestControllerTests.TEST_MY_FOOTBALL_CLUB_ID)).willReturn(myCoach);
	}

	//

	@WithMockUser(username = "president1")
	@Test //CASO POSITIVO
	void testShowRequestTransferCoach() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/request/{coachId}", CoachTransferRequestControllerTests.TEST_REQUESTED_COACH_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("coachTransferRequest")).andExpect(MockMvcResultMatchers.view().name("coachTransferRequest/createOrUpdateCoachTransferRequestForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/coachTransferRequest/createOrUpdateCoachTransferRequestForm.jsp"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontShowRequestTransferCoach() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/request/{coachId}", CoachTransferRequestControllerTests.TEST_REQUESTED_COACH_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	//

	@WithMockUser(username = "president1")
	@Test //CASO POSITIVO
	void testProcessCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/transfers/coaches/request/{coachId}", CoachTransferRequestControllerTests.TEST_REQUESTED_COACH_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("offer", "1000000"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/transfers/coaches")).andExpect(MockMvcResultMatchers.redirectedUrl("/transfers/coaches"));
	}

	@WithMockUser(username = "president1")
	@Test //CASO NEGATIVO
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/transfers/coaches/request/{coachId}", CoachTransferRequestControllerTests.TEST_REQUESTED_COACH_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("offer", "").param("contractTime", ""))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors()).andExpect(MockMvcResultMatchers.model().attributeExists("coachTransferRequest"))
			.andExpect(MockMvcResultMatchers.view().name("coachTransferRequest/createOrUpdateCoachTransferRequestForm")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/coachTransferRequest/createOrUpdateCoachTransferRequestForm.jsp"));
	}

	//

	@WithMockUser(username = "president1")
	@Test //CASO POSITIVO
	void testShowPlayerRequestsSentList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/requests/sent")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("coachTransferRequests"))
			.andExpect(MockMvcResultMatchers.view().name("coachTransferRequest/coachTransferRequestSentList")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/coachTransferRequest/coachTransferRequestSentList.jsp"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontShowPlayerRequestsSentList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/requests/sent")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	//

	@WithMockUser(username = "president1")
	@Test //CASO POSITIVO
	void testDeleteTransferPlayerRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/requests/sent/delete/{requestId}", CoachTransferRequestControllerTests.TEST_MY_COACH_TRANSFER_REQUEST_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/transfers/coaches/requests/sent")).andExpect(MockMvcResultMatchers.redirectedUrl("/transfers/coaches/requests/sent"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontDeleteTransferPlayerRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/requests/sent/delete/{requestId}", CoachTransferRequestControllerTests.TEST_MY_COACH_TRANSFER_REQUEST_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	//

	@WithMockUser(username = "president1")
	@Test //CASO POSITIVO
	void testViewTransferPlayerReceivedList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/requests/received")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("coachTransferRequests"))
			.andExpect(MockMvcResultMatchers.view().name("coachTransferRequest/coachTransferRequestReceivedList")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/coachTransferRequest/coachTransferRequestReceivedList.jsp"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontViewTransferPlayerReceivedList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/players/requests/received")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	//

	@WithMockUser(username = "president2")
	@Test //CASO POSITIVO
	void testRejectTransferPlayerRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/requests/received/reject/{coachRequestId}", CoachTransferRequestControllerTests.TEST_MY_COACH_TRANSFER_REQUEST_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/transfers/coaches/requests/received")).andExpect(MockMvcResultMatchers.redirectedUrl("/transfers/coaches/requests/received"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontRejectTransferPlayerRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/requests/received/reject/{coachId}", CoachTransferRequestControllerTests.TEST_MY_COACH_TRANSFER_REQUEST_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	//

	@WithMockUser(username = "president2")
	@Test //CASO POSITIVO
	void testAcceptTransferPlayerRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/requests/received/accept/{playerId}", CoachTransferRequestControllerTests.TEST_MY_COACH_TRANSFER_REQUEST_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/transfers/coaches/requests/received")).andExpect(MockMvcResultMatchers.redirectedUrl("/transfers/coaches/requests/received"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testDontAcceptTransferPlayerRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/transfers/coaches/requests/received/accept/{playerId}", CoachTransferRequestControllerTests.TEST_MY_COACH_TRANSFER_REQUEST_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

}
