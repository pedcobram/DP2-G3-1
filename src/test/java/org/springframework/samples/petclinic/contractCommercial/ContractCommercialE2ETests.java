
package org.springframework.samples.petclinic.contractCommercial;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
//@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
//@AutoConfigureTestDatabase(replace = Replace.ANY)
public class ContractCommercialE2ETests {

	@Autowired
	private MockMvc mockMvc;


	@WithMockUser(username = "gonzalo", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void showContractsList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractsCommercial")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test //CASO NEGATIVO
	@WithAnonymousUser
	void dontShowContractsList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractsCommercial")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "gonzalo", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void showContract() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractsCommercial/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test //CASO POSITIVO
	@WithAnonymousUser
	void dontShowContract() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractsCommercial/1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "gonzalo", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void addContractToMyClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractsCommercial/1/addToMyClub")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("/footballClubs/myClub/gonzalo"));
	}

	@Test //CASO NEGATIVO
	@WithAnonymousUser
	void dontAddContractToMyClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractsCommercial/1/addToMyClub")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "gonzalo", authorities = {
		"president"
	})
	@Test //CASO POSITIVO
	void deleteContractFromMyClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractsCommercial/1/removeFromMyClub")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test //CASO NEGATIVO
	@WithAnonymousUser
	void dontDeleteContractFromMyClub() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/contractsCommercial/1/removeFromMyClub")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

}
