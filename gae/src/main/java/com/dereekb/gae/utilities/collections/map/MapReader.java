package com.dereekb.gae.utilities.collections.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Utility for reading elements from a {@link Map}.
 *
 * @author dereekb
 *
 */
public final class MapReader {

	/**
	 * Reads the elements from the map. Keys that don't match any element are
	 * ignored.
	 *
	 * @param map
	 * @param keys
	 * @return a list of found objects that matches the keys.
	 */
	public static <T, K> List<T> quickRead(Map<K, T> map,
	                                       Iterable<K> keys) {
		List<T> objects = new ArrayList<T>();

		for (K key : keys) {
			T object = map.get(key);

			if (object != null) {
				objects.add(object);
			}
		}

		return objects;
	}

}
