package com.dereekb.gae.server.auth.security.login.oauth.impl;

import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthCode;

/**
 * {@link OAuthAuthCode} implementation.
 * 
 * @author dereekb
 *
 */
public class OAuthAuthCodeImpl
        implements OAuthAuthCode {

	private String authCode;
	private String codeType;

	public OAuthAuthCodeImpl(String authCode) {
		this(authCode, null);
	}

	public OAuthAuthCodeImpl(String authCode, String codeType) {
		this.setAuthCode(authCode);
		this.setCodeType(codeType);
	}

	@Override
	public String getAuthCode() {
		return this.authCode;
	}

	public void setAuthCode(String authCode) {
		if (authCode == null) {
			throw new IllegalArgumentException("AuthCode cannot be null.");
		}

		this.authCode = authCode;
	}

	@Override
	public String getCodeType() {
		return this.codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	@Override
	public String toString() {
		return "OAuthAuthCodeImpl [authCode=" + this.authCode + ", codeType=" + this.codeType + "]";
	}

}
