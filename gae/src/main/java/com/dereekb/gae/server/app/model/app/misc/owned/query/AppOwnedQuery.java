package com.dereekb.gae.server.app.model.app.misc.owned.query;

import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder.ModelKeyQueryFieldParameter;

/**
 * Query for an {@link App}-related model that can query by app.
 *
 * @author dereekb
 *
 */
public interface AppOwnedQuery {

	/**
	 * Returns the app parameter, if set.
	 *
	 * @return {@link ModelKeyQueryFieldParameter}, or {@code null} if not set.
	 */
	public ModelKeyQueryFieldParameter getApp();

}
