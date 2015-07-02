package com.dereekb.gae.model.extension.data.conversion.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.SingleDirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;

/**
 * Abstract implementation of {@link DirectionalConverter} that uses itself as a
 * {@link SingleDirectionalConverter} to convert all internal elements.
 *
 * @author dereekb
 *
 * @param <I>
 *            Input Type
 * @param <O>
 *            Output Type
 */
public abstract class AbstractDirectionalConverter<I, O>
        implements DirectionalConverter<I, O>, SingleDirectionalConverter<I, O> {

	@Override
	public List<O> convert(Collection<I> input) throws ConversionFailureException {
		return this.convert((Iterable<I>) input);
	}

	public List<O> convert(Iterable<I> input) throws ConversionFailureException {
		List<O> convertedObjects = new ArrayList<O>();

		for (I object : input) {
			O convertedObject = this.convertSingle(object);
			convertedObjects.add(convertedObject);
		}

		return convertedObjects;
	}

}
