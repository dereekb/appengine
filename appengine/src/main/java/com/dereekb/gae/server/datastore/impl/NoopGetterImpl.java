package com.dereekb.gae.server.datastore.impl;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.exception.UninitializedModelException;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link Getter} implementation that does nothing.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class NoopGetterImpl<T extends UniqueModel>
        implements Getter<T> {

	@Override
	public T get(T model) throws UninitializedModelException {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<T> get(Iterable<T> models) throws UninitializedModelException {
		throw new UnsupportedOperationException();
	}

	@Override
	public T get(ModelKey key) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<T> getWithKeys(Iterable<ModelKey> keys) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getModelType() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean exists(T model) throws UninitializedModelException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean exists(ModelKey key) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean allExist(Iterable<ModelKey> keys) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<ModelKey> getExisting(Iterable<ModelKey> keys) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

}
