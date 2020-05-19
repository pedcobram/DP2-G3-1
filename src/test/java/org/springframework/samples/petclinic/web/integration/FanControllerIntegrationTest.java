
package org.springframework.samples.petclinic.web.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.web.FanController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FanControllerIntegrationTest {

	private static final int	TEST_CLUB_ID					= 1;

	private static final String	VIEWS_FAN_CREATE_OR_UPDATE_FORM	= "fan/createOrUpdateFanForm";
	//	@Autowired
	//	private AuthenticatedService	authenticatedService;
	//	@Autowired
	//	private FootballClubService		footballClubService;
	//	@Autowired
	//	private FanService				fanService;
	@Autowired
	private FanController		fanController;


	@WithMockUser(username = "manuel", authorities = {
		"authenticated"
	})
	@Test
	void testInitCreationFormSuccess() throws Exception {

		ModelMap model = new ModelMap();

		String view = this.fanController.initCreationForm(FanControllerIntegrationTest.TEST_CLUB_ID, model);

		Assertions.assertEquals(view, FanControllerIntegrationTest.VIEWS_FAN_CREATE_OR_UPDATE_FORM);
		Assertions.assertNotNull(model.get("fan"));
	}
	@WithMockUser(username = "ignacio", authorities = {
		"authenticated"
	})
	@Test
	void testInitCreationFormFail() throws Exception {

		ModelMap model = new ModelMap();

		String view = this.fanController.initCreationForm(FanControllerIntegrationTest.TEST_CLUB_ID, model);

		Assertions.assertEquals(view, "footballClubs/footballClubDetails");
		Assertions.assertNotNull(model.get("footballClub"));
		Assertions.assertNotNull(model.get("existFan"));
	}

}
