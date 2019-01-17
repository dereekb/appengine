package com.dereekb.gae.server.datastore.objectify.query;

import java.util.Map;

import com.dereekb.gae.server.datastore.models.query.IndexedModelQueryRequestBuilderFactory;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.utilities.query.exception.IllegalQueryArgumentException;

/**
 * Used for building new {@link ObjectifyQueryRequest} instances.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyQueryRequestBuilderFactory<T extends ObjectifyModel<T>> extends IndexedModelQueryRequestBuilderFactory<T> {

	/**
	 * Creates a new {@link ObjectifyQueryRequestBuilder}.
	 *
	 * @return {@link ObjectifyQueryRequestBuilder}. Never {@code null}.
	 */
	@Override
	public ObjectifyQueryRequestBuilder<T> makeQuery();

	/**
	 * Creates a new {@link ObjectifyQueryRequestBuilder} and initializes it
	 * with the input parameters.
	 *
	 * @param parameters
	 *            Optional {@link Map} of parameters.
	 * @return {@link ObjectifyQueryRequestBuilder}. Never {@code null}.
	 * @throws IllegalQueryArgumentException
	 *             if the input parameters have one or more invalid values.
	 */
	@Override
	public ObjectifyQueryRequestBuilder<T> makeQuery(Map<String, String> parameters)
	        throws IllegalQueryArgumentException;

}
