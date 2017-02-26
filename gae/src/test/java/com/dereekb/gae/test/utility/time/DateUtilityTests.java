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

}
