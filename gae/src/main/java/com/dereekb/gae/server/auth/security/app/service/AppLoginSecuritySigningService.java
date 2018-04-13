package com.dereekb.gae.server.auth.security.app.service;

import com.dereekb.gae.server.auth.security.token.model.SignedEncodedLoginToken;

/**
 * Service used for signing content with a token for security purposes. Verifies
 * the requestor's identity and provides a hash of the request.
 *
 * @author dereekb
 *
 */
public interface AppLoginSecuritySigningService {

	/**
	 * Creates a signature with the token and content, and converts it to a
	 * hexadecimal string value.
	 *
	 * @param secret
	 *            {@link String}. Never {@code null}.
	 * @param token
	 *            {@link String}. Never {@code null}.
	 * @param content
	 *            {@link String}. Never {@code null}.
	 * @return {@link SignedEncodedLoginToken}. Never {@code null}.
	 * @throws IllegalArgumentException
	 */
	public SignedEncodedLoginToken signToken(String secret,
	                                         String token,
	                                         String content)
	        throws IllegalArgumentException;

	/**
	 * Creates a signature with the token and content, and converts it to a
	 * hexadecimal string value.
	 *
	 * @param secret
	 *            {@link String}. Never {@code null}.
	 * @param token
	 *            {@link String}. Never {@code null}.
	 * @param content
	 *            {@link String}. Never {@code null}.
	 * @return {@link String}. Never {@code null}.
	 * @throws IllegalArgumentException
	 */
	public String hexSign(String secret,
	                      String token,
	                      String content)
	        throws IllegalArgumentException;

	/**
	 * Creates a signature with the token and content, and converts it to a byte
	 * array.
	 *
	 * @param secret
	 *            {@link String}. Never {@code null}.
	 * @param token
	 *            {@link String}. Never {@code null}.
	 * @param content
	 *            {@link String}. Never {@code null}.
	 * @return {@code byte[]}. Never {@code null}.
	 * @throws IllegalArgumentException
	 */
	public byte[] byteSign(String secret,
	                       String token,
	                       String content)
	        throws IllegalArgumentException;

}
