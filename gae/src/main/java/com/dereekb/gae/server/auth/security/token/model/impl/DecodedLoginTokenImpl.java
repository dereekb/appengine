package com.dereekb.gae.server.auth.security.token.model.impl;

import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;

import io.jsonwebtoken.Claims;

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
	private Claims claims;

	public DecodedLoginTokenImpl(String encodedLoginToken, T loginToken, Claims claims)
	        throws IllegalArgumentException {
		this.setEncodedLoginToken(encodedLoginToken);
		this.setLoginToken(loginToken);
		this.setClaims(claims);
	}

	// MARK: DecodedLoginToken
	@Override
	public String getEncodedLoginToken() {
		return this.encodedLoginToken;
	}

	@Override
	public String getTokenSignature() {
		return null;
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
	public Claims getClaims() {
		return this.claims;
	}

	public void setClaims(Claims claims) {
		if (claims == null) {
			throw new IllegalArgumentException("claims cannot be null.");
		}

		this.claims = claims;
	}

	@Override
	public String toString() {
		return "DecodedLoginTokenImpl [encodedLoginToken=" + this.encodedLoginToken + ", toString()=" + super.toString()
		        + "]";
	}

}
