package com.dereekb.gae.server.auth.security.login.oauth;


public interface OAuthAuthorizationInfo {

	public OAuthLoginInfo getLoginInfo();

	public OAuthAccessToken getAccessToken();

}
