package com.dereekb.gae.server.auth.security.model.roles.ownership.impl;

import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.model.roles.ownership.SecurityContextModelOwnershipChecker;

/**
 * Abstract {@link SecurityContextModelOwnershipChecker} implementation that
 * checks if the user is an administrator before checking ownership.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractSecurityContextModelOwnershipChecker<T>
        implements SecurityContextModelOwnershipChecker<T> {

	// MARK: SecurityContextModelOwnershipChecker
	@Override
	public final boolean isOwnedInSecurityContext(T model) {
		try {
			return this.isAdministrator() || this.checkIsOwnedInSecurityContext(model);
		} catch (NoSecurityContextException e) {
			return false;
		}
	}

	protected boolean isAdministrator() throws NoSecurityContextException {
		return LoginSecurityContext.isAdministrator();
	}

	protected abstract boolean checkIsOwnedInSecurityContext(T model) throws NoSecurityContextException;

}
