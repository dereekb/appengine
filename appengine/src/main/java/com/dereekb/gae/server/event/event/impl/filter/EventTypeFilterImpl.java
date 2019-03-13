package com.dereekb.gae.server.event.event.impl.filter;

import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.event.EventGroup;
import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.server.event.event.impl.EventTypeImpl;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.impl.AbstractFilter;

/**
 * {@link Filter} for an {@link EventType}.
 *
 * @author dereekb
 *
 */
public class EventTypeFilterImpl extends AbstractFilter<Event> {

	private EventType eventType;

	public EventTypeFilterImpl(EventGroup eventGroup, String eventType) {
		this(new EventTypeImpl(eventGroup, eventType));
	}

	public EventTypeFilterImpl(EventType eventType) {
		this.setEventType(eventType);
	}

	public EventType getEventType() {
		return this.eventType;
	}

	public void setEventType(EventType eventType) {
		if (eventType == null) {
			throw new IllegalArgumentException("eventType cannot be null.");
		}

		this.eventType = eventType;
	}

	// MARK: Filter
	@Override
	public FilterResult filterObject(Event event) {
		EventType eventType = event.getEventType();
		return FilterResult.valueOf(EventType.isEquivalent(eventType, this.eventType));
	}

	@Override
	public String toString() {
		return "EventTypeFilterImpl [eventType=" + this.eventType + "]";
	}

}
