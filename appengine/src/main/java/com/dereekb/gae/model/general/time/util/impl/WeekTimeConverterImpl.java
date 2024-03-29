package com.dereekb.gae.model.general.time.util.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.general.time.DaySpan;
import com.dereekb.gae.model.general.time.Time;
import com.dereekb.gae.model.general.time.TimeSpan;
import com.dereekb.gae.model.general.time.WeekTime;
import com.dereekb.gae.model.general.time.impl.TimeSpanImpl;
import com.dereekb.gae.model.general.time.impl.WeekTimeImpl;
import com.dereekb.gae.model.general.time.util.DaySpanConverter;
import com.dereekb.gae.model.general.time.util.TimeValueConverter;
import com.dereekb.gae.model.general.time.util.WeekTimeConverter;
import com.dereekb.gae.utilities.misc.bit.BitIndex;
import com.dereekb.gae.utilities.misc.bit.impl.LongBitContainer;

/**
 * {@link WeekTimeConverter} implementation.
 *
 * @author dereekb
 */
public class WeekTimeConverterImpl
        implements WeekTimeConverter {

	private final static Integer DAYS_BIT_INDEX = BitIndex.hexIndex(6);
	private final static Long DAYS_HEX_MASK = 0xFF000000L;

	private final static Integer START_TIME_BIT_INDEX = BitIndex.hexIndex(3);
	private final static Long START_TIME_HEX_MASK = 0x00FFF000L;

	private final static Integer END_TIME_BIT_INDEX = 0;
	private final static Long END_TIME_HEX_MASK = 0x00000FFFL;

	private final static TimeValueConverter timeConverter = new TimeValueConverterImpl();
	private final static DaySpanConverter daysSpanConverter = new DaySpanConverterImpl();

	public final static WeekTimeConverterImpl CONVERTER = new WeekTimeConverterImpl();

	public List<WeekTime> weekTimesFromNumbers(Iterable<Integer> encodedTimes) {
		List<WeekTime> weekTimes = new ArrayList<WeekTime>();

		for (Integer encodedTime : encodedTimes) {
			weekTimes.add(this.weekTimeFromNumber(encodedTime));
		}

		return weekTimes;
	}

	// MARK: WeekTimeConverter
	@Override
	public Integer weekTimeToNumber(WeekTime weekTime) {
		DaySpan daySpan = weekTime.getDaySpan();
		TimeSpan timeSpan = weekTime.getTimeSpan();
		Time startTime = timeSpan.getStartTime();
		Time endTime = timeSpan.getEndTime();

		Integer dayTimeNumber = daysSpanConverter.daysToNumber(daySpan);
		Integer startTimeNumber = timeConverter.timeToNumber(startTime);
		Integer endTimeNumber = timeConverter.timeToNumber(endTime);

		LongBitContainer container = new LongBitContainer();

		// Days
		container.applyOr(LongBitContainer.bitsFromInteger(dayTimeNumber));

		// Start
		container.bitShiftLeft(BitIndex.hexIndex(3));
		container.applyOr(LongBitContainer.bitsFromInteger(startTimeNumber));

		// End
		container.bitShiftLeft(BitIndex.hexIndex(3));
		container.applyOr(LongBitContainer.bitsFromInteger(endTimeNumber));

		return container.getIntegerBits();
	}

	@Override
	public WeekTime weekTimeFromNumber(Integer number) {
		LongBitContainer container = new LongBitContainer(number);
		Long daysValue = container.focusBitsWithMask(DAYS_HEX_MASK, DAYS_BIT_INDEX);
		Long startValue = container.focusBitsWithMask(START_TIME_HEX_MASK, START_TIME_BIT_INDEX);
		Long endValue = container.focusBitsWithMask(END_TIME_HEX_MASK, END_TIME_BIT_INDEX);

		DaySpan daySpan = daysSpanConverter.daysFromNumber(daysValue.intValue());
		Time startTime = timeConverter.timeFromNumber(startValue.intValue());
		Time endTime = timeConverter.timeFromNumber(endValue.intValue());
		TimeSpan timeSpan = new TimeSpanImpl(startTime, endTime);

		WeekTimeImpl weekTime = new WeekTimeImpl(daySpan, timeSpan);
		return weekTime;
	}

}
