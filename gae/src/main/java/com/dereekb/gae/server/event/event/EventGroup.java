package com.dereekb.gae.server.event.event;

/**
 * An {@link Event} group gives a general idea of what type of event is
 * occurring.
 * <p>
 * I.E. model event, etc.
 *
 * @author dereekb
 *
 */
public interface EventGroup {

	/**
	 * Returns the event group code.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getEventGroupCode();

}
