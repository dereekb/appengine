package com.dereekb.gae.server.auth.model.login.misc.owned.query;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder.ModelKeyQueryFieldParameter;

/**
 * Query for an {@link Login}-related model that can query by login.
 *
 * @author dereekb
 *
 */
public interface LoginOwnedQuery {

	/**
	 * Returns the login parameter, if set.
	 *
	 * @return {@link ModelKeyQueryFieldParameter}, or {@code null} if not set.
	 */
	public ModelKeyQueryFieldParameter getLogin();

}
