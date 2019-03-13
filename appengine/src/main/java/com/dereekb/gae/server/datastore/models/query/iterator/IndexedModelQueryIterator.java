package com.dereekb.gae.server.datastore.models.query.iterator;

import com.dereekb.gae.server.datastore.models.query.ModelQueryIterator;
import com.dereekb.gae.utilities.collections.iterator.cursor.ResultsCursorIterator;
import com.dereekb.gae.utilities.collections.iterator.index.IndexedIterator;

/**
 * Objectify iterator that implements both {@link ModelQueryIterator} and
 * {@link IndexedIterator}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface IndexedModelQueryIterator<T>
        extends ModelQueryIterator<T>, ResultsCursorIterator<T> {

}
