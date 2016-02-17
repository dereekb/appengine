package com.dereekb.gae.server.datastore.objectify.query.iterator;

import com.dereekb.gae.server.datastore.models.query.ModelQueryIterator;
import com.dereekb.gae.utilities.collections.iterator.index.IndexedIterator;

/**
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyQueryIterator<T>
        extends ModelQueryIterator<T>, IndexedIterator<T> {

}
