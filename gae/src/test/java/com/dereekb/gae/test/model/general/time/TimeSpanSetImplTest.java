package com.dereekb.gae.test.model.general.time;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.model.general.time.Time;
import com.dereekb.gae.model.general.time.TimeAmPm;
import com.dereekb.gae.model.general.time.TimeSpan;
import com.dereekb.gae.model.general.time.impl.TimeImpl;
import com.dereekb.gae.model.general.time.impl.TimeSpanImpl;
import com.dereekb.gae.model.general.time.impl.TimeSpanSetImpl;


public class TimeSpanSetImplTest {

	@Test
	public void testAddingSameTime() {

		TimeSpanSetImpl set = new TimeSpanSetImpl();

		Time startA = TimeImpl.midnight();
		Time endA = TimeImpl.noon();
		TimeSpan spanA = new TimeSpanImpl(startA, endA);

		set.add(spanA);
		set.add(spanA);

		Assert.assertTrue(set.contains(spanA));
	}

	@Test
	public void testAddingTimes() {

		TimeSpanSetImpl set = new TimeSpanSetImpl();

		Time startA = TimeImpl.midnight();
		Time endA = new TimeImpl(2, 30, TimeAmPm.AM);
		TimeSpan spanA = new TimeSpanImpl(startA, endA);

		Time startB = TimeImpl.noon();
		Time endB = new TimeImpl(2, 30, TimeAmPm.PM);
		TimeSpan spanB = new TimeSpanImpl(startB, endB);

		Time startC = new TimeImpl(4, 30, TimeAmPm.PM);
		Time endC = TimeImpl.max();
		TimeSpan spanC = new TimeSpanImpl(startC, endC);

		set.add(spanA);
		set.add(spanB);
		set.add(spanC);

		Assert.assertTrue(set.contains(spanA));
		Assert.assertTrue(set.contains(spanB));
		Assert.assertTrue(set.contains(spanC));
		Assert.assertTrue(set.getTimeSpans().size() == 3);

		set.add(TimeSpanImpl.allDay());
		Assert.assertTrue(set.getTimeSpans().size() == 1);

		Assert.assertTrue(set.contains(spanA));
		Assert.assertTrue(set.contains(spanB));
		Assert.assertTrue(set.contains(spanC));
	}

}
