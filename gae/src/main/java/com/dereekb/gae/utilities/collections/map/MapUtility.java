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

	public static <T, K> void putIntoMapMultipleTimes(Map<K, T> map,
	                                                  List<K> keys,
	                                                  T value) {
		for (K key : keys) {
			map.put(key, value);
		}
	}

	public static <T, K> void removeAll(Map<K, T> map,
	                                    List<K> keys) {
		for (K key : keys) {
			map.remove(key);
		}
	}

}
