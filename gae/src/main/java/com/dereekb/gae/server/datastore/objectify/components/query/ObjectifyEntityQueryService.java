package com.dereekb.gae.server.datastore.objectify.components.query;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequest;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryResponse;

/**
 * Service for querying entities and keys.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyEntityQueryService<T extends ObjectifyModel<T>> {

	/**
	 * Performs a query.
	 *
	 * @param request
	 *            {@link ObjectifyQueryRequest} implementation.
	 * @return {@link ObjectifyQueryResponse}. Never {@code null}.
	 */
	public ObjectifyQueryResponse<T> query(ObjectifyQueryRequest<T> request);

}
