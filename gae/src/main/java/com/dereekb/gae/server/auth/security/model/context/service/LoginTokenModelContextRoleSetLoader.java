package com.dereekb.gae.server.auth.security.model.context.service;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextRoleSet;
import com.dereekb.gae.server.auth.security.model.context.exception.NoModelContextRolesGrantedException;

/**
 * Used for building roles for a specific model.
 * 
 * @author dereekb
 *
 * @param <T>
 */
public interface LoginTokenModelContextRoleSetLoader<T> {

	/**
	 * Loads the roles for the input model. It is valid for this function to
	 * return an empty {@link LoginTokenModelContextRoleSet}.
	 * 
	 * @param model
	 *            Model. Never {@code null}.
	 * @return {@link LoginTokenModelContextRoleSet}. Never {@code null}.
	 * @throws NoModelContextRolesGrantedException
	 *             thrown if the implementation decides that the model should
	 *             not be given roles.
	 */
	public LoginTokenModelContextRoleSet loadRolesForModel(T model) throws NoModelContextRolesGrantedException;

}
