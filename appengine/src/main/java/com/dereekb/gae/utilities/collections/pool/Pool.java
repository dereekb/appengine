package com.dereekb.gae.utilities.collections.pool;

import java.util.Set;

/**
 * Abstract pool of entities.
 * 
 * @author dereekb
 *
 * @param <T>
 *            Pool type
 * 
 * @see QueryPool
 */
public abstract interface Pool<T extends Pool<T>> {

	/**
	 * Returns the set of all child {@link QueryPool} keys.
	 * 
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<String> getChildKeys();

	/**
	 * Returns the child match pool.
	 * 
	 * @param key
	 *            {@link String}. Never {@code null}.
	 * @return {@link QueryPool}, or {@code null} if it doesn't exist.
	 */
	public T getChildPool(String key);

}
