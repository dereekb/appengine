package com.dereekb.gae.model.extension.data.conversion;

import com.dereekb.gae.server.datastore.models.TypedModel;

/**
 * {@link TypedModel} extension that represents the types of the model and it's
 * data transfer object.
 * <p>
 * The type represents the input model type.
 *
 * @author dereekb
 *
 * @param <I>
 *            input type
 * @param <O>
 *            output type
 */
public interface ModelDataConversionInfo<I, O>
        extends TypedModel {

	/**
	 *
	 * @return {@link Class}. Never {@code null}.
	 */
	public Class<I> getModelClass();

	/**
	 *
	 * @return {@link Class}. Never {@code null}.
	 */
	public Class<O> getModelDataClass();

}
