package com.dereekb.gae.server.event.model.shared.event.impl;

import java.util.Collections;
import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.event.model.shared.event.ModelEventData;
import com.dereekb.gae.server.event.model.shared.event.ModelKeyEventData;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;

/**
 * Abstract {@link ModelKeyEventData} implementation.
 * <p>
 * Lacks the proper functions to convert to {@link ApiResponseData}.
 *
 * @author dereekb
 *
 */
public abstract class AbstractModelEventDataImpl<T extends UniqueModel> extends AbstractModelKeyEventDataImpl
        implements ModelEventData<T> {

	public static final String MODEL_KEY_EVENT_DATA_TYPE = "model_key";

	private List<T> eventModels;

	public AbstractModelEventDataImpl(String modelType) {
		this(modelType, Collections.emptyList());
	}

	public AbstractModelEventDataImpl(String modelType, List<T> eventModels) {
		super(MODEL_KEY_EVENT_DATA_TYPE, modelType);
		this.setEventModels(eventModels);
	}

	// MARK: ModelEventData
	@Override
	public List<T> getEventModels() {
		return this.eventModels;
	}

	public void setEventModels(List<T> eventModels) {
		if (eventModels == null) {
			throw new IllegalArgumentException("eventModels cannot be null.");
		}

		this.eventModels = eventModels;
	}

	// MARK: AbstractModelKeyEventDataImpl
	@Override
	public List<ModelKey> getEventModelKeys() {
		return ModelKey.readModelKeys(this.getEventModels());
	}

	@Override
	public String toString() {
		return "AbstractModelEventDataImpl [eventModels=" + this.eventModels + ", getModelType()=" + this.getModelType()
		        + ", getEventDataType()=" + this.getEventDataType() + "]";
	}

}
