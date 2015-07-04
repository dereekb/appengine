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

		hour = hour % NOON;

		if (timeAmPm.equals(TimeAmPm.PM)) {
			hour += 12;
		}

		this.dayHour = hour;
	}

	public void setHour(Integer hour) {
		if (hour < MIDNIGHT || hour > MAX_HOUR) {
			throw new IllegalArgumentException("Valid hour ranges from 0-23.");
		}

		this.dayHour = hour;
	}

	@Override
	public String toString() {
		return "HourImpl [hour=" + this.dayHour + "]";
	}

}
