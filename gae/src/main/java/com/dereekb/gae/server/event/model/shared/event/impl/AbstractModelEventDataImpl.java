package com.dereekb.gae.server.event.model.shared.event.impl;

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

	public AbstractModelEventDataImpl(String modelType) {
		super(ModelEventData.EVENT_DATA_TYPE, modelType);
	}

	// MARK: ModelEventData
	@Override
	public abstract List<T> getEventModels();

	// MARK: AbstractModelKeyEventDataImpl
	@Override
	public List<ModelKey> getEventModelKeys() {
		return ModelKey.readModelKeys(this.getEventModels());
	}

}
