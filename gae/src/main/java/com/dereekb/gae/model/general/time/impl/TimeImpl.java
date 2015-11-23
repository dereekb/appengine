package com.dereekb.gae.model.general.time.impl;

import com.dereekb.gae.model.general.time.Hour;
import com.dereekb.gae.model.general.time.Time;
import com.dereekb.gae.model.general.time.TimeAmPm;

/**
 * Default {@link Time} implementation.
 *
 * @author dereekb
 *
 */
public class TimeImpl
        implements Time {

	private Hour hour;
	private Integer minutes;

	public TimeImpl(Time time) {
		this(time.getHour(), time.getMinutes());
	}

	public TimeImpl(Integer hours, Integer minutes, TimeAmPm timeAmPm) {
		this.hour = new HourImpl(hours, timeAmPm);
		this.minutes = minutes;
	}

	public TimeImpl(Integer hours, Integer minutes) {
		this.hour = new HourImpl(hours);
		this.minutes = minutes;
	}

	public TimeImpl(Hour hour, Integer minutes) {
		this.hour = hour;
		this.minutes = minutes;
	}

	public static TimeImpl noon() {
		return new TimeImpl(12, 0, TimeAmPm.PM);
	}

	public static TimeImpl midnight() {
		return new TimeImpl(12, 0, TimeAmPm.AM);
	}

	public static TimeImpl max() {
		return new TimeImpl(11, 59, TimeAmPm.PM);
	}

	@Override
	public Hour getHour() {
		return this.hour;
	}

	@Override
	public void setHour(Hour hour) {
		this.hour = hour;
	}

	@Override
	public Integer getMinutes() {
		return this.minutes;
	}

	@Override
    public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}

	// MARK: Comparison
	@Override
	public int compareTo(Time o) {
		int comparison = 0;
		int hourComparison = this.hour.compareTo(o.getHour());

		if (hourComparison == 0) {
			comparison = this.minutes.compareTo(o.getMinutes());
		} else {
			comparison = hourComparison;
		}

		return comparison;
	}

	public boolean isBefore(Time time) {
		return TimeImpl.isBefore(this, time);
	}

	public boolean isAfter(Time time) {
		return TimeImpl.isAfter(this, time);
	}

	/**
	 * Checks if the first time is before the second time.
	 *
	 * @param a
	 * @param b
	 * @return {@code true} if {@code a} is before {@code b}.
	 */
	public static boolean isBefore(Time a,
	                               Time b) {
		return a.compareTo(b) < 0;
	}

	public static boolean isBeforeOrEqual(Time a,
	                                      Time b) {
		return (a.equals(b) || isBefore(a, b));
	}

	/**
	 * Checks if the first time comes after the second time.
	 *
	 * @param a
	 * @param b
	 * @return {@code true} if {@code a} is after {@code b}.
	 */
	public static boolean isAfter(Time a,
	                              Time b) {
		return a.compareTo(b) > 0;
	}

	public static boolean isAfterOrEqual(Time a,
	                                     Time b) {
		return (a.equals(b) || isAfter(a, b));
	}

	@Override
	public boolean equals(Time time) {
		return this.compareTo(time) == 0;
	}

	@Override
	public String toString() {
		return "TimeImpl [hour=" + this.hour + ", minutes=" + this.minutes + "]";
	}

}
