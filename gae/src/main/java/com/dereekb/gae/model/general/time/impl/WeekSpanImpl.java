package com.dereekb.gae.model.general.time.impl;

import java.util.Set;

import com.dereekb.gae.model.general.time.Day;
import com.dereekb.gae.model.general.time.DaySpan;
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

	private static TimeSpanSet[] makeSetList() {
		TimeSpanSet[] sets = new TimeSpanSet[Day.values().length];

		for (Day day : Day.values()) {
			sets[day.bit] = new TimeSpanSetImpl();
		}

		return sets;
	}

	private TimeSpanSet getSetForDay(Day day) {
		return this.sets[day.bit];
	}

	// MARK: WeekSpan
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

	@Override
	public void add(WeekTime time) {
		DaySpan daySpan = time.getDaySpan();
		TimeSpan timeSpan = time.getTimeSpan();

		for (Day day : daySpan.getDays()) {
			TimeSpanSet set = this.getSetForDay(day);
			set.add(timeSpan);
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

}
