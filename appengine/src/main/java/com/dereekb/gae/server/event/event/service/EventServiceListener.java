package com.dereekb.gae.server.event.event.service;

import com.dereekb.gae.server.event.event.Event;

/**
 * {@link EventService} listener.
 *
 * @author dereekb
 *
 */
public interface EventServiceListener {

	/**
	 * Handles an event. The event can also be ignored by the listener.
	 *
	 * @param event
	 *            {@link Event}. Never {@code null}.
	 */
	public void handleEvent(Event event);

}
