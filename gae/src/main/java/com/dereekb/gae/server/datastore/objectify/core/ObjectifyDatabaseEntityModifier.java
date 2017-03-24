package com.dereekb.gae.server.datastore.objectify.core;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

/**
 * Used for modifying the Objectify database.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyDatabaseEntityModifier<T extends ObjectifyModel<T>> {

	@Deprecated
	public boolean isAsyncronous();

	// MARK: Put
	public void put(T entity);

	public void put(Iterable<T> entities);

	// MARK: Delete
	public void delete(T entity);

	public void delete(Key<T> key);

	public void delete(Ref<T> ref);

	public void delete(Iterable<T> list);

	public void deleteWithKeys(Iterable<Key<T>> list);

}
