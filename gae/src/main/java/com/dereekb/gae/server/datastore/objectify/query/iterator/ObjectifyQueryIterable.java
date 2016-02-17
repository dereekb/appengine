package com.dereekb.gae.server.datastore.objectify.query.iterator;

import com.dereekb.gae.server.datastore.models.query.ModelQueryIterable;
import com.dereekb.gae.utilities.collections.iterator.index.IndexedIterable;

/**
 * {@link ModelQueryIterable} and {@link IndexedIterable} implementations used
 * by Objectify iterators.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 *
 * @see {@link ObjectifyQueryIterableFactory}
 */
public interface ObjectifyQueryIterable<T>
        extends ModelQueryIterable<T>, IndexedIterable<T> {

	@Override
	public ObjectifyQueryIterator<T> iterator();

}
