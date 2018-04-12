package com.dereekb.gae.server.auth.security.token.model;

/**
 * Service used for verifying and decoding login tokens.
 *
 * @author dereekb
 *
 */
public interface LoginTokenVerifier {

	public void validate(LoginTokenVerificationRequest request);

}
