package com.dereekb.gae.server.datastore;

/**
 * Interface for deleting models using models.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @see Deleter
 */
public interface ModelDeleter<T> {

	/**
	 * Deletes an entity. If the entity does not have an identifier, the
	 * function does nothing.
	 *
	 * @param entity
	 *            Entity model to delete from a source.
	 * @param async
	 *            Whether or not to delete asynchronously.
	 */
	public void delete(T entity,
	                   boolean async);

	/**
	 * Deletes a list of entities. Entities without identifiers are ignored.
	 *
	 * @param entities
	 *            Iterable list of models to delete from a source.
	 * @param async
	 *            Whether or not to delete asynchronously.
	 */
	public void delete(Iterable<T> entities,
	                   boolean async);

}
