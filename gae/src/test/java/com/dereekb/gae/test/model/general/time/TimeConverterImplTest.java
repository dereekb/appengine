package com.dereekb.gae.test.model.general.time;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.model.general.time.Hour;
import com.dereekb.gae.model.general.time.Time;
import com.dereekb.gae.model.general.time.TimeAmPm;
import com.dereekb.gae.model.general.time.TimeSpan;
import com.dereekb.gae.model.general.time.impl.TimeImpl;
import com.dereekb.gae.model.general.time.impl.TimeSpanImpl;
import com.dereekb.gae.model.general.time.util.TimeStringConverter;
import com.dereekb.gae.model.general.time.util.TimeValueConverter;
import com.dereekb.gae.model.general.time.util.impl.TimeStringConverterImpl;
import com.dereekb.gae.model.general.time.util.impl.TimeValueConverterImpl;


/**
 * {@link TimeValueConverter} tests.
 *
 * @author dereekb
 *
 */
public class TimeConverterImplTest {

	private final static TimeStringConverter stringConverter = new TimeStringConverterImpl();
	private final static TimeValueConverterImpl valueConverter = new TimeValueConverterImpl();

	@Test
	public void testNumberMidnightConversion() {
		Integer number = 0; // Midnight

		TimeAmPm expectedAmPm = TimeAmPm.AM;
		Integer expectedVisualHour = 12;
		Integer expectedDayHour = 0;
		Integer expectedMinute = 0;

		Time time = valueConverter.timeFromNumber(number);
		Hour hour = time.getHour();

		TimeAmPm amPm = hour.getAmPm();
		Assert.assertTrue(amPm.equals(expectedAmPm));
		Assert.assertTrue(hour.getVisualHour().equals(expectedVisualHour));
		Assert.assertTrue(hour.getDayHour().equals(expectedDayHour));
		Assert.assertTrue(time.getMinutes().equals(expectedMinute));
	}

	@Test
	public void testNumberNoonConversion() {
		Integer number = 12 * Time.MINUTES_IN_HOUR + 30; // Noon

		TimeAmPm expectedAmPm = TimeAmPm.PM;
		Integer expectedVisualHour = 12;
		Integer expectedDayHour = 12;
		Integer expectedMinute = 30;

		Time time = valueConverter.timeFromNumber(number);
		Hour hour = time.getHour();

		TimeAmPm amPm = hour.getAmPm();
		Assert.assertTrue(amPm.equals(expectedAmPm));
		Assert.assertTrue(hour.getVisualHour().equals(expectedVisualHour));
		Assert.assertTrue(hour.getDayHour().equals(expectedDayHour));
		Assert.assertTrue(time.getMinutes().equals(expectedMinute));
	}

	@Test
	public void testStringConversion() {
		TimeSpan timeSpan = TimeSpanImpl.toMidnight(TimeImpl.noon());
		String string = stringConverter.convertToString(timeSpan);

		Assert.assertNotNull(string);
	}

}
