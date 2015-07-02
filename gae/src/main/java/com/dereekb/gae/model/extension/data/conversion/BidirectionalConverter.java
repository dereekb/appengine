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
public interface BidirectionalConverter<I, O> {

	/**
	 * Converts to the input type.
	 *
	 * @param input
	 *            Input models.
	 * @return
	 * @throws ConversionFailureException
	 */
	public List<O> convertTo(Collection<I> input) throws ConversionFailureException;

	/**
	 * Converts to the output type.
	 *
	 * @param input
	 *            Input models.
	 * @return
	 * @throws ConversionFailureException
	 */
	public List<I> convertFrom(Collection<O> input) throws ConversionFailureException;

}
