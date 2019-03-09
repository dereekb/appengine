package com.dereekb.gae.model.general.time.util.impl;

import com.dereekb.gae.model.general.time.DaySpan;
import com.dereekb.gae.model.general.time.impl.DaySpanBitImpl;
import com.dereekb.gae.model.general.time.util.DaySpanConverter;

/**
 * {@link DaySpanConverter} implementation.
 *
 * @author dereekb
 *
 */
public class DaySpanConverterImpl
        implements DaySpanConverter {

	@Override
	public Integer daysToNumber(DaySpan span) throws IllegalArgumentException {
		DaySpanBitImpl days = new DaySpanBitImpl(span);
		return days.getDaysNumber();
	}

	@Override
	public DaySpan daysFromNumber(Integer number) throws IllegalArgumentException {
		return new DaySpanBitImpl(number);
	}

}
