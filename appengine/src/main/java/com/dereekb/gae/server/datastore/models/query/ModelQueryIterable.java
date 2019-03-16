package com.dereekb.gae.server.datastore.models.query;

/**
 * Special {@link Iterable}
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelQueryIterable<T>
        extends Iterable<T> {

	@Override
	public ModelQueryIterator<T> iterator();

}
