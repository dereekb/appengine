package com.dereekb.gae.server.datastore.objectify.query;

import java.util.List;

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
        extends ObjectifyQueryKeyResponse<T> {

	/**
	 * Retrieves models that meet the query parameters.
	 *
	 * @return {@link List} of matching models.
	 */
	public List<T> queryModels();

	/**
	 * Retrieves a raw {@link QueryResultIterator} instance.
	 *
	 * @return {@link QueryResultIterator}. Never {@code null}.
	 */
	public QueryResultIterator<T> queryModelsIterator();

}
