package com.dereekb.gae.server.auth.security.login.oauth;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthAuthorizationTokenRequestException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthConnectionException;

/**
 * OAuthService that provides authentication using a remote OAuth service.
 * <p>
 * Tokens are provided by each of the clients and are validated by this service.
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
