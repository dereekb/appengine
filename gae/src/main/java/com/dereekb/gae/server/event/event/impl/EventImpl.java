package com.dereekb.gae.server.event.event.impl;

import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.event.EventData;
import com.dereekb.gae.server.event.event.EventType;

/**
 * {@link Event} implementation.
 *
 * @author dereekb
 *
 */
public class EventImpl extends BasicEventImpl
        implements Event {

	private EventData eventData;

	public EventImpl(EventType eventType) {
		this(eventType, EmptyEventData.make());
	}

	public EventImpl(EventType eventType, EventData eventData) {
		super(eventType);
		this.setEventData(eventData);
	}

	@Override
	public EventData getEventData() {
		return this.eventData;
	}

	public void setEventData(EventData eventData) {
		if (eventData == null) {
			throw new IllegalArgumentException("eventData cannot be null.");
		}

		this.eventData = eventData;
	}

	@Override
	public String toString() {
		return "EventImpl [eventData=" + this.eventData + ", getEventType()=" + this.getEventType() + "]";
	}

}
