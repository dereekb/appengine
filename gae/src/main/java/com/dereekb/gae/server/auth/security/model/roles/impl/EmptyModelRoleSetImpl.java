package com.dereekb.gae.server.auth.security.model.roles.impl;

import java.util.Collections;
import java.util.Set;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;

/**
 * {@link ModelRoleSet} implementation that is always empty.
 * 
 * @author dereekb
 *
 */
public final class EmptyModelRoleSetImpl
        implements ModelRoleSet {

	public static final EmptyModelRoleSetImpl SINGLETON = new EmptyModelRoleSetImpl();

	private EmptyModelRoleSetImpl() {
		super();
	}

	public static EmptyModelRoleSetImpl make() {
		return SINGLETON;
	}

	// MARK: ModelRoleSet
	@Override
	public boolean isEmpty() {
		return true;
	}
	
	@Override
	public boolean hasRole(ModelRole role) {
		return false;
	}

	@Override
	public Set<ModelRole> getRoles() {
		return Collections.emptySet();
	}

	@Override
	public String toString() {
		return "EmptyModelRoleSetImpl []";
	}

}
