package com.dereekb.gae.server.datastore.objectify;

import com.dereekb.gae.server.datastore.models.MutableUniqueModel;

/**
 * Mutable {@link ObjectifyModel}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface MutableObjectifyModel<T>
        extends ObjectifyModel<T>, MutableUniqueModel {

}
