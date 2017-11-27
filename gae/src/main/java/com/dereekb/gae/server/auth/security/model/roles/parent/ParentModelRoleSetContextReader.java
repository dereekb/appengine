package com.dereekb.gae.server.auth.security.model.roles.parent;

import com.dereekb.gae.model.exception.NoParentException;
import com.dereekb.gae.server.auth.security.model.roles.loader.AnonymousModelRoleSetContext;

/**
 * Used for "child" models to load the parent context.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ParentModelRoleSetContextReader<T> {

	/**
	 * Returns the roles context for the parent. An
	 * {@link AnonymousModelRoleSetContext} is always returned, regardless of
	 * whether or not the parent exists.
	 *
	 * @param child
	 *            Model. Never {@code null}.
	 * @return {@link AnonymousModelRoleSetContext}. Never {@code null}.
	 * @throws NoParentException
	 *             if the child has no parent associated with it.
	 */
	public AnonymousModelRoleSetContext getParentRoleSetContext(T child) throws NoParentException;

}
