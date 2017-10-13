package com.dereekb.gae.server.auth.security.token.model;

/**
 * {@link LoginToken} that has been decoded and has access to it's original
 * string form, allowing it to implement {@link EncodedLoginToken}.
 * 
 * @author dereekb
 *
 */
public interface DecodedLoginToken<T extends LoginToken>
        extends EncodedLoginToken {

	/**
	 * Returns the decoded login token.
	 * 
	 * @return {@link LoginToken}. Never {@code null}.
	 */
	public T getLoginToken();

}
