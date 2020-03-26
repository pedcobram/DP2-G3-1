
package org.springframework.samples.petclinic.web.nacho;

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
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthenticatedService;
import org.springframework.samples.petclinic.web.AuthenticatedController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = AuthenticatedController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class AuthenticatedControllerTest {

	@Autowired
	private AuthenticatedController	authenticatedController;

	@Autowired
	private MockMvc					mockMvc;

	@MockBean
	private AuthenticatedService	authenticatedService;

	private Authenticated			au;
	private static final int		TEST_ID	= 1;


	@BeforeEach
	void setup() {
		User user = new User();

		user.setUsername("auth");
		user.setPassword("auth");
		user.setEnabled(true);

		this.au = new Authenticated();

		this.au.setId(AuthenticatedControllerTest.TEST_ID);
		this.au.setFirstName("Auth");
		this.au.setLastName("Test");
		this.au.setDni("12345678T");
		this.au.setEmail("auth@test.com");
		this.au.setTelephone("657063253");
		this.au.setUser(user);

		BDDMockito.given(this.authenticatedService.findAuthenticatedByUsername(user.getUsername())).willReturn(this.au);

	}
	@WithMockUser()
	@Test //CASO POSITIVO 
	void testInitCreationFormSuccess() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/authenticateds/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("authenticated"))
			.andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp"));
	}

}
