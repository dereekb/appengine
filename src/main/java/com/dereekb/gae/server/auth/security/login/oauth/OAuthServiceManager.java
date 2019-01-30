package com.dereekb.gae.server.auth.security.login.oauth;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthServiceUnavailableException;

/**
 * Service used for retrieving {@link OAuthService} implementations and
 * performing logging in.
 *
 * @author dereekb
 *
 */
public interface OAuthServiceManager extends OAuthLoginService {

	/**
	 * Returns the service for the given string type.
	 *
	 * @param type
	 *            {@link LoginPointerType}. Never {@code null}.
	 * @return {@link OAuthService}. Never {@code null}.
	 * @throws OAuthServiceUnavailableException
	 *             if the service is not available.
	 */
	public OAuthService getService(String type) throws OAuthServiceUnavailableException;

	/**
	 * Returns the service for the given {@link LoginPointerType}.
	 *
	 * @param type
	 *            {@link LoginPointerType}. Never {@code null}.
	 * @return {@link OAuthService}. Never {@code null}.
	 * @throws OAuthServiceUnavailableException
	 *             if the service is not available.
	 */
	public OAuthService getService(LoginPointerType type) throws OAuthServiceUnavailableException;

}
