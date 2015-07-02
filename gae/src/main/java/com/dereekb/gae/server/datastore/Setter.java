package com.dereekb.gae.server.datastore;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Interface for saving and deleting models.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model type.
 * @param <K>
 *            Key/Identifier type of the model.
 * @see {@link Getter} for retrieving models from the source.
 * @see {@link Deleter} for deleting using a {@link ModelKey}.
 */
public interface Setter<T> {

	/**
	 * Saves a single entity. Saving synchronously will update entities with identifiers, if
	 * they are not already set.
	 *
	 * @param entity
	 *            Entity model to save to a source.
	 * @param async
	 *            Whether or not to save asynchronously.
	 */
	public void save(T entity,
	                 boolean async);

	/**
	 * Saves an iterable list of entities to a source. Saving synchronously will update entities with identifiers, if
	 * they are not already set.
	 *
	 * @param entities
	 *            Iterable list of models to save to a source.
	 * @param async
	 *            Whether or not to save asynchronously.
	 */
	public void save(Iterable<T> entities,
	                 boolean async);

	/**
	 * Deletes an entity. If the entity does not have an identifier, the function does nothing.
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
