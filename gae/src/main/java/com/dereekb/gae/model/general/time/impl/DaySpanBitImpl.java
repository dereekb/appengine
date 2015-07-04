package com.dereekb.gae.model.general.time.impl;

import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.model.general.time.Day;
import com.dereekb.gae.model.general.time.DaySpan;
import com.dereekb.gae.utilities.misc.BitAccessor;

/**
 * {@link DaySpan} implementation using {@link BitAccessor}.
 *
 * @author dereekb
 */
public class DaySpanBitImpl
        implements DaySpan {

	private BitAccessor accessor;

	public DaySpanBitImpl() {
		this.accessor = new BitAccessor();
	}

	public DaySpanBitImpl(Integer value) {
		this.accessor = new BitAccessor(value);
	}

	public DaySpanBitImpl(DaySpan span) throws IllegalArgumentException {
		if (span == null) {
			throw new IllegalArgumentException("DaySpan cannot be null.");
		}

		this.accessor = new BitAccessor();
		this.setDays(span.getDays());
	}

	public Integer getDaysNumber() {
		return this.accessor.getIntegerValue();
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
		this.accessor.setValue(0);

		for (Day day : days) {
			this.add(day);
		}
	}

	@Override
	public void add(Day day) {
		this.accessor.writeBit(true, day.bit);
	}

	@Override
	public void remove(Day day) {
		this.accessor.writeBit(false, day.bit);
	}

	@Override
	public boolean contains(Day day) {
		return this.accessor.readBit(day.bit);
	}

}
