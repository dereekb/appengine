package com.dereekb.gae.server.auth.security.login.oauth.impl;

import com.dereekb.gae.server.auth.security.login.oauth.OAuthAccessToken;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthorizationInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthLoginInfo;

/**
 * {@link OAuthAuthorizationInfo} implementation.
 *
 * @author dereekb
 *
 */
public class OAuthAuthorizationInfoImpl
        implements OAuthAuthorizationInfo {

	private OAuthLoginInfo loginInfo;
	private OAuthAccessToken accessToken;

	public OAuthAuthorizationInfoImpl() {}

	public OAuthAuthorizationInfoImpl(OAuthLoginInfo loginInfo) {
		this(loginInfo, null);
	}

	public OAuthAuthorizationInfoImpl(OAuthLoginInfo loginInfo, OAuthAccessToken accessToken) {
		this.setLoginInfo(loginInfo);
		this.setAccessToken(accessToken);
	}

	@Override
	public OAuthLoginInfo getLoginInfo() {
		return this.loginInfo;
	}

	public void setLoginInfo(OAuthLoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	@Override
	public OAuthAccessToken getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(OAuthAccessToken accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public String toString() {
		return "OAuthAuthorizationInfoImpl [loginInfo=" + this.loginInfo + ", accessToken=" + this.accessToken + "]";
	}

}
