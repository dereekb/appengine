package com.dereekb.gae.model.general.time.impl;

import com.dereekb.gae.model.general.time.Day;
import com.dereekb.gae.model.general.time.DayTimeSpanPair;
import com.dereekb.gae.model.general.time.Time;
import com.dereekb.gae.model.general.time.TimeSpan;

/**
 * {@link DayTimeSpanPair} implementation.
 *
 * @author dereekb
 *
 */
public class DayTimeSpanPairImpl
        implements DayTimeSpanPair {

	private Day day;
	private TimeSpan timeSpan;

	public DayTimeSpanPairImpl() {}

	public DayTimeSpanPairImpl(Day day, TimeSpan timeSpan) {
		this.day = day;
		this.timeSpan = timeSpan;
	}

	@Override
	public Day getDay() {
		return this.day;
	}

	public void setDay(Day day) {
		this.day = day;
    }

    @Override
    public TimeSpan getTimeSpan() {
		return this.timeSpan;
	}

	public void setTimeSpan(TimeSpan timeSpan) {
		this.timeSpan = timeSpan;
    }

    public boolean isToday() {
		return this.day.isToday();
    }

	public boolean isTime() {
		Time now = TimeImpl.now();
		return this.timeSpan.contains(now);
	}

	@Override
	public boolean isNow() {
		return this.isToday() && this.isTime();
	}

	@Override
	public String toString() {
		return "DayTimeSpanPairImpl [day=" + this.day + ", timeSpan=" + this.timeSpan + "]";
	}

}
