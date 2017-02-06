package com.dereekb.gae.utilities.cache;

import com.dereekb.gae.utilities.collections.pairs.HandlerPair;

/**
 * Base class for caching a given value with a key.
 * 
 * Is just a basic immutable tuple.
 * 
 * @author dereekb
 *
 * @param <T>
 *            Type to be cached.
 * @param <K>
 *            Type to act as the key.
 * @deprecated {@link HandlerPair} does the same thing...
 */
@Deprecated
public abstract class CachePair<T, K> extends HandlerPair<K, T> {

	public CachePair(K key, T object) {
		super(key, object);
	}

	public T getCachedValue() {
		return this.getObject();
	}

}