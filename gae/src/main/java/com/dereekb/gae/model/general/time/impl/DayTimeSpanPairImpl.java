package com.dereekb.gae.model.general.time.impl;

import java.util.ArrayList;
import java.util.List;

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
		this.setDay(day);
		this.setTimeSpan(timeSpan);
	}

	public static List<DayTimeSpanPair> makePairs(Day day,
	                                              List<TimeSpan> timeSpans) {
		List<DayTimeSpanPair> pairs = new ArrayList<DayTimeSpanPair>();

		for (TimeSpan timeSpan : timeSpans) {
			DayTimeSpanPair pair = new DayTimeSpanPairImpl(day, timeSpan);
			pairs.add(pair);
		}

		return pairs;
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
	public int compareTo(DayTimeSpanPair o) {
		int comparison = this.day.compareTo(o.getDay());

		if (comparison == 0) {
			comparison = this.timeSpan.compareTo(o.getTimeSpan());
		}

		return comparison;
	}

	@Override
	public String toString() {
		return "DayTimeSpanPairImpl [day=" + this.day + ", timeSpan=" + this.timeSpan + "]";
	}


}
