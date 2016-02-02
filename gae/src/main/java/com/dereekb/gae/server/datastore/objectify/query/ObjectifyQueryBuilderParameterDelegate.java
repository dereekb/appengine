package com.dereekb.gae.server.datastore.objectify.query;

import java.util.Map;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;

/**
 * Delegate used to initialize an {@link ObjectifyQueryBuilder}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyQueryBuilderParameterDelegate<T extends ObjectifyModel<T>> {

	/**
	 * Initializes the input builder using the provided parameters.
	 *
	 * @param builder
	 *            {@link ObjectifyQueryBuilder} to update. Never {@code null}.
	 * @param parameters
	 *            {@link Map} of optional parameters. Never {@code null}.
	 */
	public void updateBuilder(ObjectifyQueryBuilder<T> builder,
	                          Map<String, String> parameters);

}
