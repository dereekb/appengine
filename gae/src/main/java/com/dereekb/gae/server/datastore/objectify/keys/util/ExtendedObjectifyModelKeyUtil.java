package com.dereekb.gae.server.datastore.objectify.keys.util;

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
 * {@link ObjectifyModelKeyUtil} extension that provides additional
 * functionality, and implements the {@link ObjectifyKeyConverter} interfaces.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ExtendedObjectifyModelKeyUtil<T> extends ObjectifyModelKeyUtil<T>
        implements ObjectifyKeyConverter<T, ModelKey> {

	private final ObjectifyKeyConverter<T, ModelKey> converter;

	public ExtendedObjectifyModelKeyUtil(Class<T> type, ModelKeyType keyType) {
		super(type);
		this.converter = ObjectifyModelKeyUtil.converterForType(type, keyType);
	}

	public ExtendedObjectifyModelKeyUtil(Class<T> type, ObjectifyKeyConverter<T, ModelKey> converter) {
		super(type);
		this.converter = converter;
	}

	public ObjectifyKeyConverter<T, ModelKey> getConverter() {
		return this.converter;
	}

	// MARK: Model Key
	public ModelKey toModelKey(Key<T> key) {
		ModelKey modelKey;

		try {
			modelKey = this.converter.readKey(key);
		} catch (IllegalKeyConversionException e) {
			modelKey = null;
		}

		return modelKey;
	}

	// MARK: Key
	public Key<T> fromModelKey(ModelKey modelKey) {
		Key<T> key;

		try {
			key = this.converter.writeKey(modelKey);
		} catch (IllegalKeyConversionException e) {
			key = null;
		}

		return key;
	}

	// MARK: List
	public List<ModelKey> listFromKey(Key<T> key) {
		List<ModelKey> list = new ArrayList<ModelKey>();

		try {
			list.add(this.converter.readKey(key));
		} catch (IllegalKeyConversionException e) {

		}

		return list;
	}

	// MARK: ObjectifyKeyConverter

	@Override
	public ModelKey readKey(Key<T> key) throws IllegalKeyConversionException {
		return this.converter.readKey(key);
	}

	@Override
	public List<ModelKey> readKeys(Iterable<Key<T>> keys) throws IllegalKeyConversionException {
		return this.converter.readKeys(keys);
	}

	@Override
	public Key<T> writeKey(ModelKey element) throws IllegalKeyConversionException {
		return this.converter.writeKey(element);
	}

	@Override
	public List<Key<T>> writeKeys(Iterable<ModelKey> elements) throws IllegalKeyConversionException {
		return this.converter.writeKeys(elements);
	}

	@Override
	public List<ModelKey> convertTo(Collection<Key<T>> input) throws ConversionFailureException {
		return this.converter.convertTo(input);
	}

	@Override
	public List<Key<T>> convertFrom(Collection<ModelKey> input) throws ConversionFailureException {
		return this.converter.convertFrom(input);
	}

	// MARK: Static
	public static <T> ExtendedObjectifyModelKeyUtil<T> make(Class<T> type,
	                                                        ModelKeyType keyType) {
		return new ExtendedObjectifyModelKeyUtil<T>(type, keyType);
	}

}
