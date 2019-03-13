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
public interface BasicEvent {

	/**
	 * Returns the Server/Service Scope of the event.
	 *
	 * @return {@link String}, or {@code null} if not set.
	 */
	public String getScope();

	/**
	 * Returns the event type.
	 *
	 * @return {@link EventType}. Never {@code null}.
	 */
	public EventType getEventType();

}
