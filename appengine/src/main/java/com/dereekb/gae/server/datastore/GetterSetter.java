package com.dereekb.gae.server.datastore;

import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Merged implementation of {@link Getter} and {@link Setter}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface GetterSetter<T extends UniqueModel>
        extends Getter<T>, Setter<T> {}
