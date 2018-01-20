package com.dereekb.gae.server.auth.security.app.service;

import com.dereekb.gae.server.auth.security.token.model.SignedEncodedLoginToken;

/**
 * Service used for signing a token for an app.
 *
 * @author dereekb
 *
 */
public interface AppLoginSecuritySigningService {

	/**
	 * Creates a signature and converts it to a hexadecimal string value.
	 *
	 * @param secret
	 *            {@link String}. Never {@code null}.
	 * @param token
	 *            {@link String}. Never {@code null}.
	 * @return {@link SignedEncodedLoginToken}. Never {@code null}.
	 * @throws IllegalArgumentException
	 */
	public SignedEncodedLoginToken signToken(String secret,
	                                         String token)
	        throws IllegalArgumentException;

	/**
	 * Creates a signature and converts it to a hexadecimal string value.
	 *
	 * @param secret
	 *            {@link String}. Never {@code null}.
	 * @param token
	 *            {@link String}. Never {@code null}.
	 * @return {@link String}. Never {@code null}.
	 * @throws IllegalArgumentException
	 */
	public String hexSign(String secret,
	                      String token)
	        throws IllegalArgumentException;

	/**
	 * Creates a signature and converts it to a byte array.
	 *
	 * @param secret
	 *            {@link String}. Never {@code null}.
	 * @param token
	 *            {@link String}. Never {@code null}.
	 * @return {@code byte[]}. Never {@code null}.
	 * @throws IllegalArgumentException
	 */
	public byte[] byteSign(String secret,
	                       String token)
	        throws IllegalArgumentException;

}
