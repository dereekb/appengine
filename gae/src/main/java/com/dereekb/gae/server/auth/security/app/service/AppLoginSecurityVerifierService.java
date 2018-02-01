package com.dereekb.gae.server.auth.security.app.service;

import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;

/**
 * Used for verifying an app request.
 *
 * @author dereekb
 *
 */
public interface AppLoginSecurityVerifierService {

	/**
	 * Verifies a decoded token and signature for the token's app.
	 *
	 * @param token
	 *            {@link DecodedLoginToken}. Never {@code null}.
	 * @param signature
	 *            {@link String}. Never {@code null}.
	 * @return {@code true} if valid.
	 */
	public boolean isValidToken(DecodedLoginToken<?> token,
	                            String signature);

}
