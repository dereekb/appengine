package com.dereekb.gae.server.auth.security.token.model.impl;

import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;

/**
 * {@link EncodedLoginToken} implementation that wraps a string token.
 *
 * @author dereekb
 *
 */
public class EncodedLoginTokenImpl
        implements EncodedLoginToken {

	private String encodedLoginToken;
	private String tokenSignature;

	public EncodedLoginTokenImpl(String encodedLoginToken) {
		this.setEncodedLoginToken(encodedLoginToken);
	}

	public EncodedLoginTokenImpl(String encodedLoginToken, String tokenSignature) {
		super();
		this.setEncodedLoginToken(encodedLoginToken);
		this.setTokenSignature(tokenSignature);
	}

	// MARK: EncodedLoginToken
	@Override
	public String getEncodedLoginToken() {
		return this.encodedLoginToken;
	}

	public void setEncodedLoginToken(String encodedLoginToken) {
		if (encodedLoginToken == null) {
			throw new IllegalArgumentException("encodedLoginToken cannot be null.");
		}

		this.encodedLoginToken = encodedLoginToken;
	}

	@Override
	public String getTokenSignature() {
		return this.tokenSignature;
	}

	public void setTokenSignature(String tokenSignature) {
		this.tokenSignature = tokenSignature;
	}

	@Override
	public String toString() {
		return "EncodedLoginTokenImpl [encodedLoginToken=" + this.encodedLoginToken + "]";
	}

}
