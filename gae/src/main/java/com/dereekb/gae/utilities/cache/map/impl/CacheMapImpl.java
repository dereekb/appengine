package com.dereekb.gae.utilities.cache.map.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.dereekb.gae.utilities.cache.map.CacheMap;
import com.dereekb.gae.utilities.cache.map.CacheMapDelegate;

/**
 * {@link CacheMap} implementation.
 * 
 * Uses {@link ConcurrentHashMap} internally for thread safety.
 * 
 * @author dereekb
 *
 * @param <K>
 *            key type
 * @param <T>
 *            model type
 */
public class CacheMapImpl<K, T>
        implements CacheMap<K, T> {

	private Map<K, T> cache;
	private CacheMapDelegate<K, T> delegate;

	public CacheMapImpl(CacheMapDelegate<K, T> delegate) throws IllegalArgumentException {
		this(null, delegate);
	}

	public CacheMapImpl(Map<K, T> cache, CacheMapDelegate<K, T> delegate) throws IllegalArgumentException {
		this.setCache(cache);
		this.setDelegate(delegate);
	}

	public Map<K, T> getCache() {
		return this.cache;
	}

	private void setCache(Map<K, T> cache) {
		if (cache == null) {
			cache = new ConcurrentHashMap<K, T>();
		}

		this.cache = cache;
	}

	public CacheMapDelegate<K, T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(CacheMapDelegate<K, T> delegate) throws IllegalArgumentException {
		if (delegate == null) {
			throw new IllegalArgumentException("Delegate is required.");
		}

		this.delegate = delegate;
	}

	// MARK: CacheMap
	public boolean isCached(K key) {
		return this.cache.containsKey(key);
	}

	@Override
	public T get(K key) throws IllegalArgumentException {
		if (this.isCached(key) == false) {
			this.cache.put(key, this.delegate.makeCacheElement(key));
		}

		return this.cache.get(key);
	}

}
