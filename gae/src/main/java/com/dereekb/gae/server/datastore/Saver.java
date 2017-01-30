package com.dereekb.gae.server.datastore;

/**
 * Interface for saving models.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model type.
 */
public interface Saver<T> {

	/**
	 * Saves a single entity. Saving synchronously will update entities with
	 * identifiers, if
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
	 * Saves an iterable list of entities to a source. Saving synchronously will
	 * update entities with identifiers, if
	 * they are not already set.
	 *
	 * @param entities
	 *            Iterable list of models to save to a source.
	 * @param async
	 *            Whether or not to save asynchronously.
	 */
	public void save(Iterable<T> entities,
	                 boolean async);

}
