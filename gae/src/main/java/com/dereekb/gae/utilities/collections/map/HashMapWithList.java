package com.dereekb.gae.utilities.collections.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HashMapWithList<T, U> extends HashMapWithCollection<T, U, List<U>> {

	@Override
	protected List<U> makeCollection() {
		return new ArrayList<U>();
	}

	public HashMapWithList() {
		super();
	}

	public HashMapWithList(HashMapWithList<T, U> listMap) {
		super(listMap);
	}

	public HashMapWithList(Collection<MapPairing<T, U>> pairings) {
		super(pairings);
	}

}
