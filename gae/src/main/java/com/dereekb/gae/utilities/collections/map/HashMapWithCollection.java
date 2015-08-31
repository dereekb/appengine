package com.dereekb.gae.utilities.collections.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
        implements Iterable<T> {

	private final Map<K, C> map;

	public HashMapWithCollection() {
		this.map = new HashMap<K, C>();
	}

	public HashMapWithCollection(HashMapWithCollection<? extends K, ? extends T, C> map) {
		// TODO: Replace with different internal replication method to allow
		// this type of constraint.
		this.map = map.getMapCopy();
	}

	public HashMapWithCollection(Iterable<MapPairing<? extends K, ? extends T>> pairings) {
		this.map = new HashMap<K, C>();
		this.addAllPairings(pairings);
	}

	/**
	 * Generates a new {@link Collection} for the map.
	 *
	 * @return new, empty {@link Collection} for the map.
	 */
	protected abstract C makeCollection();

	/**
	 * Returns the values for the {@code key} value specified.
	 *
	 * @param key
	 *            key value. Never {@code null}.
	 * @return {@link Collection} for the key value. Returns {@code null} if the
	 *         key value does not exist in this map.
	 */
	private C getCollectionForKey(K key) {
		C collection = this.map.get(key);

		if (collection == null) {
			collection = this.makeCollection();
			this.map.put(key, collection);
		}

		return collection;
	}

	/**
	 * Removes the values for this key.
	 *
	 * @param key
	 *            key value. Never {@code null}.
	 * @return {@link Collection} for this key.
	 */
	private C removeCollectionForKey(K key) {
		return this.map.remove(key);
	}

	/**
	 * Returns the {@link Set} of all keys in this map.
	 *
	 * @return {@link Set} of all keys. Never {@code null}.
	 */
	public Set<K> getKeySet() {
		return this.map.keySet();
	}

	/**
	 * Returns all elements for the specified {@code key} value. Returns an
	 * empty {@link List} if the key does not exist in this map.
	 *
	 * @param key
	 *            key value. Never {@code null}.
	 * @return {@link List} of values for the input key. Never {@code null}.
	 */
	public List<T> getElements(K key) {
		C collection = this.map.get(key);
		List<T> objects = null;

		if (collection == null) {
			objects = Collections.emptyList();
		} else {
			objects = new ArrayList<T>(collection);
		}

		return objects;
	}

	public Set<T> getAllElements() {
		Collection<C> collections = this.map.values();
		Set<T> objects = new HashSet<T>();

		for (C collection : collections) {
			objects.addAll(collection);
		}

		return objects;
	}

	public void add(K key,
	                T object) {
		C collection = this.getCollectionForKey(key);
		collection.add(object);
	}

	public void addAll(K key,
	                   Iterable<? extends T> objects) {
		C collection = this.getCollectionForKey(key);

		for (T object : objects) {
			collection.add(object);
		}
	}

	public void addAll(K key,
	                   Collection<? extends T> objects) {
		C collection = this.getCollectionForKey(key);
		collection.addAll(objects);
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

	public void remove(K key) {
		this.removeCollectionForKey(key);
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
	 */
	public void remove(K key,
	                   T object) {
		C collection = this.getCollectionForKey(key);

		if (collection != null) {
			collection.remove(object);

			if (collection.isEmpty()) {
				this.removeCollectionForKey(key);
			}
		}
	}

	/**
	 * @deprecated Used {@code #removeAll(Object, Collection)} where possible.
	 * @param key
	 * @param objects
	 */
	@Deprecated
	public void removeAll(K key,
	                      Iterable<T> objects) {
		C collection = this.getCollectionForKey(key);

		if (collection != null) {
			for (T object : objects) {
				collection.remove(object);
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
		C collection = this.getCollectionForKey(key);

		if (collection != null) {
			collection.removeAll(objects);

			if (collection.isEmpty()) {
				this.removeCollectionForKey(key);
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
		Set<K> keys = collection.getKeySet();

		for (K key : keys) {
			List<T> objects = collection.getElements(key);
			this.addAll(key, objects);
		}
	}

	public final Map<K, C> getMapCopy() {
		return new HashMap<K, C>(this.map);
	}

	public void clear() {
		this.map.clear();
	}

	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	@Override
	public Iterator<T> iterator() {
		Set<T> allObjects = this.getAllElements();
		return allObjects.iterator();
	}

	@Override
	public String toString() {
		return "HashMapWithCollection [map=" + this.map + "]";
	}

}
