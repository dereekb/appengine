package com.dereekb.gae.server.event.model.shared.event.impl;

import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.event.event.impl.AbstractEventData;
import com.dereekb.gae.server.event.model.shared.event.ModelKeyEventData;

/**
 * Abstract {@link ModelKeyEventData} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractModelKeyEventDataImpl extends AbstractEventData
        implements ModelKeyEventData {

	private String modelType;

	public AbstractModelKeyEventDataImpl(String eventDataType, String modelType) {
		super(eventDataType);
		this.setModelType(modelType);
	}

	@Override
	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		if (modelType == null) {
			throw new IllegalArgumentException("modelType cannot be null.");
		}

		this.modelType = modelType;
	}

	// MARK: ModelKeyEventData
	@Override
	public abstract List<ModelKey> getEventModelKeys();

	@Override
	public String toString() {
		return "AbstractModelKeyEventDataImpl [modelType=" + this.modelType + ", getEventModelKeys()="
		        + this.getEventModelKeys() + "]";
	}

}
