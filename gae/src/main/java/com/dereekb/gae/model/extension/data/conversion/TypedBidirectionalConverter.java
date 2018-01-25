package com.dereekb.gae.model.extension.data.conversion;

import com.dereekb.gae.server.datastore.models.TypedModel;

/**
 * {@link BidirectionalConverter} extension that implements {@link TypedModel}.
 * <p>
 * The type represents the input model type.
 *
 * @author dereekb
 *
 * @param <I>
 *            Input Type
 * @param <O>
 *            Output Type
 */
public interface TypedBidirectionalConverter<I, O>
        extends BidirectionalConverter<I, O>, TypedModel {}
