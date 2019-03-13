package com.dereekb.gae.utilities.collections.map;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.utilities.collections.set.CaseInsensitiveSet;

/**
 * {@link HashMapWithSet} extension that is keyed by case-insensitive strings.
 *
 * @author dereekb
 *
 * @param <U>
 *            value type
 */
public class CaseInsensitiveMapWithSet<U> extends HashMapWithSet<String, U> {

	public CaseInsensitiveMapWithSet() {
		super();
	}

	public CaseInsensitiveMapWithSet(Iterable<MapPairing<? extends String, ? extends U>> pairings) {
		super(pairings);
	}

	public CaseInsensitiveMapWithSet(Map<? extends String, ? extends Collection<? extends U>> map) {
		super(map);
	}

	@Override
	public CaseInsensitiveSet keySet() {
		return new CaseInsensitiveSet(super.keySet());
	}

	// MARK: Override
	@Override
	protected Map<String, Set<U>> makeMap() {
		return new CaseInsensitiveMap<Set<U>>();
	}

}
