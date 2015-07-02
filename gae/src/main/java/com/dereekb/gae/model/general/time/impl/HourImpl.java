package com.dereekb.gae.model.general.time.impl;

import com.dereekb.gae.model.general.time.Hour;
import com.dereekb.gae.model.general.time.TimeAmPm;

/**
 * {@link Hour} implementation.
 *
 * @author dereekb
 *
 */
public class HourImpl
        implements Hour {

	private static final Integer MIDNIGHT = 0;
	private static final Integer NOON = 12;
	private static final Integer MAX_HOUR = 23;

	private Integer hour;

	public HourImpl(Integer hour) {
		this.setHour(hour);
	}

	public HourImpl(Integer hour, TimeAmPm timeAmPm) {
		this.setHour(hour, timeAmPm);
	}

	@Override
	public TimeAmPm getAmPm() {
		return TimeAmPm.withBoolean(this.hour >= 12);
    }

	@Override
	public Integer getHour() {
		return this.hour % 12;
	}

	public void setHour(Integer hour,
	                    TimeAmPm timeAmPm) throws IllegalArgumentException {
		if (hour > 12) {
			throw new IllegalArgumentException("Hour cannot be greater than 12.");
		}

		hour = hour % NOON;

		if (timeAmPm.equals(TimeAmPm.PM)) {
			hour += 12;
		}

		this.hour = hour;
	}

	public void setHour(Integer hour) {
		if (hour < MIDNIGHT || hour > MAX_HOUR) {
			throw new IllegalArgumentException("Valid hour ranges from 0-23.");
		}

		this.hour = hour;
	}

	@Override
	public String toString() {
		return "HourImpl [hour=" + this.hour + "]";
	}

}
