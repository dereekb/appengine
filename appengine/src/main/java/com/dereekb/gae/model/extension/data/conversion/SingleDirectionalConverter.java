package com.dereekb.gae.model.extension.data.conversion;

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
public interface SingleDirectionalConverter<I, O> {

	/**
	 * Converts to the input type.
	 *
	 * @param input
	 *            Input model.
	 * @return Output model. Never null.
	 * @throws ConversionFailureException
	 *             if the conversion cannot be completed.
	 */
	public O convertSingle(I input) throws ConversionFailureException;

}
