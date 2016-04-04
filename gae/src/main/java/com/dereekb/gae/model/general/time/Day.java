package com.dereekb.gae.model.general.time;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Enumeration for days of the week.
 *
 * Each day has a bit following ISO 8601 rules. (Monday 1, Tuesday 2, etc...)
 *
 * @author dereekb
 */
public enum Day {

	MONDAY(1, "Monday", "Mon", "MON"),

	TUESDAY(2, "Tuesday", "Tues", "TUE"),

	WEDNESDAY(3, "Wednesday", "Wed", "WED"),

	THURSDAY(4, "Thursday", "Thur", "THU"),

	FRIDAY(5, "Friday", "Fri", "FRI"),

	SATURDAY(6, "Saturday", "Sat", "SAT"),

	SUNDAY(7, "Sunday", "Sun", "SUN");

	public final int bit;
	public final String name;
	public final String abbreviation;
	public final String key;

	private Day(int bit, String name, String abbreviation, String key) {
		this.bit = bit;
		this.name = name;
		this.abbreviation = abbreviation;
		this.key = key;
	}

	public int getBit() {
		return this.bit;
	}

	public String getName() {
		return this.name;
	}

	public String getAbbreviation() {
		return this.abbreviation;
	}

	public String getKey() {
		return this.key;
	}

	public boolean isToday() {
		Day today = Day.getToday();
		return this.equals(today);
	}

	public static Day getToday() {
		Calendar calendar = Calendar.getInstance();
		int bit = calendar.get(Calendar.DAY_OF_WEEK);
		return Day.dayForBit(bit);
	}

	public static Day dayForBit(int bit) throws IllegalArgumentException {
		if (bit <= 0 || bit >= 8) {
			throw new IllegalArgumentException("Bit must be between 1 and 7");
		}

		return Day.values()[bit - 1];
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

	public static Set<String> getKeyStrings(Iterable<Day> days) {
		Set<String> dayStrings = new HashSet<String>();

		for (Day day : days) {
			dayStrings.add(day.key);
		}

		return dayStrings;
	}

}