
package org.springframework.samples.petclinic.web.gonzalo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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
import org.springframework.samples.petclinic.model.ContractCommercial;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.ContractCommercialService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.web.ContractCommercialController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = ContractCommercialController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class ContractCommercialControllerTest {

	private static final int			TEST_FOOTBALL_CLUB_ID		= 1;
	private static final int			TEST_CONTRACT_COMMERCIAL_ID	= 1;

	@Autowired
	private MockMvc						mockMvc;

	@MockBean
	private FootballClubService			footballClubService;

	@MockBean
	private ContractCommercialService	contractCommercialService;

	private FootballClub				shinraInc;


	@BeforeEach
	void setup() {

		Date now = new Date(System.currentTimeMillis() - 1);

		User user = new User();
		user.setUsername("rufus");
		user.setPassword("shinra");
		user.setEnabled(true);

		President rufus = new President();
		rufus.setId(98);
		rufus.setFirstName("Rufus");
		rufus.setLastName("Shinra");
		rufus.setDni("12345678H");
		rufus.setEmail("rufus@shinra.com");
		rufus.setTelephone("608551023");
		rufus.setUser(user);

		this.shinraInc = new FootballClub();
		this.shinraInc.setName("Shinra Inc");
		this.shinraInc.setCity("Midgar");
		this.shinraInc.setStadium("Suburbios Stadium");
		this.shinraInc.setMoney(100000000);
		this.shinraInc.setFans(0);
		this.shinraInc.setFoundationDate(now);
		this.shinraInc.setStatus(true);
		this.shinraInc.setPresident(rufus);
		this.shinraInc.setId(ContractCommercialControllerTest.TEST_FOOTBALL_CLUB_ID);

		Date startContractDate = new GregorianCalendar(2013, Calendar.JANUARY, 1).getTime();
		Date endContractDate = new GregorianCalendar(2023, Calendar.JANUARY, 1).getTime();

		Collection<ContractCommercial> contratos = new ArrayList<>();
		ContractCommercial cc1 = new ContractCommercial();
		ContractCommercial cc2 = new ContractCommercial();

		cc1.setClause(100000);
		cc1.setMoney(10000);
		cc1.setStartDate(startContractDate);
		cc1.setEndDate(endContractDate);
		cc1.setPublicity("https://www.imagen.com.mx/assets/img/imagen_share.png");

		cc2.setClause(100000);
		cc2.setMoney(10000);
		cc2.setStartDate(startContractDate);
		cc2.setEndDate(endContractDate);
		cc2.setPublicity("https://www.imagen.com.mx/assets/img/imagen_share.png");

		contratos.add(cc1);
		contratos.add(cc2);

		BDDMockito.given(this.contractCommercialService.findAllCommercialContracts()).willReturn(contratos);
		BDDMockito.given(this.contractCommercialService.findContractCommercialById(1)).willReturn(cc1);
	}

	@WithMockUser(username = "rufus")
	@Test //CASO POSITIVO - LISTA DE CONTRATOS DE JUGADORES DE MI CLUB
	void testShowMyContractList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractsCommercial")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("contractsCommercial"))
			.andExpect(MockMvcResultMatchers.model().attribute("contractsCommercial", Matchers.hasSize(2))).andExpect(MockMvcResultMatchers.view().name("contracts/contractCommercialList"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/contracts/contractCommercialList.jsp"));
	}

	@Test //CASO NEGATIVO - LISTA DE CONTRATOS DE JUGADORES SIN SER PRESIDENTE
	void test_NOT_ShowMyContractListWithoutBeingPresident() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractsCommercial")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")
	@Test //CASO POSITIVO - VISTA DE CONTRATO DETALLADA
	void testShowContract() throws Exception {

		Date startContractDate = new GregorianCalendar(2013, Calendar.JANUARY, 1).getTime();
		Date endContractDate = new GregorianCalendar(2023, Calendar.JANUARY, 1).getTime();

		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractsCommercial/{contractCommercialId}", ContractCommercialControllerTest.TEST_CONTRACT_COMMERCIAL_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("contractCommercial")).andExpect(MockMvcResultMatchers.model().attribute("contractCommercial", Matchers.hasProperty("clause", Matchers.is(100000))))
			.andExpect(MockMvcResultMatchers.model().attribute("contractCommercial", Matchers.hasProperty("club", Matchers.nullValue())))
			.andExpect(MockMvcResultMatchers.model().attribute("contractCommercial", Matchers.hasProperty("endDate", Matchers.is(endContractDate))))
			.andExpect(MockMvcResultMatchers.model().attribute("contractCommercial", Matchers.hasProperty("startDate", Matchers.is(startContractDate))))
			.andExpect(MockMvcResultMatchers.model().attribute("contractCommercial", Matchers.hasProperty("publicity", Matchers.is("https://www.imagen.com.mx/assets/img/imagen_share.png"))))
			.andExpect(MockMvcResultMatchers.model().attribute("contractCommercial", Matchers.hasProperty("money", Matchers.is(10000)))).andExpect(MockMvcResultMatchers.view().name("contracts/contractCommercialDetails"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/contracts/contractCommercialDetails.jsp"));
	}

	@Test //CASO NEGATIVO - VISTA DE CONTRATO DETALLADA SIN SER PRESIDENTE
	void test_NOT_ShowContractWithoutBeingPresident() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractsCommercial/{contractCommercialId}", ContractCommercialControllerTest.TEST_CONTRACT_COMMERCIAL_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")
	@Test //CASO POSITIVO - AÑADIR CONTRATO A MI CLUB
	void testAddContractToMyClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractsCommercial/{contractCommercialId}/addToMyClub", ContractCommercialControllerTest.TEST_CONTRACT_COMMERCIAL_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/footballClubs/myClub/rufus"));
	}

	@Test //CASO NEGATIVO - AÑADIR CONTRATO A MI CLUB SIN SER PRESIDENTE
	void test_NOT_AddContractToMyClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractsCommercial/{contractCommercialId}/addToMyClub", ContractCommercialControllerTest.TEST_CONTRACT_COMMERCIAL_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")
	@Test //CASO POSITIVO - ELIMINAR CONTRATO DE MI CLUB
	void testRemoveContractFromMyClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractsCommercial/{contractCommercialId}/removeFromMyClub", ContractCommercialControllerTest.TEST_CONTRACT_COMMERCIAL_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/footballClubs/myClub/rufus"));
	}

	@Test //CASO NEGATIVO - ELIMINAR CONTRATO A MI CLUB SIN SER PRESIDENTE
	void test_NOT_removeContractFromMyClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractsCommercial/{contractCommercialId}/removeFromMyClub", ContractCommercialControllerTest.TEST_CONTRACT_COMMERCIAL_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

}
