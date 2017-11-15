package com.dereekb.gae.server.auth.security.model.context.impl;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenTypedModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.exception.UnavailableModelContextTypeException;

/**
 * Abstract {@link LoginTokenModelContextSet} implementation.
 * 
 * @author dereekb
 *
 */
public abstract class AbstractLoginTokenModelContextSet
        implements LoginTokenModelContextSet {

	// MARK: LoginTokenModelContextSet
	@Override
	public LoginTokenTypedModelContextSet getContextsForType(String modelType)
	        throws UnavailableModelContextTypeException {
		LoginTokenTypedModelContextSet set = this.tryGetContextsForType(modelType);

		if (set == null) {
			throw new UnavailableModelContextTypeException();
		}

		return set;
	}

	public abstract LoginTokenTypedModelContextSet tryGetContextsForType(String modelType);

}
