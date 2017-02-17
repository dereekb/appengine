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
	 * 
	 * @deprecated The client should first retrieve and decode an authorization
	 *             code.
	 */
	@Deprecated
	public OAuthAuthorizationInfo processAuthorizationCodeResponse(HttpServletRequest request)
	        throws OAuthConnectionException,
	            OAuthAuthorizationTokenRequestException;

	/**
	 * Used for processing an authorization code.
	 * <p>
	 * The authorization token is retrieved after the dialogue response. The
	 * implementation will contact the remote server using the auth code to
	 * retrieve actual authorization.
	 * 
	 * @param authToken
	 *            {@link OAuthAuthCode}. Never {@code null}.
	 * @return {@link OAuthAuthorizationInfo}. Never {@code null}.
	 * @throws OAuthConnectionException
	 *             thrown if a connection issue arises while attempting to
	 *             retrieve authorization info.
	 * @throws OAuthAuthorizationTokenRequestException
	 */
	public OAuthAuthorizationInfo processAuthorizationCode(OAuthAuthCode authCode)
	        throws OAuthConnectionException,
	            OAuthAuthorizationTokenRequestException;

	/**
	 * Used for retrieving the actual authorization info from the remote server
	 * after getting a valid access token from the remote OAuth provider.
	 * 
	 * @param token
	 *            {@link OAuthAccessToken}. Never {@code null}.
	 * @return {@link OAuthAuthorizationInfo}. Never {@code null}.
	 * @throws OAuthConnectionException
	 *             thrown if a connection issue arises while attempting to
	 *             retrieve authorization info.
	 * @throws OAuthAuthorizationTokenRequestException
	 */
	public OAuthAuthorizationInfo retrieveAuthorizationInfo(OAuthAccessToken token)
	        throws OAuthConnectionException,
	            OAuthAuthorizationTokenRequestException;

}
