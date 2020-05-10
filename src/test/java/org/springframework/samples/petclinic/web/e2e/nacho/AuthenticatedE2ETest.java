
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

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
//@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
//@AutoConfigureTestDatabase(replace = Replace.ANY)
public class AuthenticatedE2ETest {

	@Autowired
	private MockMvc			mockMvc;

	public static final int	TEST_ID	= 1;


	@WithAnonymousUser
	@Test //CASO POSITIVO
	void testInitCreationFormSuccess() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/authenticateds/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("authenticated"))
			.andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp"));
	}

	@WithAnonymousUser
	@Test //CASO POSITIVO

	void testCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/authenticateds/new").param("firstName", "Auth").param("lastName", "Test").param("dni", "12345678T").param("email", "auth@test.com").param("telephone", "657063253").param("user.username", "auth1")
			.param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/authenticateds/" + 4));
	}
	@WithAnonymousUser
	@Test // CASO NEGATIVO
	void testCreationFormFirstNameError() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/authenticateds/new").param("firstName", "").param("lastName", "Test").param("dni", "12345678T").param("email", "auth@test.com").param("telephone", "657063253").param("user.username", "auth")
				.param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp")).andExpect(MockMvcResultMatchers.model().attributeExists("authenticated"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("authenticated", "firstName"));
	}
	@WithAnonymousUser
	@Test // CASO NEGATIVO
	void testCreationFormLastNameError() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/authenticateds/new").param("firstName", "Auth").param("lastName", "").param("dni", "12345678T").param("email", "auth@test.com").param("telephone", "657063253").param("user.username", "auth")
				.param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("authenticated")).andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("authenticated", "lastName"));
	}
	@WithAnonymousUser
	@Test // CASO NEGATIVO
	void testCreationFormDNIError() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/authenticateds/new").param("firstName", "Auth").param("lastName", "Test").param("dni", "123T").param("email", "auth@test.com").param("telephone", "657063253").param("user.username", "auth")
				.param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("authenticated")).andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("authenticated", "dni"));
	}
	@WithAnonymousUser
	@Test // CASO NEGATIVO
	void testCreationFormEmailError() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/authenticateds/new").param("firstName", "Auth").param("lastName", "Test").param("dni", "12345678T").param("email", "").param("telephone", "657063253").param("user.username", "auth")
				.param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("authenticated")).andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("authenticated", "email"));
	}
	@WithAnonymousUser
	@Test // CASO NEGATIVO
	void testCreationFormTelephoneError() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/authenticateds/new").param("firstName", "Auth").param("lastName", "Test").param("dni", "12345678T").param("email", "auth@test.com").param("telephone", "65ok253").param("user.username", "auth")
				.param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("authenticated")).andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("authenticated", "telephone"));
	}
	@WithAnonymousUser
	@Test // CASO NEGATIVO
	void testCreationFormPasswordError() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/authenticateds/new").param("firstName", "Auth").param("lastName", "Test").param("dni", "12345678T").param("email", "auth@test.com").param("telephone", "65ok253").param("user.username", "auth")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("authenticated")).andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp")).andExpect(MockMvcResultMatchers.model().attributeHasErrors("authenticated"));
	}

	@WithMockUser(username = "auth1", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO 
	void testInitFindFormSuccess() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/authenticateds/find")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("authenticated"))
			.andExpect(MockMvcResultMatchers.view().name("authenticateds/findAuthenticateds")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/findAuthenticateds.jsp"));
	}

	@WithMockUser(username = "auth1", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void testFindFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/authenticateds").param("lastName", "Test")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/authenticateds/1"));
	}
	@WithMockUser(username = "auth1", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void testFindFormEmptyLastnameSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/authenticateds").param("lastName", "")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("authenticateds/authenticatedsList"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/authenticatedsList.jsp"));
	}
	@WithMockUser(username = "auth1", authorities = {
		"authenticated"
	})
	@Test //CASO NEGATIVO-AUTH NO ENCONTRADO
	void testFindFormEmptyErrorNotFound() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/authenticateds").param("lastName", "Lopez")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("authenticated", "lastName"))
			.andExpect(MockMvcResultMatchers.view().name("authenticateds/findAuthenticateds")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/findAuthenticateds.jsp"));
	}

	@WithMockUser(username = "auth1", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void testInitUpdateAuthenticatedSuccess() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/myProfile/4/edit")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp"));
	}
	@WithMockUser(username = "ignacio", authorities = {
		"authenticated"
	})
	@Test //CASO NEGATIVO
	void testInitUpdateAuthenticatedAuthError() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/myProfile/4/edit")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("authenticated"))
			.andExpect(MockMvcResultMatchers.view().name("exceptions/exception"));
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void testInitUpdateAuthenticatedAutError() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/myProfile/4/edit")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(username = "ignacio", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void testUpdateFormSuccess() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/myProfile/{authenticatedId}/edit", AuthenticatedE2ETest.TEST_ID).param("firstName", "AuthQ").param("lastName", "Test").param("dni", "12345678T").param("email", "auth@test.com")
				.param("telephone", "657063253").param("user.username", "ignacio").param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/myProfile/ignacio"));
	}

	@WithMockUser(username = "auth1", authorities = {
		"authenticated"
	})
	@Test //CASO NEGATIVO
	void testUpdateFormFirstNameError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/myProfile/4/edit").param("firstName", "").param("lastName", "Test").param("dni", "12345678T").param("email", "auth@test.com").param("telephone", "657063253").param("user.username", "auth1")
			.param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithMockUser(username = "auth1", authorities = {
		"authenticated"
	})
	@Test //CASO NEGATIVO
	void testUpdateFormLastNameError() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/myProfile/4/edit").param("firstName", "AuthQ").param("lastName", "").param("dni", "12345678T").param("email", "auth@test.com").param("telephone", "657063253").param("user.username", "auth1")
				.param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("authenticated", "lastName")).andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp"));
	}
	@WithMockUser(username = "auth1", authorities = {
		"authenticated"
	})
	@Test //CASO NEGATIVO
	void testUpdateFormDNIError() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/myProfile/4/edit").param("firstName", "AuthQ").param("lastName", "TEst").param("dni", "128T").param("email", "auth@test.com").param("telephone", "657063253").param("user.username", "auth1")
				.param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp"));
	}
	@WithMockUser(username = "auth1", authorities = {
		"authenticated"
	})
	@Test //CASO NEGATIVO
	void testUpdateFormEmailError() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/myProfile/4/edit").param("firstName", "AuthQ").param("lastName", "TEst").param("dni", "12345678T").param("email", "").param("telephone", "657063253").param("user.username", "auth1")
				.param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("authenticated", "email")).andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp"));
	}
	@WithMockUser(username = "auth1", authorities = {
		"authenticated"
	})
	@Test //CASO NEGATIVO
	void testUpdateFormTelephoneError() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/myProfile/4/edit").param("firstName", "AuthQ").param("lastName", "TEst").param("dni", "12345678T").param("email", "auth@test.com").param("telephone", "65fyh53").param("user.username", "auth1")
				.param("user.password", "auth").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("authenticated", "telephone")).andExpect(MockMvcResultMatchers.view().name("authenticateds/createOrUpdateAuthenticatedForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/createOrUpdateAuthenticatedForm.jsp"));
	}

	@WithMockUser(username = "auth1", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void testShowAuthenticatedSuccess() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/authenticateds/4")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("authenticated"))
			.andExpect(MockMvcResultMatchers.view().name("authenticateds/authenticatedDetails")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/authenticatedDetails.jsp"));
	}
	@WithMockUser(username = "auth1", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void testShowAuthenticatedAuthError() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/authenticateds/{authenticatedId}", AuthenticatedE2ETest.TEST_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("authenticated"))
			.andExpect(MockMvcResultMatchers.view().name("exceptions/exception"));
	}
	@WithMockUser(username = "auth1", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void testShowAuthenticatedProfileSuccess() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/myProfile/auth1")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("authenticated"))
			.andExpect(MockMvcResultMatchers.view().name("authenticateds/authenticatedDetails")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/authenticateds/authenticatedDetails.jsp"));
	}

	@WithMockUser(username = "auth1", authorities = {
		"authenticated"
	})
	@Test //CASO POSITIVO
	void testShowAuthenticatedProfileAuthError() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/myProfile/ignacio")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("authenticated"))
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"));
	}

}
