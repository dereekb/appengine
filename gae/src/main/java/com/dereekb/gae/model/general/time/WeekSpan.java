package com.dereekb.gae.model.general.time;


/**
 * Represents a full week, and all times within that week.
 *
 * @author dereekb
 *
 */
public interface WeekSpan
        extends DayTimeSpanPairSetConvertable {

	/**
	 * Check if this contains a specific {@link Time}.
	 *
	 * @param time
	 *            {@link Time}. Never {@code null}.
	 * @return {@code true} if the week contains that time.
	 */
	// public boolean contains(Time time);

	/**
	 * Check if this contains a specific {@link TimeSpan}.
	 *
	 * @param timespan
	 *            {@link TimeSpan}. Never {@code null}.
	 * @return {@code true} if the week contains the entire timespan.
	 */
	// public boolean contains(TimeSpan timespan);

	/**
	 * Check if this contains/matches all times in a {@link WeekTime}.
	 *
	 * @param times
	 *            {@link WeekTime}. Never {@code null}.
	 * @return
	 */
	public boolean contains(WeekTime times);

	/**
	 * Adds new times to this {@link WeekSpan}.
	 *
	 * @param time
	 *            {@link WeekTime} used to add matching times.
	 */
	public void add(WeekTime time);

	/**
	 * Removes all matching times from this {@link WeekSpan}.
	 *
	 * @param time
	 *            {@link WeekTime} use to remove matching times.
	 */
	public void remove(WeekTime time);

}
