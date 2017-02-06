package com.dereekb.gae.utilities.collections.map;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

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

	// MARK: Override
	@Override
	protected Map<String, Set<U>> makeMap() {
		return new CaseInsensitiveMap<Set<U>>();
	}

}
