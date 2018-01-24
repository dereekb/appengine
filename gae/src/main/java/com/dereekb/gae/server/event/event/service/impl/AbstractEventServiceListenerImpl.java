package com.dereekb.gae.server.event.event.service.impl;

import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.event.EventType;
import com.dereekb.gae.server.event.event.service.EventServiceListener;

/**
 * Abstract {@link EventServiceListener} implementation that filters on a
 * specific event group and casts the input type to another expected class.
 *
 * @author dereekb
 *
 */
public abstract class AbstractEventServiceListenerImpl<T extends Event>
        implements EventServiceListener {

	private Class<T> eventClass;
	private String eventGroup;

	public AbstractEventServiceListenerImpl(Class<T> eventClass, String eventGroup) {
		this.setEventClass(eventClass);
		this.setEventGroup(eventGroup);
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

	public String getEventGroup() {
		return this.eventGroup;
	}

	public void setEventGroup(String eventGroup) {
		if (eventGroup == null) {
			throw new IllegalArgumentException("eventGroup cannot be null.");
		}

		this.eventGroup = eventGroup;
	}

	// MARK: EventServiceListener
	@Override
	@SuppressWarnings("unchecked")
	public void handleEvent(Event event) {
		EventType type = event.getEventType();
		String eventGroup = type.getEventGroupCode();

		// Filter on specific event group.
		if (eventGroup.equals(this.eventGroup)) {

			// Try casting to ModelKeyEvent
			if (this.eventClass.isAssignableFrom(event.getClass())) {
				T castEvent = (T) event;
				this.handleCastEvent(castEvent);
			}
		}
	}

	public abstract void handleCastEvent(T event);

	@Override
	public String toString() {
		return "AbstractEventServiceListenerImpl [eventClass=" + this.eventClass + ", eventGroup=" + this.eventGroup
		        + "]";
	}

}
