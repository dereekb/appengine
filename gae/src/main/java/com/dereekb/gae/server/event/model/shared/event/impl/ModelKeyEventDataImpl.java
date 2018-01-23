package com.dereekb.gae.server.event.model.shared.event.impl;

import java.util.Collections;
import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.event.model.shared.event.ModelKeyEventData;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;

/**
 * {@link ModelKeyEventData} implementation.
 *
 * @author dereekb
 *
 */
public class ModelKeyEventDataImpl extends AbstractModelKeyEventDataImpl {

	public static final String MODEL_KEY_EVENT_DATA_TYPE = "model_key";

	private List<ModelKey> eventModelKeys;

	public ModelKeyEventDataImpl(String modelType) {
		this(modelType, Collections.emptyList());
	}

	public ModelKeyEventDataImpl(String modelType, List<ModelKey> eventModelKeys) {
		super(MODEL_KEY_EVENT_DATA_TYPE, modelType);
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
	public ApiResponseData getWebSafeData() {
		// TODO Auto-generated method stub
		return null;
	}

}
