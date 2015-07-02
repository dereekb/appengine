package com.dereekb.gae.model.general.time;

/**
 * Represents a period between two {@link Time} instances during the day.
 *
 * @author dereekb
 */
public interface TimeSpan {

	/**
	 * Returns the {@link Time} at which this {@link TimeSpan} starts.
	 *
	 * @return {@link Time} instance. Never null.
	 */
	public Time getStartTime();

	/**
	 * Sets a new start {@link Time}.
	 *
	 * @param time
	 *            New {@link Time} to set.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>time</code> is null.
	 */
	public void setStartTime(Time time) throws IllegalArgumentException;

	/**
	 * Returns the {@link Time} at which this {@link TimeSpan} ends.
	 *
	 * @return {@link Time} instance. Never null.
	 */
	public Time getEndTime();

	/**
	 * Sets a new end {@link Time}.
	 *
	 * @param time
	 *            New {@link Time} to set.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>time</code> is null.
	 */
	public void setEndTime(Time time) throws IllegalArgumentException;

}
