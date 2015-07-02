package com.dereekb.gae.server.datastore.objectify.keys.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.googlecode.objectify.Key;

@Deprecated
public final class KeysConverter<T> {

	private final Collection<Key<T>> keys;

	public KeysConverter(Collection<Key<T>> keys) {
		this.keys = keys;
	}

	public boolean keysExist() {
		return (this.keys == null && (this.keys.isEmpty() == false));
	}

	public List<Long> convertToLongs() {
		List<Long> identifiers = new ArrayList<Long>();

		if (this.keys.isEmpty() == false) {
			for (Key<?> key : this.keys) {
				Long identifier = key.getId();
				identifiers.add(identifier);
			}
		}

		return identifiers;
	}

	/**
	 * Used by Containers mostly; will maintain container consistency by returning null.
	 * @param keys
	 * @return
	 */
	public static <T> List<Long> convertKeysToLongs(Set<Key<T>> keys) {
		List<Long> identifiers = null;

		if (keys != null) {
			identifiers = new ArrayList<Long>();

			if (keys.isEmpty() == false) {
				for (Key<?> key : keys) {
					Long identifier = key.getId();
					identifiers.add(identifier);
				}
			}
		}

		return identifiers;
	}

	public static <T> Set<Key<T>> convertLongsToKeys(List<Long> identifiers, Class<T> type) {
		Set<Key<T>> keys = null;

		if (identifiers != null) {
			keys = new HashSet<Key<T>>();

			if (identifiers.isEmpty() == false) {
				for(Long identifier : identifiers) {
					Key<T> key = Key.create(type, identifier);
					keys.add(key);
				}
			}
		}

		return keys;
	}

}
