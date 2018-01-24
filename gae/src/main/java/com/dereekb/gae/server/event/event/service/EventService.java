package com.dereekb.gae.server.event.event.service;

import com.dereekb.gae.server.event.event.Event;
import com.dereekb.gae.server.event.event.service.exception.EventServiceException;

/**
 * {@link Event} service use for submitting events.
 *
 * @author dereekb
 *
 */
public interface EventService {

	/**
	 * Submits the event to the service.
	 *
	 * @param event
	 *            {@link Event}. Never {@code null}.
	 * @throws EventServiceException
	 *             thrown if the service encounters an error.
	 */
	public void submitEvent(Event event) throws EventServiceException;

}
