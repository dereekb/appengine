package com.dereekb.gae.server.auth.security.login.oauth.impl;

import com.dereekb.gae.server.auth.security.login.oauth.OAuthAccessToken;

/**
 * {@link OAuthAccessToken} implementation
 *
 * @author dereekb
 *
 */
public class OAuthAccessTokenImpl
        implements OAuthAccessToken {

	private String accessToken;
	private String refreshToken;
	private Long expiration;

	public OAuthAccessTokenImpl() {}

	public OAuthAccessTokenImpl(String accessToken) {
		this(accessToken, null);
	}

	public OAuthAccessTokenImpl(String accessToken, String refreshToken) {
		this(accessToken, refreshToken, null);
	}

	public OAuthAccessTokenImpl(String accessToken, String refreshToken, Long expiration) {
		this.setAccessToken(accessToken);
		this.setRefreshToken(refreshToken);
		this.setExpiration(expiration);
	}

	@Override
	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public String getRefreshToken() {
		return this.refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Long getExpiresIn() {
		return this.expiration;
	}

	public void setExpiration(Long expiration) {
		this.expiration = expiration;
	}

	@Override
	public String toString() {
		return "OAuthAccessTokenImpl [accessToken=" + this.accessToken + ", refreshToken=" + this.refreshToken
		        + ", expiration=" + this.expiration + "]";
	}

}
