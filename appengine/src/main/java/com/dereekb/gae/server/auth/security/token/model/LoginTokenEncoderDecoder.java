package com.dereekb.gae.server.auth.security.token.model;

/**
 * {@link LoginTokenEncoder} and {@link LoginTokenDecoder} interface.
 *
 * @author dereekb
 *
 */
public interface LoginTokenEncoderDecoder<T extends LoginToken>
        extends LoginTokenEncoder<T>, LoginTokenDecoder<T> {

	/**
	 * Makes a new token.
	 *
	 * @return {@link LoginToken}. Never {@code null}.
	 */
	public T makeToken();

	/**
	 * Makes a new token with a copy of the other.
	 *
	 * @return {@link LoginToken}. Never {@code null}.
	 */
	public T makeToken(LoginToken token);

}
