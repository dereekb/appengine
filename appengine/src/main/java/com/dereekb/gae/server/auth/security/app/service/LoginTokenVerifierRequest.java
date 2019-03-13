package com.dereekb.gae.server.auth.security.app.service;

import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;

/**
 * {@link AppLoginSecurityVerifierService} request.
 *
 * @author dereekb
 *
 */
public interface LoginTokenVerifierRequest {

	/**
	 * Returns the decoded token to verify.
	 *
	 * @return {@link DecodedLoginToken}. Never {@code null}.
	 */
	public DecodedLoginToken<?> getLoginToken();

	/**
	 * Returns the content to verify with the token, if applicable.
	 *
	 * @return {@link String}. May be {@code null}.
	 */
	public String getContent();

	/**
	 * Returns the content signature. May match the signature in the
	 * login token or be different, or {@code null}.
	 *
	 * @return {@link String}. May be {@code null}.
	 */
	public String getSignature();

}
