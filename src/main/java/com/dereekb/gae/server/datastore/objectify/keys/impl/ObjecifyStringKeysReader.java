package com.dereekb.gae.server.datastore.objectify.keys.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.keys.IllegalKeyConversionException;
import com.dereekb.gae.server.datastore.objectify.keys.ObjectifyKeyReader;
import com.googlecode.objectify.Key;

/**
 * Implementation of {@link ObjectifyKeyReader} for converting to
 * {@link ModelKey} using a {@link Key}'s name.
 *
 * @author dereekb
 *
 * @param <T>
 *            {@link Key}'s generic.
 */
public class ObjecifyStringKeysReader<T>
        implements ObjectifyKeyReader<T, ModelKey> {

	@Override
	public List<ModelKey> readKeys(Iterable<? extends Key<T>> keys) throws IllegalKeyConversionException {
		List<ModelKey> modelKeys = new ArrayList<ModelKey>();

		for (Key<T> key : keys) {
			String name = key.getName();

			if (name == null) {
				throw new IllegalKeyConversionException();
			} else {
				ModelKey modelKey = new ModelKey(name);
				modelKeys.add(modelKey);
			}
		}

		return modelKeys;
	}

	@Override
	public ModelKey readKey(Key<T> key) throws IllegalKeyConversionException {
		String name = key.getName();

		if (name == null) {
			throw new IllegalKeyConversionException();
		} else {
			return new ModelKey(name);
		}
	}

}