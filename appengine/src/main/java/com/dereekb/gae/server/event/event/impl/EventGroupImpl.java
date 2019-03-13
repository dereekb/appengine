package com.dereekb.gae.server.event.event.impl;

import com.dereekb.gae.server.event.event.EventGroup;

/**
 * {@link EventGroup} implementation.
 *
 * @author dereekb
 *
 */
public class EventGroupImpl
        implements EventGroup {

	private String eventGroupCode;

	public EventGroupImpl(EventGroup eventGroup) {
		this(eventGroup.getEventGroupCode());
	}

	public EventGroupImpl(String eventGroupCode) {
		this.setEventGroupCode(eventGroupCode);
	}

	// MARK: EventGroup
	@Override
	public String getEventGroupCode() {
		return this.eventGroupCode;
	}

	public void setEventGroupCode(String eventGroupCode) {
		if (eventGroupCode == null) {
			throw new IllegalArgumentException("eventGroupCode cannot be null.");
		}

		this.eventGroupCode = eventGroupCode.toLowerCase();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.eventGroupCode == null) ? 0 : this.eventGroupCode.hashCode());
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		EventGroupImpl other = (EventGroupImpl) obj;
		return EventGroup.isEquivalent(this, other);
	}

	@Override
	public String toString() {
		return "EventGroupImpl [eventGroupCode=" + this.eventGroupCode + "]";
	}

}
