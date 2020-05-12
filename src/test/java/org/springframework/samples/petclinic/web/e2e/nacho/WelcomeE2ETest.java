
package org.springframework.samples.petclinic.web.e2e.nacho;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
//@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
//@AutoConfigureTestDatabase(replace = Replace.NONE)
public class WelcomeE2ETest {

	@Autowired
	private MockMvc mockMvc;

	//	@WithMockUser(username = "manuel", authorities = {
	//		"authenticated"
	//	})
	//	@Test //CASO POSITIVO - CON FAN
	//	void testWelcomeFanSuccess() throws Exception {
	//
	//		this.mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("isVip")).andExpect(MockMvcResultMatchers.model().attributeExists("isFan"))
	//			.andExpect(MockMvcResultMatchers.model().attributeExists("club")).andExpect(MockMvcResultMatchers.model().attributeExists("lastMatches")).andExpect(MockMvcResultMatchers.model().attribute("isFan", true))
	//			.andExpect(MockMvcResultMatchers.view().name("welcome")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/welcome.jsp"));
	//	}


	//	@WithMockUser(username = "ignacio", authorities = {
	//		"authenticated"
	//	})
	//	@Test //CASO POSITIVO - SIN FAN
	//	void testWelcomeWithUserNoFanSuccess() throws Exception {
	//
	//		this.mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("isVip")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("isFan"))
	//			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("club")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("lastMatches")).andExpect(MockMvcResultMatchers.view().name("welcome"))
	//			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/welcome.jsp"));
	//	}

	@WithAnonymousUser()
	@Test //CASO POSITIVO - SIN FAN
	void testWelcomeWithoutUserSuccess() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("isVip")).andExpect(MockMvcResultMatchers.model().attributeExists("isFan"))
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("club")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("lastMatches")).andExpect(MockMvcResultMatchers.model().attribute("isFan", false))
			.andExpect(MockMvcResultMatchers.model().attribute("isVip", false)).andExpect(MockMvcResultMatchers.view().name("welcome")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/welcome.jsp"));
	}
}
