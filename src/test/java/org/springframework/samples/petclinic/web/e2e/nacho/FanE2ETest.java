
package org.springframework.samples.petclinic.web.e2e.nacho;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
//@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//@TestPropertySource(locations = "classpath:application-mysql.properties")

@Transactional
public class FanE2ETest {

	@Autowired
	private MockMvc			mockMvc;

	public static final int	TEST_ID	= 1;


	@WithMockUser(username = "ignacio", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO - LA CREACIÓN DE UN FAN NO VIP
	void testInitCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/fan/{clubId}/new", FanE2ETest.TEST_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("footballClubs/footballClubDetails"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO - LA CREACIÓN DE UN FAN NO VIP
	void testInitCreationFormErrorNotUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/fan/{clubId}/new", FanE2ETest.TEST_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "manuel", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO - LA CREACIÓN DE UN FAN VIP
	void testCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/fan/{clubId}/new", FanE2ETest.TEST_ID).param("creditCard.creditCardNumber", "7894561234568794").param("creditCard.expirationDate", "11/23").param("creditCard.cvv", "879")
			.with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO - LA CREACIÓN DE UN FAN VIP
	void testCreationFormErrorNotUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/fan/{clubId}/new", FanE2ETest.TEST_ID).param("creditCard.creditCardNumber", "7894561234568794").param("creditCard.expirationDate", "11/23").param("creditCard.cvv", "879")
			.with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}
	@WithMockUser(username = "manuel", authorities = {
		"authenticated"
	})
	@Test //CASO NEGATIVO - LA CREACION DE UN FAN  VIP
	void testCreationFormErrorCCNumber() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/fan/{clubId}/new", FanE2ETest.TEST_ID).param("creditCard.creditCardNumber", "568794").param("creditCard.expirationDate", "11/23").param("creditCard.cvv", "879")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("fan", "creditCard.creditCardNumber")).andExpect(MockMvcResultMatchers.view().name("fan/createOrUpdateFanForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/fan/createOrUpdateFanForm.jsp"));
	}
	@WithMockUser(username = "manuel", authorities = {
		"authenticated"
	})
	@Test //CASO NEGATIVO - LA CREACION DE UN FAN  VIP
	void testCreationFormErrorCCDate() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/fan/{clubId}/new", FanE2ETest.TEST_ID).param("creditCard.creditCardNumber", "7894561234568794").param("creditCard.expirationDate", "11/19").param("creditCard.cvv", "879")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("fan", "creditCard.expirationDate")).andExpect(MockMvcResultMatchers.view().name("fan/createOrUpdateFanForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/fan/createOrUpdateFanForm.jsp"));
	}
	@WithMockUser(username = "manuel", authorities = {
		"authenticated"
	})
	@Test //CASO NEGATIVO - LA CREACION DE UN FAN  VIP
	void testCreationFormErrorCCcvv() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/fan/{clubId}/new", FanE2ETest.TEST_ID).param("creditCard.creditCardNumber", "7894561234568794").param("creditCard.expirationDate", "11/23").param("creditCard.cvv", "8")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("fan", "creditCard.cvv")).andExpect(MockMvcResultMatchers.view().name("fan/createOrUpdateFanForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/fan/createOrUpdateFanForm.jsp"));
	}

	@WithMockUser(username = "ignacio", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO - LA CREACION DE UN FAN VIP
	void testInitUpdateFanFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/fan/noVip")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("fan/createOrUpdateFanForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/fan/createOrUpdateFanForm.jsp"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO - LA CREACION DE UN FAN VIP
	void testInitUpdateFanFormErrorNotUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/fan/noVip")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}
	@WithMockUser(username = "manuel", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO - LA EDICION DE UN FAN VIP
	void testUpdateFanFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/fan/noVip").param("clubId", "1").param("userId", "1").param("creditCard.creditCardNumber", "7894561234568794").param("creditCard.expirationDate", "11/23").param("creditCard.cvv", "879")
			.with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithAnonymousUser
	@Test //CASO NEGATIVO - LA EDICION DE UN FAN  VIP
	void testUpdateFanFormErrorNotUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/fan/noVip").param("clubId", "1").param("userId", "1").param("creditCard.creditCardNumber", "7894561234568794").param("creditCard.expirationDate", "11/23").param("creditCard.cvv", "879")
			.with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}
	@WithMockUser(username = "ignacio", authorities = {
		"authenticated"
	})
	@Test //CASO NEGATIVO - LA EDICION DE UN FAN  VIP
	void testUpdateFanFormErrorCCNumber() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/fan/noVip").param("clubId", "1").param("userId", "1").param("creditCard.creditCardNumber", "568794").param("creditCard.expirationDate", "11/23").param("creditCard.cvv", "879")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("fan", "creditCard.creditCardNumber")).andExpect(MockMvcResultMatchers.view().name("fan/createOrUpdateFanForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/fan/createOrUpdateFanForm.jsp"));
	}
	@WithMockUser(username = "ignacio", authorities = {
		"authenticated"
	})
	@Test //CASO NEGATIVO - LA EDICION DE UN FAN  VIP
	void testUpdateFanFormErrorCCDate() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/fan/noVip").param("clubId", "1").param("userId", "1").param("creditCard.creditCardNumber", "7894561234568794").param("creditCard.expirationDate", "11/19").param("creditCard.cvv", "879")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("fan", "creditCard.expirationDate")).andExpect(MockMvcResultMatchers.view().name("fan/createOrUpdateFanForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/fan/createOrUpdateFanForm.jsp"));
	}

	@WithMockUser(username = "ignacio", authorities = {
		"authenticated"
	})
	@Test //CASO NEGATIVO - LA EDICION DE UN FAN  VIP
	void testUpdateFanFormErrorCCCVV() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/fan/noVip").param("clubId", "1").param("userId", "1").param("creditCard.creditCardNumber", "7894561234568794").param("creditCard.expirationDate", "11/23").param("creditCard.cvv", "")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("fan", "creditCard.cvv")).andExpect(MockMvcResultMatchers.view().name("fan/createOrUpdateFanForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/fan/createOrUpdateFanForm.jsp"));
	}

	@WithMockUser(username = "ignacio", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO - BORRAR FAN
	void testDeleteFan() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/fan/delete")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO - BORRAR FAN
	void testDeleteFanErrorNotUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/fan/delete")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));

	}

}
