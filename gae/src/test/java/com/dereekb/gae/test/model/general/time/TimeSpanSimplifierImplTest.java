package com.dereekb.gae.test.model.general.time;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.model.general.time.impl.TimeImpl;
import com.dereekb.gae.model.general.time.impl.TimeSpanImpl;
import com.dereekb.gae.model.general.time.util.impl.TimeSpanSimplifierImpl;

public class TimeSpanSimplifierImplTest {

	private TimeSpanSimplifierImpl impl = new TimeSpanSimplifierImpl();

	@Test
	public void testCanMerge() {

		Assert.assertTrue(this.impl.canMerge(TimeSpanImpl.allDay(), TimeSpanImpl.fromMidnight(TimeImpl.noon())));

	}

}
