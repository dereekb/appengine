package com.dereekb.gae.server.event.event;

/**
 * {@link Event} with a described "action".
 * <p>
 * Useful in cases where a specific event code has specific sub-changes further.
 * I.E. CRUD Model changes, which can be created, update, or delete.
 * <p>
 * In many cases the key will be encoded.
 *
 * @author dereekb
 *
 * @deprecated Between groups and event codes, this isn't necessary for now. It
 *             belongs more with EventType than it does {@link Event}.
 */
@Deprecated
public interface ActionEvent
        extends Event {

	/**
	 * Returns the action key.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getEventAction();

}
