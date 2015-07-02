package com.dereekb.gae.server.datastore.objectify.keys.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.keys.IllegalKeyConversionException;
import com.dereekb.gae.server.datastore.objectify.keys.ObjectifyKeyConverter;
import com.googlecode.objectify.Key;

/**
 * Implementation of {@link ObjectifyKeysConverter} for converting {@link Key}
 * instances with {@link Long} id types to {@link ModelKey} instances.
 *
 * @author dereekb
 */
public final class ObjectifyLongKeysConverter<T> extends ObjecifyLongKeysReader<T>
        implements ObjectifyKeyConverter<T, ModelKey> {

	// TODO: Make implement directional converter.

	private final Class<T> type;

	public ObjectifyLongKeysConverter(Class<T> type) {
		this.type = type;
	}

	@Override
	public List<Key<T>> writeKeys(Iterable<ModelKey> modelKeys) throws IllegalKeyConversionException {
		List<Key<T>> keys = new ArrayList<Key<T>>();

		for (ModelKey modelKey : modelKeys) {
			Key<T> key = this.writeKey(modelKey);
			keys.add(key);
		}

		return keys;
    }

	@Override
	public Key<T> writeKey(ModelKey modelKey) throws IllegalKeyConversionException {
		Long id = modelKey.getId();

		if (id == null || id == 0) {
			throw new IllegalKeyConversionException();
		} else {
			return Key.create(this.type, id);
		}
	}

}
