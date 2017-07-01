package com.dereekb.gae.utilities.collections.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.utilities.misc.keyed.AlwaysKeyed;

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

	/**
	 * Makes a {@link HashMapWithList} using the input objects.
	 * 
	 * @param objects
	 *            {@link Iterable}. Never {@code null}.
	 * @return {@link HashMapWithList}. Never {@code null}.
	 */
	public static <K, T extends AlwaysKeyed<K>> HashMapWithList<K, T> makeHashMapWithList(Iterable<? extends T> objects) {
		HashMapWithList<K, T> map = new HashMapWithList<K, T>();
		HashMapWithList.addAllKeyedToMap(map, objects);
		return map;
	}

}
