package com.dereekb.gae.server.auth.security.model.context.impl;

import java.util.Collections;
import java.util.Set;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextRole;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextRoleSet;

/**
 * {@link LoginTokenModelContextRoleSet} implementation that is always empty.
 * 
 * @author dereekb
 *
 */
public final class EmptyLoginTokenModelContextRoleSet
        implements LoginTokenModelContextRoleSet {

	public static final EmptyLoginTokenModelContextRoleSet SINGLETON = new EmptyLoginTokenModelContextRoleSet();

	private EmptyLoginTokenModelContextRoleSet() {
		super();
	}

	public static EmptyLoginTokenModelContextRoleSet make() {
		return SINGLETON;
	}

	// MARK: LoginTokenModelContextRoleSet
	@Override
	public boolean hasRole(LoginTokenModelContextRole role) {
		return false;
	}

	@Override
	public Set<LoginTokenModelContextRole> getRoles() {
		return Collections.emptySet();
	}

	@Override
	public String toString() {
		return "EmptyLoginTokenModelContextRoleSet []";
	}

}
