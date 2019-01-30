package com.dereekb.gae.server.event.event.impl;

import com.dereekb.gae.server.event.event.BasicEvent;
import com.dereekb.gae.server.event.event.EventType;

/**
 * {@link BasicEvent} implementation.
 *
 * @author dereekb
 *
 */
public class BasicEventImpl
        implements BasicEvent {

	private String scope;
	private EventType eventType;

	public BasicEventImpl(EventType eventType) {
		this(null, eventType);
	}

	public BasicEventImpl(String scope, EventType eventType) {
		this.setScope(scope);
		this.setEventType(eventType);
	}

	@Override
	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public EventType getEventType() {
		return this.eventType;
	}

	public void setEventType(EventType eventType) {
		if (eventType == null) {
			throw new IllegalArgumentException("eventType cannot be null.");
		}

		this.eventType = eventType;
	}

	@Override
	public String toString() {
		return "BasicEventImpl [eventType=" + this.eventType + "]";
	}

}
