package com.dereekb.gae.utilities.collections.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Abstract utility class for handling {@link Collection} values within a
 * {@link Map}.
 * <p>
 * A single {@link Collection} instance is managed for each key value.
 *
 * @author dereekb
 *
 * @param <K>
 *            key type
 * @param <T>
 *            model type
 * @param <C>
 *            collection type
 */
public abstract class HashMapWithCollection<K, T, C extends Collection<T>>
        implements Map<K, C>, Iterable<T> {

	private final Map<K, C> map;

	public HashMapWithCollection() {
		this.map = new HashMap<K, C>();
	}

	public HashMapWithCollection(HashMapWithCollection<? extends K, ? extends T, ?> map) {
		this.map = new HashMap<K, C>();
		this.addAll(map);
	}

	public HashMapWithCollection(Iterable<MapPairing<? extends K, ? extends T>> pairings) {
		this.map = new HashMap<K, C>();
		this.addAllPairings(pairings);
	}

	// MARK: Map
	@Override
	public int size() {
		return this.map.size();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return this.map.containsValue(value);
	}

	@Override
	public C get(Object key) {
		return this.map.get(key);
	}

	@Override
	public C put(K key,
	             C value) {
		return this.map.put(key, value);
	}

	@Override
	public C remove(Object key) {
		return this.map.remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends C> m) {
		this.map.putAll(m);
	}

	@Override
	public Set<K> keySet() {
		return this.map.keySet();
	}

	@Override
	public Collection<C> values() {
		return this.map.values();
	}

	@Override
	public Set<java.util.Map.Entry<K, C>> entrySet() {
		return this.map.entrySet();
	}

	@Override
	public void clear() {
		this.map.clear();
	}

	@Override
	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	@Override
	public Iterator<T> iterator() {
		List<T> list = this.valuesList();
		return list.iterator();
	}

	// MARK: HashMapWithCollection

	/**
	 * Returns all elements for the specified {@code key} value. Returns an
	 * empty {@link List} if the key does not exist in this map.
	 *
	 * @param key
	 *            key value. Never {@code null}.
	 * @return {@link List} of values for the input key. Never {@code null}.
	 */
	public List<T> valuesForKey(Object key) {
		C collection = this.map.get(key);
		List<T> objects = null;

		if (collection == null) {
			objects = Collections.emptyList();
		} else {
			objects = new ArrayList<T>(collection);
		}

		return objects;
	}

	public List<T> valuesList() {
		Collection<C> collections = this.map.values();
		List<T> objects = new ArrayList<T>();

		for (C collection : collections) {
			objects.addAll(collection);
		}

		return objects;
	}

	public Set<T> valuesSet() {
		Collection<C> collections = this.map.values();
		Set<T> objects = new HashSet<T>();

		for (C collection : collections) {
			objects.addAll(collection);
		}

		return objects;
	}

	/**
	 * Adds a single item at the given key.
	 *
	 * @param key
	 *            Key value. Never {@code null}.
	 * @param object
	 *            Objects to add. Never {@code null}.
	 */
	public void add(K key,
	                T object) {
		C collection = this.getOrBuildCollectionForKey(key);
		collection.add(object);
	}

	/**
	 * Adds all items from a {@link Collection}.
	 *
	 * @param key
	 *            Key value. Never {@code null}.
	 * @param objects
	 *            {@link Collection} of objects to add. Never {@code null}.
	 */
	public void addAll(K key,
	                   Collection<? extends T> objects) {
		C collection = this.getOrBuildCollectionForKey(key);
		collection.addAll(objects);
	}

	/**
	 * Adds all items from a {@link Iterable} value.
	 *
	 * @param key
	 *            Key value. Never {@code null}.
	 * @param objects
	 *            {@link Iterable} values to add. Never {@code null}.
	 */
	public void addAll(K key,
	                   Iterable<? extends T> objects) {
		C collection = this.getOrBuildCollectionForKey(key);

		for (T object : objects) {
			collection.add(object);
		}
	}

	public void addAll(HashMapWithCollection<? extends K, ? extends T, C> map) {
		Set<? extends K> keys = map.keySet();

		for (K key : keys) {
			List<? extends T> values = map.valuesForKey(key);
			this.addAll(key, values);
		}
    }

	/**
	 * Adds the specified {@code object} to each of the keys specified.
	 *
	 * @param keys
	 *            {@link Iterable} key values. Never {@code null}.
	 * @param object
	 *            Object. Never {@code null}.
	 */
	public void addAll(Iterable<? extends K> keys,
	                   T object) {
		for (K key : keys) {
			this.add(key, object);
		}
	}

	/**
	 * Adds all values from the {@link Map}.
	 *
	 * @param map
	 *            {@link Map} containing values. Never {@code null}.
	 */
	public void addAll(Map<? extends K, ? extends Collection<? extends T>> map) {
		for (Entry<? extends K, ? extends Collection<? extends T>> entry : map.entrySet()) {
			K key = entry.getKey();
			Collection<? extends T> values = entry.getValue();
			this.addAll(key, values);
		}
	}

	private void addAllPairings(Iterable<MapPairing<? extends K, ? extends T>> pairings) {
		for (MapPairing<? extends K, ? extends T> pairing : pairings) {
			Iterable<? extends K> keys = pairing.getKeys();
			T value = pairing.getValue();
			this.addAll(keys, value);
		}
	}

	public void removeAll(Iterable<? extends K> keys) {
		for (K key : keys) {
			this.remove(key);
		}
	}

	/**
	 * Removes the input object from this map.
	 *
	 * @param key
	 *            key value. Never {@code null}.
	 * @param object
	 *            object to remove from the map. Never {@code null}.
	 * @return
	 */
	public void removeObject(K key,
	                         T object) {
		C collection = this.get(key);

		if (collection != null) {
			collection.remove(object);

			if (collection.isEmpty()) {
				this.remove(key);
			}
		}
	}

	/**
	 * Removes all input values from this map under the specified key.
	 *
	 * @param key
	 *            key value. Never {@code null}.
	 * @param objects
	 *            objects to remove. Never {@code null}.
	 */
	public void removeAll(K key,
	                      Collection<T> objects) {
		C collection = this.get(key);

		if (collection != null) {
			collection.removeAll(objects);

			if (collection.isEmpty()) {
				this.remove(key);
			}
		}
	}

	/**
	 * Inserts all values from the input {@code collection} into this map
	 * instance.
	 *
	 * @param collection
	 *            {@link HashMapWithCollection} value to merge with. Never
	 *            {@code null}.
	 */
	public void merge(HashMapWithCollection<K, T, ?> collection) {
		Set<K> keys = collection.keySet();

		for (K key : keys) {
			List<T> objects = collection.valuesForKey(key);
			this.addAll(key, objects);
		}
	}

	// MARK: Internal
	/**
	 * Generates a new {@link Collection} for the map.
	 *
	 * @return new, empty {@link Collection} for the map.
	 */
	protected abstract C makeCollection();

	/**
	 * Retrieves a collection if it exists in {@link #map} or creates a new one.
	 *
	 * @param key
	 *            Key value. Never {@code null}.
	 * @return Collection of type {@code C}. Never {@code null}.
	 */
	private C getOrBuildCollectionForKey(K key) {
		C collection = this.map.get(key);

		if (collection == null) {
			collection = this.makeCollection();
			this.map.put(key, collection);
		}

		return collection;
	}

	@Override
	public String toString() {
		return "HashMapWithCollection [map=" + this.map + "]";
	}

}
