package com.dereekb.gae.model.general.time;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Enumeration for days of the week.
 *
 * Each day has a bit following ISO 8601 rules. (Monday 1, Tuesday 2, etc...)
 *
 * @author dereekb
 */
public enum Day {

	MONDAY(1),

	TUESDAY(2),

	WEDNESDAY(3),

	THURSDAY(4),

	FRIDAY(5),

	SATURDAY(6),

	SUNDAY(7);

	public static final List<Day> ALL_DAYS = Day.getAllDays();

	public final int bit;

	private Day(int bit) {
		this.bit = bit;
	}

	public int getBit() {
		return this.bit;
	}

	@Override
	public String toString() {
		return this.name();
	}

	public static Set<String> getDaysAsStrings(Iterable<Day> days) {
		Set<String> dayStrings = new HashSet<String>();

		for (Day day : days) {
			dayStrings.add(day.toString());
		}

		return dayStrings;
	}

	private static List<Day> getAllDays() {
		List<Day> days = new ArrayList<Day>();

		days.add(Day.MONDAY);
		days.add(Day.TUESDAY);
		days.add(Day.WEDNESDAY);
		days.add(Day.THURSDAY);
		days.add(Day.FRIDAY);
		days.add(Day.SATURDAY);
		days.add(Day.SUNDAY);

		return days;
    }

}