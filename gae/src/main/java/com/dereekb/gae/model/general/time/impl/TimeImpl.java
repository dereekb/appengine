package com.dereekb.gae.model.general.time.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

	public TimeImpl() {
		this(0, 0);
	}

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

	public static TimeImpl now() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		return new TimeImpl(hour, minute);
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

	public static List<Integer> getMilitaryHoursBetween(Time a,
	                                                    Time b) {
		return getMilitaryHoursBetween(a, b, null);
	}

	/**
	 * Gets the numbers of the military hours between two {@link Time} values.
	 *
	 * @param a
	 *            {@link Time}. Never {@code null}.
	 * @param b
	 *            {@link Time}. Never {@code null}.
	 * @param threshold
	 *            {@link Integer} threshold for rounding hours up. {@code null}
	 *            if no threshold.
	 * @return {@link List} of integers corresponding to the hours between the
	 *         times.
	 */
	public static List<Integer> getMilitaryHoursBetween(Time a,
	                                                    Time b,
	                                                    Integer threshold) {
		int aHour = a.getHour().getDayHour();
		int bHour = b.getHour().getDayHour();

		if (threshold != null) {
			// Excludes threshold time.
			if (a.getMinutes() > threshold) {
				aHour += 1;
			}

			// Includes threshold time.
			if (b.getMinutes() >= threshold) {
				bHour += 1;
			}
		}

		List<Integer> hours = new ArrayList<Integer>();

		// Does not include the "b" hour.
		for (int i = aHour; i < bHour; i += 1) {
			hours.add(i);
		}

		return hours;
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
