package com.dereekb.gae.server.event.model.shared.event.impl;

import java.util.Collections;
import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.event.model.shared.event.ModelKeyEventData;

/**
 * {@link ModelKeyEventData} implementation.
 *
 * @author dereekb
 *
 */
public class ModelKeyEventDataImpl extends AbstractModelKeyEventDataImpl {

	private List<ModelKey> eventModelKeys;

	public ModelKeyEventDataImpl(String modelType) {
		this(modelType, Collections.emptyList());
	}

	public ModelKeyEventDataImpl(String modelType, List<ModelKey> eventModelKeys) {
		super(ModelKeyEventData.EVENT_DATA_TYPE, modelType);
		this.setEventModelKeys(eventModelKeys);
	}

	// MARK: AbstractModelKeyEventDataImpl
	@Override
	public List<ModelKey> getEventModelKeys() {
		return this.eventModelKeys;
	}

	public void setEventModelKeys(List<ModelKey> eventModelKeys) {
		if (eventModelKeys == null) {
			throw new IllegalArgumentException("eventModelKeys cannot be null.");
		}

		this.eventModelKeys = eventModelKeys;
	}

	@Override
	public String toString() {
		return "ModelKeyEventDataImpl [eventModelKeys=" + this.eventModelKeys + ", getModelType()="
		        + this.getModelType() + ", getEventDataType()=" + this.getEventDataType() + "]";
	}

}
