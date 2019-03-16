package com.dereekb.gae.server.datastore.objectify.core;

import java.util.Map;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Result;

/**
 * Used for modifying the Objectify database.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyDatabaseEntityWriter<T extends ObjectifyModel<T>> {

	// MARK: Put
	/**
	 * Performs a put on multiple entities.
	 * 
	 * @param entity
	 *            Entity. Never {@code null}.
	 */
	public Result<Key<T>> put(T entity) throws IllegalArgumentException;

	/**
	 * Performs a put on multiple entities.
	 * 
	 * @param entities
	 *            Iterable of entities. Never {@code null}.
	 */
	public Result<Map<Key<T>, T>> put(Iterable<T> entities) throws IllegalArgumentException;

	// MARK: Delete
	public Result<Void> delete(T entity) throws IllegalArgumentException;

	public Result<Void> deleteWithKey(Key<T> key) throws IllegalArgumentException;

	public Result<Void> delete(Iterable<T> list) throws IllegalArgumentException;

	public Result<Void> deleteWithKeys(Iterable<Key<T>> list) throws IllegalArgumentException;

}
