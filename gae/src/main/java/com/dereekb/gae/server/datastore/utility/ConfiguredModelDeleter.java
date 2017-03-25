package com.dereekb.gae.server.datastore.utility;

import com.dereekb.gae.server.datastore.ModelDeleter;

/**
 * Deleter that is already configured to delete synchronously or asynchronously.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ConfiguredModelDeleter<T>
        extends ModelDeleter<T> {

	/**
	 * Deletes an entity. If the entity does not have an identifier, the
	 * function does nothing.
	 *
	 * @param entity
	 *            Entity model to delete from a source.
	 */
	public void delete(T entity);

	/**
	 * Deletes a list of entities. Entities without identifiers are ignored.
	 *
	 * @param entities
	 *            Iterable list of models to delete from a source.
	 */
	public void delete(Iterable<T> entities);

}
