package com.dereekb.gae.server.auth.old.security.registration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.dereekb.gae.server.auth.old.security.filter.exception.NewLoginAuthenticationException;

/**
 * Used for handling {@link NewLoginAuthenticationException} events.
 *
 * @author dereekb
 *
 */
public interface NewLoginHandler {

	/**
	 * Handles the new {@link Login}. Users should become fully authenticated,
	 * or the
	 *
	 * @param e
	 *            {@link NewLoginAuthenticationException} containing the
	 *            information.
	 * @param request
	 * @param response
	 * @return a new {@link Authentication} if the user became fully
	 *         authenticated. If null is returned, it is assumed that a redirect
	 *         has been sent.
	 *
	 * @throws AuthenticationException
	 *             if handling the new login fails.
	 */
	public Authentication handleNewLogin(NewLoginAuthenticationException e,
	                           HttpServletRequest request,
	                                     HttpServletResponse response) throws AuthenticationException;

}
