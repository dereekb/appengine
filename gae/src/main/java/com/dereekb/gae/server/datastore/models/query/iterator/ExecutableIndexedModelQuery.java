package com.dereekb.gae.server.datastore.models.query.iterator;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.query.IndexedModelQueryRequest;
import com.dereekb.gae.server.datastore.models.query.IndexedModelQueryResponse;

/**
 * Wraps the {@link IndexedModelQueryResponse} along with the
 * {@link IndexedModelQueryRequest}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ExecutableIndexedModelQuery<T extends UniqueModel>
        extends IndexedModelQueryResponse, IndexedModelQueryRequest {

}
