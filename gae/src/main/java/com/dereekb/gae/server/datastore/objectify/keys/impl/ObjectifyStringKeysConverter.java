package com.dereekb.gae.server.datastore.objectify.keys.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.keys.IllegalKeyConversionException;
import com.dereekb.gae.server.datastore.objectify.keys.ObjectifyKeyConverter;
import com.googlecode.objectify.Key;

/**
 * Implementation of {@link ObjectifyKeysConverter} for converting {@link Key}
 * instances with {@link String} id types to {@link ModelKey} instances.
 *
 * @author dereekb
 */
public final class ObjectifyStringKeysConverter<T> extends ObjecifyStringKeysReader<T>
        implements ObjectifyKeyConverter<T, ModelKey> {

	private final Class<T> type;

	public ObjectifyStringKeysConverter(Class<T> type) {
		this.type = type;
	}

	@Override
	public ModelKeyType getModelKeyType() {
		return ModelKeyType.NAME;
	}

	@Override
	public List<Key<T>> writeKeys(Iterable<? extends ModelKey> modelKeys) throws IllegalKeyConversionException {
		List<Key<T>> keys = new ArrayList<Key<T>>();

		for (ModelKey modelKey : modelKeys) {
			Key<T> key = this.writeKey(modelKey);
			keys.add(key);
		}

		return keys;
	}

	@Override
	public Key<T> writeKey(ModelKey modelKey) throws IllegalKeyConversionException {
		String name = modelKey.getName();

		if (name == null || name.isEmpty()) {
			throw new IllegalKeyConversionException();
		} else {
			return Key.create(this.type, name);
		}
	}

	// MARK: BidirectionalConverter
	@Override
	public List<ModelKey> convertTo(Collection<? extends Key<T>> input) throws ConversionFailureException {
		return this.readKeys(input);
	}

	@Override
	public List<Key<T>> convertFrom(Collection<? extends ModelKey> input) throws ConversionFailureException {
		return this.writeKeys(input);
	}

	@Override
	public String toString() {
		return "ObjectifyStringKeysConverter [type=" + this.type + "]";
	}

}
