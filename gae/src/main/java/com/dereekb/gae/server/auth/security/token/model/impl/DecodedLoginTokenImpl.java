package com.dereekb.gae.server.auth.security.token.model.impl;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;

/**
 * {@link DecodedLoginToken} implementation.
 * 
 * @author dereekb
 *
 */
public class DecodedLoginTokenImpl extends LoginTokenImpl
        implements DecodedLoginToken {

	private String encodedTokenString;

	public DecodedLoginTokenImpl(String encodedTokenString) {
		super();
		this.setEncodedTokenString(encodedTokenString);
	}

	public DecodedLoginTokenImpl(String encodedTokenString, LoginPointerType pointerType)
	        throws IllegalArgumentException {
		super(pointerType);
		this.setEncodedTokenString(encodedTokenString);
	}

	public DecodedLoginTokenImpl(String encodedTokenString, DecodedLoginToken loginToken)
	        throws IllegalArgumentException {
		super(loginToken);
		this.setEncodedTokenString(encodedTokenString);
	}

	// MARK: DecodedLoginToken
	@Override
	public String getEncodedLoginToken() {
		return this.encodedTokenString;
	}

	private void setEncodedTokenString(String encodedTokenString) {
		if (encodedTokenString == null) {
			throw new IllegalArgumentException("TokenString cannot be null.");
		}

		this.encodedTokenString = encodedTokenString;
	}

	@Override
	public String toString() {
		return "DecodedLoginTokenImpl [encodedTokenString=" + this.encodedTokenString + ", toString()="
		        + super.toString() + "]";
	}

}
