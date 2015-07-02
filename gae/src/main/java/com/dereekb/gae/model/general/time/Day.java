package com.dereekb.gae.model.general.time;

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

	Monday(1),
	Tuesday(2),
	Wednesday(3),
	Thursday(4),
	Friday(5),
	Saturday(6),
	Sunday(7);

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

}