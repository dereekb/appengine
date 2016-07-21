package com.dereekb.gae.server.auth.security.login.oauth;

/**
 * Authorization information given after authorization occurs.
 * 
 * @author dereekb
 *
 */
public interface OAuthAuthorizationInfo {

	public OAuthLoginInfo getLoginInfo();

	public OAuthAccessToken getAccessToken();

}
