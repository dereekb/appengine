package com.dereekb.gae.server.event.event;

/**
 * {@link Event} code is a unique code to the type of event. Is part of a
 * {@link EventGroup} that helps identify it.
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

	// MARK: Utility
	public static boolean safeIsEquivalent(EventType a,
	                                       EventType b) {
		if (a == null) {
			return (b == null);
		} else if (b == null) {
			return false;
		} else {
			return isEquivalent(a, b);
		}
	}

	public static boolean isEquivalent(EventType a,
	                                   EventType b) {

		String aType = a.getEventTypeCode();
		String bType = b.getEventTypeCode();

		if (!EventGroup.isEquivalent(a, b)) {
			return false;
		}

		if (aType == null) {
			if (bType != null) {
				return false;
			}
		} else if (!aType.equalsIgnoreCase(bType)) {
			return false;
		}

		return true;
	}

}
