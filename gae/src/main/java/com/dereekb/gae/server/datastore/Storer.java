package com.dereekb.gae.server.datastore;

import com.dereekb.gae.server.datastore.exception.StoreKeyedEntityException;

/**
 * Interface used for saving new, uninitialized items.
 * 
 * @author dereekb
 *
 * @param <T>
 *            Model type.
 */
public interface Storer<T> {

	/**
	 * Stores a new entity.
	 * 
	 * If the entity has a key attached to it, the implementation has the choice
	 * to throw an exception or accept it.
	 * 
	 * When the function returns, the entity will have an identifier.
	 * 
	 * @param entity
	 *            Entity. Never {@code null}.
	 * @throws StoreKeyedEntityException
	 *             thrown if attempting to store a model that has been stored
	 *             already.
	 */
	public void store(T entity) throws StoreKeyedEntityException;

	/**
	 * Stores new entities.
	 * 
	 * When the function returns, the entity will have an identifier.
	 *
	 * @param entities
	 *            Entities. Never {@code null}.
	 * @throws StoreKeyedEntityException
	 *             thrown if attempting to store a model that has been stored
	 *             already.
	 */
	public void store(Iterable<T> entities) throws StoreKeyedEntityException;

}
