package com.dereekb.gae.server.event.event;

/**
 * The {@link Event}. code is a unique code to the type of event.
 * I.E. Model Updating
 *
 * @author dereekb
 *
 */
public interface EventType
        extends EventGroup {

	/**
	 * Returns the event type code.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getEventTypeCode();

}
