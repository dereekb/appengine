package com.dereekb.gae.utilities.collections.map;

import java.util.Set;

/**
 * Map with only get/set/keys functionality.
 * 
 * @author dereekb
 *
 */
public interface PseudoMap<K, T> {

	public Set<K> keys();

	public T get(K key);

	public void set(K key,
	                T value);

}
