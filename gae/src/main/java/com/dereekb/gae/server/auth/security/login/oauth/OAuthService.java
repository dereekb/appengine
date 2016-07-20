package com.dereekb.gae.server.auth.security.login.oauth;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthAuthorizationTokenRequestException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthDeniedException;

/**
 * OAuth Service that provides methods for retrieving
 *
 * @author dereekb
 *
 */
public interface OAuthService {

	public String getAuthorizationCodeRequestUrl();

	public OAuthAuthorizationInfo processAuthorizationCodeResponse(HttpServletRequest request)
	        throws OAuthDeniedException,
	            OAuthAuthorizationTokenRequestException;

	public OAuthAuthorizationInfo processAuthorizationCode(String authToken)
	        throws OAuthDeniedException,
	            OAuthAuthorizationTokenRequestException;

}
