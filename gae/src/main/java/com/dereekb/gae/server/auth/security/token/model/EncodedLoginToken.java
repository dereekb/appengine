package com.dereekb.gae.server.auth.security.token.model;

/**
 * Object that contains the encoded form of a {@link LoginToken}.
 * 
 * @author dereekb
 *
 */
public interface EncodedLoginToken {

	/**
	 * Returns the encoded login token.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getEncodedLoginToken();

}
