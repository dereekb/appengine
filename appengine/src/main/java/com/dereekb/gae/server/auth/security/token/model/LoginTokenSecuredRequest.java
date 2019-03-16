package com.dereekb.gae.server.auth.security.token.model;

import javax.servlet.http.HttpServletRequest;

/**
 * Represents a request that may be secured by a signature.
 *
 * @author dereekb
 *
 */
public interface LoginTokenSecuredRequest {

	/**
	 * Returns the token to verify.
	 *
	 * @return {@link EncodedLoginToken}. Never {@code null}.
	 */
	public EncodedLoginToken getLoginToken();

	/**
	 * Returns the request.
	 *
	 * @return {@link HttpServletRequest}. Never {@code null}.
	 */
	public HttpServletRequest getRequest();

}
