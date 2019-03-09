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
@Deprecated
public interface ObjectifyDatabaseEntityModifier<T extends ObjectifyModel<T>> {

	/**
	 * Whether or not to perform changes asynchronously.
	 * 
	 * @return {@code true} if asynchronous.
	 */
	public boolean isAsync();

	// MARK: Put
	/**
	 * Performs a put on multiple entities.
	 * 
	 * @param entity
	 *            Entity. Can be {@code null}.
	 */
	public void put(T entity);

	/**
	 * Performs a put on multiple entities.
	 * 
	 * @param entities
	 *            Iterable of entities. Can be {@code null}.
	 */
	public void put(Iterable<T> entities);

	// MARK: Delete
	public void delete(T entity);

	public void deleteWithKey(Key<T> key);

	@Deprecated
	public void delete(Ref<T> ref);

	public void delete(Iterable<T> list);

	public void deleteWithKeys(Iterable<Key<T>> list);

}
