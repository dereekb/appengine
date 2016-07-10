package com.dereekb.gae.server.auth.security.token.model;

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
	 * @throws TokenUnauthorizedException
	 *             Thrown if the token is invalid.
	 */
	public LoginToken decodeLoginToken(String token) throws TokenUnauthorizedException;

}
