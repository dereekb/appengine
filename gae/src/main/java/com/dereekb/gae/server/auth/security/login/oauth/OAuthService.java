package com.dereekb.gae.server.auth.security.login.oauth;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthAuthorizationTokenRequestException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthConnectionException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthDeniedException;

/**
 * OAuth Service that provides methods for retrieving
 *
 * @author dereekb
 *
 */
public interface OAuthService {

	public LoginPointerType getLoginType();

	public String getAuthorizationCodeRequestUrl();

	public OAuthAuthorizationInfo processAuthorizationCodeResponse(HttpServletRequest request)
	        throws OAuthDeniedException,
	            OAuthConnectionException,
	            OAuthAuthorizationTokenRequestException;

	public OAuthAuthorizationInfo processAuthorizationCode(String authToken)
	        throws OAuthDeniedException,
	            OAuthConnectionException,
	            OAuthAuthorizationTokenRequestException;

	public OAuthAuthorizationInfo getAuthorizationInfo(OAuthAccessToken token)
	        throws OAuthAuthorizationTokenRequestException,
	            OAuthConnectionException;

}
