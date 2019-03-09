package com.dereekb.gae.server.datastore.objectify.query.iterator;

import com.dereekb.gae.server.datastore.models.query.ModelQueryIterable;
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
public interface ObjectifyQueryIterable<T>
        extends ModelQueryIterable<T>, IndexedIterable<T> {

	@Override
	public ObjectifyQueryIterator<T> iterator();

}
