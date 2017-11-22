package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.impl;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.ModelRoleGranter;

/**
 * {@link ModelRoleGranter} implementation that always grants or does not
 * grant the role.
 *
 * @author dereekb
 *
 */
public final class AutoModelRoleGranter<T> extends AbstractModelRoleGranterImpl<T> {

	private final boolean grant;

	public AutoModelRoleGranter(ModelRole grantedRole, boolean grant) {
		super(grantedRole);
		this.grant = grant;
	}

	@Override
	public boolean hasRole(T model) {
		return this.grant;
	}

}
