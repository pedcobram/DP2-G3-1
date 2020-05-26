
package org.springframework.samples.petclinic.web.integration;

import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.datatypes.CreditCard;
import org.springframework.samples.petclinic.model.Fan;
import org.springframework.samples.petclinic.service.FanService;
import org.springframework.samples.petclinic.web.FanController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class FanControllerIntegrationTest {

	private static final int	TEST_CLUB_ID					= 1;

	private static final String	VIEWS_FAN_CREATE_OR_UPDATE_FORM	= "fan/createOrUpdateFanForm";
	//	@Autowired
	//	private AuthenticatedService	authenticatedService;
	//	@Autowired
	//	private FootballClubService		footballClubService;
	@Autowired
	private FanService			fanService;
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

	@WithMockUser(username = "ignacio", authorities = {
		"authenticated"
	})
	@Test
	void testProcessCreationFormSuccess() throws Exception {

		ModelMap model = new ModelMap();
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		Fan f = this.fanService.findByUserId(1);
		CreditCard cc = new CreditCard();
		cc.setCreditCardNumber("7894561234568794");
		cc.setExpirationDate("11/23");
		cc.setCvv("879");
		f.setCreditCard(cc);
		String view = this.fanController.processCreationForm(FanControllerIntegrationTest.TEST_CLUB_ID, f, bindingResult, model);

		Assertions.assertEquals(view, "redirect:/");

	}
	@WithMockUser(username = "ignacio", authorities = {
		"authenticated"
	})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {

		ModelMap model = new ModelMap();
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		Fan f = this.fanService.findByUserId(1);
		CreditCard cc = new CreditCard();
		cc.setCreditCardNumber("7");
		cc.setExpirationDate("11/23");
		cc.setCvv("879");
		f.setCreditCard(cc);
		String view = this.fanController.processCreationForm(FanControllerIntegrationTest.TEST_CLUB_ID, f, bindingResult, model);

		Assertions.assertEquals(view, FanControllerIntegrationTest.VIEWS_FAN_CREATE_OR_UPDATE_FORM);
		Assertions.assertNotNull(model.get("isNew"));
		Assertions.assertNotNull(model.get("fan"));

	}
	//	@WithMockUser(username = "ignacio", authorities = {
	//		"authenticated"
	//	})
	//	@Test
	//	void testProcessCreationFormFail() throws Exception {
	//
	//		ModelMap model = new ModelMap();
	//		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
	//		Fan f = this.fanService.findByUserId(1);
	//		CreditCard cc = new CreditCard();
	//		cc.setCreditCardNumber("7894561234568794");
	//		cc.setExpirationDate("11/23");
	//		cc.setCvv("879");
	//		f.setCreditCard(cc);
	//		String view = this.fanController.processCreationForm(FanControllerIntegrationTest.TEST_CLUB_ID, f, bindingResult, model);
	//		String view1 = this.fanController.processCreationForm(FanControllerIntegrationTest.TEST_CLUB_ID, f, bindingResult, model);
	//
	//		Assertions.assertEquals(view1, "footballClubs/footballClubDetails");
	//		Assertions.assertNotNull(model.get("footballClub"));
	//		Assertions.assertNotNull(model.get("existFan"));
	//
	//	}

	@WithMockUser(username = "ignacio", authorities = {
		"authenticated"
	})
	@Test
	void testInitUpdateFanFormSuccess() throws Exception {

		ModelMap model = new ModelMap();

		String view = this.fanController.initUpdateFanForm(model);

		Assertions.assertEquals(view, FanControllerIntegrationTest.VIEWS_FAN_CREATE_OR_UPDATE_FORM);
		Assertions.assertNotNull(model.get("fan"));
	}

	@WithMockUser(username = "ignacio", authorities = {
		"authenticated"
	})
	@Test
	void testProcessUpdateFormSuccess() throws Exception {

		ModelMap model = new ModelMap();
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		Fan f = this.fanService.findByUserId(1);
		CreditCard cc = new CreditCard();
		cc.setCreditCardNumber("7894561234568794");
		cc.setExpirationDate("11/23");
		cc.setCvv("879");
		f.setCreditCard(cc);
		String view = this.fanController.processUpdateFanForm(f, FanControllerIntegrationTest.TEST_CLUB_ID, FanControllerIntegrationTest.TEST_CLUB_ID, bindingResult, model);

		Assertions.assertEquals(view, "redirect:/");

	}
	@WithMockUser(username = "ignacio", authorities = {
		"authenticated"
	})
	@Test
	void testProcessUpdateFormHasErrors() throws Exception {

		ModelMap model = new ModelMap();
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		Fan f = this.fanService.findByUserId(1);
		CreditCard cc = new CreditCard();
		cc.setCreditCardNumber("7");
		cc.setExpirationDate("11/23");
		cc.setCvv("879");
		f.setCreditCard(cc);
		String view = this.fanController.processUpdateFanForm(f, FanControllerIntegrationTest.TEST_CLUB_ID, FanControllerIntegrationTest.TEST_CLUB_ID, bindingResult, model);

		Assertions.assertEquals(view, FanControllerIntegrationTest.VIEWS_FAN_CREATE_OR_UPDATE_FORM);
		Assertions.assertNotNull(model.get("isNew"));
		Assertions.assertNotNull(model.get("fan"));

	}
	@WithMockUser(username = "ignacio", authorities = {
		"authenticated"
	})
	@Test
	void testProcessDeleteSuccess() throws Exception {

		String view = this.fanController.processDeleteForm();

		Assertions.assertEquals(view, "redirect:/");

	}

}
