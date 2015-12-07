package com.dereekb.gae.test.model.general.time;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.model.general.time.Day;
import com.dereekb.gae.model.general.time.TimeSpan;
import com.dereekb.gae.model.general.time.impl.DayTimeSpanPairImpl;
import com.dereekb.gae.model.general.time.impl.TimeImpl;
import com.dereekb.gae.model.general.time.impl.TimeSpanImpl;


public class DayTimeSpanPairImplTest {

	@Test
	public void testComparisons() {

		TimeSpan aTimes = new TimeSpanImpl(TimeImpl.midnight(), TimeImpl.noon());

		DayTimeSpanPairImpl a = new DayTimeSpanPairImpl(Day.TUESDAY, aTimes);
		Assert.assertTrue(a.compareTo(a) == 0); // A is the same as A

		DayTimeSpanPairImpl b = new DayTimeSpanPairImpl(Day.MONDAY, aTimes);
		Assert.assertTrue(a.compareTo(b) > 0); // A is after B

		DayTimeSpanPairImpl c = new DayTimeSpanPairImpl(Day.WEDNESDAY, aTimes);
		Assert.assertTrue(a.compareTo(c) < 0); // A is before C

	}

}
