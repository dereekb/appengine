package com.dereekb.gae.model.extension.data.conversion.impl;

import com.dereekb.gae.model.extension.data.conversion.ModelDataConversionInfo;
import com.dereekb.gae.server.datastore.models.impl.TypedModelImpl;

/**
 * {@link ModelDataConversionInfo} implementation.
 *
 * @author dereekb
 *
 * @param <I>
 *            model type
 * @param <O>
 *            model dto type
 */
public class ModelDataConversionInfoImpl<I, O> extends TypedModelImpl
        implements ModelDataConversionInfo<I, O> {

	private Class<I> modelClass;
	private Class<O> modelDataClass;

	public ModelDataConversionInfoImpl(String modelType, Class<I> modelClass, Class<O> modelDataClass) {
		super(modelType);
		this.setModelClass(modelClass);
		this.setModelDataClass(modelDataClass);
	}

	@Override
	public Class<I> getModelClass() {
		return this.modelClass;
	}

	public void setModelClass(Class<I> modelClass) {
		if (modelClass == null) {
			throw new IllegalArgumentException("modelClass cannot be null.");
		}

		this.modelClass = modelClass;
	}

	@Override
	public Class<O> getModelDataClass() {
		return this.modelDataClass;
	}

	public void setModelDataClass(Class<O> modelDataClass) {
		if (modelDataClass == null) {
			throw new IllegalArgumentException("modelDataClass cannot be null.");
		}

		this.modelDataClass = modelDataClass;
	}

	@Override
	public String toString() {
		return "ModelDataConversionInfoImpl [modelType=" + this.modelType + ", modelClass=" + this.modelClass
		        + ", modelDataClass=" + this.modelDataClass + "]";
	}

}
