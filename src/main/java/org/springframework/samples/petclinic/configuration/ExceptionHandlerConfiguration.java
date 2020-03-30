
package org.springframework.samples.petclinic.configuration;

import javax.security.auth.login.CredentialException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * This advice is necessary because MockMvc is not a real servlet environment, therefore it does not redirect error
 * responses to [ErrorController], which produces validation response. So we need to fake it in tests.
 * It's not ideal, but at least we can use classic MockMvc tests for testing error response + document it.
 */
@ControllerAdvice
public class ExceptionHandlerConfiguration {

	@Autowired
	private BasicErrorController errorController;
	// add any exceptions/validations/binding problems


	@ExceptionHandler(Exception.class)
	public String defaultErrorHandler(final HttpServletRequest request, final Exception ex) {
		request.setAttribute("javax.servlet.error.request_uri", request.getPathInfo());
		request.setAttribute("javax.servlet.error.status_code", 400);
		request.setAttribute("exeption", ex);
		return "exceptions/exception";
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String notFoundErrorHandler(final HttpServletRequest request, final NoHandlerFoundException ex) {
		request.setAttribute("javax.servlet.error.request_uri", request.getPathInfo());
		request.setAttribute("javax.servlet.error.status_code", 404);
		request.setAttribute("exeption", ex);
		return "exceptions/exception";
	}

	@ExceptionHandler(CredentialException.class)
	public String forbiddenErrorHandler(final HttpServletRequest request, final CredentialException ex) {
		request.setAttribute("javax.servlet.error.request_uri", request.getPathInfo());
		request.setAttribute("javax.servlet.error.status_code", 401);
		request.setAttribute("exeption", ex);
		return "exceptions/forbidden";
	}

}
