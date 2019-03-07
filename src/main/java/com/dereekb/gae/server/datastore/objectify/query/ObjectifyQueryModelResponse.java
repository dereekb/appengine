package com.dereekb.gae.server.datastore.objectify.query;

import com.dereekb.gae.server.datastore.models.query.IndexedModelQueryModelResponse;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.google.cloud.datastore.QueryResults;


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
	 * Retrieves a raw {@link QueryResults} instance.
	 *
	 * @return {@link QueryResults}. Never {@code null}.
	 */
	public QueryResults<T> objectifyQueryModelsIterator();

}
