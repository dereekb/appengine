package com.dereekb.gae.web.api.auth.controller.token.impl;

import com.dereekb.gae.server.auth.security.token.model.SignedEncodedLoginToken;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.web.api.auth.controller.token.TokenValidationRequest;

/**
 * {@link TokenValidationRequest} implementation.
 *
 * @author dereekb
 *
 */
public class TokenValidationRequestImpl
        implements TokenValidationRequest {

	private String token;
	private String content;
	private String signature;
	private Boolean quick;

	public TokenValidationRequestImpl(SignedEncodedLoginToken signedToken) {
		this(signedToken, true);
	}

	public TokenValidationRequestImpl(SignedEncodedLoginToken signedToken, Boolean quick) {
		this(signedToken, null, quick);
	}

	public TokenValidationRequestImpl(SignedEncodedLoginToken signedToken, String content, Boolean quick) {
		this(signedToken.getEncodedLoginToken(), content, signedToken.getTokenSignature(), quick);
	}

	public TokenValidationRequestImpl(String token) {
		this(token, true);
	}

	public TokenValidationRequestImpl(String token, Boolean quick) {
		this(token, null, quick);
	}

	public TokenValidationRequestImpl(String token, String signature) {
		this(token, signature, true);
	}

	public TokenValidationRequestImpl(String token, String signature, Boolean quick) {
		this(token, null, signature, quick);
	}

	public TokenValidationRequestImpl(String token, String content, String signature, Boolean quick) {
		super();
		this.setToken(token);
		this.setContent(content);
		this.setSignature(signature);
		this.setQuick(quick);
	}

	@Override
	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		if (StringUtility.isEmptyString(token)) {
			throw new IllegalArgumentException("token cannot be null or empty.");
		}

		this.token = token;
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
	public Boolean getQuick() {
		return this.quick;
	}

	public void setQuick(Boolean quick) {
		this.quick = quick;
	}

	@Override
	public String toString() {
		return "TokenValidationRequestImpl [token=" + this.token + ", content=" + this.content + ", signature="
		        + this.signature + ", quick=" + this.quick + "]";
	}

}
