package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.impl;

import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.model.roles.ModelRole;

/**
 * {@link AbstractModelRoleGranterImpl} extension that grants the role if
 * the user is an administrator.
 *
 * @author dereekb
 *
 */
public abstract class AbstractAdminModelRoleGranterImpl<T> extends AbstractModelRoleGranterImpl<T> {

	public AbstractAdminModelRoleGranterImpl(ModelRole grantedRole) {
		super(grantedRole);
	}

	@Override
	public final boolean hasRole(T model) {
		return LoginSecurityContext.safeIsAdministrator() || this.nonAdminHasRole(model);
	}

	public boolean nonAdminHasRole(T model) {
		return false;
	}

}