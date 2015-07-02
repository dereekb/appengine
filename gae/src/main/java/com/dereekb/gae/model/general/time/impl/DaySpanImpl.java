package com.dereekb.gae.model.general.time.impl;

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
public class DaySpanImpl
        implements DaySpan {

	private Set<Day> days;

	public DaySpanImpl() {
		this.days = new HashSet<Day>();
	}

	public DaySpanImpl(Set<Day> days) {
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
	public String toString() {
		return "DaySpanImpl [days=" + this.days + "]";
	}

}
