package com.dereekb.gae.model.general.time;

import java.util.Collection;
import java.util.List;

/**
 * Represents a set of {@link TimeSpan} values for a single day.
 *
 * @author dereekb
 */
public interface TimeSpanSet {

	/**
	 * Adds the {@link TimeSpan} to the set.
	 *
	 * @param input
	 *            {@link TimeSpan}. Never {@code null}.
	 */
	public void add(TimeSpan input);

	/**
	 * Removes the {@link TimeSpan} from the set.
	 *
	 * @param input
	 *            {@link TimeSpan}. Never {@code null}.
	 */
	public void remove(TimeSpan input);

	/**
	 * Returns a {@link List} of {@link TimeSpan} values.
	 *
	 * @return {@link Collection} of {@link TimeSpan}. Never {@code null}.
	 */
	public List<TimeSpan> getTimeSpans();

	/**
	 * Returns {@code true} if the {@link Time} is contained in this set.
	 *
	 * @param time
	 *            {@link Time}. Never {@code null}.
	 * @return {@code true} if in this set.
	 */
	public boolean contains(Time time);

	/**
	 * Returns {@code true} if the entire {@link TimeSpan} is contained in this
	 * set.
	 *
	 * @param timeSpan
	 *            {@link TimeSpan}. Never {@code null}.
	 * @return {@code true} if in this set.
	 */
	public boolean contains(TimeSpan timeSpan);

	/**
	 * Clears the set.
	 */
	public void clear();

}
