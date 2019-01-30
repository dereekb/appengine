package com.dereekb.gae.server.event.model.shared.event.impl;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.server.event.model.shared.event.ModelEvent;
import com.dereekb.gae.server.event.model.shared.event.ModelEventData;

/**
 * {@link ModelEvent} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelEventImpl<T extends UniqueModel> extends ModelKeyEventImpl
        implements ModelEvent<T> {

	public ModelEventImpl(EventType eventType, ModelEventData<T> eventData) {
		super(eventType, eventData);
	}

	// MARK: Override
	@SuppressWarnings("unchecked")
	@Override
	public ModelEventData<T> getEventData() {
		return (ModelEventData<T>) super.getEventData();
	}

	public void setEventData(ModelEventData<T> eventData) {
		super.setEventData(eventData);
	}

	@Override
	public String toString() {
		return "ModelEventImpl [getEventData()=" + this.getEventData() + ", getEventType()=" + this.getEventType()
		        + "]";
	}

}
