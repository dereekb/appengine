package com.dereekb.gae.server.datastore.models.query;

import java.util.Map;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.query.exception.IllegalQueryArgumentException;

/**
 * Used for building {@link IndexedModelQueryRequestBuilder} instances.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface IndexedModelQueryRequestBuilderFactory<T extends UniqueModel> {

	/**
	 * Creates a new {@link IndexedModelQueryRequestBuilder} with no parameters.
	 *
	 * @return {@link IndexedModelQueryRequestBuilder}. Never {@code null}.
	 */
	public IndexedModelQueryRequestBuilder<T> makeQuery();

	/**
	 * Creates a new {@link IndexedModelQueryRequestBuilder} and initializes it
	 * with the input parameters.
	 *
	 * @param parameters
	 *            Optional {@link Map} of parameters.
	 * @return {@link IndexedModelQueryRequestBuilder}. Never {@code null}.
	 * @throws IllegalQueryArgumentException
	 *             if the input parameters have one or more invalid values.
	 */
	public IndexedModelQueryRequestBuilder<T> makeQuery(Map<String, String> parameters)
	        throws IllegalQueryArgumentException;

}
