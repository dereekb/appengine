package com.dereekb.gae.server.event.event;

/**
 * A server event that is emitted when a certain action occurs.
 * <p>
 * Events are primarily used for relaying information to external sources that
 * something has happened.
 * <p>
 * They should not be used for modifying objects; those sorts of actions should
 * be configured to occur in the taskqueue as a {@link Task}.
 *
 * @author dereekb
 *
 */
public interface Event {

	/**
	 * Returns the event group.
	 */
	public String getEventGroup();

	/**
	 * Returns the event type code.
	 */
	public String getEventType();

	/**
	 * Returns the event data, if applicable.
	 *
	 * @return {@link EventData}, {@code null} if none exists.
	 */
	public EventData getEventData();

}
