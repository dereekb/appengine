package com.dereekb.gae.server.auth.security.token.model;

import com.dereekb.gae.server.auth.security.token.exception.TokenExpiredException;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;

import io.jsonwebtoken.Claims;

/**
 * Used for decoding a login token.
 *
 * @author dereekb
 *
 */
public interface LoginTokenDecoder<T extends LoginToken> {

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
	public DecodedLoginToken<T> decodeLoginToken(String token) throws TokenExpiredException, TokenUnauthorizedException;

	/**
	 * Decodes a token using the input claims.
	 *
	 * @param claims
	 *            {@link Claims}. Never {@code null}.
	 * @return {@link LoginToken}. Never {@code null}.
	 * @throws TokenExpiredException
	 *             Thrown if the token was validated, but is considered expired.
	 * @throws TokenUnauthorizedException
	 *             Thrown if the token was unauthorized by either not existing
	 *             or not matching against the details.
	 */
	public DecodedLoginToken<T> decodeLoginTokenFromClaims(Claims claims)
	        throws TokenExpiredException,
	            TokenUnauthorizedException;

}
