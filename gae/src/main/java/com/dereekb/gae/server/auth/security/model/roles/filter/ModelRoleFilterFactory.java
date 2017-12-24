package com.dereekb.gae.server.auth.security.model.roles.filter;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.utilities.filters.Filter;

/**
 * Factory for a {@link Filter} for filtering objects with a specific role in the current security
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelRoleFilterFactory<T> {

	/**
	 * Creates a filter for objects with a specific role in the current security
	 * context.
	 *
	 * @param role
	 *            {@link ModelRole}. Never {@code null}.
	 * @return {@link Filter}. Never {@code null}.
	 */
	public Filter<T> makeRoleFilter(ModelRole role);

}
