package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.impl;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.ModelRoleGranter;

/**
 * Abstract {@link ModelRoleGranter} implementation.
 * <p>
 * Wraps the granted role.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractModelRoleGranterImpl<T>
        implements ModelRoleGranter<T> {

	protected final ModelRole grantedRole;

	public AbstractModelRoleGranterImpl(ModelRole grantedRole) {
		super();
		this.grantedRole = grantedRole;
	}

	// MARK: ModelRoleGranter
	@Override
	public ModelRole getGrantedRole() {
		return this.grantedRole;
	}

	@Override
	public abstract boolean hasRole(T model);

	@Override
	public String toString() {
		return "AbstractModelRoleGranterImpl [grantedRole=" + this.grantedRole + "]";
	}

}
