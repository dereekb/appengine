package com.dereekb.gae.server.event.event.service.impl;

import java.util.List;

import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.event.service.EventService;
import com.dereekb.gae.server.event.event.service.EventServiceListener;
import com.dereekb.gae.server.event.event.service.exception.EventServiceException;

/**
 * {@link EventService}.
 *
 * @author dereekb
 *
 */
public class EventServiceImpl
        implements EventService {

	private List<EventServiceListener> listeners;

	public EventServiceImpl(List<EventServiceListener> listeners) {
		this.setListeners(listeners);
	}

	public List<EventServiceListener> getListeners() {
		return this.listeners;
	}

	public void setListeners(List<EventServiceListener> listeners) {
		if (listeners == null) {
			throw new IllegalArgumentException("listeners cannot be null.");
		}

		this.listeners = listeners;
	}

	// MARK: EventService
	@Override
	public void submitEvent(Event event) throws EventServiceException {
		for (EventServiceListener listener : this.listeners) {
			listener.handleEvent(event);
		}
	}

	@Override
	public String toString() {
		return "EventServiceImpl [listeners=" + this.listeners + "]";
	}

}
