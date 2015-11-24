package com.dereekb.gae.model.general.time.util;

import java.util.List;

import com.dereekb.gae.model.general.time.Time;
import com.dereekb.gae.model.general.time.TimeSpan;
import com.dereekb.gae.model.general.time.util.exception.MergeTimeSpanException;
import com.dereekb.gae.model.general.time.util.exception.SplitTimeSpanException;


/**
 * Interface for simplifying a {@link TimeSpan}.
 *
 * @author dereekb
 *
 */
public interface TimeSpanSimplifier {

	/**
	 * Checks if the input {@link TimeSpan} views can be merged into a single
	 * {@link TimeSpan}.
	 *
	 * @param timeSpan
	 *            {@link TimeSpan}, never {@code null}.
	 * @return {@code true} if a split is possible.
	 */
	public boolean canSplit(TimeSpan timeSpan);

	/**
	 * Splits the {@link TimeSpan}.
	 *
	 * @param timeSpan
	 *            {@link TimeSpan}, never {@code null}.
	 * @return {@link TimeSpan} array of split values.
	 * @throws SplitTimeSpanException
	 *             if the input cannot be split.
	 */
	public TimeSpan[] split(TimeSpan timeSpan) throws SplitTimeSpanException;

	/**
	 * Checks if the input {@link TimeSpan} views can be merged into a single
	 * {@link TimeSpan}.
	 *
	 * @param a
	 *            {@link TimeSpan}, never {@code null}.
	 * @param b
	 *            {@link TimeSpan}, never {@code null}.
	 * @return {@code true} if a merge is possible.
	 */
	public boolean canMerge(TimeSpan a,
	                        TimeSpan b);

	/**
	 * Merges together the input {@link TimeSpan} values.
	 *
	 * @param a
	 *            {@link TimeSpan}, never {@code null}.
	 * @param b
	 *            {@link TimeSpan}, never {@code null}.
	 * @return {@link TimeSpan} result.
	 * @throws MergeTimeSpanException
	 *             if they cannot be merged.
	 */
	public TimeSpan merge(TimeSpan a,
	                      TimeSpan b) throws MergeTimeSpanException;

	/**
	 * Returns {@code true} if the {@link TimeSpan} contains the input time.
	 *
	 * @param span
	 *            {@link TimeSpan}, never {@code null}.
	 * @param time
	 *            {@link Time}, never {@code null}.
	 * @return {@code true} if the {@link TimeSpan} contains the {@link Time}.
	 */
	public boolean isContained(TimeSpan span,
	                           Time time);

	/**
	 * Returns {@code true} if the first {@link TimeSpan} contains the times of
	 * the second.
	 *
	 * @param a
	 *            {@link TimeSpan}, never {@code null}.
	 * @param b
	 *            {@link TimeSpan}, never {@code null}.
	 * @return {@code true} if the first contains the second.
	 */
	public boolean isContained(TimeSpan a,
	                           TimeSpan b);

	/**
	 * Subtracts one {@link TimeSpan} from another.
	 *
	 * @param value
	 *            {@link TimeSpan}, never {@code null}.
	 * @param negative
	 *            {@link TimeSpan}, never {@code null}.
	 * @return {@link List} of {@link TimeSpan} values resulting from the
	 *         subtraction.
	 */
	public List<TimeSpan> subtract(TimeSpan value,
	                               TimeSpan negative);

}
