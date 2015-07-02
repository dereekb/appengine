package com.dereekb.gae.model.general.time;

/**
 * Denotes the AM/PM time.
 *
 * @author dereekb
 */
public enum TimeAmPm {

	/**
	 * Is in the morning.
	 */
	AM,

	/**
	 * Is in the afternoon.
	 */
	PM;

	public static TimeAmPm withBoolean(boolean isAm) {
		TimeAmPm amPm;

		if (isAm) {
			amPm = TimeAmPm.AM;
		} else {
			amPm = TimeAmPm.PM;
		}

		return amPm;
	}

}
