package com.dereekb.gae.test.utility.time;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.dereekb.gae.utilities.time.DateUtility;

/**
 * Used for testing {@link DateUtility}.
 *
 * @author dereekb
 *
 */
public class DateUtilityTests {

	@Test
	public void testDateIsAfter() {
		Date date = new Date(5L);
		Date earlier = new Date(2L);

		assertTrue(DateUtility.dateIsAfterDate(date, earlier, 0L));
	}

	@Test
	public void testDateIsAfterWithPrecision() {

		Long precision = 5000L;	// 5 seconds

		Date reset = new Date(1488075544867L);
		Date issued = new Date(1488075542000L);

		if (DateUtility.dateIsAfterDate(reset, issued, precision)) {
			fail();
		}
	}

	@Test
	public void testTimeHasPassed() {
		Long time = 5000L;	// 5 seconds

		Date origin = new Date(0);
		Date now = new Date(time * 2);	// 10 seconds

		assertTrue(DateUtility.timeHasPassed(now, origin, time));
		assertTrue(DateUtility.timeHasPassed(new Date(time), origin, time));
		assertFalse(DateUtility.timeHasPassed(origin, origin, time));
		assertFalse(DateUtility.timeHasPassed(new Date(time - 5), origin, time));
	}

	@Test
	public void testTodaySecondsDoubleConversions() {
		Date date = new Date();

		Double seconds = DateUtility.dateToSecondsDouble(date);
		Date roundedDate = DateUtility.secondsDoubleToDate(seconds);

		assertTrue(DateUtility.getDifference(date, roundedDate) < 1000);
	}

	@Test
	public void test500YearSecondsDoubleConversions() {
		Date date = DateUtility.getDateInDays(365 * 500L);

		Double seconds = DateUtility.dateToSecondsDouble(date);
		Date roundedDate = DateUtility.secondsDoubleToDate(seconds);

		assertTrue(DateUtility.getDifference(date, roundedDate) < 1000);
	}

	@Test
	public void testTodayMinutesDoubleConversions() {
		Date date = new Date();

		Double minutes = DateUtility.dateToMinutesDouble(date);
		Date roundedDate = DateUtility.minutesDoubleToDate(minutes);

		assertTrue(DateUtility.getDifference(date, roundedDate) < DateUtility.TIME_IN_MINUTE);
	}

	/**
	 * Unique test for testing the Search API's number field can contain a date
	 * number rounded to minutes.
	 */
	@Test
	public void test500YearMinutesDoubleConversionsLessThanIntMax() {
		Date date = DateUtility.getDateInDays(365 * 500L);

		Double minutes = DateUtility.dateToMinutesDouble(date);

		assertTrue(Math.floor(minutes) < Integer.MAX_VALUE);
	}

}
