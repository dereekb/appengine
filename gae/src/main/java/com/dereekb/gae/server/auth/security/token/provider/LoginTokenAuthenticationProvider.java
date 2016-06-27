package com.dereekb.gae.server.auth.security.token.provider;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.dereekb.gae.server.auth.security.token.exception.TokenExpiredException;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;

/**
 * Used for retrieving an {@link LoginTokenAuthentication} using a token.
 *
 * @author dereekb
 *
 */
public interface LoginTokenAuthenticationProvider {

	/**
	 * Attempts to authenticate using the input token against the details.
	 *
	 * @param token
	 *            Token string. Never {@code null}.
	 * @param details
	 *            {@link WebAuthenticationDetails}. Never {@code null}.
	 *
	 * @return {@link LoginTokenAuthentication}. Never {@code null}.
	 * @throws TokenExpiredException
	 *             Thrown if the token was validated, but is considered expired.
	 * @throws TokenUnauthorizedException
	 *             Thrown if the token was unauthorized by either not existing
	 *             or not matching against the details.
	 */
	public LoginTokenAuthentication authenticate(String token,
	                                             WebAuthenticationDetails details) throws TokenExpiredException, TokenUnauthorizedException;

}
