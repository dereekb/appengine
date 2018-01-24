package com.dereekb.gae.server.event.event.impl;

import com.dereekb.gae.server.event.event.EventType;

/**
 * {@link EventType} implementation.
 *
 * @author dereekb
 *
 */
public class EventTypeImpl
        implements EventType {

	private String eventGroupCode;
	private String eventTypeCode;

	public EventTypeImpl(EventType eventType) {
		this(eventType.getEventGroupCode(), eventType.getEventTypeCode());
	}

	public EventTypeImpl(String eventGroupCode, String eventTypeCode) {
		this.setEventGroupCode(eventGroupCode);
		this.setEventTypeCode(eventTypeCode);
	}

	// MARK: EventType
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
	public String getEventTypeCode() {
		return this.eventTypeCode;
	}

	public void setEventTypeCode(String eventTypeCode) {
		if (eventTypeCode == null) {
			throw new IllegalArgumentException("eventTypeCode cannot be null.");
		}

		this.eventTypeCode = eventTypeCode.toLowerCase();
	}

	public static boolean safeIsEquivalent(EventType a,
	                                       EventType b) {
		if (a == null) {
			return (b == null);
		} else if (b == null) {
			return false;
		} else {
			return isEquivalent(a, b);
		}
	}

	public static boolean isEquivalent(EventType a,
	                                   EventType other) {

		String aGroup = a.getEventGroupCode();
		String bGroup = other.getEventGroupCode();

		String aType = a.getEventTypeCode();
		String bType = other.getEventTypeCode();

		if (aGroup == null) {
			if (bGroup != null) {
				return false;
			}
		} else if (!aGroup.equalsIgnoreCase(bGroup)) {
			return false;
		}

		if (aType == null) {
			if (bType != null) {
				return false;
			}
		} else if (!aType.equalsIgnoreCase(bType)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.eventGroupCode == null) ? 0 : this.eventGroupCode.hashCode());
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

		return isEquivalent(this, (EventType) obj);
	}

	@Override
	public String toString() {
		return "EventTypeImpl [eventGroupCode=" + this.eventGroupCode + ", eventTypeCode=" + this.eventTypeCode + "]";
	}

}
