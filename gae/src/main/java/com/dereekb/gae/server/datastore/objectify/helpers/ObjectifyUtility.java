package com.dereekb.gae.server.datastore.objectify.helpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;
import com.googlecode.objectify.Key;

/**
 * General utility for Objectify and {@link Key} values.
 *
 * @author dereekb
 * 
 * @see ObjectifyKeyUtility
 */
public class ObjectifyUtility {

	public static <T extends ObjectifyModel<T>> List<Key<T>> readKeys(Iterable<T> models) {
		List<Key<T>> values = new ArrayList<Key<T>>();

		if (models != null) {
			for (T model : models) {
				Key<T> key = model.getObjectifyKey();

				if (key != null) {
					values.add(key);
				}
			}
		}

		return values;
	}

	public static <T extends ObjectifyModel<T>> List<Long> readKeyIdentifiers(Iterable<Key<T>> keys) {
		List<Long> values = new ArrayList<Long>();

		if (keys != null) {
			for (Key<T> key : keys) {
				Long value = key.getId();

				if (value != null) {
					values.add(value);
				}
			}
		}

		return values;
	}

	public static <T extends ObjectifyModel<T>> List<String> readKeyNames(Iterable<Key<T>> keys) {
		List<String> values = new ArrayList<String>();

		if (keys != null) {
			for (Key<T> key : keys) {
				String value = key.getName();

				if (value != null) {
					values.add(value);
				}
			}
		}

		return values;
	}

	public static <T extends ObjectifyModel<T>> Long readKeyIdentifier(Key<T> key) {
		Long identifier = null;

		if (key != null) {
			identifier = key.getId();
		}

		return identifier;
	}

	/**
	 * Creates a new key.
	 *
	 * @param type
	 *            {@link Class}. Never {@code null}.
	 * @param identifier
	 *            {@link Long}. May be {@code null}.
	 * @return {@link Key}. {@code null} if identifer was also null.
	 */
	public static <T extends ObjectifyModel<T>> Key<T> createKey(Class<T> type,
	                                                             Long identifier) {
		Key<T> key = null;

		if (identifier != null) {
			key = Key.create(type, identifier);
		}

		return key;
	}

	/**
	 * Creates a new key.
	 *
	 * @param type
	 *            {@link Class}. Never {@code null}.
	 * @param name
	 *            {@link String}. May be {@code null}.
	 * @return {@link Key}. {@code null} if identifier was also null.
	 */
	public static <T extends ObjectifyModel<T>> Key<T> createKey(Class<T> type,
	                                                             String name) {
		Key<T> key = null;

		if (name != null) {
			key = Key.create(type, name);
		}

		return key;
	}

	public static <T extends ObjectifyModel<T>> Set<Key<T>> createKeySet(Class<T> type,
	                                                                     Iterable<Long> identifiers) {
		Set<Key<T>> keys = new HashSet<Key<T>>();

		if (identifiers != null) {
			for (Long identifier : identifiers) {
				Key<T> key = Key.create(type, identifier);
				keys.add(key);
			}
		}

		return keys;
	}
	
}
