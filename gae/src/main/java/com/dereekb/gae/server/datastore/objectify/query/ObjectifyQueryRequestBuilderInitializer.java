package com.dereekb.gae.server.datastore.objectify.query;

import java.util.Map;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;

/**
 * Special type used to initialize an {@link ObjectifyQueryRequestBuilder}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyQueryRequestBuilderInitializer<T extends ObjectifyModel<T>>
        extends ObjectifyQueryRequestLimitedBuilderInitializer {

	/**
	 * Initializes the input builder using the provided parameters.
	 *
	 * @param builder
	 *            {@link ObjectifyQueryRequestBuilder} to update. Never {@code null}.
	 * @param parameters
	 *            {@link Map} of optional parameters. Never {@code null}.
	 */
	public void initalizeBuilder(ObjectifyQueryRequestBuilder<T> builder,
	                             Map<String, String> parameters);

}
