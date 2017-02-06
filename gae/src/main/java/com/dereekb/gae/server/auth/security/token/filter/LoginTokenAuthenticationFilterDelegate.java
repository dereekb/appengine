package com.dereekb.gae.server.auth.security.token.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

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
	 * @return {@link Authentication}. Never {@code null}.
	 */
	public Authentication performPreAuth(String token,
	                                     WebAuthenticationDetails details);

}
