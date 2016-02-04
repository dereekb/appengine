package com.dereekb.gae.server.datastore.objectify.query.builder;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequest;

/**
 * Used for configuring a {@link ObjectifyQueryRequest}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyQueryRequestConfigurer<T extends ObjectifyModel<T>> {

	/**
	 * Configures the input request.
	 *
	 * @param request
	 *            {@link ObjectifyQueryRequest}. Never {@code null}.
	 */
	public void configure(ObjectifyQueryRequest<T> request);

}
