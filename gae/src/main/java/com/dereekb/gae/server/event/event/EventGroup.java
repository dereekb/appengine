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

	// MARK: Utility
	public static boolean safeIsEquivalent(EventGroup a,
	                                       EventGroup b) {
		if (a == null) {
			return (b == null);
		} else if (b == null) {
			return false;
		} else {
			return isEquivalent(a, b);
		}
	}

	public static boolean isEquivalent(EventGroup a,
	                                   EventGroup b) {

		String aGroup = a.getEventGroupCode();
		String bGroup = b.getEventGroupCode();

		return isEquivalent(aGroup, bGroup);
	}

	public static boolean isEquivalent(String aGroup,
	                                   String bGroup) {

		if (aGroup == null) {
			if (bGroup != null) {
				return false;
			}
		} else if (!aGroup.equalsIgnoreCase(bGroup)) {
			return false;
		}

		return true;
	}

}
