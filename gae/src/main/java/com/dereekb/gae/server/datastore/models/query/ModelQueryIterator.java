package com.dereekb.gae.server.datastore.models.query;

import java.util.Iterator;

import com.dereekb.gae.utilities.collections.iterator.cursor.ResultsCursorIterator;

/**
 * Special model {@link Iterator} that provides cursor information.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelQueryIterator<T>
        extends ResultsCursorIterator<T> {

}
