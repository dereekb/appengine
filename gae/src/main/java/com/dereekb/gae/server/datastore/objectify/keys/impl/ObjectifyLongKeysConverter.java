package com.dereekb.gae.server.datastore.objectify.keys.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
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

	private final Class<T> type;

	public ObjectifyLongKeysConverter(Class<T> type) {
		this.type = type;
	}

	public static <T> ObjectifyLongKeysConverter<T> withClass(Class<T> type) {
		return new ObjectifyLongKeysConverter<T>(type);
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

	// MARK: BidirectionalConverter
	@Override
	public List<ModelKey> convertTo(Collection<Key<T>> input) throws ConversionFailureException {
		return this.readKeys(input);
	}

	@Override
	public List<Key<T>> convertFrom(Collection<ModelKey> input) throws ConversionFailureException {
		return this.writeKeys(input);
	}

	@Override
	public String toString() {
		return "ObjectifyLongKeysConverter [type=" + this.type + "]";
	}

}
