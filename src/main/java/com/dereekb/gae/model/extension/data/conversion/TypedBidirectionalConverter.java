package com.dereekb.gae.model.extension.data.conversion;

/**
 * {@link BidirectionalConverter} extension that implements
 * {@link ModelDataConversionInfo}.
 *
 * @author dereekb
 *
 * @param <I>
 *            Input Type
 * @param <O>
 *            Output Type
 */
public interface TypedBidirectionalConverter<I, O>
        extends BidirectionalConverter<I, O>, ModelDataConversionInfo<I, O> {

}
