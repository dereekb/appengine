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

public abstract class HashMapWithCollection<T, U, C extends Collection<U>>
        implements Iterable<U> {

	private final Map<T, C> map;

	public HashMapWithCollection() {
		this.map = new HashMap<T, C>();
	}

	public HashMapWithCollection(HashMapWithCollection<T, U, C> map) {
		this.map = map.getMapCopy();
	}

	public HashMapWithCollection(Iterable<MapPairing<T, U>> pairings) {
		this.map = new HashMap<T, C>();
		this.addAll(pairings);
	}

	protected abstract C newCollection();

	private C getCollectionForKey(T key) {
		C collection = this.map.get(key);

		if (collection == null) {
			collection = this.newCollection();
			this.map.put(key, collection);
		}

		return collection;
	}

	private void removeCollectionForKey(T key) {
		this.map.remove(key);
	}

	public Set<T> getKeySet() {
		return this.map.keySet();
	}

	public List<U> getObjects(T key) {
		C collection = this.map.get(key);
		List<U> objects = null;

		if (collection == null) {
			objects = Collections.emptyList();
		} else {
			objects = new ArrayList<U>(collection);
		}

		return objects;
	}

	public Set<U> getAllObjects() {
		Collection<C> collections = this.map.values();
		Set<U> objects = new HashSet<U>();

		for (C collection : collections) {
			objects.addAll(collection);
		}

		return objects;
	}

	public void add(T key,
	                U object) {
		C collection = this.getCollectionForKey(key);
		collection.add(object);
	}

	public void addAll(T key,
	                   Iterable<U> objects) {
		C collection = this.getCollectionForKey(key);

		for (U object : objects) {
			collection.add(object);
		}
	}

	public void addAll(T key,
	                   Collection<U> objects) {
		C collection = this.getCollectionForKey(key);
		collection.addAll(objects);
	}

	public void addAll(Iterable<T> keys,
	                   U object) {
		for (T key : keys) {
			this.add(key, object);
		}
	}

	private void addAll(Iterable<MapPairing<T, U>> pairings) {
		for (MapPairing<T, U> pairing : pairings) {
			Iterable<T> keys = pairing.getKeys();
			U value = pairing.getValue();
			this.addAll(keys, value);
		}
	}

	public void remove(T key) {
		this.removeCollectionForKey(key);
	}

	public void removeAll(Iterable<T> keys) {
		for (T key : keys) {
			this.remove(key);
		}
	}

	public void remove(T key,
	                   U object) {
		C collection = this.getCollectionForKey(key);
		collection.remove(object);

		if (collection.isEmpty()) {
			this.removeCollectionForKey(key);
		}
	}

	public void removeAll(T key,
	                      Iterable<U> objects) {
		C collection = this.getCollectionForKey(key);

		for (U object : objects) {
			collection.remove(object);
		}
	}

	public void removeAll(T key,
	                      Collection<U> objects) {
		C collection = this.getCollectionForKey(key);
		collection.removeAll(objects);
	}

	public void merge(HashMapWithCollection<T, U, ?> collection) {
		Set<T> keys = collection.getKeySet();

		for (T key : keys) {
			List<U> objects = collection.getObjects(key);
			this.addAll(key, objects);
		}
	}

	public final Map<T, C> getMapCopy() {
		return new HashMap<T, C>(this.map);
	}

	public void clear() {
		this.map.clear();
	}

	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	public Iterator<U> iterator() {
		Set<U> allObjects = this.getAllObjects();
		return allObjects.iterator();
	}

	@Override
	public String toString() {
		return "HashMapWithCollection [map=" + map + "]";
	}

}
