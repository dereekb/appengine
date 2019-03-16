package com.dereekb.gae.server.event.event;

/**
 * An {@link BasicEvent} with data.
 *
 * @author dereekb
 *
 */
public interface Event
        extends BasicEvent {

	/**
	 * Returns the event data, if applicable.
	 *
	 * @return {@link EventData}. Never {@code null}.
	 */
	public EventData getEventData();

}
