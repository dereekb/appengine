package com.dereekb.gae.server.event.event.impl;

import com.dereekb.gae.server.event.event.EventGroup;
import com.dereekb.gae.server.event.event.EventType;

/**
 * {@link EventType} implementation.
 *
 * @author dereekb
 *
 */
public class EventTypeImpl extends EventGroupImpl
        implements EventType {

	private String eventTypeCode;

	public EventTypeImpl(EventType eventType) {
		this(eventType.getEventGroupCode(), eventType.getEventTypeCode());
	}

	public EventTypeImpl(EventGroup eventGroup, String eventTypeCode) {
		this(eventGroup.getEventGroupCode(), eventTypeCode);
	}

	public EventTypeImpl(String eventGroupCode, String eventTypeCode) {
		super(eventGroupCode);
		this.setEventTypeCode(eventTypeCode);
	}

	// MARK: EventType
	@Override
	public String getEventTypeCode() {
		return this.eventTypeCode;
	}

	public void setEventTypeCode(String eventTypeCode) {
		if (eventTypeCode == null) {
			throw new IllegalArgumentException("eventTypeCode cannot be null.");
		}

		this.eventTypeCode = eventTypeCode.toLowerCase();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.eventTypeCode == null) ? 0 : this.eventTypeCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (!EventType.class.isAssignableFrom(obj.getClass())) {
			return false;
		}

		return EventType.isEquivalent(this, (EventType) obj);
	}

	@Override
	public String toString() {
		return "EventTypeImpl [eventTypeCode=" + this.eventTypeCode + ", getEventGroupCode()="
		        + this.getEventGroupCode() + "]";
	}

}
