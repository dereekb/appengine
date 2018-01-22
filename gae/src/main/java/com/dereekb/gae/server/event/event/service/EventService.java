package com.dereekb.gae.server.event.event.service;

import com.dereekb.gae.server.event.event.Event;

/**
 * {@link Event} service use for submitting events.
 *
 * @author dereekb
 *
 */
public interface EventService {

	/**
	 * Submits the event to the service.
	 * <p>
	 * The event will be immediately digested and used by the relevant
	 * listeners.
	 *
	 */
	public void submitEvent(Event event);

}
