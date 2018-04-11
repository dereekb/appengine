package com.dereekb.gae.server.auth.security.token.model;

import io.jsonwebtoken.Claims;

/**
 * Used for encoding a {@link Claims} value to a {@link String}.
 *
 * @author dereekb
 *
 */
public interface JwtStringEncoder {

	/**
	 * Encodes the input claims to a string.
	 *
	 * @param claims
	 *            {@link Claims}. Never {@code null}.
	 * @return {@link String}. Never {@code null}.
	 */
	public String encodeClaims(Claims claims);

}
