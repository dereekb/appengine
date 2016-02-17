package com.dereekb.gae.model.general.time;

/**
 * Represents a period between two {@link Time} instances during the day.
 *
 * @author dereekb
 */
public interface TimeSpan
        extends Comparable<TimeSpan> {

	/**
	 * Returns the {@link Time} at which this {@link TimeSpan} starts.
	 *
	 * @return {@link Time} instance. Never {@code null}.
	 */
	public Time getStartTime();

	/**
	 * Sets a new start {@link Time}.
	 *
	 * @param time
	 *            New {@link Time} to set.
	 * @throws IllegalArgumentException
	 *             Thrown if {@code time} is {@code null}.
	 */
	public void setStartTime(Time time) throws IllegalArgumentException;

	/**
	 * Returns the {@link Time} at which this {@link TimeSpan} ends.
	 *
	 * @return {@link Time} instance. Never {@code null}.
	 */
	public Time getEndTime();

	/**
	 * Sets a new end {@link Time}.
	 *
	 * @param time
	 *            New {@link Time} to set.
	 * @throws IllegalArgumentException
	 *             Thrown if {@code time} is {@code null}.
	 */
	public void setEndTime(Time time) throws IllegalArgumentException;

	/**
	 *
	 * @param time
	 *            {@link Time}. Never {@code null}.
	 * @return {@code true} if the {@link Time} is in this span.
	 */
	public boolean contains(Time time);

	/**
	 * @param timeSpan
	 *            {@link TimeSpan}. Never {@code null}.
	 * @return {@code true} if the {@link TimeSpan} is in this span.
	 */
	public boolean contains(TimeSpan timeSpan);

}
