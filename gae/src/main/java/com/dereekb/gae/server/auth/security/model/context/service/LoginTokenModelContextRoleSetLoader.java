package com.dereekb.gae.server.auth.security.model.context.service;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextRoleSet;

/**
 * Used for building roles for a specific model.
 * 
 * @author dereekb
 *
 * @param <T>
 */
public interface LoginTokenModelContextRoleSetLoader<T> {

	/**
	 * Loads the roles for the input model.
	 * 
	 * @param model
	 *            Model. Never {@code null}.
	 * @return {@link LoginTokenModelContextRoleSet}. Never {@code null}.
	 */
	public LoginTokenModelContextRoleSet loadRolesForModel(T model);

}
