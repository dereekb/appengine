package com.dereekb.gae.test.model.general.time;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.model.general.time.Day;
import com.dereekb.gae.model.general.time.DaySpan;
import com.dereekb.gae.model.general.time.Time;
import com.dereekb.gae.model.general.time.TimeAmPm;
import com.dereekb.gae.model.general.time.TimeSpan;
import com.dereekb.gae.model.general.time.WeekTime;
import com.dereekb.gae.model.general.time.impl.DaySpanBitImpl;
import com.dereekb.gae.model.general.time.impl.HourImpl;
import com.dereekb.gae.model.general.time.impl.TimeImpl;
import com.dereekb.gae.model.general.time.impl.TimeSpanImpl;
import com.dereekb.gae.model.general.time.impl.WeekTimeImpl;
import com.dereekb.gae.model.general.time.search.WeekTimeSearchTagsConverterImpl;
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

		// WeekSpan Time Number
		System.out.println("WeekSpan Time Number: " + Integer.toHexString(weekTimeNumber));
		LongBitContainer validator = new LongBitContainer(weekTimeNumber);

		// WeekSpan Time Number
		System.out.println("ContentValidator: " + validator.getValue() + " , " + Long.toHexString(validator.getValue()));

		Integer daySpanNumber = daySpan.getDaysNumber();
		Long daySpanFocus = validator.focusBitsWithMask(0xFF000000L, BitIndex.hexIndex(6));
		Assert.assertTrue(daySpanFocus.equals(daySpanNumber.longValue()));

		Long startTimeFocus = validator.focusBitsWithMask(0x00FFF000L, BitIndex.hexIndex(3));
		Assert.assertTrue(timeConverter.timeToNumber(startTime).equals(startTimeFocus.intValue()));

		Long endTimeFocus = validator.focusBitsWithMask(0x00000FFFL, BitIndex.hexIndex(0));
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

	@Test
	public void testWeekTimeConversionReversal() {
		WeekTimeConverterImpl converter = WeekTimeConverterImpl.CONVERTER;

		DaySpanBitImpl daySpan = new DaySpanBitImpl();
		daySpan.add(Day.FRIDAY);

		Time startTime = new TimeImpl(12, 30, TimeAmPm.AM); // ~Midnight
		Time endTime = new TimeImpl(12, 30, TimeAmPm.PM); // ~Noon

		TimeSpan timeSpan = new TimeSpanImpl(startTime, endTime);
		WeekTimeImpl weekTime = new WeekTimeImpl(daySpan, timeSpan);

		Integer weekTimeNumber = converter.weekTimeToNumber(weekTime);
		Assert.assertNotNull(weekTimeNumber);

		WeekTime convertedWeekTime = converter.weekTimeFromNumber(weekTimeNumber);
		DaySpan convertedDaySpan = convertedWeekTime.getDaySpan();
		TimeSpan convertedTimeSpan = convertedWeekTime.getTimeSpan();

		Assert.assertTrue(convertedDaySpan.equals(daySpan));
		Assert.assertTrue(convertedTimeSpan.equals(timeSpan));
	}

	@Test
	public void testTagsConversion() {
		WeekTimeSearchTagsConverterImpl converter = WeekTimeSearchTagsConverterImpl.CONVERTER;

		Day day = Day.MONDAY;
		DaySpanBitImpl daySpan = new DaySpanBitImpl();
		daySpan.add(day);

		Time startTime = new TimeImpl(0, 0);
		Time endTime = new TimeImpl(23, 59);

		TimeSpan timeSpan = new TimeSpanImpl(startTime, endTime);
		WeekTimeImpl weekTime = new WeekTimeImpl(daySpan, timeSpan);

		Set<String> tags = converter.buildTags(weekTime);
		Assert.assertNotNull(tags);
		Assert.assertTrue(tags.contains(converter.buildTag(day, new HourImpl(23))));

		// Multiple Days
		daySpan = new DaySpanBitImpl(0xFF);
		converter.buildTags(weekTime);
		Assert.assertNotNull(tags);
		Assert.assertTrue(tags.size() == 24); // 23 hour entries, 0-

		// Encoded
		tags = converter.buildTagsForEncoded(WeekTimeConverterImpl.CONVERTER.weekTimeToNumber(weekTime));
		Assert.assertNotNull(tags);

	}

}
