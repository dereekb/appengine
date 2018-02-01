package com.dereekb.gae.server.auth.security.token.model;

/**
 * {@link EncodedLoginToken} that always has a signature.
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
	@Override
	public String getTokenSignature();

}
