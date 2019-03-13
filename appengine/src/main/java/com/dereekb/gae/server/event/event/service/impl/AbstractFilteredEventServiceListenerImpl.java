package com.dereekb.gae.server.event.event.service.impl;

import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.event.EventGroup;
import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.server.event.event.impl.filter.EventGroupFilterImpl;
import com.dereekb.gae.server.event.event.impl.filter.EventTypeFilterImpl;
import com.dereekb.gae.server.event.event.service.EventServiceListener;
import com.dereekb.gae.utilities.filters.Filter;

/**
 * Abstract {@link EventServiceListener} implementation that attempts to casts
 * the input type to another expected class.
 *
 * @author dereekb
 *
 */
public abstract class AbstractFilteredEventServiceListenerImpl<T extends Event> extends AbstractCastEventServiceListenerImpl<T> {

	private Filter<Event> filter;

	public AbstractFilteredEventServiceListenerImpl(Class<T> eventClass, EventType eventType) {
		super(eventClass);
		this.setFilter(eventType);
	}

	public AbstractFilteredEventServiceListenerImpl(Class<T> eventClass, EventGroup eventGroup) {
		super(eventClass);
		this.setFilter(eventGroup);
	}

	public AbstractFilteredEventServiceListenerImpl(Class<T> eventClass, Filter<Event> filter) {
		super(eventClass);
		this.setFilter(filter);
	}

	public Filter<Event> getFilter() {
		return this.filter;
	}

	public void setFilter(EventType eventType) {
		this.setFilter(new EventTypeFilterImpl(eventType));
	}

	public void setFilter(EventGroup eventGroup) {
		this.setFilter(new EventGroupFilterImpl(eventGroup));
	}

	public void setFilter(Filter<Event> filter) {
		if (filter == null) {
			throw new IllegalArgumentException("filter cannot be null.");
		}

		this.filter = filter;
	}

	// MARK: EventServiceListener
	@Override
	public void handleEvent(Event event) {
		if (this.filter.filterObject(event).isPass()) {
			super.handleEvent(event);
		}
	}

	@Override
	public String toString() {
		return "AbstractFilteredEventServiceListenerImpl [filter=" + this.filter + "]";
	}

}
