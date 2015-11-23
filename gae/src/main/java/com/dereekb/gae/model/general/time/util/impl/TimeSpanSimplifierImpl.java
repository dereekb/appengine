package com.dereekb.gae.model.general.time.util.impl;

import com.dereekb.gae.model.general.time.Time;
import com.dereekb.gae.model.general.time.TimeSpan;
import com.dereekb.gae.model.general.time.impl.TimeImpl;
import com.dereekb.gae.model.general.time.impl.TimeSpanImpl;
import com.dereekb.gae.model.general.time.util.TimeSpanSimplifier;
import com.dereekb.gae.model.general.time.util.exception.MergeTimeSpanException;
import com.dereekb.gae.model.general.time.util.exception.SplitTimeSpanException;

/**
 * {@link TimeSpanSimplifier} implementation.
 *
 * @author dereekb
 *
 */
public class TimeSpanSimplifierImpl
        implements TimeSpanSimplifier {

	/**
	 * Can split if the {@link Time} in a {@link TimeSpan} goes to the next day.
	 */
	@Override
	public boolean canSplit(TimeSpan timeSpan) {
		Time start = timeSpan.getStartTime();
		Time end = timeSpan.getEndTime();

		return TimeImpl.isAfter(start, end);
	}

	@Override
	public TimeSpan[] split(TimeSpan timeSpan) throws SplitTimeSpanException {
		if (this.canSplit(timeSpan) == false) {
			throw new SplitTimeSpanException();
		}

		Time start = timeSpan.getStartTime();
		Time end = timeSpan.getEndTime();

		return new TimeSpan[] { TimeSpanImpl.toMidnight(start), TimeSpanImpl.fromMidnight(end) };
	}

	/**
	 * Can merge if the {@link TimeSpan} values intersect with each other.
	 *
	 * Cannot merge values that {@link #canSplit(TimeSpan)} returns {@code true}
	 * for.
	 */
	@Override
	public boolean canMerge(TimeSpan a,
	                        TimeSpan b) {

		Time startA = a.getStartTime();
		Time startB = b.getStartTime();

		Time endA = a.getEndTime();
		Time endB = b.getEndTime();

		return (a.equals(b) || (TimeImpl.isBeforeOrEqual(startB, endA) && TimeImpl.isBeforeOrEqual(startA, endB)));
	}

	@Override
	public TimeSpan merge(TimeSpan a,
	                      TimeSpan b) throws MergeTimeSpanException {
		if (this.canMerge(a, b) == false) {
			throw new MergeTimeSpanException();
		}

		Time startA = a.getStartTime();
		Time startB = b.getStartTime();

		Time endA = a.getEndTime();
		Time endB = b.getEndTime();

		Time start = TimeImpl.isBefore(startA, startB) ? startA : startB;
		Time end = TimeImpl.isAfter(endA, endB) ? endA : endB;

		return new TimeSpanImpl(start, end);
	}

	@Override
	public boolean isContained(TimeSpan span,
	                           Time time) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isContained(TimeSpan a,
	                           TimeSpan b) {
		Time startA = a.getStartTime();
		Time startB = b.getStartTime();

		Time endA = a.getEndTime();
		Time endB = b.getEndTime();

		return (TimeImpl.isBeforeOrEqual(startA, startB) && TimeImpl.isAfterOrEqual(endA, endB));
	}

}
