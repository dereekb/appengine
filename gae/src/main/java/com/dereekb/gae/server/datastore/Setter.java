package com.dereekb.gae.server.datastore;

/**
 * Interface for saving and deleting models.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model type.
 * 
 * @see Getter
 * @see Deleter
 */
public interface Setter<T>
        extends Saver<T> {

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
