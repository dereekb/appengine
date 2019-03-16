package com.dereekb.gae.server.auth.security.login.oauth.impl;

import com.dereekb.gae.server.auth.security.login.oauth.OAuthAccessToken;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthorizationInfo;

/**
 * Abstract {@link OAuthAuthorizationInfo} implementation.
 * 
 * @author dereekb
 *
 */
public abstract class AbstractOAuthAuthorizationInfo
        implements OAuthAuthorizationInfo {

	private OAuthAccessToken accessToken;

	public AbstractOAuthAuthorizationInfo(OAuthAccessToken accessToken) throws IllegalArgumentException {
		this.setAccessToken(accessToken);
	}

	@Override
	public OAuthAccessToken getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(OAuthAccessToken accessToken) {
		if (accessToken == null) {
			throw new IllegalArgumentException();
		}

		this.accessToken = accessToken;
	}

}
