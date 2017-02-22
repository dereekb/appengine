package com.dereekb.gae.server.auth.security.login.oauth;

/**
 * Authorization information given after authorization occurs.
 * 
 * @author dereekb
 *
 */
public interface OAuthAuthorizationInfo {

	/**
	 * Returns the login info for this authorization.
	 * 
	 * @return {@link OAuthLoginInfo}. Never {@code null}.
	 */
	public OAuthLoginInfo getLoginInfo();

	/**
	 * Returns the access token used to build this authorization.
	 * 
	 * @return {@link OAuthAccessToken}. Never {@code null}.
	 */
	public OAuthAccessToken getAccessToken();

}
