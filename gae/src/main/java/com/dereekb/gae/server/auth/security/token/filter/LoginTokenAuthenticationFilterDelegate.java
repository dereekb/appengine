package com.dereekb.gae.server.auth.security.token.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.dereekb.gae.server.auth.security.token.exception.TokenException;

/**
 * Delegate for {@link LoginTokenAuthenticationFilter}.
 * 
 * @author dereekb
 *
 */
public interface LoginTokenAuthenticationFilterDelegate {

	/**
	 * Generates a {@link Authentication} used for pre-authentication.
	 * 
	 * @param token
	 *            Encoded LoginToken {@link String}. Never {@code null}.
	 * @param details
	 *            {@link WebAuthenticationDetails}. Never {@code null}.
	 * @param request
	 *            {@link HttpServletRequest}. Never {@code null}.
	 * @return {@link Authentication}. Never {@code null}.
	 * @throws TokenException
	 *             thrown if an exception occurs related to the token.
	 */
	public Authentication performPreAuth(String token,
	                                     WebAuthenticationDetails details,
	                                     HttpServletRequest request)
	        throws TokenException;

}
