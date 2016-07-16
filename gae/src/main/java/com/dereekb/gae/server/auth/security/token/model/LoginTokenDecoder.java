package com.dereekb.gae.server.auth.security.token.model;

import com.dereekb.gae.server.auth.security.token.exception.TokenExpiredException;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;

/**
 * Used for decoding a login token.
 *
 * @author dereekb
 *
 */
public interface LoginTokenDecoder {

	/**
	 * Decodes a token.
	 *
	 * @param token
	 *            Token string. Never {@code null}.
	 * @return {@link LoginToken}. Never {@code null}.
	 * @throws TokenExpiredException
	 *             Thrown if the token was validated, but is considered expired.
	 * @throws TokenUnauthorizedException
	 *             Thrown if the token was unauthorized by either not existing
	 *             or not matching against the details.
	 */
	public LoginToken decodeLoginToken(String token) throws TokenExpiredException, TokenUnauthorizedException;

}
