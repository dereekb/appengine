package com.dereekb.gae.server.auth.security.token.parameter;

import java.util.List;

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
	public KeyedEncodedParameter buildTokenAuthenticationParameter(String token);

	/**
	 * Builds a signature parameter/header.
	 *
	 * @param signature
	 *            {@link String}. Never {@code null}.
	 * @return {@link KeyedEncodedParameter}. Never {@code null}.
	 */
	public KeyedEncodedParameter buildSignatureAuthenticationParameter(String signature);

	/**
	 * Builds an authentication parameter/header.
	 *
	 * @param token
	 *            {@link EncodedLoginToken} token. Never {@code null}.
	 * @return {@link KeyedEncodedParameter}. Never {@code null}.
	 */
	public List<KeyedEncodedParameter> buildAuthenticationParameters(EncodedLoginToken token);

}
