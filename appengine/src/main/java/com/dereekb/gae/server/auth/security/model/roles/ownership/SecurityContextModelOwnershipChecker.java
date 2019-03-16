package com.dereekb.gae.server.auth.security.model.roles.ownership;

import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;

/**
 * Checks whether or not the model is owned by the user in the current security
 * context.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface SecurityContextModelOwnershipChecker<T> {

	/**
	 * Whether or not the specified model is owned in the current context.
	 *
	 * @param model
	 *            Model. Never {@code null}.
	 * @return {@code true} if the input model is considered owned.
	 */
	public boolean isOwnedInSecurityContext(T model) throws NoSecurityContextException;

}
