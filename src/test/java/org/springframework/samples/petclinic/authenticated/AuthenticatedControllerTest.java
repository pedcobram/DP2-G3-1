
package org.springframework.samples.petclinic.authenticated;

import java.util.ArrayList;
import java.util.Collection;

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
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = AuthenticatedController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class AuthenticatedControllerTest {

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
		Collection<Authenticated> results = new ArrayList<Authenticated>();
		results.add(this.au);
		results.add(new Authenticated());
		Collection<Authenticated> result = new ArrayList<Authenticated>();
		result.add(this.au);
		BDDMockito.given(this.authenticatedService.findAuthenticatedByLastName("")).willReturn(results);
		BDDMockito.given(this.authenticatedService.findAuthenticatedByLastName(this.au.getLastName())).willReturn(result);
		BDDMockito.given(this.authenticatedService.findAuthenticatedByUsername(this.au.getUser().getUsername())).willReturn(this.au);
		BDDMockito.given(this.authenticatedService.findAuthenticatedById(this.au.getId())).willReturn(this.au);

	}
	@WithMockUser()
	@Test //CASO POSITIVO 
	void testInitCreationFormSuccess() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/authenticateds/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("authenticated"))
			.andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp"));
	}

	@WithMockUser()
	@Test //CASO POSITIVO

	void testCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/authenticateds/new").param("firstName", "Auth").param("lastName", "Test").param("dni", "12345678T").param("email", "auth@test.com").param("telephone", "657063253").param("user.username", "auth")
			.param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/authenticateds/" + null));
	}
	@WithMockUser()
	@Test // CASO NEGATIVO
	void testCreationFormFirstNameError() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/authenticateds/new").param("firstName", "").param("lastName", "Test").param("dni", "12345678T").param("email", "auth@test.com").param("telephone", "657063253").param("user.username", "auth")
				.param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp")).andExpect(MockMvcResultMatchers.model().attributeExists("authenticated"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("authenticated", "firstName"));
	}
	@WithMockUser()
	@Test // CASO NEGATIVO
	void testCreationFormLastNameError() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/authenticateds/new").param("firstName", "Auth").param("lastName", "").param("dni", "12345678T").param("email", "auth@test.com").param("telephone", "657063253").param("user.username", "auth")
				.param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("authenticated")).andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("authenticated", "lastName"));
	}
	@WithMockUser()
	@Test // CASO NEGATIVO
	void testCreationFormDNIError() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/authenticateds/new").param("firstName", "Auth").param("lastName", "Test").param("dni", "123T").param("email", "auth@test.com").param("telephone", "657063253").param("user.username", "auth")
				.param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("authenticated")).andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("authenticated", "dni"));
	}
	@WithMockUser()
	@Test // CASO NEGATIVO
	void testCreationFormEmailError() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/authenticateds/new").param("firstName", "Auth").param("lastName", "Test").param("dni", "12345678T").param("email", "").param("telephone", "657063253").param("user.username", "auth")
				.param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("authenticated")).andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("authenticated", "email"));
	}
	@WithMockUser()
	@Test // CASO NEGATIVO
	void testCreationFormTelephoneError() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/authenticateds/new").param("firstName", "Auth").param("lastName", "Test").param("dni", "12345678T").param("email", "auth@test.com").param("telephone", "65ok253").param("user.username", "auth")
				.param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("authenticated")).andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("authenticated", "telephone"));
	}
	@WithMockUser()
	@Test // CASO NEGATIVO
	void testCreationFormPasswordError() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/authenticateds/new").param("firstName", "Auth").param("lastName", "Test").param("dni", "12345678T").param("email", "auth@test.com").param("telephone", "65ok253").param("user.username", "auth")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("authenticated")).andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp")).andExpect(MockMvcResultMatchers.model().attributeHasErrors("authenticated"));
	}

	@WithMockUser()
	@Test //CASO POSITIVO 
	void testInitFindFormSuccess() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/authenticateds/find")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("authenticated"))
			.andExpect(MockMvcResultMatchers.view().name("authenticateds/findAuthenticateds")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/findAuthenticateds.jsp"));
	}

	@WithMockUser()
	@Test //CASO POSITIVO
	void testFindFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/authenticateds").param("lastName", "Test")).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/authenticateds/" + AuthenticatedControllerTest.TEST_ID));
	}
	@WithMockUser()
	@Test //CASO POSITIVO
	void testFindFormEmptyLastnameSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/authenticateds").param("lastName", "")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("authenticateds/authenticatedsList"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/authenticatedsList.jsp"));
	}
	@WithMockUser()
	@Test //CASO NEGATIVO-AUTH NO ENCONTRADO
	void testFindFormEmptyErrorNotFound() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/authenticateds").param("lastName", "Lopez")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("authenticated", "lastName"))
			.andExpect(MockMvcResultMatchers.view().name("authenticateds/findAuthenticateds")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/findAuthenticateds.jsp"));
	}

	@WithMockUser(username = "auth")
	@Test //CASO POSITIVO 
	void testInitUpdateAuthenticatedSuccess() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/myProfile/{authenticatedId}/edit", AuthenticatedControllerTest.TEST_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("authenticated"))
			.andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp"));
	}
	@WithMockUser(username = "ignacio")
	@Test //CASO NEGATIVO
	void testInitUpdateAuthenticatedAuthError() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/myProfile/{authenticatedId}/edit", AuthenticatedControllerTest.TEST_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("authenticated")).andExpect(MockMvcResultMatchers.view().name("exceptions/exception"));
	}
	@WithMockUser()
	@Test //CASO NEGATIVO
	void testInitUpdateAuthenticatedAutError() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/myProfile/{authenticatedId}/edit", AuthenticatedControllerTest.TEST_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("authenticated")).andExpect(MockMvcResultMatchers.view().name("exceptions/exception"));
	}

	@WithMockUser(username = "auth")
	@Test //CASO POSITIVO
	void testUpdateFormSuccess() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/myProfile/{authenticatedId}/edit", AuthenticatedControllerTest.TEST_ID).param("firstName", "AuthQ").param("lastName", "Test").param("dni", "12345678T").param("email", "auth@test.com")
				.param("telephone", "657063253").param("user.username", "auth").param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/myProfile/" + this.au.getUser().getUsername()));
	}
	@WithMockUser(username = "ignacio")
	@Test //CASO NEGATIVO
	void testUpdateFormAuthError() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/myProfile/{authenticatedId}/edit", AuthenticatedControllerTest.TEST_ID).param("firstName", "AuthQ").param("lastName", "Test").param("dni", "12345678T").param("email", "auth@test.com")
				.param("telephone", "657063253").param("user.username", "auth").param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("exceptions/exception"));
	}
	@WithMockUser(username = "auth")
	@Test //CASO NEGATIVO
	void testUpdateFormFirstNameError() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/myProfile/{authenticatedId}/edit", AuthenticatedControllerTest.TEST_ID).param("firstName", "").param("lastName", "Test").param("dni", "12345678T").param("email", "auth@test.com")
				.param("telephone", "657063253").param("user.username", "auth").param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("authenticated", "firstName")).andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp"));
	}
	@WithMockUser(username = "auth")
	@Test //CASO NEGATIVO
	void testUpdateFormLastNameError() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/myProfile/{authenticatedId}/edit", AuthenticatedControllerTest.TEST_ID).param("firstName", "AuthQ").param("lastName", "").param("dni", "12345678T").param("email", "auth@test.com")
				.param("telephone", "657063253").param("user.username", "auth").param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("authenticated", "lastName")).andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp"));
	}
	@WithMockUser(username = "auth")
	@Test //CASO NEGATIVO
	void testUpdateFormDNIError() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/myProfile/{authenticatedId}/edit", AuthenticatedControllerTest.TEST_ID).param("firstName", "AuthQ").param("lastName", "TEst").param("dni", "128T").param("email", "auth@test.com")
				.param("telephone", "657063253").param("user.username", "auth").param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("authenticated", "dni")).andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp"));
	}
	@WithMockUser(username = "auth")
	@Test //CASO NEGATIVO
	void testUpdateFormEmailError() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/myProfile/{authenticatedId}/edit", AuthenticatedControllerTest.TEST_ID).param("firstName", "AuthQ").param("lastName", "TEst").param("dni", "12345678T").param("email", "").param("telephone", "657063253")
				.param("user.username", "auth").param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("authenticated", "email")).andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp"));
	}
	@WithMockUser(username = "auth")
	@Test //CASO NEGATIVO
	void testUpdateFormTelephoneError() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/myProfile/{authenticatedId}/edit", AuthenticatedControllerTest.TEST_ID).param("firstName", "AuthQ").param("lastName", "TEst").param("dni", "12345678T").param("email", "auth@test.com")
				.param("telephone", "65fyh53").param("user.username", "auth").param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("authenticated", "telephone")).andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp"));
	}

	@WithMockUser(username = "auth")
	@Test //CASO POSITIVO 
	void testShowAuthenticatedSuccess() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/authenticateds/{authenticatedId}", AuthenticatedControllerTest.TEST_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("authenticated"))
			.andExpect(MockMvcResultMatchers.view().name("authenticateds/authenticatedDetails")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/authenticatedDetails.jsp"));
	}
	@WithMockUser(username = "ignacio")
	@Test //CASO POSITIVO 
	void testShowAuthenticatedAuthError() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/authenticateds/{authenticatedId}", AuthenticatedControllerTest.TEST_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("authenticated")).andExpect(MockMvcResultMatchers.view().name("exceptions/exception"));
	}
	@WithMockUser(username = "auth")
	@Test //CASO POSITIVO 
	void testShowAuthenticatedProfileSuccess() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/myProfile/{authenticatedUsername}", this.au.getUser().getUsername())).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("authenticated"))
			.andExpect(MockMvcResultMatchers.view().name("authenticateds/authenticatedDetails")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/authenticatedDetails.jsp"));
	}
	@WithMockUser(username = "ignacio")
	@Test //CASO POSITIVO 
	void testShowAuthenticatedProfileAuthError() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/myProfile/{authenticatedUsername}", this.au.getUser().getUsername())).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("authenticated"))
			.andExpect(MockMvcResultMatchers.view().name("exceptions/exception"));
	}

}
