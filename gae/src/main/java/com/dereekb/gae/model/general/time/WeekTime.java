package com.dereekb.gae.model.general.time;

/**
 * Represents a week, and a time-span during that week.
 *
 * @author dereekb
 *
 */
public interface WeekTime {

	/**
	 * Returns the {@link DaySpan} for this {@link WeekTime}.
	 *
	 * @return {@link DaySpan} instance. Never null.
	 */
	public DaySpan getDaySpan();

	/**
	 * Returns the {@link TimeSpan} for this {@link WeekTime}.
	 *
	 * @return {@link TimeSpan} instance. Never null.
	 */
	public TimeSpan getTimeSpan();

}
