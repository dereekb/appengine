package com.dereekb.gae.model.general.time.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.model.general.time.Day;
import com.dereekb.gae.model.general.time.DaySpan;

/**
 * Default {@link DaySpan} implementation.
 *
 * @author dereekb
 *
 */
public class DaySpanSetImpl
        implements DaySpan {

	private Set<Day> days;

	public DaySpanSetImpl() {
		this.days = new HashSet<Day>();
	}

	public DaySpanSetImpl(Set<Day> days) {
		this.setDays(days);
	}

	@Override
	public Set<Day> getDays() {
		return this.days;
	}

	@Override
	public void setDays(Set<Day> days) {
		if (days == null) {
			days = new HashSet<Day>();
		}

		this.days = days;
	}

	@Override
	public void add(Day day) {
		this.days.add(day);
	}

	@Override
	public void remove(Day day) {
		this.days.remove(day);
	}

	@Override
	public boolean contains(Day day) {
		return this.days.contains(day);
	}

	@Override
	public boolean equals(DaySpan daySpan) {
		Set<Day> comparisonDays = daySpan.getDays();
		return (this.days.size() == comparisonDays.size() && this.days.containsAll(comparisonDays));
	}

	@Override
	public boolean containsAll(DaySpan daySpan) {
		Set<Day> comparisonDays = daySpan.getDays();
		return this.containsAll(comparisonDays);
	}

	@Override
	public boolean containsAll(Collection<Day> comparisonDays) {
		return this.days.containsAll(comparisonDays);
	}

	@Override
	public String toString() {
		return "DaySpanSetImpl [days=" + this.days + "]";
	}

}
