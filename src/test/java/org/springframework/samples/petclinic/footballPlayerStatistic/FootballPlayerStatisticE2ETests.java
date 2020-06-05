
package org.springframework.samples.petclinic.footballPlayerStatistic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
public class FootballPlayerStatisticE2ETests {

	@Autowired
	private MockMvc mockMvc;


	@WithMockUser(username = "pedro", authorities = {
		"competitionAdmin"
	})
	@Test //CASO POSITIVO
	void viewFootballPlayerStatistic() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballPlayerStatistic/detail/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test //CASO NEGATIVO
	void dontViewFootballPlayerStatistic() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/footballPlayerStatistic/detail/1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

}
