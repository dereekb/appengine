package com.dereekb.gae.server.auth.security.app.service;

import com.dereekb.gae.server.auth.security.app.AppLoginSecurityDetails;
import com.dereekb.gae.server.auth.security.token.model.SignedEncodedLoginToken;

/**
 * {@link AppLoginSecuritySigningService} implementation for a specific app.
 *
 * @author dereekb
 *
 */
public interface ConfiguredAppLoginSecuritySigningService
        extends AppLoginSecuritySigningService {

	/**
	 * Security details for the configured app.
	 *
	 * @return {@link AppLoginSecurityDetails}. Never {@code null}.
	 */
	public AppLoginSecurityDetails getAppDetails();

	/**
	 * Signs the content with the token and a pre-configured secret.
	 *
	 * @param token
	 *            {@link String}. Never {@code null}.
	 * @param content
	 *            {@link String}. Never {@code null}.
	 * @return {@link SignedEncodedLoginToken}. Never {@code null}.
	 * @throws IllegalArgumentException
	 */
	public SignedEncodedLoginToken signWithToken(String token,
	                                             String content);

	/**
	 * Signs the content with the token and a pre-configured secret.
	 *
	 * @param token
	 *            {@link String}. Never {@code null}.
	 * @param content
	 *            {@link String}. Never {@code null}.
	 * @return {@link String}. Never {@code null}.
	 * @throws IllegalArgumentException
	 */
	public String hexSign(String token,
	                      String content)
	        throws IllegalArgumentException;

	/**
	 * Signs the content with the token and a pre-configured secret.
	 *
	 * @param token
	 *            {@link String}. Never {@code null}.
	 * @param content
	 *            {@link String}. Never {@code null}.
	 * @return {@code byte[]}. Never {@code null}.
	 * @throws IllegalArgumentException
	 */
	public byte[] byteSign(String token,
	                       String content)
	        throws IllegalArgumentException;

}
