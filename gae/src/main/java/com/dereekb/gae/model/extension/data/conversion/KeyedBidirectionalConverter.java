package com.dereekb.gae.model.extension.data.conversion;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;

/**
 * Extension of {@link BidirectionalConverter} that allows specifying a
 * conversion type using a {@link String} key.
 *
 * @author dereekb
 *
 * @param <I>
 *            Input type.
 * @param <O>
 *            Output type.
 */
public interface KeyedBidirectionalConverter<I, O>
        extends BidirectionalConverter<I, O> {

	/**
	 * Converts to the input type.
	 *
	 * @param key
	 *            (Optional) key to use for conversion.
	 * @param input
	 *            Input models.
	 * @return
	 * @throws ConversionFailureException
	 */
	public List<O> convertTo(String key,
	                         Collection<I> input) throws ConversionFailureException;

}
