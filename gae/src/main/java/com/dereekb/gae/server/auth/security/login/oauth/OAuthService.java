package com.dereekb.gae.server.auth.security.login.oauth;

import javax.servlet.http.HttpServletRequest;

/**
 * OAuth Service that provides methods for retrieving
 *
 * @author dereekb
 *
 */
public interface OAuthService {

	public String getAuthorizationCodeRequestUrl();

	public OAuthLoginInfo processAuthorizationToken(HttpServletRequest request);

	public OAuthLoginInfo processAuthorizationToken(String authToken);

}
