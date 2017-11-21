package com.dereekb.gae.utilities.collections.set.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.exception.NoModelKeyException;
import com.dereekb.gae.utilities.collections.map.KeyDelegate;
import com.dereekb.gae.utilities.collections.set.KeyedDelegatedSet;

/**
 * {@link KeyedDelegatedSet} implementation.s
 * 
 * @author dereekb
 *
 * @param <K>
 *            key type
 * @param <T>
 *            model type
 */
public class KeyedDelegatedSetImpl<K, T>
        implements KeyedDelegatedSet<K, T> {

	private Map<K, T> map;
	private KeyDelegate<K, T> keyDelegate;

	public KeyedDelegatedSetImpl(KeyDelegate<K, T> keyDelegate) {
		this.setKeyDelegate(keyDelegate);
		this.map = this.makeMap();
	}

	protected Map<K, T> makeMap() {
		return new HashMap<K, T>();
	}

	@Override
	public KeyDelegate<K, T> getKeyDelegate() {
		return this.keyDelegate;
	}

	public void setKeyDelegate(KeyDelegate<K, T> keyDelegate) {
		if (keyDelegate == null) {
			throw new IllegalArgumentException("keyDelegate cannot be null.");
		}

		this.keyDelegate = keyDelegate;
	}

	// MARK: KeyedDelegatedSet
	@Override
	public Set<K> keySet() {
		return this.map.keySet();
	}

	@Override
	public Collection<T> values() {
		return this.map.values();
	}

	@Override
	public int size() {
		return this.map.size();
	}

	@Override
	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	@Override
	public boolean contains(T value) throws NoModelKeyException {
		K key = this.keyDelegate.keyForModel(value);
		return this.map.containsKey(key);
	}

	@Override
	public T add(T value) throws NoModelKeyException {
		K key = this.keyDelegate.keyForModel(value);
		return this.map.put(key, value);
	}

	@Override
	public T remove(T value) throws NoModelKeyException {
		K key = this.keyDelegate.keyForModel(value);
		return this.map.remove(key);
	}

	@Override
	public void addAll(Iterable<T> values) throws NoModelKeyException {
		for (T value : values) {
			this.add(value);
		}
	}

	@Override
	public void removeAll(Iterable<T> values) throws NoModelKeyException {
		for (T value : values) {
			this.remove(value);
		}
	}

	@Override
	public void clear() {
		this.map.clear();
	}

	@Override
	public String toString() {
		return "KeyedDelegatedSetImpl [keyDelegate=" + this.keyDelegate + ", keySet()=" + this.keySet() + "]";
	}

}
