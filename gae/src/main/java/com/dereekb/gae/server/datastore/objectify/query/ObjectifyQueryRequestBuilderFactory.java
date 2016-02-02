package com.dereekb.gae.server.datastore.objectify.query;

import java.util.Map;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;

/**
 * Used for building new {@link ObjectifyQueryRequest} instances.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyQueryRequestBuilderFactory<T extends ObjectifyModel<T>> {

	/**
	 * Creates a new {@link ObjectifyQueryRequestBuilder}.
	 *
	 * @return {@link ObjectifyQueryRequestBuilder}. Never {@code null}.
	 */
	public ObjectifyQueryRequestBuilder<T> makeQuery();

	/**
	 * Creates a new {@link ObjectifyQueryRequestBuilder} and initializes it
	 * with the input parameters.
	 *
	 * @param parameters
	 *            Optional {@link Map} of parameters.
	 * @return {@link ObjectifyQueryRequestBuilder}. Never {@code null}.
	 */
	public ObjectifyQueryRequestBuilder<T> makeQuery(Map<String, String> parameters);

}
