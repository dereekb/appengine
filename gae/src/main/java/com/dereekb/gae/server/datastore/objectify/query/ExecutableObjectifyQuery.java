package com.dereekb.gae.server.datastore.objectify.query;

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
        extends ObjectifyQueryResponse<T>, ObjectifyQueryRequest<T> {

}
