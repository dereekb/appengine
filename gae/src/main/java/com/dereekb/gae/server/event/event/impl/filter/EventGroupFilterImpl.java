package com.dereekb.gae.server.event.event.impl.filter;

import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.event.EventGroup;
import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.server.event.event.impl.EventGroupImpl;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.impl.AbstractFilter;

/**
 * {@link Filter} for an {@link EventGroup}.
 *
 * @author dereekb
 *
 */
public class EventGroupFilterImpl extends AbstractFilter<Event> {

	private EventGroup eventGroup;

	public EventGroupFilterImpl(String eventGroup) {
		this(new EventGroupImpl(eventGroup));
	}

	public EventGroupFilterImpl(EventGroup eventGroup) {
		this.setEventGroup(eventGroup);
	}

	public EventGroup getEventGroup() {
		return this.eventGroup;
	}

	public void setEventGroup(EventGroup eventGroup) {
		if (eventGroup == null) {
			throw new IllegalArgumentException("eventGroup cannot be null.");
		}

		this.eventGroup = eventGroup;
	}

	// MARK: Filter
	@Override
	public FilterResult filterObject(Event event) {
		EventType eventType = event.getEventType();
		return FilterResult.valueOf(EventGroup.isEquivalent(eventType, this.eventGroup));
	}

	@Override
	public String toString() {
		return "EventGroupFilterImpl [eventGroup=" + this.eventGroup + "]";
	}

}
