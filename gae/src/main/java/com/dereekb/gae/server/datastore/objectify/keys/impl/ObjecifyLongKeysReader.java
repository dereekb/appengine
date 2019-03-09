package com.dereekb.gae.server.datastore.objectify.keys.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.keys.IllegalKeyConversionException;
import com.dereekb.gae.server.datastore.objectify.keys.ObjectifyKeyReader;
import com.googlecode.objectify.Key;

/**
 * Implementation of {@link ObjectifyKeyReader} for converting to
 * {@link ModelKey}.
 *
 * @author dereekb
 *
 * @param <T>
 *            {@link Key}'s generic.
 */
public class ObjecifyLongKeysReader<T>
        implements ObjectifyKeyReader<T, ModelKey> {

	@Override
	public List<ModelKey> readKeys(Iterable<? extends Key<T>> keys) throws IllegalKeyConversionException {
		List<ModelKey> modelKeys = new ArrayList<>();

		for (Key<T> key : keys) {
			Long id = key.getId();

			if (id == 0) {
				throw new IllegalKeyConversionException();
			} else {
				ModelKey modelKey = new ModelKey(id);
				modelKeys.add(modelKey);
			}
		}

		return modelKeys;
	}

	@Override
	public ModelKey readKey(Key<T> key) throws IllegalKeyConversionException {
		Long id = key.getId();

		if (id == 0) {
			throw new IllegalKeyConversionException();
		} else {
			return new ModelKey(id);
		}
	}

}
