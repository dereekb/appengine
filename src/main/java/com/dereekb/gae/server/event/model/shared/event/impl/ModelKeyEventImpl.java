package com.dereekb.gae.server.event.model.shared.event.impl;

import com.dereekb.gae.server.event.event.EventData;
import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.server.event.event.impl.EventImpl;
import com.dereekb.gae.server.event.model.shared.event.ModelKeyEvent;
import com.dereekb.gae.server.event.model.shared.event.ModelKeyEventData;

/**
 * {@link ModelKeyEvent} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelKeyEventImpl extends EventImpl
        implements ModelKeyEvent {

	public ModelKeyEventImpl(EventType eventType, ModelKeyEventData eventData) {
		super(eventType, eventData);
	}

	// MARK: ModelKeyEvent
	@Override
	public String getModelType() {
		return this.getEventData().getEventDataType();
	}

	// MARK: Override
	@Override
	public ModelKeyEventData getEventData() {
		return (ModelKeyEventData) super.getEventData();
	}

	@Override
	public void setEventData(EventData eventData) {
		if (ModelKeyEventData.class.isAssignableFrom(eventData.getClass())) {
			this.setEventData((ModelKeyEventData) eventData);
		} else {
			throw new UnsupportedOperationException("Can only set model event data.");
		}
	}

	public void setEventData(ModelKeyEventData eventData) {
		super.setEventData(eventData);
	}

	@Override
	public String toString() {
		return "ModelKeyEventImpl [getEventData()=" + this.getEventData() + ", getEventType()=" + this.getEventType()
		        + "]";
	}

}
