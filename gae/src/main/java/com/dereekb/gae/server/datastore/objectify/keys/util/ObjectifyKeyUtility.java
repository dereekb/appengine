package com.dereekb.gae.server.datastore.objectify.keys.util;

import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.server.datastore.objectify.helpers.ObjectifyUtility;
import com.googlecode.objectify.Key;

/**
 * Utility for safely reading values to/from {@link Key}.
 *
 * @author dereekb
 *
 */
public class ObjectifyKeyUtility<T> extends ObjectifyUtility {

	private Class<T> type;

	public ObjectifyKeyUtility(Class<T> type) {
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

	public static <T> ObjectifyKeyUtility<T> make(Class<T> type) {
		return new ObjectifyKeyUtility<T>(type);
	}

}
