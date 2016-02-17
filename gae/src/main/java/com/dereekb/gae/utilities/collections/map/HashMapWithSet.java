package com.dereekb.gae.utilities.collections.map;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link HashMapWithCollection} extension that uses {@link HashSet}.
 *
 * @author dereekb
 *
 * @param <T>
 *            key type
 * @param <U>
 *            model type
 */
public class HashMapWithSet<T, U> extends HashMapWithCollection<T, U, Set<U>> {

	@Override
	protected Set<U> makeCollection() {
		return new HashSet<U>();
	}

	public HashMapWithSet() {
		super();
	}

	public HashMapWithSet(HashMapWithCollection<? extends T, ? extends U, ?> map) {
		super(map);
	}

	public HashMapWithSet(Collection<MapPairing<? extends T, ? extends U>> pairings) {
		super(pairings);
	}

}
