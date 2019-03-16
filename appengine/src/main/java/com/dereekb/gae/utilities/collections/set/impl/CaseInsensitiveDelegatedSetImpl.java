package com.dereekb.gae.utilities.collections.set.impl;

import java.util.Map;

import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;
import com.dereekb.gae.utilities.collections.map.KeyDelegate;

/**
 * Case-insensitive {@link KeyedDelegatedSetImpl}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class CaseInsensitiveDelegatedSetImpl<T> extends KeyedDelegatedSetImpl<String, T> {

	public CaseInsensitiveDelegatedSetImpl(KeyDelegate<String, T> keyDelegate) {
		super(keyDelegate);
	}

	@Override
	protected Map<String, T> makeMap() {
		return new CaseInsensitiveMap<T>();
	}

}
