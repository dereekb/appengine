package com.dereekb.gae.server.datastore;

import java.util.List;

import com.dereekb.gae.server.datastore.exception.UpdateUnkeyedEntityException;

/**
 * Interface for saving models that have previously been created via a
 * {@link Storer} (I.E. already have an identifier and are in the system).
 * <p>
 * By default, the updater saves synchronously to ensure that the datastore is
 * updated immediately to reflect the changes.
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
	 * @return {@code true} if entity was updated.
	 * @throws UpdateUnkeyedEntityException
	 *             thrown if attempting to update a model that has not yet been
	 *             created via {@link Storer}.
	 */
	public boolean update(T entity) throws UpdateUnkeyedEntityException;

	/**
	 * Updates the input entities synchronously.
	 * 
	 * @param entities
	 *            Entities. Never {@code null}.
	 * @return {@link List} of updated entities.
	 * @throws UpdateUnkeyedEntityException
	 *             thrown if attempting to update a model that has not yet been
	 *             created via {@link Storer}.
	 */
	public List<T> update(Iterable<T> entities) throws UpdateUnkeyedEntityException;

	/**
	 * Updates the input entity asynchronously.
	 * 
	 * @param entity
	 *            Entity. Never {@code null}.
	 * @return {@code true} if entity was updated.
	 * @throws UpdateUnkeyedEntityException
	 *             thrown if attempting to update a model that has not yet been
	 *             created via {@link Storer}.
	 */
	public boolean updateAsync(T entity) throws UpdateUnkeyedEntityException;

	/**
	 * Updates the input entities asynchronously.
	 * 
	 * @param entities
	 *            Entities. Never {@code null}.
	 * @return {@link List} of updated entities.
	 * @throws UpdateUnkeyedEntityException
	 *             thrown if attempting to update a model that has not yet been
	 *             created via {@link Storer}.
	 */
	public List<T> updateAsync(Iterable<T> entities) throws UpdateUnkeyedEntityException;

}
