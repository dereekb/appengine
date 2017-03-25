package com.dereekb.gae.server.datastore;

/**
 * Interface for deleting models using models.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @see KeyDeleter
 */
public interface ModelDeleter<T> {

	/**
	 * Deletes an entity synchronously. If the entity does not have an
	 * identifier, the function does nothing.
	 *
	 * @param entity
	 *            Entity model to delete from a source.
	 */
	public void delete(T entity);

	/**
	 * Deletes a list of entities synchronously. Entities without identifiers
	 * are ignored.
	 *
	 * @param entities
	 *            Iterable list of models to delete from a source.
	 */
	public void delete(Iterable<T> entities);

	/**
	 * Deletes an entity asynchronously. If the entity does not have an
	 * identifier, the function does nothing.
	 *
	 * @param entity
	 *            Entity model to delete from a source.
	 * @param async
	 *            Whether or not to delete asynchronously.
	 */
	public void deleteAsync(T entity);

	/**
	 * Deletes a list of entities asynchronously. Entities without identifiers
	 * are ignored.
	 * 
	 * @param entities
	 *            Iterable list of models to delete from a source.
	 */
	public void deleteAsync(Iterable<T> entities);

}
