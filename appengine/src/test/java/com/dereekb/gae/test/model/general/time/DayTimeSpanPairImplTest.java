package com.dereekb.gae.test.model.general.time;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

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
		assertTrue(a.compareTo(a) == 0); // A is the same as A

		DayTimeSpanPairImpl b = new DayTimeSpanPairImpl(Day.MONDAY, aTimes);
		assertTrue(a.compareTo(b) > 0); // A is after B

		DayTimeSpanPairImpl c = new DayTimeSpanPairImpl(Day.WEDNESDAY, aTimes);
		assertTrue(a.compareTo(c) < 0); // A is before C

	}

}
