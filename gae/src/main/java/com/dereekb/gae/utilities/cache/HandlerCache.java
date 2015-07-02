package com.dereekb.gae.utilities.cache;

/**
 * Base class for caching a given value with a key. 
 * 
 * Is just a basic immutable tuple.
 * @author dereekb
 *
 * @param <T> Type to be cached.
 * @param <K> Type to act as the key.
 */
public abstract class HandlerCache<T, K> {
	protected final K key;
	protected final T cachedValue;
	
	public HandlerCache(K key, T cachedValue) {
		this.key = key;
		this.cachedValue = cachedValue;
	}

	public K getKey() {
		return key;
	}

	public T getCachedValue() {
		return cachedValue;
	}

}