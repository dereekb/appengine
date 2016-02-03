package com.dereekb.gae.server.datastore.objectify.keys.util;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.keys.ObjectifyKeyConverter;
import com.dereekb.gae.server.datastore.objectify.keys.impl.ObjectifyLongKeysConverter;
import com.dereekb.gae.server.datastore.objectify.keys.impl.ObjectifyStringKeysConverter;
import com.googlecode.objectify.Key;

/**
 * Utility class for converting {@link ModelKey} to {@link Key}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ObjectifyModelKeyUtil<T> {

	protected final Class<T> type;

	public ObjectifyModelKeyUtil(Class<T> type) {
		this.type = type;
	}

	public Class<T> getType() {
		return this.type;
	}

	// MARK: Key
	public Key<T> keyFromNumber(ModelKey modelKey) {
		Key<T> key;

		Long id = modelKey.getId();
		if (id != null) {
			key = Key.create(this.type, id);
		} else {
			key = null;
		}

		return key;
	}

	public Key<T> keyFromName(ModelKey modelKey) {
		Key<T> key;

		String name = modelKey.getName();
		if (name != null) {
			key = Key.create(this.type, name);
		} else {
			key = null;
		}

		return key;
	}

	// MARK: Static
	public static <T> ObjectifyModelKeyUtil<T> make(Class<T> type) {
		return new ObjectifyModelKeyUtil<T>(type);
	}

	public static <T> ObjectifyKeyConverter<T, ModelKey> converterForType(Class<T> type,
	                                                                      ModelKeyType keyType) {
		ObjectifyKeyConverter<T, ModelKey> converter;

		switch (keyType) {
			case NAME:
				converter = new ObjectifyStringKeysConverter<T>(type);
				break;
			case NUMBER:
				converter = new ObjectifyLongKeysConverter<T>(type);
				break;
			default:
				throw new IllegalArgumentException("Invalid type. Only NAME or NUMBER are permitted.");
		}

		return converter;
	}

}
