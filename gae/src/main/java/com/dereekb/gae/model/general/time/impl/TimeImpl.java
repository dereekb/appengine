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

	@Override
	public String toString() {
		return "TimeImpl [hour=" + this.hour + ", minutes=" + this.minutes + "]";
	}

}
