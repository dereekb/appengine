package com.dereekb.gae.model.extension.visibility.impl;

import com.dereekb.gae.model.extension.visibility.VisibilityState;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.collections.pairs.SuccessResultsPair;

/**
 * Used by {@link VisibilityFunction}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public final class VisibilityPair<T> extends SuccessResultsPair<T> {

	private final VisibilityState change;

	public VisibilityPair(T source, VisibilityState change) {
		super(source);
		this.change = change;
	}

	public VisibilityState getChange() {
		return this.change;
	}

	public static <T> HashMapWithList<VisibilityState, VisibilityPair<T>> getChangesMap(Iterable<VisibilityPair<T>> pairs) {
		HashMapWithList<VisibilityState, VisibilityPair<T>> map = new HashMapWithList<VisibilityState, VisibilityPair<T>>();

		for (VisibilityPair<T> pair : pairs) {
			VisibilityState action = pair.getChange();
			map.add(action, pair);
		}

		return map;
	}

}
