package com.dereekb.gae.server.datastore.models.query.iterator;

import com.dereekb.gae.server.datastore.models.query.ModelQueryIterable;
import com.dereekb.gae.server.datastore.objectify.query.iterator.ObjectifyQueryIterableFactory;
import com.dereekb.gae.utilities.collections.iterator.cursor.ResultsCursorIterable;
import com.dereekb.gae.utilities.collections.iterator.index.IndexedIterable;

/**
 * {@link ModelQueryIterable} and {@link IndexedIterable} interface extension.
 *
 * Is used as a second layer iterator over query results, allowing the user to
 * iterate over the entire database.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 *
 * @see ObjectifyQueryIterableFactory
 */
public interface IndexedModelQueryIterable<T>
        extends ModelQueryIterable<T>, ResultsCursorIterable<T> {

	@Override
	public IndexedModelQueryIterator<T> iterator();

}
