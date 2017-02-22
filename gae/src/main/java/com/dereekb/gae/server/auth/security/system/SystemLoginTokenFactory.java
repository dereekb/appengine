package com.dereekb.gae.server.auth.security.system;

import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;

/**
 * Factory for creating encoded login token instances that are used by the
 * server itself.
 * 
 * Server tokens are short-lived.
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
	public String makeTokenString() throws FactoryMakeFailureException;

	public KeyedEncodedParameter makeTokenHeader() throws FactoryMakeFailureException;

}
