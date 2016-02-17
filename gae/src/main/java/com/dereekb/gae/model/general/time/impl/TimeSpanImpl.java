package com.dereekb.gae.model.general.time.impl;

import com.dereekb.gae.model.general.time.Time;
import com.dereekb.gae.model.general.time.TimeSpan;
import com.dereekb.gae.model.general.time.util.impl.TimeSpanSimplifierImpl;

/**
 * Default {@link TimeSpan} implementation.
 *
 * @author dereekb
 *
 */
public class TimeSpanImpl
        implements TimeSpan {

	private Time start;
	private Time end;

	public TimeSpanImpl(Time start, Time end) throws IllegalArgumentException {
		this.setStartTime(start);
		this.setEndTime(end);
	}

	public TimeSpanImpl(TimeSpan input) throws IllegalArgumentException {
		if (input == null) {
			throw new IllegalArgumentException("Input TimeSpan cannot be null.");
		}

		this.setStartTime(input.getStartTime());
		this.setEndTime(input.getEndTime());
	}

	public static TimeSpanImpl fromMidnight(Time end) {
		return new TimeSpanImpl(TimeImpl.midnight(), end);
	}

	public static TimeSpanImpl toMidnight(Time start) {
		return new TimeSpanImpl(start, TimeImpl.max());
	}

	public static TimeSpan allDay() {
		return new TimeSpanImpl(TimeImpl.midnight(), TimeImpl.max());
	}

	@Override
	public Time getStartTime() {
		return this.start;
	}

	@Override
	public void setStartTime(Time time) throws IllegalArgumentException {
		if (time == null) {
			throw new IllegalArgumentException("Start time cannot be null.");
		}

		this.start = time;
	}

	@Override
	public Time getEndTime() {
		return this.end;
	}

	@Override
	public void setEndTime(Time time) throws IllegalArgumentException {
		if (time == null) {
			throw new IllegalArgumentException("End time cannot be null.");
		}

		this.end = time;
	}

	// MARK: Comparable
	/**
	 * {@link TimeSpan} is compared by first comparing the starting times.
	 * <p>
	 * If {@link #start} for this time is before the other value, then this
	 * {@link TimeSpan} is before the other.
	 * <p>
	 * If they are equal, then the {@link TimeSpan} with the smaller
	 * {@link #end} value is considered "greater" and comes before the other.
	 */
	@Override
	public int compareTo(TimeSpan o) {
		int compare = 0;
		Integer startComparison = this.start.compareTo(o.getStartTime());

		if (startComparison == 0) {
			compare = this.end.compareTo(o.getEndTime());
		} else {
			compare = startComparison;
		}

		return compare;
	}

	@Override
	public String toString() {
		return "TimeSpanImpl [start=" + this.start + ", end=" + this.end + "]";
	}

	@Override
	public boolean contains(Time time) {
		return TimeSpanSimplifierImpl.SIMPLIFIER.isContained(this, time);
	}

	@Override
	public boolean contains(TimeSpan timeSpan) {
		return TimeSpanSimplifierImpl.SIMPLIFIER.isContained(this, timeSpan);
	}

}
