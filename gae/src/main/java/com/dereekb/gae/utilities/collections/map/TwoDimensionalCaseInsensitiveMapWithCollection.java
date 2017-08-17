package com.dereekb.gae.utilities.collections.map;

import java.util.Collection;
import java.util.Map;

/**
 * {@link CaseInsensitiveMap} that wraps another {@link HashMapWithCollection} and provides functions for adding tuple keys.
 *  
 * @author dereekb
 *
 */
public abstract class TwoDimensionalCaseInsensitiveMapWithCollection<K, T, C extends Collection<T>, H extends HashMapWithCollection<K, T, C>> extends CaseInsensitiveMap<H> {

	private static final long serialVersionUID = 1L;

	public TwoDimensionalCaseInsensitiveMapWithCollection() {
		super();
	}

	public TwoDimensionalCaseInsensitiveMapWithCollection(Map<? extends String, ? extends H> m) {
		super(m);
	}

	// MARK: Put
	public void add(String name, K key, T object) {
		this.getOrMakeHashMapCollection(name).add(key, object);
	}

	public void addAll(String name, 
	                   Iterable<? extends K> keys,
	                   T object) {
		this.getOrMakeHashMapCollection(name).addAll(keys, object);
	}

	// MARK: Internal
	protected abstract H makeNewHashMapCollection();
	
	protected H getOrMakeHashMapCollection(String name) {
		H map = this.get(name);
		
		if (map == null) {
			map = this.makeNewHashMapCollection();
		}
		
		return map;
	}
	
}
