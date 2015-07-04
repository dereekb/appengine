package com.dereekb.gae.model.general.time.util.impl;

import com.dereekb.gae.model.general.time.DaySpan;
import com.dereekb.gae.model.general.time.Time;
import com.dereekb.gae.model.general.time.TimeSpan;
import com.dereekb.gae.model.general.time.WeekTime;
import com.dereekb.gae.model.general.time.impl.TimeSpanImpl;
import com.dereekb.gae.model.general.time.impl.WeekTimeImpl;
import com.dereekb.gae.model.general.time.util.DaySpanConverter;
import com.dereekb.gae.model.general.time.util.TimeValueConverter;
import com.dereekb.gae.model.general.time.util.WeekTimeConverter;
import com.dereekb.gae.utilities.misc.BitAccessor;

/**
 * {@link WeekTimeConverter} implementation.
 *
 * @author dereekb
 */
public class WeekTimeConverterImpl
        implements WeekTimeConverter {

	private final static Integer DAYS_HEX_INDEX = 6;
	private final static Integer DAYS_HEX_MASK = 0xFF000000;

	private final static Integer START_TIME_HEX_INDEX = 3;
	private final static Integer START_TIME_HEX_MASK = 0x00FFF000;

	private final static Integer END_TIME_HEX_INDEX = 0;
	private final static Integer END_TIME_HEX_MASK = 0x00000FFF;

	private final static TimeValueConverter timeConverter = new TimeValueConverterImpl();
	private final static DaySpanConverter daysSpanConverter = new DaySpanConverterImpl();

	@Override
	public Integer weekTimeToNumber(WeekTime weekTime) {
		DaySpan daySpan = weekTime.getDaySpan();
		TimeSpan timeSpan = weekTime.getTimeSpan();
		Time startTime = timeSpan.getStartTime();
		Time endTime = timeSpan.getEndTime();

		Integer dayTimeNumber = daysSpanConverter.daysToNumber(daySpan);
		Integer startTimeNumber = timeConverter.timeToNumber(startTime);
		Integer endTimeNumber = timeConverter.timeToNumber(endTime);

		BitAccessor accessor = new BitAccessor();

		// Days
		accessor = accessor.or(dayTimeNumber.longValue());
		accessor.shiftBytesLeft(2);

		// Start
		accessor = accessor.or(startTimeNumber.longValue());
		accessor.shiftBytesLeft(3);

		// End
		accessor = accessor.or(endTimeNumber.longValue());
		accessor.shiftBytesLeft(3);

		return accessor.getIntegerValue();
	}

	@Override
	public WeekTime weekTimeFromNumber(Integer number) {
		BitAccessor accessor = new BitAccessor(number);
		Long daysValue = accessor.focusValue(DAYS_HEX_MASK, DAYS_HEX_INDEX);
		Long startValue = accessor.focusValue(START_TIME_HEX_MASK, START_TIME_HEX_INDEX);
		Long endValue = accessor.focusValue(END_TIME_HEX_MASK, END_TIME_HEX_INDEX);

		DaySpan daySpan = daysSpanConverter.daysFromNumber(daysValue.intValue());
		Time startTime = timeConverter.timeFromNumber(startValue.intValue());
		Time endTime = timeConverter.timeFromNumber(endValue.intValue());
		TimeSpan timeSpan = new TimeSpanImpl(startTime, endTime);

		WeekTimeImpl weekTime = new WeekTimeImpl(daySpan, timeSpan);
		return weekTime;
	}

}
