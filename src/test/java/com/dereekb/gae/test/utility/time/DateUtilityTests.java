package com.dereekb.gae.test.utility.time;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.utilities.time.DateUtility;

public class DateUtilityTests {

	@Test
	public void testDateIsAfter() {
		Date date = new Date(5L);
		Date earlier = new Date(2L);

		Assert.assertTrue(DateUtility.dateIsAfterDate(date, earlier, 5L));
	}

	@Test
	public void testDateIsAfterWithPrecision() {

		Long precision = 5000L;	// 5 seconds

		Date reset = new Date(1488075544867L);
		Date issued = new Date(1488075542000L);

		if (DateUtility.dateIsAfterDate(reset, issued, precision)) {
			Assert.fail("FUG");
		}
	}

	@Test
	public void testTimeHasPassed() {
		Long time = 5000L;	// 5 seconds

		Date origin = new Date(0);
		Date now = new Date(time * 2);	// 10 seconds

		Assert.assertTrue(DateUtility.timeHasPassed(now, origin, time));
		Assert.assertTrue(DateUtility.timeHasPassed(new Date(time), origin, time));
		Assert.assertFalse(DateUtility.timeHasPassed(origin, origin, time));
		Assert.assertFalse(DateUtility.timeHasPassed(new Date(time - 5), origin, time));
	}

}
