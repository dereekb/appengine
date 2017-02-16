package com.dereekb.gae.server.auth.security.login.oauth;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthAuthorizationTokenRequestException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthConnectionException;

/**
 * OAuthService that provides authentication using a remote OAuth service.
 *
 * @author dereekb
 *
 */
public interface OAuthService {

	/**
	 * Returns the login type this service is associated with.
	 * 
	 * @return {@link LoginPointerType}. Never {@code null}.
	 */
	public LoginPointerType getLoginType();

	/**
	 * Creates a new authorization request URL for the user to use for logins.
	 * 
	 * @return {@link String} url. Never {@code null}.
	 */
	public String getAuthorizationCodeRequestUrl();

	/**
	 * Used for processing the result of the dialogue response.
	 * 
	 * @param request
	 *            {@link HttpServletRequest}. Never {@code null}.
	 * @return
	 * @throws OAuthConnectionException
	 * @throws OAuthAuthorizationTokenRequestException
	 */
	public OAuthAuthorizationInfo processAuthorizationCodeResponse(HttpServletRequest request)
	        throws OAuthConnectionException,
	            OAuthAuthorizationTokenRequestException;

	/**
	 * Used for processing an authorization code.
	 * 
	 * @param authToken
	 * @return
	 * @throws OAuthConnectionException
	 * @throws OAuthAuthorizationTokenRequestException
	 */
	public OAuthAuthorizationInfo processAuthorizationCode(String authToken)
	        throws OAuthConnectionException,
	            OAuthAuthorizationTokenRequestException;

	/**
	 * Used for retrieving the actual authorization info after getting an access
	 * token from the remote OAuth provider.
	 * 
	 * @param token
	 *            {@link OAuthAccessToken}. Never {@code null}.
	 * @return {@link OAuthAuthorizationInfo}. Never {@code null}.
	 * @throws OAuthAuthorizationTokenRequestException
	 * @throws OAuthConnectionException
	 *             thrown if a connection issue arises while attempting to
	 *             retrieve authorization info.
	 */
	public OAuthAuthorizationInfo getAuthorizationInfo(OAuthAccessToken token)
	        throws OAuthAuthorizationTokenRequestException,
	            OAuthConnectionException;

}
