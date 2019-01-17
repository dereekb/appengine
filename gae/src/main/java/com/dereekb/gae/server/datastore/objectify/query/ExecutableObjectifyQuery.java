package com.dereekb.gae.server.datastore.objectify.query;

import com.dereekb.gae.server.datastore.models.query.iterator.ExecutableIndexedModelQuery;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;

/**
 * {@link ObjectifyQueryRequest} that allows direct queries.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ExecutableObjectifyQuery<T extends ObjectifyModel<T>>
        extends ExecutableIndexedModelQuery<T>, ObjectifyQueryModelResponse<T>, ObjectifyQueryRequest<T> {

}
