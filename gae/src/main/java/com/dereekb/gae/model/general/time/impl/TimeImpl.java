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

	@Override
	public String toString() {
		return "TimeImpl [hour=" + this.hour + ", minutes=" + this.minutes + "]";
	}

}
