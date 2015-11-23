package com.dereekb.gae.model.general.time.impl;

import com.dereekb.gae.model.general.time.Hour;
import com.dereekb.gae.model.general.time.TimeAmPm;

/**
 * Default {@link Hour} implementation.
 *
 * @author dereekb
 *
 */
public class HourImpl
        implements Hour {

	private Integer dayHour;

	public HourImpl(Integer hour) {
		this.setHour(hour);
	}

	public HourImpl(Integer hour, TimeAmPm timeAmPm) {
		this.setHour(hour, timeAmPm);
	}

	public static HourImpl noon() {
		return new HourImpl(Hour.NOON_HOUR);
	}

	public static HourImpl midnight() {
		return new HourImpl(Hour.MIDNIGHT_HOUR);
	}

	@Override
	public TimeAmPm getAmPm() {
		return TimeAmPm.withBoolean(this.dayHour < 12);
    }

	@Override
	public Integer getVisualHour() {
		Integer visualHour = (this.dayHour % 12);

		if (visualHour == 0) {
			visualHour = 12;
		}

		return visualHour;
	}

	@Override
	public Integer getDayHour() {
		return this.dayHour;
	}

	public void setHour(Integer hour,
	                    TimeAmPm timeAmPm) throws IllegalArgumentException {
		if (hour > 12) {
			throw new IllegalArgumentException("Hour cannot be greater than 12.");
		}

		hour = hour % NOON_HOUR;

		if (timeAmPm.equals(TimeAmPm.PM)) {
			hour += 12;
		}

		this.dayHour = hour;
	}

	public void setHour(Integer hour) {
		if (hour < MIDNIGHT_HOUR || hour > MAX_HOUR) {
			throw new IllegalArgumentException("Valid hour ranges from 0-23.");
		}

		this.dayHour = hour;
	}

	@Override
	public int compareTo(Hour o) {
		return this.getDayHour().compareTo(o.getDayHour());
	}

	@Override
	public boolean equals(Hour hour) {
		return (this.compareTo(hour) == 0);
	}

	@Override
	public String toString() {
		return "HourImpl [hour=" + this.dayHour + "]";
	}

}
