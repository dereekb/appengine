package com.dereekb.gae.server.datastore;

/**
 * {@link Storer} extension that allows force-storing objects.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ForceStorer<T>
        extends Storer<T> {

	/**
	 * Forces storing an entity, synchronously.
	 * 
	 * @param entity
	 *            Entity. Never {@code null}.
	 */
	public void forceStore(T entity);

	/**
	 * Forces storing entities, synchronously.
	 *
	 * @param entities
	 *            Entities. Never {@code null}.
	 */
	public void forceStore(Iterable<T> entities);

}
