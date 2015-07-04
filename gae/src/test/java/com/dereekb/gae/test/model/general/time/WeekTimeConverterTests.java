package com.dereekb.gae.test.model.general.time;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.model.general.time.Time;
import com.dereekb.gae.model.general.time.TimeAmPm;
import com.dereekb.gae.model.general.time.TimeSpan;
import com.dereekb.gae.model.general.time.impl.DaySpanBitImpl;
import com.dereekb.gae.model.general.time.impl.TimeImpl;
import com.dereekb.gae.model.general.time.impl.TimeSpanImpl;
import com.dereekb.gae.model.general.time.impl.WeekTimeImpl;
import com.dereekb.gae.model.general.time.util.impl.WeekTimeConverterImpl;
import com.dereekb.gae.utilities.misc.BitAccessor;


public class WeekTimeConverterTests {

	@Test
	public void testWeekTimeConversion() {
		WeekTimeConverterImpl converter = new WeekTimeConverterImpl();

		DaySpanBitImpl daySpan = new DaySpanBitImpl();

		Time startTime = new TimeImpl(12, 0, TimeAmPm.AM);	//Midnight
		Time endTime = new TimeImpl(12, 0, TimeAmPm.PM);	//Noon

		TimeSpan timeSpan = new TimeSpanImpl(startTime, endTime);
		WeekTimeImpl weekTime = new WeekTimeImpl(daySpan, timeSpan);

		Integer weekTimeNumber = converter.weekTimeToNumber(weekTime);
		Assert.assertNotNull(weekTimeNumber);

		// Week Time Number
		System.out.println("Week Time Number: " + Integer.toHexString(weekTimeNumber));

		BitAccessor validator = new BitAccessor(weekTimeNumber);

		// Week Time Number
		System.out.println("Validator: " + validator.getValue() + " , " + Long.toHexString(validator.getValue()));

		Integer daySpanNumber = daySpan.getDaysNumber();
		Long daySpanFocus = validator.focusValue(0xFF000000, 6);

		// Focus (Should be d0)
		System.out.println("Focus: " + Long.toHexString(daySpanFocus));

		// Number
		System.out.println("Day Span: " + Integer.toHexString(daySpanNumber));
		Assert.assertTrue(daySpanFocus.equals(daySpanNumber));

		System.out.println(Integer.toHexString(weekTimeNumber));
		System.out.println(weekTimeNumber);
	}

}
