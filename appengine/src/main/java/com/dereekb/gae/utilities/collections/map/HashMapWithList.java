package com.dereekb.gae.utilities.collections.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * {@link HashMapWithCollection} extension that uses {@link ArrayList}.
 *
 * @author dereekb
 *
 * @param <T>
 *            key type
 * @param <U>
 *            model type
 */
public class HashMapWithList<T, U> extends HashMapWithCollection<T, U, List<U>> {

	@Override
	protected List<U> makeCollection() {
		return new ArrayList<U>();
	}

	public HashMapWithList() {
		super();
	}

	public HashMapWithList(HashMapWithCollection<? extends T, ? extends U, ?> map) {
		super(map);
	}

	public HashMapWithList(Iterable<MapPairing<? extends T, ? extends U>> pairings) {
		super(pairings);
	}

	protected HashMapWithList(Map<? extends T, ? extends Collection<? extends U>> map) {
		super(map);
	}

}
