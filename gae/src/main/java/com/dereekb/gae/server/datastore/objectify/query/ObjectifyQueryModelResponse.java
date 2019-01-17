package com.dereekb.gae.server.datastore.objectify.query;

import com.dereekb.gae.server.datastore.models.query.IndexedModelQueryModelResponse;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.google.appengine.api.datastore.QueryResultIterator;

/**
 * {@link ObjectifyQueryResponse} extension that provides model results too.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyQueryModelResponse<T extends ObjectifyModel<T>>
        extends IndexedModelQueryModelResponse<T>, ObjectifyQueryKeyResponse<T> {

	/**
	 * Retrieves a raw {@link QueryResultIterator} instance.
	 *
	 * @return {@link QueryResultIterator}. Never {@code null}.
	 */
	public QueryResultIterator<T> objectifyQueryModelsIterator();

}
