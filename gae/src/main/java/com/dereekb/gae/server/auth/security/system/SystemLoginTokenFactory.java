package com.dereekb.gae.server.auth.security.system;

import com.dereekb.gae.server.auth.security.token.model.SignedEncodedLoginToken;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;

/**
 * Factory for creating encoded login token instances that are used by the
 * server itself.
 * <p>
 * Server tokens are long-lived administrator tokens with full system access.
 * All requests with a system token must be signed.
 *
 * @author dereekb
 *
 */
public interface SystemLoginTokenFactory {

	/**
	 * Makes an encoded system token.
	 *
	 * @return {@link String} token. Never {@code null}.
	 *
	 * @throws FactoryMakeFailureException
	 *             thrown if the token fails to be generated.
	 */
	@Deprecated
	public String makeEncodedToken() throws FactoryMakeFailureException;

	/**
	 *
	 * @return
	 * @throws FactoryMakeFailureException
	 */
	@Deprecated
	public KeyedEncodedParameter makeTokenHeader() throws FactoryMakeFailureException;

	/**
	 * Generates an encoded system token for the current system.
	 *
	 * @return {@link String} token. Never {@code null}.
	 * @throws FactoryMakeFailureException
	 *             thrown if the token fails to be generated.
	 */
	public SignedEncodedLoginToken makeSystemToken() throws FactoryMakeFailureException;

}
