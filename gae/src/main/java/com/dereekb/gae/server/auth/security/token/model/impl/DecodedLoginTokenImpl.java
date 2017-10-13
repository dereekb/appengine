package com.dereekb.gae.server.auth.security.token.model.impl;

import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;

/**
 * {@link DecodedLoginToken} implementation.
 * 
 * @author dereekb
 *
 */
public class DecodedLoginTokenImpl<T extends LoginToken>
        implements DecodedLoginToken<T> {

	private String encodedLoginToken;
	private T loginToken;

	public DecodedLoginTokenImpl(String encodedLoginToken) {
		this.setEncodedLoginToken(encodedLoginToken);
	}

	public DecodedLoginTokenImpl(String encodedLoginToken, T loginToken)
	        throws IllegalArgumentException {
		this.setEncodedLoginToken(encodedLoginToken);
		this.setLoginToken(loginToken);
	}

	// MARK: DecodedLoginToken
	@Override
	public String getEncodedLoginToken() {
		return this.encodedLoginToken;
	}

	@Override
	public T getLoginToken() {
		return this.loginToken;
	}

	public void setLoginToken(T loginToken) {
		if (loginToken == null) {
			throw new IllegalArgumentException("loginToken cannot be null.");
		}

		this.loginToken = loginToken;
	}

	private void setEncodedLoginToken(String encodedLoginToken) {
		if (encodedLoginToken == null) {
			throw new IllegalArgumentException("TokenString cannot be null.");
		}

		this.encodedLoginToken = encodedLoginToken;
	}

	@Override
	public String toString() {
		return "DecodedLoginTokenImpl [encodedLoginToken=" + this.encodedLoginToken + ", toString()=" + super.toString()
		        + "]";
	}

}
