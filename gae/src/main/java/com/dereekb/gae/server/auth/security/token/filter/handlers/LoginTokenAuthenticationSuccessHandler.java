package com.dereekb.gae.server.auth.security.token.filter.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


/**
 * {@link LoginTokenAuthenticationSuccessHandler} implementation for the system.
 *
 * @author dereekb
 *
 */
public class LoginTokenAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
	                                    HttpServletResponse response,
	                                    Authentication authentication) {
		// Don't do anything.
	}

}