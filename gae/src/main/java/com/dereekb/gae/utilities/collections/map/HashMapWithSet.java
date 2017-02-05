package com.dereekb.gae.utilities.collections.map;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * {@link HashMapWithCollection} extension that uses {@link HashSet}.
 *
 * @author dereekb
 *
 * @param <T>
 *            key type
 * @param <U>
 *            value type
 */
public class HashMapWithSet<T, U> extends HashMapWithCollection<T, U, Set<U>> {

	public HashMapWithSet() {
		super();
	}

	public HashMapWithSet(Map<? extends T, ? extends Collection<? extends U>> map) {
		super(map);
	}

	public HashMapWithSet(Iterable<MapPairing<? extends T, ? extends U>> pairings) {
		super(pairings);
	}

	// MARK: Override
	@Override
	protected Set<U> makeCollection() {
		return new HashSet<U>();
	}

}
