package com.dereekb.gae.server.auth.old.gae.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Basic {@link AuthenticationEntryPoint} implementation for redirecting to the
 * Google App Engine login page directly.
 *
 * @author dereekb
 */
@Deprecated
public final class GoogleAccountAuthenticationEntryPoint
        implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request,
	                     HttpServletResponse response,
	                     AuthenticationException authException) throws IOException, ServletException {

		UserService userService = UserServiceFactory.getUserService();

		String postLoginUrl = request.getRequestURI();
		String loginUrl = userService.createLoginURL(postLoginUrl);

		response.sendRedirect(loginUrl);
	}

}
