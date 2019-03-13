package com.dereekb.gae.model.general.time;

import java.util.Collection;
import java.util.Set;

/**
 * Represents days in a week.
 *
 * @author dereekb
 */
public interface DaySpan {

	public Set<Day> getDays();

	public void setDays(Set<Day> days);

	public void add(Day day);

	public void remove(Day day);

	public boolean contains(Day day);

	public boolean containsAll(Collection<Day> day);

	public boolean equals(DaySpan daySpan);

	public boolean containsAll(DaySpan daySpan);

}
