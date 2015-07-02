package com.dereekb.gae.model.extension.data.conversion;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;

/**
 * Converts from one type to another.
 *
 * @author dereekb
 *
 * @param <I>
 *            Input Type
 * @param <O>
 *            Output Type
 */
public interface DirectionalConverter<I, O> {

	/**
	 * Converts to the input type.
	 *
	 * @param input
	 *            Input models.
	 * @return List of converted input models. Its size should match the size of
	 *         the input collection.
	 * @throws ConversionFailureException
	 *             If any of the input models fail to be converted.
	 */
	public List<O> convert(Collection<I> input) throws ConversionFailureException;

}
