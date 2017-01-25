package com.dereekb.gae.server.auth.security.system;

import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;

/**
 * Factory for creating {@link LoginToken} instances that are used by the server
 * itself.
 * 
 * Server tokens a short-lived.
 * 
 * @author dereekb
 *
 */
public interface SystemLoginTokenFactory
        extends Factory<LoginToken> {

	public String makeTokenString() throws FactoryMakeFailureException;

	public KeyedEncodedParameter makeTokenHeader() throws FactoryMakeFailureException;

}
