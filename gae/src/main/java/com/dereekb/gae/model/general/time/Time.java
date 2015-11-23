package com.dereekb.gae.model.general.time;

/**
 * Represents a time of the day.
 *
 * @author dereekb
 *
 */
public interface Time
        extends Comparable<Time> {

	public final static Integer MINUTES_IN_HOUR = 60;
	public final static Integer MAX_TIME = 60 * 60 * 24; // Minutes in the day

	/**
	 * Returns the hours for this time.
	 *
	 * @return {@link Hour} instance. Never {@code null}.
	 */
	public Hour getHour();

	/**
	 * Sets the new hour value.
	 *
	 * @param hour
	 *            New hour to set it to. Never {@code null}.
	 * @throws IllegalArgumentException
	 *             Thrown if {@code hour} is {@code null} or invalid.
	 */
	public void setHour(Hour hour) throws IllegalArgumentException;

	/**
	 * Returns the minute for this time. Ranges from 0-59.
	 *
	 * @return {@link Integer} between 0-59. Never {@code null}.
	 */
	public Integer getMinutes();

	/**
	 * Sets the minute for this time.
	 *
	 * @param minutes
	 *            {@link Integer} from 0-59.
	 * @throws IllegalArgumentException
	 *             Thrown if {@code minute} is not between 0-59.
	 */
	public void setMinutes(Integer minutes) throws IllegalArgumentException;

	/**
	 * @param time
	 *            {@link Time}. Never {@code null}.
	 * @return {@link true} if the {@code Time} values are the same.
	 */
	public boolean equals(Time time);

}
