package com.dereekb.gae.test.model.general.time;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.model.general.time.Day;
import com.dereekb.gae.model.general.time.DaySpan;
import com.dereekb.gae.model.general.time.DayTimeSpanPair;
import com.dereekb.gae.model.general.time.TimeSpan;
import com.dereekb.gae.model.general.time.WeekTime;
import com.dereekb.gae.model.general.time.impl.DaySpanBitImpl;
import com.dereekb.gae.model.general.time.impl.TimeImpl;
import com.dereekb.gae.model.general.time.impl.TimeSpanImpl;
import com.dereekb.gae.model.general.time.impl.WeekSpanImpl;
import com.dereekb.gae.model.general.time.impl.WeekTimeImpl;

public class WeekSpanImplTest {

	@Test
	public void testWeekSpanAddAndRemove() {
		WeekSpanImpl impl = new WeekSpanImpl();

		DaySpan aDays = new DaySpanBitImpl(Day.MONDAY);
		TimeSpan aTimes = new TimeSpanImpl(TimeImpl.midnight(), TimeImpl.noon());
		WeekTime a = new WeekTimeImpl(aDays, aTimes);

		impl.add(a);
		assertTrue(impl.contains(a));

		DaySpan bDays = new DaySpanBitImpl(Day.MONDAY);
		TimeSpan bTimes = new TimeSpanImpl(TimeImpl.noon(), TimeImpl.max());
		WeekTime b = new WeekTimeImpl(bDays, bTimes);

		impl.add(b);
		assertTrue(impl.contains(b));

		impl.remove(a);
		assertFalse(impl.contains(a));

		impl.remove(b);
		assertFalse(impl.contains(b));
	}

	@Test
	public void testWeekSpanPairConversion() {
		WeekSpanImpl impl = new WeekSpanImpl();

		DaySpan aDays = new DaySpanBitImpl(Day.MONDAY);
		TimeSpan aTimes = new TimeSpanImpl(TimeImpl.midnight(), TimeImpl.noon());
		WeekTime a = new WeekTimeImpl(aDays, aTimes);

		impl.add(a);
		assertTrue(impl.contains(a));

		List<DayTimeSpanPair> weekSpanPairs = impl.toDayTimeSpanPairs();
		List<DayTimeSpanPair> weekTimePairs = a.toDayTimeSpanPairs();

		assertTrue(weekSpanPairs.size() == weekTimePairs.size());


	}

}
