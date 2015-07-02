package com.dereekb.gae.model.general.time;

/**
 * Represents a time of the day.
 *
 * @author dereekb
 *
 */
public interface Time {

	/**
	 * Returns the hours for this time.
	 *
	 * @return {@link Hour} instance. Never null.
	 */
	public Hour getHour();

	/**
	 * Sets the new hour value.
	 *
	 * @param hour
	 *            New hour to set it to. Never null.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>hour</code> is null or invalid.
	 */
	public void setHour(Hour hour) throws IllegalArgumentException;

	/**
	 * Returns the minute for this time. Ranges from 0-59.
	 *
	 * @return {@link Integer} between 0-59. Never null.
	 */
	public Integer getMinutes();

	/**
	 * Sets the minute for this time.
	 *
	 * @param minutes
	 *            {@link Integer} from 0-59.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>minute</code> is not between 0-59.
	 */
	public void setMinutes(Integer minutes) throws IllegalArgumentException;

}
