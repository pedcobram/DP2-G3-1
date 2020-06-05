
package org.springframework.samples.petclinic.referee;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.CredentialException;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Referee;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthenticatedService;
import org.springframework.samples.petclinic.service.MatchRefereeRequestService;
import org.springframework.samples.petclinic.service.RefereeService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
//@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RefereeServiceTests {

	@Autowired
	protected RefereeService				refereeService;

	@Autowired
	protected MatchRefereeRequestService	matchRefereeRequestService;

	@Autowired
	protected AuthenticatedService			authenticatedService;


	@Test //CASO POSITIVO
	void shouldFindRefereetById() {

		Boolean res = true;

		Referee r = this.refereeService.findRefereeById(1);

		if (r == null) {
			res = false;
		}

		Assertions.assertTrue(res);
	}

	@Test //CASO NEGATIVO
	void shouldNotFindRefereetById() {

		Boolean res = true;

		Referee r = this.refereeService.findRefereeById(100);

		if (r == null) {
			res = false;
		}

		Assertions.assertFalse(res);
	}

	@Test //CASO POSITIVO
	void shouldFindRefereeByUsername() {

		Boolean res = true;

		Referee r = this.refereeService.findRefereeByUsername("referee1");

		if (r == null) {
			res = false;
		}

		Assertions.assertTrue(res);
	}

	@Test //CASO NEGATIVO
	void shouldNotFindRefereeByUsername() {

		Boolean res = true;

		Referee r = this.refereeService.findRefereeByUsername("referee100");

		if (r == null) {
			res = false;
		}

		Assertions.assertFalse(res);
	}

	@Test //CASO POSITIVO
	void shouldFindAllReferees() {

		Boolean res = true;

		List<Referee> rs = new ArrayList<>();

		rs.addAll(this.refereeService.findAllReferees());

		for (Referee r : rs) {
			if (r == null) {
				res = false;
				break;
			}
		}

		Assertions.assertTrue(res);
	}

	@Test //CASO POSITIVO
	void shouldDeleteReferee() throws DataAccessException, CredentialException {

		User user = new User();

		user.setEnabled(true);
		user.setPassword("password");
		user.setUsername("password");

		Referee ref = new Referee();

		ref.setFirstName("First Name");
		ref.setLastName("Last Name");
		ref.setDni("12345678A");
		ref.setEmail("test@gmail.com");
		ref.setTelephone("123456789");
		ref.setUser(user);

		this.refereeService.saveReferee(ref);

		int pre_delete = this.refereeService.count();
		Assertions.assertTrue(pre_delete == 3);

		this.refereeService.deleteReferee(ref);

		int post_delete = this.refereeService.count();
		Assertions.assertTrue(post_delete == 2);
	}

	@Test //CASO NEGATIVO
	void shouldNotDeleteReferee() {

		Referee ref = this.refereeService.findRefereeByUsername("referee10");

		Assertions.assertThrows(NullPointerException.class, () -> {
			this.refereeService.deleteReferee(ref);
		});
	}

	@Test //CASO POSITIVO
	void shouldSaveReferee() {

		User user = new User();

		user.setEnabled(true);
		user.setPassword("password");
		user.setUsername("password");

		Referee r = new Referee();

		r.setId(100);
		r.setFirstName("Test");
		r.setLastName("Test");
		r.setEmail("Test@gmail.com");
		r.setDni("49032196Z");
		r.setTelephone("693968582");
		r.setUser(user);

		this.refereeService.saveReferee(r);

		int count = this.refereeService.count();

		Assertions.assertTrue(count == 3);
	}

	@Test //CASO NEGATIVO
	void shouldNotSaveReferee() {

		User user = new User();

		user.setEnabled(true);
		user.setPassword("password");
		user.setUsername("password");

		Referee r = new Referee();

		r.setId(100);
		r.setFirstName("");
		r.setLastName("Test");
		r.setEmail("Test@gmail.com");
		r.setDni("49032196Z");
		r.setTelephone("693968582");
		r.setUser(user);

		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			this.refereeService.saveReferee(r);
		});
	}

}
