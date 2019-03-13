package com.dereekb.gae.utilities.collections.map;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.utilities.collections.set.CaseInsensitiveSet;

/**
 * {@link CaseInsensitiveMapWithSet} that has {@link CaseInsensitiveSet} values.
 * 
 * @author dereekb
 *
 */
public class CaseInsensitiveMapAndSet extends CaseInsensitiveMapWithSet<String> {

	public CaseInsensitiveMapAndSet() {
		super();
	}

	public CaseInsensitiveMapAndSet(Iterable<MapPairing<? extends String, ? extends String>> pairings) {
		super(pairings);
	}

	public CaseInsensitiveMapAndSet(Map<? extends String, ? extends Collection<? extends String>> map) {
		super(map);
	}

	// MARK: Override
	@Override
	protected Set<String> makeCollection() {
		return new CaseInsensitiveSet();
	}

}
