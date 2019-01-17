package com.dereekb.gae.server.datastore.models.query;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.query.iterator.ExecutableIndexedModelQuery;
import com.dereekb.gae.server.datastore.objectify.query.exception.InvalidQuerySortingException;

/**
 * {@link IndexedModelQueryRequest} configurer that produces
 * {@link ExecutableIndexedModelQuery}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface IndexedModelQueryRequestBuilder<T extends UniqueModel>
        extends IndexedModelQueryRequest {

	public ExecutableIndexedModelQuery<T> buildExecutableQuery() throws InvalidQuerySortingException;

}
