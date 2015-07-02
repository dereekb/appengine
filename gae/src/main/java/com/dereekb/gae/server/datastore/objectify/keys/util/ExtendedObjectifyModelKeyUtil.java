package com.dereekb.gae.server.datastore.objectify.keys.util;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.keys.IllegalKeyConversionException;
import com.dereekb.gae.server.datastore.objectify.keys.ObjectifyKeyConverter;
import com.googlecode.objectify.Key;

public class ExtendedObjectifyModelKeyUtil<T> extends ObjectifyModelKeyUtil<T> {

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

	// MARK: Static
	public static <T> ExtendedObjectifyModelKeyUtil<T> make(Class<T> type,
	                                                        ModelKeyType keyType) {
		return new ExtendedObjectifyModelKeyUtil<T>(type, keyType);
	}

}
