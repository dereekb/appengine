package com.dereekb.gae.utilities.cache.map;

/**
 * Used by a {@link CacheMap} to create a new cached value.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface CacheMapDelegate<K, T> {

	public T makeCacheElement(K key) throws IllegalArgumentException;

}
