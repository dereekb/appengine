package com.dereekb.gae.utilities.cache.map;

/**
 * Cache that creates models if they don't exist and adds them to the cache.
 * 
 * Useful for creating singletons mapped to things.
 * 
 * @author dereekb
 *
 * @param <K>
 *            key type
 * @param <T>
 *            model type
 */
public interface CacheMap<K, T> {

	/**
	 * Gets the cached valued if it exists, or creatse a new one.
	 * 
	 * @param key
	 *            Key. Never {@code null}.
	 * @return cached model.
	 * @throws IllegalArgumentException
	 */
	public T get(K key) throws IllegalArgumentException;

}
