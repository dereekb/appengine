package com.dereekb.gae.utilities.collections.map.catchmap.impl;

import java.util.Map;

/**
 * Class that uses a map and an optional catch that is returned when a key in
 * the map does not exist.
 *
 * @author dereekb
 * @deprecated Use {@link CatchMapImpl} instead.
 */
@Deprecated
public class StringCatchMap<T> extends CatchMapImpl<String, T> {

	private static final String DEFAULT_NULL_KEY = "default";

	public StringCatchMap() {}

	public StringCatchMap(T catchAll, Map<String, T> map) {
		super(catchAll, DEFAULT_NULL_KEY, map);
	}

}
