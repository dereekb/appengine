package com.dereekb.gae.web.api.auth.controller.token.impl;

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
		if (token == null) {
			throw new IllegalArgumentException("token cannot be null.");
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
		if (signature == null) {
			throw new IllegalArgumentException("signature cannot be null.");
		}

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
