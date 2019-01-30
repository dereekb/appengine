package com.dereekb.gae.server.auth.security.app.service.impl;

import com.dereekb.gae.server.auth.security.app.service.LoginTokenVerifierRequest;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;

/**
 * {@link LoginTokenVerifierRequest} implementation.
 *
 * @author dereekb
 *
 */
public class LoginTokenVerifierRequestImpl
        implements LoginTokenVerifierRequest {

	private DecodedLoginToken<?> loginToken;
	private String content;
	private String signature;

	public LoginTokenVerifierRequestImpl(DecodedLoginToken<?> loginToken) {
		this(loginToken, null, null);
	}

	public LoginTokenVerifierRequestImpl(DecodedLoginToken<?> loginToken, String signature) {
		this(loginToken, null, signature);
	}

	public LoginTokenVerifierRequestImpl(DecodedLoginToken<?> loginToken, String content, String signature) {
		super();
		this.setLoginToken(loginToken);
		this.setSignature((signature != null) ? signature : loginToken.getTokenSignature());
		this.setContent(content);
	}

	// MARK:
	@Override
	public DecodedLoginToken<?> getLoginToken() {
		return this.loginToken;
	}

	public void setLoginToken(DecodedLoginToken<?> loginToken) {
		if (loginToken == null) {
			throw new IllegalArgumentException("loginToken cannot be null.");
		}

		this.loginToken = loginToken;
	}

	@Override
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String getSignature() {
		return this.signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public String toString() {
		return "LoginTokenVerifierRequestImpl [loginToken=" + this.loginToken + ", content=" + this.content
		        + ", signature=" + this.signature + "]";
	}

}
