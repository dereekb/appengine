package com.dereekb.gae.server.datastore.models.query;

import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.google.appengine.api.datastore.QueryResultIterator;

/**
 * {@link IndexedModelQueryKeyResponse} extension that provides model query results.
 *
 * @author dereekb
 *
 */
public interface IndexedModelQueryModelResponse<T extends UniqueModel>
        extends IndexedModelQueryKeyResponse {

	/**
	 * Retrieves models that meet the query parameters.
	 *
	 * @return {@link List} of matching models.
	 */
	public List<T> queryModels();

	/**
	 * {@link IndexedModelQueryModelResultIterator} instance for iterating over
	 * results.
	 *
	 * @return {@link QueryResultIterator}. Never {@code null}.
	 */
	public IndexedModelQueryModelResultIterator<T> queryModelResultsIterator();

}
