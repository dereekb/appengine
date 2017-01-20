package com.dereekb.gae.server.datastore.objectify.keys.util;

import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.server.datastore.objectify.helpers.ObjectifyUtility;
import com.dereekb.gae.utilities.cache.map.CacheMap;
import com.dereekb.gae.utilities.cache.map.CacheMapDelegate;
import com.dereekb.gae.utilities.cache.map.impl.CacheMapImpl;
import com.googlecode.objectify.Key;

/**
 * Utility for safely reading values to/from {@link Key}.
 * 
 * Build using {@link #make(Class)}.
 *
 * @author dereekb
 *
 */
public class ObjectifyKeyUtility<T> extends ObjectifyUtility {

	private static final CacheMap<Class<?>, ObjectifyKeyUtility<?>> SINGLETON_CACHE = new CacheMapImpl<Class<?>, ObjectifyKeyUtility<?>>(
	        new CacheMapDelegate<Class<?>, ObjectifyKeyUtility<?>>() {

		        @SuppressWarnings({ "unchecked", "rawtypes" })
		        @Override
		        public ObjectifyKeyUtility<?> makeCacheElement(Class<?> key) throws IllegalArgumentException {
			        return new ObjectifyKeyUtility(key);
		        }

	        });

	private Class<T> type;

	protected ObjectifyKeyUtility(Class<T> type) throws IllegalArgumentException {
		this.setType(type);
	}

	public Class<T> getType() {
		return this.type;
	}

	public void setType(Class<T> type) throws IllegalArgumentException {
		if (type == null) {
			throw new IllegalArgumentException("Type cannot be null.");
		}

		this.type = type;
	}

	public Key<T> keyFromId(Long id) {
		Key<T> key = null;

		if (id != null) {
			key = Key.create(this.type, id);
		}

		return key;
	}

	public Key<T> keyFromString(String name) {
		Key<T> key = null;

		if (name != null) {
			key = Key.create(this.type, name);
		}

		return key;
	}

	public Set<Key<T>> setFromIds(Iterable<Long> ids) {
		Set<Key<T>> keys = new HashSet<Key<T>>();

		if (ids != null) {
			for (Long id : ids) {
				Key<T> key = Key.create(this.type, id);
				keys.add(key);
			}
		}

		return keys;
	}

	public Set<Key<T>> setFromStrings(Iterable<String> names) {
		Set<Key<T>> keys = new HashSet<Key<T>>();

		if (names != null) {
			for (String name : names) {
				Key<T> key = Key.create(this.type, name);
				keys.add(key);
			}
		}

		return keys;
	}

	public static <T> Long idFromKey(Key<T> key) {
		Long id = null;

		if (key != null) {
			id = key.getId();
		}

		return id;
	}

	public static <T> String nameFromKey(Key<T> key) {
		String name = null;

		if (key != null) {
			name = key.getName();
		}

		return name;
	}

	@SuppressWarnings("unchecked")
	public static final <T> ObjectifyKeyUtility<T> make(Class<T> type) {
		return (ObjectifyKeyUtility<T>) SINGLETON_CACHE.get(type);
	}

}
