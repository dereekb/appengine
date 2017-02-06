package com.dereekb.gae.utilities.collections.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapUtility {

	public static <T, K> List<T> getValuesForKeys(Map<K, T> map,
	                                              Iterable<? extends K> keys) {

		List<T> values = new ArrayList<T>();

		for (K key : keys) {
			T value = map.get(key);

			if (value != null) {
				values.add(value);
			}
		}

		return values;
	}

}
