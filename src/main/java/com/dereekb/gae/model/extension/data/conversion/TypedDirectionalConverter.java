package com.dereekb.gae.model.extension.data.conversion;

import com.dereekb.gae.server.datastore.models.TypedModel;

/**
 * {@link DirectionalConverter} extension that implements {@link TypedModel}.
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
public interface TypedDirectionalConverter<I, O>
        extends DirectionalConverter<I, O>, TypedModel {}
