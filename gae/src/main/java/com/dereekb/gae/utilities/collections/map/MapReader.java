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
public class MapReader<T> {

	private static final String DEFAULT_FORMAT = "%s";

	private Map<String, T> map;
	private String keyFormat;

	public MapReader(Map<String, T> map) throws IllegalArgumentException {
		this(map, null);
	}

	public MapReader(Map<String, T> map, String keyFormat) throws IllegalArgumentException {
		this.setMap(map);
		this.setKeyFormat(keyFormat);
	}

	public Map<String, T> getMap() {
		return this.map;
	}

	public void setMap(Map<String, T> map) throws IllegalArgumentException {
		if (map == null) {
			throw new IllegalArgumentException("Map cannot be null.");
		}

		this.map = map;
	}

	public String getKeyFormat() {
		return this.keyFormat;
	}

	public void setKeyFormat(String keyFormat) {
		if (keyFormat == null || keyFormat.isEmpty()) {
			keyFormat = DEFAULT_FORMAT;
		}

		this.keyFormat = keyFormat;
	}

	// MARK: Reader
	public T get(String key) {
		key = String.format(this.keyFormat, key);
		return this.map.get(key);
	}

	public boolean containsKey(String key) {
		key = String.format(this.keyFormat, key);
		return this.map.containsKey(key);
	}

	// MARK: Static Utilities
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
