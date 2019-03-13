package com.dereekb.gae.server.auth.security.token.model;

/**
 * {@link EncodedLoginToken} that always has an HMAC Signature for the request
 * it is a part of.
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
