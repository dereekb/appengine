package com.dereekb.gae.server.auth.security.token.parameter;

import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;

/**
 * Used to build an authentication parameter.
 * 
 * @author dereekb
 *
 */
public interface AuthenticationParameterBuilder {

	/**
	 * Builds an authentication parameter/header.
	 * 
	 * @param token
	 *            {@link String} token. Never {@code null}.
	 * @return {@link KeyedEncodedParameter}. Never {@code null}.
	 */
	public KeyedEncodedParameter buildAuthenticationParameter(String token);

	/**
	 * Builds an authentication parameter/header.
	 * 
	 * @param token
	 *            {@link EncodedLoginToken} token. Never {@code null}.
	 * @return {@link KeyedEncodedParameter}. Never {@code null}.
	 */
	public KeyedEncodedParameter buildAuthenticationParameter(EncodedLoginToken token);

}
