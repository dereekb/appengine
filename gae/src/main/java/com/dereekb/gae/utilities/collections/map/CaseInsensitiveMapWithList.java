package com.dereekb.gae.utilities.collections.map;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.utilities.collections.set.CaseInsensitiveSet;

/**
 * {@link HashMapWithSet} extension that is keyed by case-insensitive strings.
 *
 * @author dereekb
 *
 * @param <U>
 *            value type
 */
public class CaseInsensitiveMapWithList<U> extends HashMapWithList<String, U> {

	public CaseInsensitiveMapWithList() {
		super();
	}

	public CaseInsensitiveMapWithList(Iterable<MapPairing<? extends String, ? extends U>> pairings) {
		super(pairings);
	}

	public CaseInsensitiveMapWithList(Map<? extends String, ? extends Collection<? extends U>> map) {
		super(map);
	}

	@Override
	public CaseInsensitiveSet keySet() {
		return new CaseInsensitiveSet(super.keySet());
	}

	// MARK: Override
	@Override
	protected Map<String, List<U>> makeMap() {
		return new CaseInsensitiveMap<List<U>>();
	}

}
