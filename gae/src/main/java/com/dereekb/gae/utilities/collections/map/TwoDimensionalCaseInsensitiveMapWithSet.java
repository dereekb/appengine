package com.dereekb.gae.utilities.collections.map;

import java.util.Map;
import java.util.Set;

/**
 * {@link CaseInsensitiveMap} that wraps another {@link HashMapWithCollection} and provides functions for adding tuple keys.
 *  
 * @author dereekb
 *
 */
public class TwoDimensionalCaseInsensitiveMapWithSet<K, T> extends TwoDimensionalCaseInsensitiveMapWithCollection<K, T, Set<T>, HashMapWithSet<K, T>> {

	private static final long serialVersionUID = 1L;

	public TwoDimensionalCaseInsensitiveMapWithSet() {
		super();
	}

	public TwoDimensionalCaseInsensitiveMapWithSet(Map<? extends String, ? extends HashMapWithSet<K, T>> m) {
		super(m);
	}

	// MARK: Internal
	@Override
	protected HashMapWithSet<K, T> makeNewHashMapCollection() {
		return new HashMapWithSet<K, T>();
	}
	
}
