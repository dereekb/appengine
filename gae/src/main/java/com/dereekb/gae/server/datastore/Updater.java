package com.dereekb.gae.server.datastore;

import com.dereekb.gae.server.datastore.exception.UpdateUnkeyedEntityException;

/**
 * Interface for saving models that had been previously created via a
 * {@link Storer}.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model type.
 */
public interface Updater<T> {

	/**
	 * Updates the input entity synchronously.
	 * 
	 * @param entity
	 *            Entity. Never {@code null}.
	 * @throws UpdateUnkeyedEntityException
	 *             thrown if attempting to update a model that has not yet been
	 *             created via {@link Storer}.
	 */
	public void update(T entity) throws UpdateUnkeyedEntityException;

	/**
	 * Updates the input entities synchronously.
	 * 
	 * @param entities
	 *            Entities. Never {@code null}.
	 * @throws UpdateUnkeyedEntityException
	 *             thrown if attempting to update a model that has not yet been
	 *             created via {@link Storer}.
	 */
	public void update(Iterable<T> entities) throws UpdateUnkeyedEntityException;

	/**
	 * Updates the input entity asynchronously.
	 * 
	 * @param entity
	 *            Entity. Never {@code null}.
	 * @throws UpdateUnkeyedEntityException
	 *             thrown if attempting to update a model that has not yet been
	 *             created via {@link Storer}.
	 */
	public void updateAsync(T entity) throws UpdateUnkeyedEntityException;

	/**
	 * Updates the input entities asynchronously.
	 * 
	 * @param entities
	 *            Entities. Never {@code null}.
	 * @throws UpdateUnkeyedEntityException
	 *             thrown if attempting to update a model that has not yet been
	 *             created via {@link Storer}.
	 */
	public void updateAsync(Iterable<T> entities) throws UpdateUnkeyedEntityException;

}
