package com.dereekb.gae.model.general.time;

/**
 * Represents the hours in a day.
 *
 * @author dereekb
 *
 */
public interface Hour
        extends Comparable<Hour> {

	public static final Integer MIDNIGHT_HOUR = 0;
	public static final Integer NOON_HOUR = 12;
	public static final Integer MAX_HOUR = 23;

	/**
	 * @return {@link TimeAmPm} for the hour. Never null.
	 */
	public TimeAmPm getAmPm();

	/**
	 * Returns the "visual" hour number for this time. Ranges from 1-12.
	 *
	 * For example, 12AM = Midnight = 0 from {@link #getDayHour()};
	 *
	 * @return {@link Integer} between 1-12.
	 */
	public Integer getVisualHour();

	/**
	 * Returns the hour of the day. Ranges from 0-23.
	 *
	 * @return {@link Integer} between 0-23.
	 */
	public Integer getDayHour();

	/**
	 *
	 * @param hour
	 *            Input {@link Hour}. Never {@code null}.
	 * @return {@code true} if equal.
	 */
	public boolean equals(Hour hour);

}
