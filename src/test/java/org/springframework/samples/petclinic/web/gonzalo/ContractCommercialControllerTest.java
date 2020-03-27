
package org.springframework.samples.petclinic.web.gonzalo;

import java.util.Date;

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
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.web.ContractCommercialController;
import org.springframework.samples.petclinic.web.FootballClubController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = ContractCommercialController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class ContractCommercialControllerTest {

	private static final int		TEST_FOOTBALL_CLUB_ID	= 1;

	@Autowired
	private FootballClubController	footballClubController;

	@Autowired
	private MockMvc					mockMvc;

	@MockBean
	private FootballClubService		footballClubService;

	private FootballClub			footballClub;


	@BeforeEach
	void setup() {

		User user = new User();
		user.setUsername("rufus");
		user.setPassword("shinra");
		user.setEnabled(true);

		President rufus = new President();
		rufus.setId(99);
		rufus.setFirstName("Rufus");
		rufus.setLastName("Shinra");
		rufus.setDni("12345678H");
		rufus.setEmail("rufus@shinra.com");
		rufus.setTelephone("608551023");
		rufus.setUser(user);

		Date now = new Date(System.currentTimeMillis() - 1);
		this.footballClub = new FootballClub();
		this.footballClub.setCity("Midgar");
		this.footballClub.setFans(0);
		this.footballClub.setFoundationDate(now);
		this.footballClub.setMoney(100000000);
		this.footballClub.setName("Shinra Inc");
		this.footballClub.setPresident(rufus);
		this.footballClub.setStadium("Suburbios Stadium");
		this.footballClub.setStatus(false);
		this.footballClub.setId(ContractCommercialControllerTest.TEST_FOOTBALL_CLUB_ID);
		BDDMockito.given(this.footballClubService.findFootballClubByPresident("rufus")).willReturn(this.footballClub);
		BDDMockito.given(this.footballClubService.findFootballClubById(ContractCommercialControllerTest.TEST_FOOTBALL_CLUB_ID)).willReturn(this.footballClub);

	}

	@WithMockUser(username = "rufus")
	@Test //CASO POSITIVO - GET - EDITAR CLUB
	void testInitUpdateForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/{principalUsername}/edit", "rufus")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("footballClub"))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("name", Matchers.is("Shinra Inc"))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("president", Matchers.is(this.footballClub.getPresident()))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("foundationDate", Matchers.is(this.footballClub.getFoundationDate()))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("city", Matchers.is("Midgar"))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("stadium", Matchers.is("Suburbios Stadium"))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("money", Matchers.is(100000000)))).andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("fans", Matchers.is(0))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("status", Matchers.is(false)))).andExpect(MockMvcResultMatchers.view().name("footballClubs/createOrUpdateFootballClubForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/createOrUpdateFootballClubForm.jsp"));
	}

	@Test //CASO NEGATIVO - GET - EDITAR CLUB
	void testInitUpdateFormError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/{principalUsername}/edit", "rufus")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")
	@Test //CASO POSITIVO - POST - EDITAR CLUB
	void testProcessUpdateFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/footballClubs/myClub/{principalUsername}/edit", "rufus").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Shinra Inc").param("city", "Midgar")
			.param("crest", "https://www.example.com").param("stadium", "Suburbios Stadium").param("foundationDate", "1997/01/01")).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/footballClubs/myClub/rufus"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO NEGATIVO - POST - EDITAR CLUB
	void testProcessUpdateFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/footballClubs/myClub/{principalUsername}/edit", "rufus").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Shinra Inc").param("crest", "https://www.example.com")
				.param("stadium", "Suburbios Stadium").param("foundationDate", "1997/01/01"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("footballClub")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("footballClubs/createOrUpdateFootballClubForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/createOrUpdateFootballClubForm.jsp"));
	}

	@WithMockUser(username = "rufus")
	@Test //CASO POSITIVO - LISTA DE CONTRATOS COMERCIALES
	void testShowContractCommercialListHtml() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractsCommercial")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("contractsCommercial"))
			.andExpect(MockMvcResultMatchers.view().name("contracts/contractCommercialList")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/contracts/contractCommercialList.jsp"));
	}

	@Test //CASO NEGATIVO - LISTA DE CONTRATOS COMERCIALES
	void testShowContractCommercialListHtmlError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - BORRAR EQUIPO
	void testDeleteFootballClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/{principalUsername}/delete", "rufus")).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/footballClubs/myClub/rufus"));
	}

	@Test //CASO NEGATIVO - BORRAR EQUIPO
	void testDeleteFootballClubError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/myClub/{principalUsername}/delete", "rufus")).andExpect(MockMvcResultMatchers.status().is4xxClientError());

	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - VER EQUIPO DETALLADAMENTE POR USERNAME
	void testShowFootballClubByUsername() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list/{principalUsername}", "rufus")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("name", Matchers.is("Shinra Inc"))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("president", Matchers.is(this.footballClub.getPresident()))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("foundationDate", Matchers.is(this.footballClub.getFoundationDate()))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("city", Matchers.is("Midgar"))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("stadium", Matchers.is("Suburbios Stadium"))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("money", Matchers.is(100000000)))).andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("fans", Matchers.is(0))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("status", Matchers.is(false)))).andExpect(MockMvcResultMatchers.view().name("footballClubs/footballClubDetails"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/myClubDetails.jsp"));
	}

	@Test //CASO NEGATIVO - VER EQUIPO DETALLADAMENTE POR USERNAME
	void testShowFootballClubByUsernameError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list/{principalUsername}", "rufus")).andExpect(MockMvcResultMatchers.status().is4xxClientError());

	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - VER EQUIPO DETALLADAMENTE POR ID
	void testShowFootballClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list/{footballClubId}", ContractCommercialControllerTest.TEST_FOOTBALL_CLUB_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("name", Matchers.is("Shinra Inc"))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("president", Matchers.is(this.footballClub.getPresident()))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("foundationDate", Matchers.is(this.footballClub.getFoundationDate()))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("city", Matchers.is("Midgar"))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("stadium", Matchers.is("Suburbios Stadium"))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("money", Matchers.is(100000000)))).andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("fans", Matchers.is(0))))
			.andExpect(MockMvcResultMatchers.model().attribute("footballClub", Matchers.hasProperty("status", Matchers.is(false)))).andExpect(MockMvcResultMatchers.view().name("footballClubs/footballClubDetails"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/footballClubs/footballClubDetails.jsp"));
	}

	@Test //CASO NEGATIVO - VER EQUIPO DETALLADAMENTE POR ID
	void testShowFootballClubError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballClubs/list/{footballClubId}", ContractCommercialControllerTest.TEST_FOOTBALL_CLUB_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

}
