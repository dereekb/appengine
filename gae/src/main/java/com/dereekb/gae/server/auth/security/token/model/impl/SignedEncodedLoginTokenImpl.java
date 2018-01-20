package com.dereekb.gae.server.auth.security.token.model.impl;

import com.dereekb.gae.server.auth.security.token.model.SignedEncodedLoginToken;

/**
 * {@link SignedEncodedLoginToken} implementation.
 *
 * @author dereekb
 *
 */
public class SignedEncodedLoginTokenImpl extends EncodedLoginTokenImpl
        implements SignedEncodedLoginToken {

	private String tokenSignature;

	public SignedEncodedLoginTokenImpl(String encodedLoginToken, String tokenSignature) {
		super(encodedLoginToken);
		this.setTokenSignature(tokenSignature);
	}

	@Override
	public String getTokenSignature() {
		return this.tokenSignature;
	}

	public void setTokenSignature(String tokenSignature) {
		if (tokenSignature == null) {
			throw new IllegalArgumentException("tokenSignature cannot be null.");
		}

		this.tokenSignature = tokenSignature;
	}

	@Override
	public String toString() {
		return "SignedEncodedLoginTokenImpl [tokenSignature=" + this.tokenSignature + ", getEncodedLoginToken()="
		        + this.getEncodedLoginToken() + "]";
	}

}
