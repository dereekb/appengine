package com.dereekb.gae.test.model.general.time;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.model.general.time.Day;
import com.dereekb.gae.model.general.time.Time;
import com.dereekb.gae.model.general.time.TimeAmPm;
import com.dereekb.gae.model.general.time.TimeSpan;
import com.dereekb.gae.model.general.time.impl.DaySpanBitImpl;
import com.dereekb.gae.model.general.time.impl.TimeImpl;
import com.dereekb.gae.model.general.time.impl.TimeSpanImpl;
import com.dereekb.gae.model.general.time.impl.WeekTimeImpl;
import com.dereekb.gae.model.general.time.util.impl.TimeValueConverterImpl;
import com.dereekb.gae.model.general.time.util.impl.WeekTimeConverterImpl;
import com.dereekb.gae.utilities.misc.bit.BitIndex;
import com.dereekb.gae.utilities.misc.bit.impl.LongBitContainer;


public class WeekTimeConverterTest {

	@Test
	public void testWeekTimeConversion() {
		WeekTimeConverterImpl converter = new WeekTimeConverterImpl();
		TimeValueConverterImpl timeConverter = new TimeValueConverterImpl();

		DaySpanBitImpl daySpan = new DaySpanBitImpl();
		daySpan.add(Day.FRIDAY);

		Time startTime = new TimeImpl(12, 30, TimeAmPm.AM); // ~Midnight
		Time endTime = new TimeImpl(12, 30, TimeAmPm.PM); // ~Noon

		TimeSpan timeSpan = new TimeSpanImpl(startTime, endTime);
		WeekTimeImpl weekTime = new WeekTimeImpl(daySpan, timeSpan);

		Integer weekTimeNumber = converter.weekTimeToNumber(weekTime);
		Assert.assertNotNull(weekTimeNumber);

		// Week Time Number
		System.out.println("Week Time Number: " + Integer.toHexString(weekTimeNumber));
		LongBitContainer validator = new LongBitContainer(weekTimeNumber);

		// Week Time Number
		System.out.println("Validator: " + validator.getValue() + " , " + Long.toHexString(validator.getValue()));

		Integer daySpanNumber = daySpan.getDaysNumber();
		Long daySpanFocus = validator.focusBits(0xFF000000L, BitIndex.hexIndex(6));
		Assert.assertTrue(daySpanFocus.equals(daySpanNumber.longValue()));

		Long startTimeFocus = validator.focusBits(0x00FFF000L, BitIndex.hexIndex(3));
		Assert.assertTrue(timeConverter.timeToNumber(startTime).equals(startTimeFocus.intValue()));

		Long endTimeFocus = validator.focusBits(0x00000FFFL, BitIndex.hexIndex(0));
		Assert.assertTrue(timeConverter.timeToNumber(endTime).equals(endTimeFocus.intValue()));

		/*
		// Focus (Should be d0)
		System.out.println("Focus: " + Long.toHexString(daySpanFocus));
		System.out.println("Focus: " + Long.toHexString(startTimeFocus));
		System.out.println("Focus: " + Long.toHexString(endTimeFocus));

		// Number
		System.out.println("Day Span: " + Integer.toHexString(daySpanNumber));
		 */
	}

}
