package com.dereekb.gae.server.datastore;

import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Merged implementation of {@link GetterSetter} with force functions.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ForceGetterSetter<T extends UniqueModel>
        extends GetterSetter<T>, ForceSetter<T> {}
