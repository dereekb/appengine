package com.dereekb.gae.model.general.time.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.general.time.Day;
import com.dereekb.gae.model.general.time.DaySpan;
import com.dereekb.gae.model.general.time.DayTimeSpanPair;
import com.dereekb.gae.model.general.time.TimeSpan;
import com.dereekb.gae.model.general.time.TimeSpanSet;
import com.dereekb.gae.model.general.time.WeekSpan;
import com.dereekb.gae.model.general.time.WeekTime;

/**
 * {@link WeekSpan} implementation.
 *
 * @author dereekb
 *
 */
public class WeekSpanImpl
        implements WeekSpan {

	private TimeSpanSet[] sets;

	public WeekSpanImpl() {
		this.sets = makeSetList();
	}

	public WeekSpanImpl(List<WeekTime> weekTimes) {
		this();

		if (weekTimes != null) {
			this.addAll(weekTimes);
		}
	}

	private static TimeSpanSet[] makeSetList() {
		TimeSpanSet[] sets = new TimeSpanSet[Day.values().length];

		for (Day day : Day.values()) {
			sets[day.bit - 1] = new TimeSpanSetImpl();
		}

		return sets;
	}

	private TimeSpanSet getSetForDay(Day day) {
		return this.sets[day.bit - 1];
	}

	// MARK: WeekSpan
	@Override
	public void add(WeekTime time) {
		DaySpan daySpan = time.getDaySpan();
		TimeSpan timeSpan = time.getTimeSpan();

		for (Day day : daySpan.getDays()) {
			TimeSpanSet set = this.getSetForDay(day);
			set.add(timeSpan);
		}
	}

	public void addAll(List<WeekTime> weekTimes) {
		for (WeekTime time : weekTimes) {
			this.add(time);
		}
	}

	@Override
	public void remove(WeekTime time) {
		DaySpan daySpan = time.getDaySpan();
		TimeSpan timeSpan = time.getTimeSpan();

		for (Day day : daySpan.getDays()) {
			TimeSpanSet set = this.getSetForDay(day);
			set.remove(timeSpan);
		}
	}

	public void removeAll(List<WeekTime> weekTimes) {
		for (WeekTime time : weekTimes) {
			this.remove(time);
		}
	}

	@Override
	public boolean contains(WeekTime time) {
		boolean isContained = true;

		TimeSpan timeSpan = time.getTimeSpan();
		DaySpan daySpan = time.getDaySpan();
		Set<Day> days = daySpan.getDays();

		for (Day day : days) {
			TimeSpanSet set = this.getSetForDay(day);
			if (set.contains(timeSpan) == false) {
				isContained = false;
				break;
			}
		}

		return isContained;
	}

	// MARK: DayTimeSpanPairSetConvertable
	@Override
	public List<DayTimeSpanPair> toDayTimeSpanPairs() {
		List<DayTimeSpanPair> pairs = new ArrayList<DayTimeSpanPair>();

		for (Day day : Day.values()) {
			TimeSpanSet set = this.getSetForDay(day);
			List<TimeSpan> timeSpans = set.getTimeSpans();

			if (timeSpans.isEmpty() == false) {
				List<DayTimeSpanPair> setPairs = DayTimeSpanPairImpl.makePairs(day, timeSpans);
				pairs.addAll(setPairs);
			}
		}

		return pairs;
	}

	@Override
	public String toString() {
		return "WeekSpanImpl [sets=" + Arrays.toString(this.sets) + "]";
	}

}
