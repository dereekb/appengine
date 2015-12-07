package com.dereekb.gae.model.general.time.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.general.time.Day;
import com.dereekb.gae.model.general.time.DaySpan;
import com.dereekb.gae.model.general.time.DayTimeSpanPair;
import com.dereekb.gae.model.general.time.TimeSpan;
import com.dereekb.gae.model.general.time.WeekTime;

/**
 * Default {@link WeekTime} implementation.
 *
 * @author dereekb
 *
 */
public class WeekTimeImpl
        implements WeekTime {

	private DaySpan daySpan;
	private TimeSpan timeSpan;

	public WeekTimeImpl(DaySpan daySpan, TimeSpan timeSpan) {
		this.setDaySpan(daySpan);
		this.setTimeSpan(timeSpan);
	}

	@Override
	public DaySpan getDaySpan() {
		return this.daySpan;
	}

	public void setDaySpan(DaySpan daySpan) {
		if (daySpan == null) {
			throw new IllegalArgumentException("Day span cannot be null.");
		}

		this.daySpan = daySpan;
	}

	@Override
	public TimeSpan getTimeSpan() {
		return this.timeSpan;
	}

	public void setTimeSpan(TimeSpan timeSpan) {
		if (timeSpan == null) {
			throw new IllegalArgumentException("Time span cannot be null.");
		}

		this.timeSpan = timeSpan;
	}

	@Override
	public List<DayTimeSpanPair> toDayTimeSpanPairs() {
		List<DayTimeSpanPair> pairs = new ArrayList<DayTimeSpanPair>(1);

		Set<Day> days = this.daySpan.getDays();
		for (Day day : days) {
			DayTimeSpanPair pair = new DayTimeSpanPairImpl(day, this.timeSpan);
			pairs.add(pair);
		}

		return pairs;
	}

	@Override
	public String toString() {
		return "WeekTimeImpl [daySpan=" + this.daySpan + ", timeSpan=" + this.timeSpan + "]";
	}

}
