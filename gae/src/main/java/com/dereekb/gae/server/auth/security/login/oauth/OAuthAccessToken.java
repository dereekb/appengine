package com.dereekb.gae.server.auth.security.login.oauth;


public interface OAuthAccessToken {

	public String getAccessToken();

	public String getRefreshToken();

	public Long getExpiresIn();

}
