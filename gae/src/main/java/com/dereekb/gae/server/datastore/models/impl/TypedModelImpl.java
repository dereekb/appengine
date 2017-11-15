package com.dereekb.gae.server.datastore.models.impl;

import com.dereekb.gae.server.datastore.models.TypedModel;

/**
 * {@link TypedModel} implementation.
 * 
 * @author dereekb
 *
 */
public class TypedModelImpl
        implements TypedModel {

	protected String modelType;

	public TypedModelImpl() {}

	public TypedModelImpl(String modelType) {
		this.setModelType(modelType);
	}

	// MARK: TypedModel
	@Override
	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	@Override
	public String toString() {
		return "TypedModelImpl [modelType=" + this.modelType + "]";
	}

}
