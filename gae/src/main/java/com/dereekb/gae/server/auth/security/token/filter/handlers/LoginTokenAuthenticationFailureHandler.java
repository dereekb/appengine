package com.dereekb.gae.server.auth.security.token.filter.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * {@link AuthenticationFailureHandler} implementation for the system.
 *
 * @author dereekb
 *
 */
public class LoginTokenAuthenticationFailureHandler
        implements AuthenticationFailureHandler {

	// MARK: AuthenticationFailureHandler
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
	                                    HttpServletResponse response,
	                                    AuthenticationException exception) throws IOException, ServletException {
		throw exception; // Re-throw the exception.
	}

}
