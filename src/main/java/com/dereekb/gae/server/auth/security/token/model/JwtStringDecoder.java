package com.dereekb.gae.server.auth.security.token.model;

import io.jsonwebtoken.Claims;

/**
 * Used for decoding a {@link String} value to a {@link Claims} value.
 *
 * @author dereekb
 *
 */
public interface JwtStringDecoder {

	/**
	 * Decodes the input JWT string to a {@link Claims}.
	 *
	 * @param token
	 *            {@link String}.
	 *            Never {@code null}.
	 * @return {@link Claims}. Never {@code null}.
	 */
	public Claims decodeTokenClaims(String token);

}
