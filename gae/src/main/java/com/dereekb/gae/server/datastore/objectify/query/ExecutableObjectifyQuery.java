package com.dereekb.gae.server.datastore.objectify.query;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;

/**
 * {@link ConfiguredObjectifyQuery} that allows direct queries.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ExecutableObjectifyQuery<T extends ObjectifyModel<T>>
        extends ObjectifyQuery<T>, ConfiguredObjectifyQuery<T> {

}
