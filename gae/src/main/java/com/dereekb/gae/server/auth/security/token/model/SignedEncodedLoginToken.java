package com.dereekb.gae.server.auth.security.token.model;

/**
 * {@link EncodedLoginToken} that has been signed.
 *
 * @author dereekb
 *
 */
public interface SignedEncodedLoginToken
        extends EncodedLoginToken {

	/**
	 * Returns the signature.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getTokenSignature();

}
