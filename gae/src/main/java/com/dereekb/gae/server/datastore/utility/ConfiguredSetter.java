package com.dereekb.gae.server.datastore.utility;

import com.dereekb.gae.server.datastore.Setter;

/**
 * Setter that is already configured to save synchronously or asynchronously.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ConfiguredSetter<T>
        extends Setter<T> {

	/**
	 * Saves a single entity.
	 *
	 * @param entity
	 *            Entity model to save to a source.
	 */
	public void save(T entity);

	/**
	 * Saves an iterable list of entities to a source. Saving synchronously will
	 * update entities with identifiers, if they are not already set.
	 *
	 * @param entities
	 *            Iterable list of models to save to a source.
	 */
	public void save(Iterable<T> entities);

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
