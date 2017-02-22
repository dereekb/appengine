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

	public EncodedLoginTokenImpl(String encodedLoginToken) {
		this.setEncodedLoginToken(encodedLoginToken);
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
	public String toString() {
		return "EncodedLoginTokenImpl [encodedLoginToken=" + this.encodedLoginToken + "]";
	}

}
