
package org.springframework.samples.petclinic.fan;

import java.sql.Date;

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
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.Fan;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthenticatedService;
import org.springframework.samples.petclinic.service.FanService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.web.FanController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = FanController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class FanControllerTest {

	@Autowired
	private MockMvc					mockMvc;

	@MockBean
	private AuthenticatedService	authenticatedService;

	@MockBean
	private FanService				fanService;

	@MockBean
	private FootballClubService		footballClubService;

	private Fan						f;
	private Authenticated			au;
	private FootballClub			club;
	private Authenticated			auNoFan;
	private static final int		TEST_ID	= 1;


	@BeforeEach
	void setup() {
		User user = new User();

		user.setUsername("fan");
		user.setPassword("fan");
		user.setEnabled(true);

		this.au = new Authenticated();

		this.au.setId(FanControllerTest.TEST_ID);
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
		rufus.setId(FanControllerTest.TEST_ID);
		rufus.setFirstName("Rufus");
		rufus.setLastName("Shinra");
		rufus.setDni("12345678H");
		rufus.setEmail("rufus@shinra.com");
		rufus.setTelephone("608551023");
		rufus.setUser(userP);

		this.club = new FootballClub();
		Date now = new Date(System.currentTimeMillis() - 1);
		this.club.setCrest("");
		this.club.setId(FanControllerTest.TEST_ID);
		this.club.setCity("Midgar");
		this.club.setFans(0);
		this.club.setFoundationDate(now);
		this.club.setMoney(100000000);
		this.club.setName("Shinra Inc");
		this.club.setPresident(rufus);
		this.club.setStadium("Suburbios Stadium");
		this.club.setStatus(true);

		this.f = new Fan();
		this.f.setId(FanControllerTest.TEST_ID);
		this.f.setUser(this.au);
		this.f.setClub(this.club);
		this.f.setVip(false);

		User userA = new User();

		userA.setUsername("auth");
		userA.setPassword("auth");
		userA.setEnabled(true);

		this.auNoFan = new Authenticated();

		this.auNoFan.setId(FanControllerTest.TEST_ID + 1);
		this.auNoFan.setFirstName("Auth");
		this.auNoFan.setLastName("Test");
		this.auNoFan.setDni("12345678T");
		this.auNoFan.setEmail("auth@test.com");
		this.auNoFan.setTelephone("657063253");
		this.auNoFan.setUser(userA);

		BDDMockito.given(this.fanService.findByUserId(this.auNoFan.getId())).willReturn(null);
		BDDMockito.given(this.fanService.findByUserId(FanControllerTest.TEST_ID)).willReturn(this.f);
		BDDMockito.given(this.authenticatedService.findAuthenticatedByUsername("fan")).willReturn(this.au);
		BDDMockito.given(this.authenticatedService.findAuthenticatedByUsername("auth")).willReturn(this.auNoFan);
		BDDMockito.given(this.footballClubService.findFootballClubById(FanControllerTest.TEST_ID)).willReturn(this.club);
	}

	@WithMockUser(username = "auth")
	@Test //CASO POSITIVO - LA CREACIÓN DE UN FAN NO VIP
	void testInitCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/fan/{clubId}/new", FanControllerTest.TEST_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("isNew"))
			.andExpect(MockMvcResultMatchers.model().attribute("isNew", true)).andExpect(MockMvcResultMatchers.model().attributeExists("fan")).andExpect(MockMvcResultMatchers.model().attribute("fan", Matchers.hasProperty("vip", Matchers.is(false))))
			.andExpect(MockMvcResultMatchers.model().attribute("fan", Matchers.hasProperty("creditCard", Matchers.nullValue()))).andExpect(MockMvcResultMatchers.model().attribute("fan", Matchers.hasProperty("user", Matchers.is(this.auNoFan))))
			.andExpect(MockMvcResultMatchers.model().attribute("fan", Matchers.hasProperty("club", Matchers.is(this.club)))).andExpect(MockMvcResultMatchers.view().name("fan/createOrUpdateFanForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/fan/createOrUpdateFanForm.jsp"));
	}
	@Test //CASO NEGATIVO - LA CREACIÓN DE UN FAN NO VIP
	void testInitCreationFormErrorNotUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/fan/{clubId}/new", FanControllerTest.TEST_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "fan")
	@Test //CASO POSITIVO - LA CREACIÓN DE UN FAN VIP
	void testCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/fan/{clubId}/new", FanControllerTest.TEST_ID).param("creditCard.creditCardNumber", "7894561234568794").param("creditCard.expirationDate", "11/23").param("creditCard.cvv", "879")
			.with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/"));
	}
	@Test //CASO NEGATIVO - LA CREACIÓN DE UN FAN VIP
	void testCreationFormErrorNotUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/fan/{clubId}/new", FanControllerTest.TEST_ID).param("creditCard.creditCardNumber", "7894561234568794").param("creditCard.expirationDate", "11/23").param("creditCard.cvv", "879")
			.with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	@WithMockUser(username = "fan")
	@Test //CASO NEGATIVO - LA CREACION DE UN FAN  VIP
	void testCreationFormErrorCCNumber() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/fan/{clubId}/new", FanControllerTest.TEST_ID).param("creditCard.creditCardNumber", "568794").param("creditCard.expirationDate", "11/23").param("creditCard.cvv", "879")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("fan", "creditCard.creditCardNumber")).andExpect(MockMvcResultMatchers.view().name("fan/createOrUpdateFanForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/fan/createOrUpdateFanForm.jsp"));
	}
	@WithMockUser(username = "fan")
	@Test //CASO NEGATIVO - LA CREACION DE UN FAN  VIP
	void testCreationFormErrorCCDate() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/fan/{clubId}/new", FanControllerTest.TEST_ID).param("creditCard.creditCardNumber", "7894561234568794").param("creditCard.expirationDate", "11/19").param("creditCard.cvv", "879")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("fan", "creditCard.expirationDate")).andExpect(MockMvcResultMatchers.view().name("fan/createOrUpdateFanForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/fan/createOrUpdateFanForm.jsp"));
	}
	@WithMockUser(username = "fan")
	@Test //CASO NEGATIVO - LA CREACION DE UN FAN  VIP
	void testCreationFormErrorCCcvv() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/fan/{clubId}/new", FanControllerTest.TEST_ID).param("creditCard.creditCardNumber", "7894561234568794").param("creditCard.expirationDate", "11/23").param("creditCard.cvv", "8")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("fan", "creditCard.cvv")).andExpect(MockMvcResultMatchers.view().name("fan/createOrUpdateFanForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/fan/createOrUpdateFanForm.jsp"));
	}
	@WithMockUser(username = "fan")
	@Test //CASO POSITIVO - LA CREACION DE UN FAN VIP
	void testInitUpdateFanFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/fan/noVip")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("isNew")).andExpect(MockMvcResultMatchers.model().attribute("isNew", false))
			.andExpect(MockMvcResultMatchers.model().attributeExists("fan")).andExpect(MockMvcResultMatchers.model().attribute("fan", Matchers.hasProperty("vip", Matchers.is(false))))
			.andExpect(MockMvcResultMatchers.model().attribute("fan", Matchers.hasProperty("creditCard", Matchers.nullValue()))).andExpect(MockMvcResultMatchers.model().attribute("fan", Matchers.hasProperty("user", Matchers.is(this.au))))
			.andExpect(MockMvcResultMatchers.model().attribute("fan", Matchers.hasProperty("club", Matchers.is(this.club)))).andExpect(MockMvcResultMatchers.model().attribute("fan", this.f))
			.andExpect(MockMvcResultMatchers.view().name("fan/createOrUpdateFanForm")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/fan/createOrUpdateFanForm.jsp"));
	}
	@Test //CASO NEGATIVO - LA CREACION DE UN FAN VIP
	void testInitUpdateFanFormErrorNotUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/fan/noVip")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	@WithMockUser(username = "fan")
	@Test //CASO POSITIVO - LA EDICION DE UN FAN VIP
	void testUpdateFanFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/fan/noVip").param("clubId", "1").param("userId", "1").param("creditCard.creditCardNumber", "7894561234568794").param("creditCard.expirationDate", "11/23").param("creditCard.cvv", "879")
			.with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/"));
	}

	@Test //CASO NEGATIVO - LA EDICION DE UN FAN  VIP
	void testUpdateFanFormErrorNotUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/fan/noVip").param("clubId", "1").param("userId", "1").param("creditCard.creditCardNumber", "7894561234568794").param("creditCard.expirationDate", "11/23").param("creditCard.cvv", "879")
			.with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	@WithMockUser(username = "fan")
	@Test //CASO NEGATIVO - LA EDICION DE UN FAN  VIP
	void testUpdateFanFormErrorCCNumber() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/fan/noVip").param("clubId", "1").param("userId", "1").param("creditCard.creditCardNumber", "568794").param("creditCard.expirationDate", "11/23").param("creditCard.cvv", "879")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("fan", "creditCard.creditCardNumber")).andExpect(MockMvcResultMatchers.view().name("fan/createOrUpdateFanForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/fan/createOrUpdateFanForm.jsp"));
	}
	@WithMockUser(username = "fan")
	@Test //CASO NEGATIVO - LA EDICION DE UN FAN  VIP
	void testUpdateFanFormErrorCCDate() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/fan/noVip").param("clubId", "1").param("userId", "1").param("creditCard.creditCardNumber", "7894561234568794").param("creditCard.expirationDate", "11/19").param("creditCard.cvv", "879")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("fan", "creditCard.expirationDate")).andExpect(MockMvcResultMatchers.view().name("fan/createOrUpdateFanForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/fan/createOrUpdateFanForm.jsp"));
	}

	@WithMockUser(username = "fan")
	@Test //CASO NEGATIVO - LA EDICION DE UN FAN  VIP
	void testUpdateFanFormErrorCCCVV() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/fan/noVip").param("clubId", "1").param("userId", "1").param("creditCard.creditCardNumber", "7894561234568794").param("creditCard.expirationDate", "11/23").param("creditCard.cvv", "")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("fan", "creditCard.cvv")).andExpect(MockMvcResultMatchers.view().name("fan/createOrUpdateFanForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/fan/createOrUpdateFanForm.jsp"));
	}
	@WithMockUser(username = "fan")
	@Test //CASO POSITIVO - BORRAR FAN
	void testDeleteFan() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/fan/delete")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/"));
	}
	@Test //CASO NEGATIVO - BORRAR FAN
	void testDeleteFanErrorNotUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/fan/delete")).andExpect(MockMvcResultMatchers.status().is4xxClientError());

	}

}
