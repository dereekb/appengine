package com.dereekb.gae.client.api.service.sender.security;

import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;

/**
 * Request security that can either pass along the current security context to
 * the request, or specify an override token to use.
 * 
 * @author dereekb
 *
 */
public interface ClientRequestSecurity {

	/**
	 * Returns the override token, if available.
	 * 
	 * @return {@link EncodedLoginToken}, or {@code null} if none set.
	 */
	public EncodedLoginToken getOverrideToken();

	/**
	 * Returns the security context to use when sending the request.
	 * 
	 * A type of {@link ClientRequestSecurityContextType#SYSTEM} will use a
	 * system authentication token when the request is made, and a
	 * {@link ClientRequestSecurityContextType#CURRENT} will attempt to use the
	 * current token authentication.
	 * 
	 * @return {@link ClientRequestSecurityContextType}. Never {@code null}.
	 */
	public ClientRequestSecurityContextType getSecurityContextType();

}
