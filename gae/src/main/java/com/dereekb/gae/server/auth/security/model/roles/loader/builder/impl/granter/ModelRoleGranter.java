package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;

// MARK: Internal
public interface ModelRoleGranter<T> {

	public ModelRole getGrantedRole();

	public boolean hasRole(T model);

}