package com.dereekb.gae.model.general.time.impl;

import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.model.general.time.Day;
import com.dereekb.gae.model.general.time.DaySpan;
import com.dereekb.gae.utilities.misc.bit.impl.LongBitContainer;

/**
 * {@link DaySpan} implementation using {@link LongBitContainer}.
 *
 * @author dereekb
 */
public class DaySpanBitImpl
        implements DaySpan {

	private LongBitContainer container;

	public DaySpanBitImpl() {
		this.container = new LongBitContainer();
	}

	public DaySpanBitImpl(Integer value) {
		this.container = new LongBitContainer(value);
	}

	public DaySpanBitImpl(DaySpan span) throws IllegalArgumentException {
		if (span == null) {
			throw new IllegalArgumentException("DaySpan cannot be null.");
		}

		this.container = new LongBitContainer();
		this.setDays(span.getDays());
	}

	public Integer getDaysNumber() {
		return this.container.getIntegerBits();
	}

	// DaySpan
	@Override
	public Set<Day> getDays() {
		Set<Day> days = new HashSet<Day>();

		for (Day day : Day.ALL_DAYS) {
			if (this.contains(day)) {
				days.add(day);
			}
		}

		return days;
	}

	@Override
	public void setDays(Set<Day> days) {
		this.container.setValue(0L);

		for (Day day : days) {
			this.add(day);
		}
	}

	@Override
	public void add(Day day) {
		this.container.setBit(true, day.bit);
	}

	@Override
	public void remove(Day day) {
		this.container.setBit(false, day.bit);
	}

	@Override
	public boolean contains(Day day) {
		return this.container.getBit(day.bit);
	}

}
