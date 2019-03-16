package com.dereekb.gae.server.event.event.impl;

import com.dereekb.gae.server.event.event.EventData;

/**
 * Abstract {@link EventData} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractEventDataImpl
        implements EventData {

	private String eventDataType;

	public AbstractEventDataImpl(String eventDataType) {
		this.setEventDataType(eventDataType);
	}

	// MARK: EventData
	@Override
	public String getEventDataType() {
		return this.eventDataType;
	}

	public void setEventDataType(String eventDataType) {
		if (eventDataType == null) {
			throw new IllegalArgumentException("eventDataType cannot be null.");
		}

		this.eventDataType = eventDataType;
	}

	@Override
	public String keyValue() {
		return this.eventDataType;
	}

	@Override
	public String toString() {
		return "AbstractEventDataImpl [eventDataType=" + this.eventDataType + "]";
	}

}
