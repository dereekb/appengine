package com.dereekb.gae.server.event.event.service.impl;

import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.event.service.EventServiceListener;

/**
 * Abstract {@link EventServiceListener} implementation that attempts to casts
 * the input type to another expected class.
 *
 * @author dereekb
 *
 */
public abstract class AbstractCastEventServiceListenerImpl<T extends Event>
        implements EventServiceListener {

	private Class<T> eventClass;

	public AbstractCastEventServiceListenerImpl(Class<T> eventClass) {
		this.setEventClass(eventClass);
	}

	public Class<T> getEventClass() {
		return this.eventClass;
	}

	public void setEventClass(Class<T> eventClass) {
		if (eventClass == null) {
			throw new IllegalArgumentException("eventClass cannot be null.");
		}

		this.eventClass = eventClass;
	}

	// MARK: EventServiceListener
	@Override
	@SuppressWarnings("unchecked")
	public void handleEvent(Event event) {

		// Try casting to event class.
		if (this.eventClass.isAssignableFrom(event.getClass())) {
			T castEvent = (T) event;
			this.handleCastEvent(castEvent);
		}
	}

	public abstract void handleCastEvent(T event);

}
