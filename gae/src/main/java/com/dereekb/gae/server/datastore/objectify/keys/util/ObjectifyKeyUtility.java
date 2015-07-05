package com.dereekb.gae.server.datastore.objectify.keys.util;

import com.googlecode.objectify.Key;

/**
 * Utility for safely reading values from {@link Key}.
 *
 * @author dereekb
 *
 */
public class ObjectifyKeyUtility {

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

}
