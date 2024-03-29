package com.dereekb.gae.server.auth.security.token.entry;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * {@link AuthenticationEntryPoint} implementation that throws an unauthorized
 * exception.
 *
 * @author dereekb
 *
 */
public class TokenAuthenticationEntryPoint
        implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request,
	                     HttpServletResponse response,
	                     AuthenticationException authException)
	        throws IOException,
	            ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}

}