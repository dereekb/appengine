package com.dereekb.gae.server.auth.security.token.model;

/**
 * Used for encoding login tokens.
 *
 * @author dereekb
 *
 */
public interface LoginTokenEncoder<T extends LoginToken> {

	/**
	 * Encodes the input {@link LoginToken} to a string.
	 *
	 * @param loginToken
	 *            {@link LoginToken}. Never {@code null}.
	 * @return {@link String}. Never {@code null}.
	 */
	public String encodeLoginToken(T loginToken);

}
