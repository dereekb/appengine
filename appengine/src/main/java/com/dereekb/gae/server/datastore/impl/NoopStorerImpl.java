package com.dereekb.gae.server.datastore.impl;

import com.dereekb.gae.server.datastore.Storer;
import com.dereekb.gae.server.datastore.exception.StoreKeyedEntityException;

/**
 * {@link Storer} implementation used to satisfy dependency injection.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class NoopStorerImpl<T>
        implements Storer<T> {

	@Override
	public void store(T entity) throws StoreKeyedEntityException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void store(Iterable<T> entities) throws StoreKeyedEntityException {
		throw new UnsupportedOperationException();

	}

}
